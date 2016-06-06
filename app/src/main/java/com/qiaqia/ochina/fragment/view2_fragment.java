package com.qiaqia.ochina.fragment;


import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiaqia.ochina.net.bean.NewsList;

import com.qiaqia.ochina.ui.BaseItemFragment;
import com.qiaqia.ochina.ui.MyItemRecyclerViewAdapter;
import com.squareup.okhttp.Callback;

import java.util.List;


/**
 * Created by 洽洽 on 2015/12/3.
 */
 public   class view2_fragment extends BaseItemFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public view2_fragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
  

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    return  super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public MyItemRecyclerViewAdapter getAdapter(List list, OnListFragmentInteractionListener no) {
        MyItemRecyclerViewAdapter myItemRecyclerViewAdapter= new MyItemRecyclerViewAdapter(list,no);
        myItemRecyclerViewAdapter.setFooterListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadNextPage();
                Log.i("chinamiao","onfooter");
            }
        });return myItemRecyclerViewAdapter;
    }


   

    @Override
    public void loadList(int pageIndex, boolean isRefresh, Callback callback) {
      
        try{
            Log.i("chinamiao","loadlist" +
                    "jkhsdfhjlkfsdghjlkfsdgjk");
            getOsChinaApplication().getNewsListByCallback(getContext(),NewsList.CATALOG_ALL,pageIndex,isRefresh,callback);
            

            
        }catch ( Exception e){
            Log.i("chinamiao","newloadListException");
        } 
    }

   
}
