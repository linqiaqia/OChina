package com.qiaqia.ochina.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiaqia.ochina.R;
import com.qiaqia.ochina.ui.BaseFragment;


/**
 * Created by 洽洽 on 2015/12/3.
 */
 public   class view3_fragment extends BaseFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public view3_fragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static view3_fragment newInstance(int sectionNumber) {
        view3_fragment fragment = new view3_fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.conten_view3, container, false);
TextView textView = (TextView) rootView.findViewById(R.id.count_tips);
        textView.setText("miao");
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}
