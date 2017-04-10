package weibo.suwei.com.suneweibo.mvp.model;

import android.content.Context;

/**
 * Created by suwei on 2017/4/7.
 */

public interface TokenListModel {
    //添加 ArrayList<Token> 到 “登录列表缓存.txt”
    public void addToken(Context context, String token, String expires_in, String refresh_token, String uid);
}
