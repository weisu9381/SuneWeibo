package weibo.suwei.com.suneweibo.mvp.model;

import android.content.Context;

import com.sina.weibo.sdk.openapi.models.User;

/**
 * Created by suwei on 2017/4/5.
 */

public interface UserModel {
    /**
     * 回调机制，按 ctrl+H 可以查看 谁实现了该接口里面的内容
     * 按 ctrl+点击鼠标左键  可以看到 谁调用了该方法
     */
    interface OnUserDetailRequestFinish{
        void onComplete(User user);
        void onError(String error);
    }

    //根据 用户id 获取 用户信息
    public void show(long id,Context context,OnUserDetailRequestFinish onUserDetailRequestFinish);
    //根据 用户名name 获取用户信息
    public void show(String screenName,Context context,OnUserDetailRequestFinish onUserDetailRequestFinish);
    //缓存 Cache  用户信息
    public void cacheSave_user(Context context,String response);
    //加载用户信息 的缓存
    public String cacheLoad_user(Context contect);
}
