package com.qiaqia.ochina;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.qiaqia.BaseDetailActivity;
import com.qiaqia.app.Contanst;
import com.qiaqia.app.UIHelper;
import com.qiaqia.ochina.adapter.SectionsPagerAdapter;

import com.qiaqia.ochina.net.bean.Base;
import com.qiaqia.ochina.net.bean.Entity;
import com.qiaqia.ochina.net.bean.News;
import com.qiaqia.ochina.net.http.ApiHttp;
import com.qiaqia.ochina.ui.BaseDetailBodyFragment;

import com.qiaqia.ochina.ui.BaseDetailCommentFragment;
import com.qiaqia.ochina.ui.BaseItemFragment;
import com.qiaqia.ochina.ui.NewDetailBodyFragment;
import com.shamanland.fab.ShowHideOnScroll;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 洽洽 on 2016/2/26.
 */
public class NewsDetailActivity extends BaseDetailActivity implements BaseDetailBodyFragment.OnFragmentInteractionListener,
        BaseDetailCommentFragment.OnFragmentInteractionListener,BaseItemFragment.OnListFragmentInteractionListener{
 

    @Override
    protected void initViewpage() {
//        detailViewpage=(ViewPager)findViewById(R.id.detailViewpage);
//        List<Fragment> lf = new ArrayList<>();
//        // lf.add( new view1_fragment());
//     
//        lf.add(NewDetailBodyFragment.newInstance("",""));
//        
//        SectionsPagerAdapter mSectionsPagerAdapter;
//        mSectionsPagerAdapter = new SectionsPagerAdapter(lf, getSupportFragmentManager());
//        detailViewpage.setAdapter(mSectionsPagerAdapter);
    }

   
 
  
   
//        detailScrolview = (NestedScrollView) findViewById(R.id.scrollView);
//        detailScrolview.setOnTouchListener(new ShowHideOnScroll(fab));
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
     
       


    }
    @Override
    protected void loadData() {
      
        Intent intent=getIntent();


        Log.i("chinamiao","intent.getExtras()      "+intent.getIntExtra(UIHelper.NEWS_ID_KEY,0));
        int i=  intent.getIntExtra(UIHelper.NEWS_ID_KEY,0);
        ApiHttp.getNewsDetail(this, i, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("chinamiao","getnewsbody      "+"onfail");

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s=response.body().string();
                try{

                    Log.i("chinamiao","response      "+s);
                final    News news= News.parse(new ByteArrayInputStream(s.getBytes()));
                    
                  Message mes=Message.obtain(handler, new Runnable() {
                        @Override
                      public void run() {
                            showBodyFragment(news);
                            toolbar.setTitle(news.getTitle());
                        
                     }
                 });
                mes.sendToTarget();
                }
                catch (Exception e){

                }
                Log.i("chinamiao","getnewsbody      "+s);

            }
        });
    }


   protected void showBodyFragment(News news){
       detailViewpage=(ViewPager)findViewById(R.id.detailViewpage);
       List<Fragment> lf = new ArrayList<>();
       // lf.add( new view1_fragment());

       lf.add(NewDetailBodyFragment.newInstance(news));
       lf.add(BaseDetailCommentFragment.newInstance(news.getId()));

       SectionsPagerAdapter mSectionsPagerAdapter;
       mSectionsPagerAdapter = new SectionsPagerAdapter(lf, getSupportFragmentManager());
       detailViewpage.setAdapter(mSectionsPagerAdapter);
   }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("chinamiao",uri.toString());
    }

    @Override
    public void onListFragmentInteraction(Entity item) {
        
    }
}
    