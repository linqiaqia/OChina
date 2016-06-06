package com.qiaqia.ochina.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qiaqia.ochina.OChinaApplication;
import com.qiaqia.ochina.R;
import com.qiaqia.ochina.modul.MessageData;
import com.qiaqia.ochina.net.bean.CommentList;
import com.qiaqia.ochina.net.bean.Entity;

import com.qiaqia.ochina.net.bean.NewsList;
import com.qiaqia.ochina.net.bean.PageList;
import com.qiaqia.ochina.net.http.DataRequestThreadHandler;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public abstract class BaseItemFragment< Data extends Entity, Result extends PageList<Data>>
        extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
        {

    //没有状态
    public static final int LISTVIEW_ACTION_NONE = -1;
    //更新状态，不显示toast
    public static final int LISTVIEW_ACTION_UPDATE = 0;
    //初始化时，加载缓存状态
    public static final int LISTVIEW_ACTION_INIT = 1;
    //刷新状态，显示toast
    public static final int LISTVIEW_ACTION_REFRESH = 2;
    //下拉到底部时，获取下一页的状态
    public static final int LISTVIEW_ACTION_SCROLL = 3;

    static final int STATE_NONE = -1;
    static final int STATE_LOADING = 0;
    static final int STATE_LOADED = 1;
            //当前页面已加载的数据总和
            private int mSumData;
    //当前加载状态
    private int mState = STATE_NONE;
    //UI状态
    private int mListViewAction = LISTVIEW_ACTION_NONE;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    OChinaApplication mappliction;
    RecyclerView recyclerView;
            private RecyclerView.Adapter mAdapter;
   // private TextView mFooterTextView;
    public Result pageList;
            public MessageData<Result> messageData;
            public List<Data> mList=new ArrayList<>();
    private View mHeaderView;
            protected ProgressBar listProgressBar;
            
    private View mFooterView;//private View mFooterProgressBar;
    //当前 加载的情况
    private int mMessageState = MessageData.MESSAGE_STATE_MORE;
            private int mPage;
    protected DataRequestThreadHandler mRequestThreadHandler = new DataRequestThreadHandler();
    
    public Handler handler=new Handler(){
        public void handleMessage(Message mes){
            Log.i("chinadog","handlemessage");
           }
    };
    /** 正在加载的状态*/
    public void onRefreshLoadingStatus() {}
    /** 加载完毕的状态*/
    public void onRefreshLoadedStatus() {
        
    }

     RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener(){
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
     // Log.i("chinamiao","onScrolled"+recyclerView.toString());
      Log.i("chinamiao","onScrolled"+dx);
      Log.i("chinamiao","onScrolled"+dy);
    }
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView,
                                     int newState) {
        Log.i("chinamiao", "onScrollStateChanged" + newState);

    }
    };
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    protected OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BaseItemFragment() {
    }

    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static ItemFragment newInstance(int columnCount) {
//        ItemFragment fragment = new ItemFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }
    
        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        mappliction=getOsChinaApplication();
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            
        }
    }
    @Override
    public void onRefresh() {
        mListViewAction=LISTVIEW_ACTION_REFRESH;
        Log.i("chinamiao","onredlkfdlkf");
        newLoadList(0, LISTVIEW_ACTION_REFRESH);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_item_list, container, false);
      
        View view=layout.findViewById(R.id.list);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
             recyclerView = (RecyclerView) view;
            
            mAdapter=getAdapter(mList,mListener);
            recyclerView.setAdapter(mAdapter);

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            newLoadList(0,LISTVIEW_ACTION_INIT);
        //  recyclerView.setAdapter(getAdapter(newsList,mListener));
        }
      
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i("chinamiao","onViewCreated");
        Log.i("chinamiao","onViewCreated"+view.toString());

        mSwipeRefreshLayout = (SwipeRefreshLayout) view;
        listProgressBar = (ProgressBar)view.findViewById(R.id.list_progress);
       // mListView = (ListView)view.findViewById(R.id.fragment_listview);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        

      // setupListView();

       
       
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Entity item);
    }
    public  void newLoadList(int page,int action){
        mPage=page;
        Log.i("chinamiao","newloadlistmlistviewaction   "+action);
        mListViewAction=action;
        boolean reflash = true;
        if(mListViewAction == LISTVIEW_ACTION_INIT) {
           
            reflash = false;
        } 
       loadList( page,reflash,getCallback());

   return  ;}
   
   
    protected Callback getCallback() {
        Log.i("chinamiao","getCallback");
        Callback callback=new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
             Message   message=Message.obtain(handler, new Runnable() {
                 @Override
                 public void run() {
                     
                 if(mListViewAction==LISTVIEW_ACTION_REFRESH){
                     if(mSwipeRefreshLayout!=null){
                         mSwipeRefreshLayout.setRefreshing(false);
                         Toast.makeText(getContext(),"网络连接失败",Toast.LENGTH_LONG).show();
                     }}
             }
             });
                message.obj=pageList;
                message.what=1;

                message.sendToTarget();
               
               Log.i("chinamiao","onFailure"+request.toString());

            }

            @Override
            public void onResponse(Response response) throws IOException
           {    
               String responseBody=response.body().string()   ;
         
                    try {
                        Log.i("chinamiao","responseBody    "+response);
                        Log.i("chinamiao","network    "+response.networkResponse());
                    Log.i("chinamiao","cache    "+response.cacheResponse() );
                  try{
                       
                    pageList=(Result) NewsList.parse(new ByteArrayInputStream(responseBody.getBytes()));

                      Log.i("chinamiao","dfdfd"+pageList.getList().get(0).toString());}
                  catch(IndexOutOfBoundsException e){
                      pageList=(Result) CommentList.parse(new ByteArrayInputStream(responseBody.getBytes()));
                      Log.i("chinamiao","comment"+pageList.getList().get(0).toString());
                  }
                  messageData=new MessageData(pageList);
                    final Message message;
                    //下载完成，ui操作
                    Runnable runnable=new Runnable() {
                        @Override
                        public void run() {
                          //更新全局的状态
                           //无数据的情况下(已经加载全部数据，与一开始没有数据)
                            if(messageData.state == MessageData.MESSAGE_STATE_EMPTY && mList.size() != 0) {
                                messageData.state = MessageData.MESSAGE_STATE_FULL;
                            }
                            if(messageData.result != null && messageData.result.getList().size() == 0) {
                                messageData.state = MessageData.MESSAGE_STATE_FULL;
                            }
                            //记录最后的数据状态
                            mMessageState = messageData.state;

                            if(messageData.state == MessageData.MESSAGE_STATE_EMPTY) {
                                //底部显示“暂无数据”
                                setFooterNoMoreState();
                                return;
                            } else if(messageData.state == MessageData.MESSAGE_STATE_ERROR){
                                setFooterErrorState();
                                return;
                            } else if(messageData.state == MessageData.MESSAGE_STATE_FULL) {
                                //当页数少于要求的加载页数的时，可以判断是已经加载完，没有更多的数据
                                setFooterFullState();
                            } else if(messageData.state == MessageData.MESSAGE_STATE_MORE) {
                                //有数据的情况下，底部显示“正在加载...”
                                setFooterHasMoreState();
                            }
                        
                            Log.i("chinadog","runable");
                            //加载结束
                            mState = STATE_LOADED;

                            Log.i("chinamiao","onPostExecute");

                            //如果动作是下拉刷新，则将刷新中的状态去掉
                            if(mListViewAction == LISTVIEW_ACTION_REFRESH) {
                                mSumData=pageList.getPageSize();
                                Log.d("chinamiao","reflash=true");
                                if(mList.containsAll(pageList.getList())){
                                    Log.i("chinamiao","     已经是最新内容了");
                                    Toast.makeText(getContext(),"已经是最新内容了",Toast.LENGTH_SHORT).show();
                                }
                                    setSwipeRefreshLoadedState();
                                    mList.clear();
                                    mList.addAll(pageList.getList());
                                
                            }
                            if(mListViewAction==LISTVIEW_ACTION_SCROLL){
                                mSumData += pageList.getPageSize();
                                mList.addAll(pageList.getList());
                            }   
                            if(mListViewAction==LISTVIEW_ACTION_INIT){
                                
                                mSumData = pageList.getPageSize();
                                mList.clear();
                                mList.addAll(pageList.getList());
                                
                            
                            }
                            mAdapter.notifyDataSetChanged();
                            listProgressBar.setVisibility(View.GONE);
                        }
                    };
                    message=Message.obtain(handler,runnable);
                    message.obj=pageList;
                    message.what=1;

                    message.sendToTarget();


                } catch (Exception e) {

                    Log.i("chinamiao","catchparse"+e.toString());
                    e.printStackTrace();
                }




            }
        };return  callback;
    }
    /** 设置顶部正在加载的状态*/
    void setSwipeRefreshLoadingState() {
        if(mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            //防止多次重复刷新
            mSwipeRefreshLayout.setEnabled(false);
        }
        onRefreshLoadingStatus();
    }

    /** 设置顶部加载完毕的状态*/
    void setSwipeRefreshLoadedState() {
        if(mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
        onRefreshLoadedStatus();
    }

    /** 设置底部有错误的状态*/
    void setFooterErrorState() {
        if(mFooterView != null) {
           // mFooterProgressBar.setVisibility(View.GONE);
          //  mFooterTextView.setText(R.string.load_error);
        }
    }

    /** 设置底部有更多数据的状态*/
    void setFooterHasMoreState() {
        if(mFooterView != null) {
           // mFooterProgressBar.setVisibility(View.GONE);
           /// mFooterTextView.setText(R.string.load_more);
        }
    }

    /** 设置底部已加载全部的状态*/
    void setFooterFullState() {
        if(mFooterView != null) {
           // mFooterProgressBar.setVisibility(View.GONE);
           // mFooterTextView.setText(R.string.load_full);
        }
    }

    /** 设置底部无数据的状态*/
    void setFooterNoMoreState() {
        if(mFooterView != null) {
          //  mFooterProgressBar.setVisibility(View.GONE);
            //mFooterTextView.setText(R.string.load_empty);
        }
    }

    /** 设置底部加载中的状态*/
    void setFooterLoadingState() {
        if(mFooterView != null) {
           // mFooterProgressBar.setVisibility(View.VISIBLE);
           // mFooterTextView.setText(R.string.load_ing);
        }
    }/** 加载下一页*/
    protected void onLoadNextPage() {
        // 当前pageIndex
       int pageIndex = 40 / mappliction.PAGE_SIZE;
        
       newLoadList(pageIndex, LISTVIEW_ACTION_SCROLL);
    }
    
    public abstract RecyclerView.Adapter getAdapter(List<Data> list,OnListFragmentInteractionListener no) ;
    public abstract void loadList( int pageIndex, boolean isRefresh, Callback callback);
          
}
