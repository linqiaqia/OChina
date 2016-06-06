package com.qiaqia.ochina.net.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by 洽洽 on 2016/1/23.
 */
public class DataRequestThreadHandler extends Thread
        implements Handler.Callback {
    private Handler mMainHandler;
    Looper mLooper;

    private Handler mThreadHandler;
    @Override
    public boolean handleMessage(Message msg) {
        Log.i("chinamiao","handleMessage");
        final AsyncDataHandler dataHandler = (AsyncDataHandler) msg.obj;
        final Object result = dataHandler.execute();
        if(mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
        //通过主线程，将数据回调
        mMainHandler.post(new Runnable() {

            @Override
            public void run() {
                dataHandler.onPostExecute(result);
            }
        });
        return true;
    }
    /**
     * 请求数据
     * */
    public synchronized void request(int what, AsyncDataHandler datahandler) {
        Log.i("chinamiao","request");
        if(datahandler == null) {
            Log.i("chinamiao","handlernull");
            return;
        }
        if(!isAlive()) {
            Log.i("chinamiao","isAlive");
            //启动线程
            start();
        }
        datahandler.onPreExecute();
        if(mThreadHandler == null) {
            mThreadHandler = new Handler(getLooper(), this);
        }
        //将相同的任务移除
        mThreadHandler.removeMessages(what);
        mThreadHandler.obtainMessage(what, datahandler).sendToTarget();
        Log.i("chinamiao","sendtotarget");
    }
    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            while (isAlive() && mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return mLooper;
    }
    
        public static interface AsyncDataHandler<Result> {

        /** 准备异步执行前(主线程)*/
        public void onPreExecute();
        /** 异步执行*/
        public Result execute();
        /** 主线程回调结果(主线程)*/
        public void onPostExecute(Result result);
    }
    
}

