package com.qiaqia.ochina.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiaqia.ochina.R;

import com.qiaqia.ochina.net.ContextNet;
import com.qiaqia.ochina.net.bean.Comment;
import com.qiaqia.ochina.net.common.BitmapManager;
import com.qiaqia.ochina.net.common.StringUtils;
import com.qiaqia.ochina.net.http.ApiHttp;
import com.qiaqia.ochina.ui.BaseItemFragment.OnListFragmentInteractionListener;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CommentListRecyclerViewAdapter extends RecyclerView.Adapter<CommentListRecyclerViewAdapter.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1; 
    private final List<Comment> mValues;
    private BitmapManager bmpManager;
    private final OnListFragmentInteractionListener mListener;
private View.OnClickListener footerListener;
ContextNet context;
    public View.OnClickListener getFooterListener() {
        return footerListener;
    }

    public void setFooterListener(View.OnClickListener footerListener) {
        this.footerListener = footerListener;
    }

    
    public CommentListRecyclerViewAdapter(ContextNet context, List<Comment> commentList, OnListFragmentInteractionListener listener) {
        bmpManager=new BitmapManager();
        mValues = commentList;
        mListener = listener;
    }

   

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(holder instanceof Footer){
            return;
        }
    
        Comment comment = mValues.get(position);
        String faceURL = comment.getFace();
        if(faceURL.endsWith("portrait.gif") || StringUtils.isEmpty(faceURL)){
            holder.face.setImageResource(R.drawable.mini_avatar);
        }else{


           bmpManager.loadBitmap(faceURL, holder.face);
            
        }
        holder.face.setTag(comment);//设置隐藏参数(实体类)
       // holder.face.setOnClickListener(faceClickListener);
        holder.name.setText(comment.getAuthor());
        holder.date.setText(StringUtils.friendly_time(comment.getPubDate()));
        holder.content.setText(comment.getContent());
        holder.content.setTag(comment);//设置隐藏参数(实体类)

        switch(comment.getAppClient())
        {
            default:
                holder.client.setText("");
                break;
            case Comment.CLIENT_MOBILE:
                holder.client.setText("来自:手机");
                break;
            case Comment.CLIENT_ANDROID:
                holder.client.setText("来自:Android");
                break;
            case Comment.CLIENT_IPHONE:
                holder.client.setText("来自:iPhone");
                break;
            case Comment.CLIENT_WINDOWS_PHONE:
                holder.client.setText("来自:Windows Phone");
                break;
        }
        

       
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_listitem, parent, false);
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
        public final TextView name,date,content,client;
        public ImageView face;
        LinearLayout relies,refers;

        public ViewHolder(View view) {
            super(view);
            mView = view;	
            face = (ImageView)mView.findViewById(R.id.comment_listitem_userface);
            name = (TextView)mView.findViewById(R.id.comment_listitem_username);
            date = (TextView)mView.findViewById(R.id.comment_listitem_date);
            content = (TextView)mView.findViewById(R.id.comment_listitem_content);
            client= (TextView)mView.findViewById(R.id.comment_listitem_client);
            relies = (LinearLayout)mView.findViewById(R.id.comment_listitem_refers);
            refers = (LinearLayout)mView.findViewById(R.id.comment_listitem_refers);
         
          
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
    public class Footer extends CommentListRecyclerViewAdapter.ViewHolder {
        public final View mView;
        public final TextView  title;
        public final View  flag;
        
    

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
