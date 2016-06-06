package com.qiaqia.ochina.ui;


import android.support.v4.app.Fragment;

import com.qiaqia.ochina.OChinaApplication;

/**
 * Created by 洽洽 on 2016/1/23.
 */
public class BaseFragment extends Fragment {
    public OChinaApplication getOsChinaApplication() {
        return (OChinaApplication) getActivity().getApplication();
    }
}
