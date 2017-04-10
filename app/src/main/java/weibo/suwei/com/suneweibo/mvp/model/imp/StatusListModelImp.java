package weibo.suwei.com.suneweibo.mvp.model.imp;

import android.content.Context;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

import weibo.suwei.com.suneweibo.mvp.model.StatusListModel;
import weibo.suwei.com.suneweibo.entity.AccessTokenKeeper;
import weibo.suwei.com.suneweibo.entity.Constants;

/**
 * Created by suwei on 2017/4/5.
 */

public class StatusListModelImp implements StatusListModel {
    private Context mContext;
    private OnDataFinishListener mOnDataFinishListener;

    /**
     *   当前的分组
     */
//    private long mCurrentGroup = Constants.GROUP_TYPE_ALL;
    /**
     *  是否全局刷新
     */
    private boolean mRefreshAll = true;
    /**
     * 获取当前登录用户及其所关注用户的最新微博。
     * WeiboSDK中的 StatusAPI中的 friendsTimeLine与 homeTimeLine 一个意思
     * @param context
     * @param onDataFinishListener
     */
    @Override
    public void friendsTimeLine(Context context, OnDataFinishListener onDataFinishListener) {
        mContext = context;
        mOnDataFinishListener = onDataFinishListener;
        //使用的是 WeiboSDK下 com.sina.weibo.sdk.openapi下的StatusesAPI
        StatusesAPI statusesAPI = new StatusesAPI(context, Constants.APP_KEY,
                AccessTokenKeeper.readAccessToken(context));
        statusesAPI.friendsTimeline(0,0,30,1,false,0,false,pullToRefreshListener);
    }
    private RequestListener pullToRefreshListener = new RequestListener() {
        @Override
        public void onComplete(String s) {

        }

        @Override
        public void onWeiboException(WeiboException e) {

        }
    };
}
