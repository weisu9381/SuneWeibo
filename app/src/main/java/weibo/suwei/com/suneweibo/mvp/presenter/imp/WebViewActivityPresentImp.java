package weibo.suwei.com.suneweibo.mvp.presenter.imp;

import android.content.Context;

import weibo.suwei.com.suneweibo.mvp.model.TokenListModel;
import weibo.suwei.com.suneweibo.mvp.model.imp.TokenListModellImp;
import weibo.suwei.com.suneweibo.mvp.presenter.WebViewActivityPresent;
import weibo.suwei.com.suneweibo.mvp.view.WebViewActivityView;

/**
 * Created by suwei on 2017/4/7.
 */

public class WebViewActivityPresentImp implements WebViewActivityPresent {
    private TokenListModel mTokenListModel;
    private WebViewActivityView mWebViewActivityView;
    public WebViewActivityPresentImp(WebViewActivityView view){
        mWebViewActivityView = view;
        mTokenListModel = new TokenListModellImp();
    }
    /**
     * 输入用户名密码登录以后，会返回url：诸如我的是：
     * http://oauth.weico.cc#access_token=..&remind_in=..&expires_in=...&refresh_token=..&uid=..&scope=..
     */
    @Override
    public void handleUrl(Context context,String url){
        if(!url.contains("error")){
            int token_index = url.indexOf("access_token=");
            int expires_in_index = url.indexOf("expires_in=");
            int refresh_token_index = url.indexOf("refresh_token");
            int uid_index = url.indexOf("uid=");
            //截取.    url.indexOf("&",index); 表示在index位置之后"&"第一次出现的位置
            String token = url.substring(token_index+13,url.indexOf("&",token_index));
            String expires_in = url.substring(expires_in_index+11,url.indexOf("&",expires_in_index));
            String refresh_token = url.substring(refresh_token_index+14,url.indexOf("&",refresh_token_index));
            String uid;
            if(url.contains("scope=")){
                uid = url.substring(uid_index+4,url.indexOf("&",url.indexOf("&",uid_index)));
            }else{
                uid = url.substring(uid_index+4);
            }
            //添加到  “登录列表缓存.txt”
            mTokenListModel.addToken(context,token,expires_in,refresh_token,uid);
            // 开启 MainActivity
            mWebViewActivityView.startMainActicity();
        }

    }


}
