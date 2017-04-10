package weibo.suwei.com.suneweibo.mvp.model.imp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.User;

import weibo.suwei.com.suneweibo.entity.AccessTokenKeeper;
import weibo.suwei.com.suneweibo.entity.Constants;
import weibo.suwei.com.suneweibo.mvp.model.UserModel;
import weibo.suwei.com.suneweibo.utils.SDCardUtils;

/**
 * Created by suwei on 2017/4/9.
 */

public class UserModelImp implements UserModel {
    private Context mContext;
    private OnUserDetailRequestFinish mOnUserDetailRequestFinish;

    /**
     * 根据 用户id 获得用户信息（通过WeiboSDK中的 UsersAPI提供的show方法）
     */
    @Override
    public void show(long id, Context context,OnUserDetailRequestFinish onUserDetailRequestFinish) {
        UsersAPI usersAPI = new UsersAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnUserDetailRequestFinish = onUserDetailRequestFinish;
        //UsersAPI的 show方法需要  微博API提供的回调接口 RequestListener
        usersAPI.show(id,userRequestListener);
    }

    /**
     * 根据 用户名 获得用户信息（通过WeiboSDK中的 UsersAPI提供的show方法）
     */
    @Override
    public void show(String screenName, Context context,OnUserDetailRequestFinish onUserDetailRequestFinish) {
        UsersAPI usersAPI = new UsersAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnUserDetailRequestFinish = onUserDetailRequestFinish;
        usersAPI.show(screenName,userRequestListener);
    }

    /**
     *  缓存  User用户信息的 Json格式数据
     */
    @Override
    public void cacheSave_user(Context context, String response) {
        Log.e("AAA","AAA1");
        SDCardUtils.put(mContext,SDCardUtils.getSDCardPath()+"/suneWeibo/profile",
                "我的基本信息"+AccessTokenKeeper.readAccessToken(context).getUid()+".txt",response);
        Log.e("AAA","AAA2");
    }

    /**
     * 读取 User 的缓存
     */
    @Override
    public String cacheLoad_user(Context context) {
        Log.e("AAA","AAA3");
        return SDCardUtils.get(context,SDCardUtils.getSDCardPath()+"/suneWeibo/profile",
                "我的基本信息"+AccessTokenKeeper.readAccessToken(context).getUid()+".txt");
    }
    /**
     * 微博 OpenAPI 回调接口。
     * 通过 User提供的parse方法解析获得的Json数据，得到User对象
     */
    public RequestListener userRequestListener = new RequestListener() {
        @Override
        public void onComplete(String s) {
            User user = User.parse(s);
            Log.e("AAA","AAA4");
            if(user!=null){
                Log.e("AAA","AAA5");

                cacheSave_user(mContext,s);
                Log.e("AAA","AAA6");

                mOnUserDetailRequestFinish.onComplete(user);
                Log.e("AAA","AAA7");

            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Log.e("AAA","AAA8");
            Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
            mOnUserDetailRequestFinish.onError(e.getMessage());
        }
    };

}
