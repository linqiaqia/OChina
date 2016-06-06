package com.qiaqia.ochina.ui;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiaqia.ochina.R;
import com.qiaqia.ochina.net.bean.News;
import com.qiaqia.ochina.net.bean.NewsList;
import com.qiaqia.ochina.net.common.StringUtils;
import com.qiaqia.ochina.ui.BaseItemFragment.OnListFragmentInteractionListener;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1; 
    private final List<News> mValues;
   
    private final OnListFragmentInteractionListener mListener;
private View.OnClickListener footerListener;

    public View.OnClickListener getFooterListener() {
        return footerListener;
    }

    public void setFooterListener(View.OnClickListener footerListener) {
        this.footerListener = footerListener;
    }
   

    public MyItemRecyclerViewAdapter(List<News> newsList, OnListFragmentInteractionListener listener) {
        
        mValues = newsList;
        mListener = listener;
    }

   

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(holder instanceof Footer){
            return;
        }
        holder.mItem = mValues.get(position);
        News news = mValues.get(position);

        holder.title.setText(news.getTitle());
        holder.title.setTag(news);// 设置隐藏参数(实体类)
        holder.author.setText(news.getAuthor());
        holder.body.setText(news.getBody());
        holder.date.setText(StringUtils.friendly_time(news.getPubDate()));
        holder.count.setText(news.getCommentCount() + "");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_listitem_cardview, parent, false);
            return new ViewHolder(view);
        }
        // type == TYPE_FOOTER 返回footerView  
        else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.listview_footer, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT));
            return  new Footer(view);
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
   
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView  title,author,count,date,body;
        public final ImageView flag;
        
        public News mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
         
            title = (TextView) view.findViewById(R.id.news_listitem_title);
            author = (TextView) view.findViewById(R.id.news_listitem_author);
            count = (TextView) view.findViewById(R.id.news_listitem_commentCount);
            date = (TextView) view.findViewById(R.id.news_listitem_date);
            body = (TextView) view.findViewById(R.id.news_listitem_body);
            flag = (ImageView) view.findViewById(R.id.news_listitem_flag);
        }

        @Override
        public String toString() {
            return super.toString() + " '" +title.getText()+ "'";
        }
    }
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView  
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
    public class Footer extends MyItemRecyclerViewAdapter.ViewHolder {
        public final View mView;
        public final TextView  title;
        public final View  flag;
        
        public News mItem;

        public Footer(View view) {
            super(view);
            mView = view;
            title=(TextView)mView.findViewById(R.id.listview_foot_more);
            flag=mView.findViewById(R.id.listview_foot_progress);
            mView.setOnClickListener(footerListener);
        
        }

        @Override
        public String toString() {
            return "im footer"+ super.toString() + " '" +title.getText()+ "'";
        }
    }
    
}
