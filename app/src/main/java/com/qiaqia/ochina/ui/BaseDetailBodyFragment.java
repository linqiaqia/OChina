package com.qiaqia.ochina.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.qiaqia.BaseDetailActivity;
import com.qiaqia.app.Contanst;
import com.qiaqia.app.UIHelper;
import com.qiaqia.ochina.R;
import com.qiaqia.ochina.net.bean.Entity;
import com.qiaqia.ochina.net.bean.News;
import com.shamanland.fab.ShowHideOnScroll;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseDetailBodyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseDetailBodyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseDetailBodyFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    WebView NewsBody;
    NestedScrollView rootview;
    TextView title, author, date,comment;
private News news;
    private RapidFloatingActionButton fab=null;
    protected Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private View rootView;
    private OnFragmentInteractionListener mListener;

    public BaseDetailBodyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
   
     * @return A new instance of fragment BaseDetailBodyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseDetailBodyFragment newInstance(Entity entity) {
        BaseDetailBodyFragment fragment = new BaseDetailBodyFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.NEWS_DATA_KEY, entity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context=getContext();
        super.onCreate(savedInstanceState);
        Bundle argus = getArguments();
        if(argus != null) {
            news = (News) argus.getSerializable(Contanst.NEWS_DATA_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_detail, container, false);
    return  rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }if (context instanceof BaseDetailActivity) {
            BaseDetailActivity   activity = (BaseDetailActivity) context;
          fab=  activity.getFab();
         Log.i("chinamiao","getafb");
            //FloatingActionButton floatingActionButton=null;
            
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        showView(news);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    protected void initView() {

        NewsBody = (WebView)rootView.findViewById(R.id.news_body);
        rootview = (NestedScrollView)rootView.findViewById(R.id.scrollView);
        comment = (TextView)rootView. findViewById(R.id.news_comment);
        title = (TextView) rootView.findViewById(R.id.news_detail_title);
        author = (TextView)rootView. findViewById(R.id.news_detail_author);
        date = (TextView) rootView.findViewById(R.id.news_detail_date);
        rootview.setOnTouchListener(new ShowHideOnScroll(fab));
    }
    protected void showView(News news){

                         //ui操作
                            String body = UIHelper.WEB_STYLE + news.getBody();
                            // 过滤掉 img标签的width,height属性
                            body = body.replaceAll(
                                    "(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
                            body = body.replaceAll(
                                    "(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");

                            // 添加点击图片放大支持
                            body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                                    "$1$2\" onClick=\"javascript:mWebViewImageListener.onImageClick('$2')\"");
                            if (news.getRelatives().size() > 0) {
                                String strRelative = "";
                                for (News.Relative relative : news.getRelatives()) {
                                    strRelative += String
                                            .format("<a href='%s' style='text-decoration:none'>%s</a><p/>",
                                                    relative.url, relative.title);
                                }
                                body += String.format(
                                        "<p/><hr/><b>相关资讯</b><div><p/>%s</div>",
                                        strRelative);
                            }

                            body += "<div style='margin-bottom: 80px'/>";
                        
                              NewsBody.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
                         title.setText(news.getTitle());
                         // toolbar.setTitle(news.getTitle());
                         author.setText(news.getAuthor());
                         date.setText(news.getPubDate());
                          news.getCommentCount();
                            comment.setText(""+news.getCommentCount());
        
                         
    }
}
