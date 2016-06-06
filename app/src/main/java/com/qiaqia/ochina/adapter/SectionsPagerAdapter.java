package com.qiaqia.ochina.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.qiaqia.ochina.fragment.view3_fragment;

import java.util.List;

/**
 * Created by 洽洽 on 2015/12/3.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
List<Fragment> lm;
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }public SectionsPagerAdapter(List<Fragment> lm, FragmentManager fm) {
        super(fm);this.lm=lm;
    }

    @Override
    public Fragment getItem(int position) {
        if (lm!=null){
            return  lm.get(position);
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
       else return view3_fragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        if(lm!=null){
            return lm.size();
            
        }
        // Show 3 total pages.
      else  return 3;
    }public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
        }
        return null;
    }
}
