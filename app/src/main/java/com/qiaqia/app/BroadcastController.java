/*
 * Copyright (c) 2014. CodeBoyTeam
 */

package com.qiaqia.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.qiaqia.ochina.net.bean.Notice;
import com.qiaqia.ochina.net.bean.Result;


/**
 * 类名 BroadcastController.java</br>
 * 创建日期 2014年4月27日</br>
 * @author LeonLee (http://my.oschina.net/lendylongli)</br>
 * Email lendylongli@gmail.com</br>
 * 更新时间 2014年4月27日 下午9:38:28</br>
 * 最后更新者 LeonLee</br>
 * 
 * 说明 广播消息控制中心
 */
public class BroadcastController {

	public static final String ACTION_USERCHANGE = "com.codeboy.oschina.ACTION_USERCHANGE";
	
	public static final String ACTION_APPWIDGET_UPDATE = "net.oschina.app.action.APPWIDGET_UPDATE";
	public static final String ACTION_APP_TWEETPUB = "net.oschina.app.action.APP_TWEETPUB";
	
	/** 
	 * 发送用户变化的广播
	 * @param context 上下文
	 * */
	public static void sendUserChangeBroadcase(Context context) {
		context.sendBroadcast(new Intent(ACTION_USERCHANGE));
	}
	
	/**
	 * 发送通知广播
	 * 
	 * @param context
	 * @param notice
	 */
	public static void sendNoticeBroadCast(Context context, Notice notice) {
//		if (!(() context.getApplicationContext()).isLogin()
//				|| notice == null) {
//			return;
//		}
		Intent intent = new Intent(ACTION_APPWIDGET_UPDATE);
		intent.putExtra("atmeCount", notice.getAtmeCount());
		intent.putExtra("msgCount", notice.getMsgCount());
		intent.putExtra("reviewCount", notice.getReviewCount());
		intent.putExtra("newFansCount", notice.getNewFansCount());
		context.sendBroadcast(intent);
	}

	
	/**
	 * 注册一个监听用户变化的广播
	 * @param context 上下文
	 * @param receiver 广播接收器
	 * */
	public static void registerUserChangeReceiver(Context context, 
			BroadcastReceiver receiver) {
		IntentFilter filter = new IntentFilter(ACTION_USERCHANGE);
		context.registerReceiver(receiver, filter);
	}
	
	/**
	 * 注销一个广播接收器
	 * @param context 上下文
	 * @param receiver 要注销的广播接收器
	 * */
	public static void unregisterReceiver(Context context, BroadcastReceiver receiver) {
		try {
			context.unregisterReceiver(receiver);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}