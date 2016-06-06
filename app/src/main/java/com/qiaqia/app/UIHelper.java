package com.qiaqia.app;

import android.content.Context;
import android.content.Intent;

import com.qiaqia.ochina.BlogDetailActivity;
import com.qiaqia.ochina.NewsDetailActivity;
import com.qiaqia.ochina.SoftwareDetailActivity;
import com.qiaqia.ochina.net.bean.News;
import com.qiaqia.ochina.net.common.StringUtils;

/**
 * Created by 洽洽 on 2016/2/26.
 */
public class UIHelper {

    /** 全局web样式 */
    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore.css\">"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>";

    public final static String WEB_STYLE = linkCss + "<style>* {font-size:14px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
            + "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
            + "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;overflow: auto;} "
            + "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";

    public static final String NEWS_ID_KEY = "news_id";
    public static final String NEWS_DATA_KEY = "news_data";

    public static final String BLOG_ID_KEY = "blog_id";
    public static final String BLOG_DATA_KEY = "blog_data";

    public static final String POST_ID_KEY = "post_id";
    public static final String POST_DATA_KEY = "post_data";

    public static final String TWEET_ID_KEY = "tweet_id";

    public static final String MESSAGE_FRIEND_NAME_KEY = "friend_name";
    public static final String MESSAGE_FRIEND_ID_KEY = "friend_id";

    public static final String SOFTWARE_ID_KEY = "ident";

    public static final String FRIENDS_TYPE = "friend_type";
    public static final String FRIENDS_FOLLOWERS = "friend_followers";
    public static final String FRIENDS_FANS = "friend_fans";
    /**
     * 新闻超链接点击跳转
     *
     * @param context
     * @param newsId
     * @param newsType
     * @param objId
     */
    public static void showNewsRedirect(Context context, News news) {
        String url = news.getUrl();
        // url为空-旧方法
        if (StringUtils.isEmpty(url)) {
            int newsId = news.getId();
            int newsType = news.getNewType().type;
            String objId = news.getNewType().attachment;
            showNewsDetail(context, newsId);
            switch (newsType) {
                case News.NEWSTYPE_NEWS:
                 //   showNewsDetail(context, newsId);
                    break;
//                case News.NEWSTYPE_SOFTWARE:
//                    showSoftwareDetail(context, objId);
//                    break;
//                case News.NEWSTYPE_POST:
//                    showQuestionDetail(context, StringUtils.toInt(objId)); 
//                    break;
//                case News.NEWSTYPE_BLOG:
//                    showBlogDetail(context, StringUtils.toInt(objId));
                 //   break;
            }
        } else {
           // showUrlRedirect(context, url);
        }
    }
    /**
     * 显示新闻详情
     *
     * @param context
     * @param newsId
     */
    public static void showNewsDetail(Context context, int newsId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NEWS_ID_KEY, newsId);
        context.startActivity(intent);
    }
    /**
     * 显示博客详情
     *
     * @param context
     * @param blogId
     */
    public static void showBlogDetail(Context context, int blogId) {
        Intent intent = new Intent(context, BlogDetailActivity.class);
        intent.putExtra(BLOG_ID_KEY, blogId);
        context.startActivity(intent);
    }

   

 

    /**
     * 显示软件详情
     *
     * @param context
     * @param ident
     */
    public static void showSoftwareDetail(Context context, String ident) {
        Intent intent = new Intent(context, SoftwareDetailActivity.class);
        intent.putExtra(SOFTWARE_ID_KEY, ident);
        context.startActivity(intent);
    }

}
