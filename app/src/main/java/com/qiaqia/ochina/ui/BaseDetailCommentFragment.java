package com.qiaqia.ochina.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiaqia.app.Contanst;
import com.qiaqia.ochina.R;
import com.qiaqia.ochina.net.ContextNet;
import com.qiaqia.ochina.net.bean.CommentList;
import com.qiaqia.ochina.net.bean.NewsList;
import com.qiaqia.ochina.net.http.ApiHttp;
import com.squareup.okhttp.Callback;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseDetailCommentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BaseDetailCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseDetailCommentFragment extends BaseItemFragment {
    // TODO: Rename parameter arguments, choose names that match
 

    // TODO: Rename and change types of parameters
 
private int newsId;
    private OnFragmentInteractionListener mListener;

    public BaseDetailCommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
    
     * @return A new instance of fragment BaseDetailBodyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseDetailCommentFragment newInstance(int newsId) {
        BaseDetailCommentFragment fragment = new BaseDetailCommentFragment();
        Bundle args = new Bundle();
        args.putSerializable(Contanst.NEWS_ID_KEY, newsId);
        fragment.setArguments(args);
     
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle argus = getArguments();
        if(argus != null) {
            newsId = argus.getInt(Contanst.NEWS_ID_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  super.onCreateView(inflater,container,savedInstanceState);
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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list, OnListFragmentInteractionListener no) {
        CommentListRecyclerViewAdapter commentListRecyclerViewAdapter=new CommentListRecyclerViewAdapter
                ((ContextNet)getContext().getApplicationContext(),list,no);
        commentListRecyclerViewAdapter.setFooterListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return commentListRecyclerViewAdapter;
    }

    @Override
    public void loadList(int pageIndex, boolean isRefresh, Callback callback) {
        try{
            Log.i("chinamiao","loadlist" +
                    "jkhsdfhjlkfsdghjlkfsdgjk");
           ApiHttp.getCommentList(getContext(), CommentList.CATALOG_NEWS, newsId, pageIndex,20,callback);



        }catch ( Exception e){
            Log.i("chinamiao","newloadListException");
        }
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
}
