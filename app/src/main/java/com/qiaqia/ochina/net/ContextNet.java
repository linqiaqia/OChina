package com.qiaqia.ochina.net;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.qiaqia.app.BroadcastController;
import com.qiaqia.ochina.net.bean.Base;
import com.qiaqia.ochina.net.bean.News;
import com.qiaqia.ochina.net.bean.NewsList;
import com.qiaqia.ochina.net.bean.Notice;
import com.qiaqia.ochina.net.bean.URLs;
import com.qiaqia.ochina.net.common.StringUtils;
import com.qiaqia.ochina.net.http.ApiHttp;
import com.qiaqia.ochina.net.http.AppException;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by 洽洽 on 2016/1/23.
 */
public class ContextNet extends Application {
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static final int PAGE_SIZE = 20;//默认分页大小
    private static final int CACHE_TIME = 60 * 60000;//缓存失效时间

    static final String PROP_KEY_UID = "user.uid";
    static final String PROP_KEY_NAME = "user.name";
    static final String PROP_KEY_FACE = "user.face";
    static final String PROP_KEY_ACCOUNT = "user.account";
    static final String PROP_KEY_PASSWORD = "user.pwd";
    static final String PROP_KEY_LOCATION = "user.location";
    static final String PROP_KEY_FOLLOWERS = "user.followers";
    static final String PROP_KEY_FANS = "user.fans";
    static final String PROP_KEY_SCORE = "user.score";
    static final String PROP_KEY_ISREMEMBERME = "user.isRememberMe";	
    private String saveImagePath;//保存图片路径
    /**
     * 检测网络是否可用
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
    @Override
    public void onCreate() {
       
        super.onCreate();
        initOkHttp();
        //注册App异常崩溃处理器
      

        init();
    }
   private void  initOkHttp(){
       File sdcache =this.getExternalCacheDir();
       int cacheSize = 10 * 1024 * 1024; // 10 MiB
    //   Cache cache = new Cache(sdcache, cacheSize);
       ApiHttp.mOkHttpClient=new OkHttpClient();
       ApiHttp.mOkHttpClient.setCache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
      
      
      // ApiHttp. mOkHttpClient.setCache(cache);
       
      
     
    }
    /**
     * 新闻列表
     * @param catalog
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws ApiException
     */
   
    public NewsList getNewsListByCallback(Context context,int catalog, int pageIndex, boolean isRefresh, Callback callback) throws AppException {
        NewsList list = null;
        String key = "newslist_" + catalog + "_" + pageIndex + "_" + PAGE_SIZE;
        if(true) {

            try{
                Log.i("contextmiao","applicationnewslist");
             ApiHttp.getNewsListByCallback(context, catalog, pageIndex, PAGE_SIZE,callback,isRefresh);

                
            }catch(AppException e){
                e.printStackTrace();
                list = (NewsList)readObject(key);
                if(list == null)
                    throw e;
            }
        } else {
            list = (NewsList)readObject(key);
            if(list == null)
                list = new NewsList();
        }
        return list ;
    }
    /**
     * 新闻详情
     * @param news_id
     * @return
     * @throws ApiException
     */
    public void getNews(int news_id, Callback callback) throws AppException {
      
             
      
           
                ApiHttp.getNewsDetail(this, news_id,callback);
                
      
        return ;
    }

    /** 发送消息广播*/
    private void sendNotice(Base entity) {
        if(entity == null) {
            return;
        }
        Notice notice = entity.getNotice();
        if(notice == null) {
            return;
        }
        BroadcastController.sendNoticeBroadCast(this, notice);
    }
    public  Serializable readObject(String file){
        Log.i("contextmiao","readObject");
        if(!isExistDataCache(file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{    Log.i("contextmiao","isReadDataCachetry");
            fis = openFileInput(file);
            ois = new ObjectInputStream(fis);
            Log.i("contextmiao","isReadDataCachetry");
            return (Serializable)ois.readObject();
        }catch(FileNotFoundException e){           
            Log.i("contextmiao","isReadDataCacheFileNotFoundException"+e.toString());

        }catch(Exception e){           
            Log.i("contextmiao","isReadDataCacheException"+e.toString());

            e.printStackTrace();
            //反序列化失败 - 删除缓存文件
            if(e instanceof InvalidClassException){
                File data = getFileStreamPath(file);
                data.delete();
            }
        }finally{
            try {
                ois.close();
            } catch (Exception e) {}
            try {
                fis.close();
            } catch (Exception e) {}
        }
        return null;
    }
    /**
     * 初始化
     */
    private void init(){
        //设置保存图片的路径
        saveImagePath = getProperty(AppConfig.SAVE_IMAGE_PATH);
        if(StringUtils.isEmpty(saveImagePath)){
            setProperty(AppConfig.SAVE_IMAGE_PATH, AppConfig.DEFAULT_SAVE_IMAGE_PATH);
            saveImagePath = AppConfig.DEFAULT_SAVE_IMAGE_PATH;
        }
       
        
    }
    /**
     * 保存对象
     * @param ser
     * @param file
     * @throws java.io.IOException
     */
    public boolean saveObject(Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            fos = openFileOutput(file, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            try {
                oos.close();
            } catch (Exception e) {}
            try {
                fos.close();
            } catch (Exception e) {}
        }
    }
   

    /**
     * 判断缓存是否存在
     * @param cachefile
     * @return
     */
    private boolean isExistDataCache(String cachefile) {
        Log.i("contextmiao","isExistDataCache");
        boolean exist = false;
        File data = getFileStreamPath(cachefile);
        if(data.exists())
            exist = true;
        return exist;
    }
    /**
     * 判断缓存数据是否可读
     * @param cachefile
     * @return
     */
    private boolean isReadDataCache(String cachefile) {
        Log.i("contextmiao","isReadDataCache");
        return readObject(cachefile) != null;
    }
    public String getProperty(String key){
        return AppConfig.getAppConfig(this).get(key);
    }
    public void setProperty(String key,String value){
        AppConfig.getAppConfig(this).set(key, value);
    }
}
