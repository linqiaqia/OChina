package com.qiaqia.ochina;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.qiaqia.app.HelloMainActivity;
import com.qiaqia.app.UIHelper;
import com.qiaqia.ochina.adapter.SectionsPagerAdapter;
import com.qiaqia.ochina.fragment.view2_fragment;
import com.qiaqia.ochina.fragment.view3_fragment;
import com.qiaqia.ochina.net.bean.Entity;
import com.qiaqia.ochina.net.bean.News;
import com.qiaqia.ochina.ui.BaseItemFragment;


import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements BaseItemFragment.OnListFragmentInteractionListener
       , NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout title_tab;

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_tab = (TabLayout) findViewById(R.id.tab_title);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // app:layout_anchor="@id/app_bar"
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intetn =new Intent(MainActivity.this, HelloMainActivity.class);
                MainActivity.this.startActivity(intetn);
               
                
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                 this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        List<Fragment> lf = new ArrayList<>();
       // lf.add( new view1_fragment());
        lf.add( new view2_fragment());
      
        lf.add(view3_fragment.newInstance(3));
        lf.add(view3_fragment.newInstance(3));

        mSectionsPagerAdapter = new SectionsPagerAdapter(lf, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        title_tab.setupWithViewPager(mViewPager);
        title_tab.setOnTabSelectedListener(this);
      OChinaApplication ap=  (OChinaApplication)getApplication();
//      try{
//          NewsList ne=ap.getNewsList(0,2,true);
//          for (News news:ne.getList()
//               ) {
//              Log.i("miao",news.getTitle());
//              
//          }
//      }catch ( Exception e){
//          
//      }
   }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    @Override
   public void onListFragmentInteraction(Entity item){
        Log.i("chinamiao","select"+item.toString());
        UIHelper.showNewsRedirect(this,(News)item);
        Toast.makeText(this,"select"+item.toString(),Toast.LENGTH_SHORT).show();
    };
}
