package com.qiaqia;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiaqia.app.UIHelper;
import com.qiaqia.ochina.R;

import com.qiaqia.ochina.net.bean.Entity;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseDetailActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
 Entity entity;
    protected Toolbar toolbar;
    protected   RapidFloatingActionButton fab;
    private RapidFloatingActionHelper rfabHelper;
    protected ViewPager detailViewpage;
 protected    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
             
        }}
    };
    public RapidFloatingActionButton getFab(){
        return  this.fab;
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_detail2);
        initView();
       
        initViewpage();
        loadData();
        initFab();
    }
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("chinamiao","setNavigationOnClickListener  ");
                onBackPressed();

            }
        });
      
    }

    private void initFab() {
         fab = (RapidFloatingActionButton) findViewById(R.id.fab);
        RapidFloatingActionLayout rfaLayout = (RapidFloatingActionLayout) findViewById(R.id.activity_main_rfal);
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
       
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("分享")
                .setResId(android.R.drawable.ic_menu_share)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );  items.add(new RFACLabelItem<Integer>()
                .setLabel("评论")
                .setResId(android.R.drawable.ic_menu_add)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );  items.add(new RFACLabelItem<Integer>()
                .setLabel("收藏")
                .setResId(android.R.drawable.ic_menu_save)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );
        rfaContent
                .setItems(items)
             
                .setIconShadowRadius(ABTextUtil.dip2px(this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(this, 5))   
             ;
        rfabHelper = new RapidFloatingActionHelper(
                this,
                rfaLayout,
                fab,
                rfaContent
        ).build();
      
    

    }

    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {
       switch (rfacLabelItem.getLabel()){
           case "分享":
               Toast.makeText(this,"将跳转到分享页面",Toast.LENGTH_SHORT).show();
           case "评论":
               Toast.makeText(this,"评论页面",Toast.LENGTH_SHORT).show();
           case "收藏":
               Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
         
               }
        fab.onClick(fab);
    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        onRFACItemLabelClick(i,rfacLabelItem);
    }

    protected abstract  void loadData() ;
    protected abstract  void initViewpage() ;
   
}
