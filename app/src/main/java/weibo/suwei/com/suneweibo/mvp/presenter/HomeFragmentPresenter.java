package weibo.suwei.com.suneweibo.mvp.presenter;

import android.content.Context;

/**
 * Created by suwei on 2017/4/5.
 */

public interface HomeFragmentPresenter {
    /**HomeFragment的 SwipeRefreshLayout 一刷新，顶部栏的中间就会显示 用户名*/
    public void refreshUserName(Context context);
}
