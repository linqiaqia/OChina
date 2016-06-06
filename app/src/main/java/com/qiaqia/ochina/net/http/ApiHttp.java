package com.qiaqia.ochina.net.http;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.qiaqia.ochina.net.ContextNet;
import com.qiaqia.ochina.net.bean.Blog;
import com.qiaqia.ochina.net.bean.CommentList;
import com.qiaqia.ochina.net.bean.News;
import com.qiaqia.ochina.net.bean.NewsList;
import com.qiaqia.ochina.net.bean.Result;
import com.qiaqia.ochina.net.bean.URLs;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 洽洽 on 2016/1/17.
 */
public class ApiHttp {
    public static final String UTF_8 = "UTF-8";
    public static final String DESC = "descend";
    public static final String ASC = "ascend";

    private final static int TIMEOUT_CONNECTION = 20000;
    private final static int TIMEOUT_SOCKET = 20000;
    private final static int RETRY_TIME = 3;

    private static String appCookie;
    private static String appUserAgent;
  public static  OkHttpClient mOkHttpClient ;
    public static void cleanCookie() {
        appCookie = "";
    }

    /**
     * 获取资讯的详情
     * @param url
     * @param news_id
     * @return
     * @throws AppException
     */
    public static void getNewsDetail(Context context, final int news_id,Callback callback){
        final String newUrl = _MakeURL(URLs.NEWS_DETAIL, new HashMap<String, Object>(){{
            put("id", news_id);
           
        }}); Log.i("chinamiao","newurl       "+newUrl);


            getHttpClient(context,newUrl,callback);
      
    }
    /**
     * 获取博客详情
     * @param blog_id
     * @return
     * @throws AppException
     */
    public static void getBlogDetail(Context context, final int blog_id,Callback callback) throws AppException {
        String newUrl = _MakeURL(URLs.BLOG_DETAIL, new HashMap<String, Object>(){{
            put("id", blog_id);
        }});
        getHttpClient(context,newUrl,callback);
    }

    /**
     * 获取帖子列表
     * @param context
     * @param catalog
     * @param pageIndex
     * @return
     * @throws AppException
     */
    public static void getPostList(Context context, final int catalog, final int pageIndex, 
                                   final int pageSize,Callback callback) throws AppException {
        String newUrl = _MakeURL(URLs.POST_LIST, new HashMap<String, Object>(){{
            put("catalog", catalog);
            put("pageIndex", pageIndex);
            put("pageSize", pageSize);
        }});
        getHttpClient(context,newUrl,callback);
    }
    /**
     * 获取帖子的详情
     * @param callback 回掉
     * @param post_id
     * @return
     * @throws AppException
     */
    public static void getPostDetail(Context appContext, final int post_id,Callback callback) 
            throws AppException {
        String newUrl = _MakeURL(URLs.POST_DETAIL, new HashMap<String, Object>(){{
            put("id", post_id);
        }});
        getHttpClient(appContext,newUrl,callback);
    }
    /**
     * 获取软件详情
     * @param ident
     * @return
     * @throws AppException
     */
    public static void getSoftwareDetail(Context appContext, final String ident,Callback callback) throws AppException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ident", ident);
        String newUrl = _MakeURL(URLs.POST_DETAIL,params);
        getHttpClient(appContext,newUrl,callback);

    }
    private static String _MakeURL(String p_url, Map<String, Object> params) {
        StringBuilder url = new StringBuilder(p_url);
        if (url.indexOf("?") < 0)
            url.append('?');

        for (String name : params.keySet()) {
            url.append('&');
            url.append(name);
            url.append('=');
            url.append(String.valueOf(params.get(name)));
            //不做URLEncoder处理
            //url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
        }
          Log.i("miao",url.toString().replace("?&", "?"));
        return url.toString().replace("?&", "?");
    }

    public static void getHttpClient(Context context,String url ,Callback callback) {
        getHttpClient(context, url, callback,false);
    }
    public static void getHttpClient(Context context,String url ,Callback callback,boolean isRefresh) {
        final Request request;
        Log.i("miao","res");
        //创建okHttpClient对象
    if(isRefresh){
        
   
//创建一个Request
       request = getRequest(url); }
        else request=getNetRequest(url);
        Log.i("miao",request.toString());

        Call call = mOkHttpClient.newCall(request);

//请求加入调度
        call.enqueue(callback);
       
        Log.i("miao","finally2");
        return ;
    }

    private static Request getRequest(String url) {
        return new Request.Builder()
                     .header("Cache-Control", "max-stale=3600")
                    .url(url)
             .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
    }  private static Request getNetRequest(String url) {
        return new Request.Builder()
                     .header("Cache-Control", "max-stale=3600")
                    .url(url)
              //.cacheControl(CacheControl.FORCE_CACHE)
                    .build();
    }

    public static void  getNewsListByCallback(Context context,final int catalog, final int pageIndex,
                                          final int pageSize,Callback callback,boolean isRefresh) throws AppException {
    Log.i("chinamiao", "getnewslist");
    final Result res = new Result();
    
    String newUrl = _MakeURL(URLs.NEWS_LIST, new HashMap<String, Object>() {{
        put("catalog", catalog);
        put("pageIndex", pageIndex);
        put("pageSize", pageSize);
        //  put("dataType", "json");
        Log.i("miao", "1");
    }});
    
    Callback cb = callback;


    Log.i("miao", "2" + newUrl);
    getHttpClient(context,newUrl, cb,isRefresh);


}
    /**
     * 获取评论列表
     * @param catalog 1新闻  2帖子  3动弹  4动态
     * @param id
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws AppException
     */
    public static void getCommentList(Context appContext, final int catalog, final int id, final int pageIndex, final int pageSize,Callback callback) throws AppException {
        String newUrl = _MakeURL(URLs.COMMENT_LIST, new HashMap<String, Object>(){{
            put("catalog", catalog);
            put("id", id);
            put("pageIndex", pageIndex);
            put("pageSize", pageSize);
        }});

        getHttpClient(appContext,newUrl, callback);
    }
public static Bitmap getNetBitmap(String url){
    Request request = new Request.Builder().url(url).build();
    Bitmap bitmap=null;
    InputStream inputStream=null;
    try{
      
 // bitmap=new Bitmap()
        inputStream= mOkHttpClient.newCall(request).execute().body().byteStream();
        bitmap= BitmapFactory.decodeStream(inputStream);
    }
    catch (Exception e){
        
    }return bitmap;
}
    /**
     * 获取用户信息个人专页（包含该用户的动态信息以及个人信息）
     * @param uid 自己的uid
     * @param hisuid 被查看用户的uid
     * @param hisname 被查看用户的用户名
     * @param pageIndex 页面索引
     * @param pageSize 每页读取的动态个数
     * @return
     * @throws AppException
     */
    public static void information(Context appContext, int uid, int hisuid, String hisname, int pageIndex, int pageSize,Callback callback) throws AppException {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("hisname", hisname);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);

        try{
            
            
            Log.i("chinamiao","get userinfromation"+_MakeURL(URLs.USER_INFORMATION,params));
           getHttpClient(appContext, _MakeURL(URLs.USER_INFORMATION,params),callback);
         //   return UserInformation.parse(_post(appContext, URLs.USER_INFORMATION, params, null));
        }catch(Exception e){
            if(e instanceof AppException)
                throw (AppException)e;
            throw AppException.network(e);
        }
    }
    
}
