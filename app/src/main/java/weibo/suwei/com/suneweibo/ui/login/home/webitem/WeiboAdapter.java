package weibo.suwei.com.suneweibo.ui.login.home.webitem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;

import weibo.suwei.com.suneweibo.R;
import weibo.suwei.com.suneweibo.ui.common.FillContent;

/**
 * 原创微博， 转发微博(从上到下显示：1.微博头像的那条顶部栏，2.微博文字内容 3.微博图片 4.转发+评论+点赞的那条底部栏)
 * 使用RecyclerView需要继承RecyclerView.Adapter的适配器，作用是将数据与每一个item的界面进行绑定。
 * 请注意这里的 WeiboAdapter是 显示微博数据的适配器
 * 而RecyclerView的真正适配器是 widget包下的 HeaderAndFooterRecyclerViewAdapter
 * Created by suwei on 2017/4/4.
 */

public class WeiboAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //原创微博
    private static final int TYPE_ORIGIN_ITEM = 0;
    //转发微博 retweet
    private static final int TYPE_RETWEET_ITEM = 1;
    private ArrayList<Status> mDatas;
    private Context mContext;
    private View mView;

    public WeiboAdapter(ArrayList<Status> datas, Context context) {
        //这里 通过 传来的 ArrayList<Status> 的大小，才决定 在 RecyclerView上显示多少行
        mDatas = datas;
        mContext = context;
    }

    /**
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewType = TYPE_ORIGIN_ITEM;
        if (viewType == TYPE_ORIGIN_ITEM) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.home_weiboitem_origin, parent, false);
            OriginViewHolder originViewHolder = new OriginViewHolder(mView);
            //原创微博  的界面中有  RecyclerView , 拿来放图片
            //添加 RecyclerVIew 的滑动监听器
            //这里的 ImageLoder使用了开源框架，在build.gradle添加
            //compile'com.nostra13.universalimageloader:universal-image-loader:1.9.5'
            originViewHolder.weibo_origin_img.addOnScrollListener(
                    new NewPauseOnScrollListener(ImageLoader.getInstance(),true,true));
            return originViewHolder;
        } else if (viewType == TYPE_RETWEET_ITEM) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.home_weiboitem_retweet, parent, false);
            RetweetViewHodler retweetViewHodler = new RetweetViewHodler(mView);
            retweetViewHodler.weibo_origin_img.addOnScrollListener(
                    new NewPauseOnScrollListener(ImageLoader.getInstance(),true,true));
            return retweetViewHodler;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //如果是原创微博
        if(holder instanceof OriginViewHolder){
            //如果该原创微博 删除
            if(mDatas.get(position).user == null){
                OriginViewHolder originViewHolder = (OriginViewHolder) holder;
                FillContent.fillTitleBar(mContext,mDatas.get(position),originViewHolder.profile_img,
                        originViewHolder.profile_img_verified,originViewHolder.titlebar_profile_name,
                        originViewHolder.titlebar_profile_time,originViewHolder.titlebar_weiboComFrom);

            }else{
                //该微博被删除

            }
        }//如果是 转发微博
        else if(holder instanceof RetweetViewHodler){
            RetweetViewHodler retweetViewHodler = (RetweetViewHodler) holder;
            FillContent.fillTitleBar(mContext,mDatas.get(position),retweetViewHodler.profile_img,
                    retweetViewHodler.profile_img_verified,retweetViewHodler.titlebar_profile_name,
                    retweetViewHodler.titlebar_profile_time,retweetViewHodler.titlebar_weiboComFrom);
        }
    }
    @Override
    public int getItemCount() {
        if(mDatas!=null){
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mDatas.get(position).retweeted_status!=null){
            return TYPE_RETWEET_ITEM;
        }else{
            return TYPE_ORIGIN_ITEM;
        }

    }

    /**
     * 原创微博
     */
    public static class OriginViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout titlebar_layout;
        public  ImageView profile_img;
        public  ImageView profile_img_verified;
        public  TextView titlebar_profile_name;
        public  TextView titlebar_profile_time;
        public  TextView titlebar_weiboComFrom;
        public  ImageView titlebar_arrow;
        public  TextView bottombar_retweet;
        public  LinearLayout bottombar_retweet_layout;
        public  TextView bottombar_comment;
        public  LinearLayout bottombar_comment_layout;
        public  TextView bottombar_attitude;
        public  LinearLayout bottombar_attitude_layout;
        public  RecyclerView weibo_origin_img;
        //原创微博内容
        public TextView weibo_origin_content;
        public OriginViewHolder(View v) {
            super(v);
            titlebar_layout= (LinearLayout) v.findViewById(R.id.titlebar_layout);
            profile_img= (ImageView) v.findViewById(R.id.profile_img);
            profile_img_verified = (ImageView) v.findViewById(R.id.profile_img_verified);
            titlebar_profile_name = (TextView) v.findViewById(R.id.titlebar_profile_name);
            titlebar_profile_time = (TextView) v.findViewById(R.id.titlebar_profile_time);
            titlebar_weiboComFrom = (TextView) v.findViewById(R.id.titlebar_weiboComFrom);
            titlebar_arrow = (ImageView) v.findViewById(R.id.titlebar_arrow);
            weibo_origin_img = (RecyclerView) v.findViewById(R.id.weibo_origin_img);
            bottombar_retweet = (TextView) v.findViewById(R.id.bottombar_retweet);
            bottombar_retweet_layout = (LinearLayout) v.findViewById(R.id.bottombar_retweet_layout);
            bottombar_comment = (TextView) v.findViewById(R.id.bottombar_comment);
            bottombar_comment_layout = (LinearLayout) v.findViewById(R.id.bottombar_comment_layout);
            bottombar_attitude = (TextView) v.findViewById(R.id.bottombar_attitude);
            bottombar_attitude_layout = (LinearLayout) v.findViewById(R.id.bottombar_attitude_layout);
            weibo_origin_content = (TextView) v.findViewById(R.id.weibo_origin_content);
        }
    }

    /**
     * 转发微博,
     */
    public static class RetweetViewHodler extends RecyclerView.ViewHolder{
        public LinearLayout titlebar_layout;
        public  ImageView profile_img;
        public  ImageView profile_img_verified;
        public  TextView titlebar_profile_name;
        public  TextView titlebar_profile_time;
        public  TextView titlebar_weiboComFrom;
        public  ImageView titlebar_arrow;
        public  TextView bottombar_retweet;
        public  LinearLayout bottombar_retweet_layout;
        public  TextView bottombar_comment;
        public  LinearLayout bottombar_comment_layout;
        public  TextView bottombar_attitude;
        public  LinearLayout bottombar_attitude_layout;
        public  RecyclerView weibo_origin_img;
        //原创微博内容
        public TextView weibo_origin_content;
        //转发 的 内容
        public  TextView weibo_retweet_cotent;

        public RetweetViewHodler(View v) {
            super(v);
            titlebar_layout= (LinearLayout) v.findViewById(R.id.titlebar_layout);
            profile_img= (ImageView) v.findViewById(R.id.profile_img);
            profile_img_verified = (ImageView) v.findViewById(R.id.profile_img_verified);
            titlebar_profile_name = (TextView) v.findViewById(R.id.titlebar_profile_name);
            titlebar_profile_time = (TextView) v.findViewById(R.id.titlebar_profile_time);
            titlebar_weiboComFrom = (TextView) v.findViewById(R.id.titlebar_weiboComFrom);
            titlebar_arrow = (ImageView) v.findViewById(R.id.titlebar_arrow);
            weibo_origin_img = (RecyclerView) v.findViewById(R.id.weibo_origin_img);
            bottombar_retweet = (TextView) v.findViewById(R.id.bottombar_retweet);
            bottombar_retweet_layout = (LinearLayout) v.findViewById(R.id.bottombar_retweet_layout);
            bottombar_comment = (TextView) v.findViewById(R.id.bottombar_comment);
            bottombar_comment_layout = (LinearLayout) v.findViewById(R.id.bottombar_comment_layout);
            bottombar_attitude = (TextView) v.findViewById(R.id.bottombar_attitude);
            bottombar_attitude_layout = (LinearLayout) v.findViewById(R.id.bottombar_attitude_layout);
            weibo_origin_content = (TextView) v.findViewById(R.id.weibo_origin_content);
            weibo_retweet_cotent = (TextView) v.findViewById(R.id.weibo_retweet_cotent);
        }
    }
}
