package com.qiaqia.app
import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import com.qiaqia.BaseDetailActivity

import com.qiaqia.ochina.NewsDetailActivity
import com.qiaqia.ochina.R
import com.qiaqia.ochina.net.bean.Notice
import com.qiaqia.ochina.net.bean.UserInformation
import com.qiaqia.ochina.net.bean.miao
import com.qiaqia.ochina.net.http.ApiHttp
import com.shamanland.fab.ShowHideOnScroll
import com.squareup.okhttp.Callback
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton
import java.io.IOException

/**
 * Created by 洽洽 on 2016/3/3.
 */
class HelloMainActivity: AppCompatActivity() {
   
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         var mao = miao(Notice())
        mao.c(1)
       mao.foo(1)
      
        setContentView(R.layout.activity_scrolling2);
        try {


            ApiHttp.information(baseContext, 190084, 265297, "理工小强", 1, 20, object : Callback {
                override fun onFailure(request: Request, e: IOException) {

                }

                @Throws(IOException::class)
                override fun onResponse(response: Response) {
                    var user= UserInformation.parse(response.body().byteStream()) as UserInformation;
                    Log.i("chinamiao","user"+user.user.toString());
                   
                   // Log.i("chinamiao","user"+user.user.toString());
                    //Log.i("chinamiao","user"+user.user.getAccount());
                  ///  Log.i("chinamiao","user"+user.user.getName());

                }
            })
        } catch (e: Exception) {

        }

        printSum(1,2);
      }
    public fun printSum(a: Int, b: Int) {
        Log.i("chinamiao",""+a + b)
    }

 
}

   
  

