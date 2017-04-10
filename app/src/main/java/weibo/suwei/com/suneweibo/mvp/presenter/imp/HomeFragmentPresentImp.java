package weibo.suwei.com.suneweibo.mvp.presenter.imp;

import android.content.Context;

import com.sina.weibo.sdk.openapi.models.User;

import weibo.suwei.com.suneweibo.entity.AccessTokenKeeper;
import weibo.suwei.com.suneweibo.mvp.model.StatusListModel;
import weibo.suwei.com.suneweibo.mvp.model.UserModel;
import weibo.suwei.com.suneweibo.mvp.model.imp.StatusListModelImp;
import weibo.suwei.com.suneweibo.mvp.model.imp.UserModelImp;
import weibo.suwei.com.suneweibo.mvp.presenter.HomeFragmentPresenter;
import weibo.suwei.com.suneweibo.mvp.view.HomeFragmentView;

/**
 * Created by suwei on 2017/4/5.
 */

public class HomeFragmentPresentImp implements HomeFragmentPresenter{
    private HomeFragmentView mHomeFragmentView;
    private StatusListModel mStatusListModel;
    private UserModel mUserModel;

    /**
     * 构造函数，传进 view
     */
    public HomeFragmentPresentImp(HomeFragmentView mHomeFragmentView){
        this.mHomeFragmentView = mHomeFragmentView;
        mStatusListModel = new StatusListModelImp();
        mUserModel = new UserModelImp();
    }
    /**
     * HomeFragment的 SwipeRefreshLayout 一刷新，顶部栏的中间就会显示 用户名
     */
    @Override
    public void refreshUserName(Context context){
        // 这里没有返回值，用到了“回调机制”
        // 这里UserModelImp 中的show方法，调用了UsersAPI的show方法（其中传入了名为userRequestListener的RequestListener）
        // userRequestListener 的onComplete中调用了 UserModel.OnUserDetailRequestFinish接口的onComplete方法
        // userRequestListener的onWeiboException中调用了 UserModel.OnUserDetailRequestFinish接口的onError方法
        // 在这里 实现了OnUserDetailRequestFinish接口的两个方法。
        // 按ctrl+点击鼠标左键 我看到WeiboSDK中的 LoginoutButton中调用了RequestListener的两个方法(我不明白)
        mUserModel.show(AccessTokenKeeper.readAccessToken(context).getUid(), context, new UserModel.OnUserDetailRequestFinish() {
            @Override
            public void onComplete(User user) {
                //这里的 User 就是得到的 用户信息
                mHomeFragmentView.setGroupName(user.name);
            }

            @Override
            public void onError(String error) {
                mHomeFragmentView.setGroupName("我的首页");
            }
        });
    }

}
