package weibo.suwei.com.suneweibo.ui.unlogin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import weibo.suwei.com.suneweibo.R;
import weibo.suwei.com.suneweibo.entity.Constants;
import weibo.suwei.com.suneweibo.mvp.presenter.WebViewActivityPresent;
import weibo.suwei.com.suneweibo.mvp.presenter.imp.WebViewActivityPresentImp;
import weibo.suwei.com.suneweibo.mvp.view.WebViewActivityView;
import weibo.suwei.com.suneweibo.ui.login.MainActivity;

/**
 * WebView 登录授权页面
 * 前提：在AndroidManifest添加 <uses-permission android:name="android.permission.INTERNET"/>
 *  添加 WeiboSDK 提供的 “登录web界面”“手机短信注册页面”“注册选择国家页面”
 */
public class WebViewActivity extends AppCompatActivity implements WebViewActivityView{
    private Context mContext;
    private WebView mWebView;
    private String mLoginUrl;
    private String mRedirectUrl;
    private boolean isUserExist;
    private WebViewActivityPresent mWebViewActivityPresent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mContext = this;
        mWebView = (WebView) findViewById(R.id.webview);
        //通过 UnLoginActivity 中的 openLoginWebView()方法，将Constants中的authurl 地址传过来
        //既然如此，为啥不直接用 mLoginUrl = Constants.authurl 呢？
        mLoginUrl = getIntent().getStringExtra("url_from_UnLoginActivity");
        //授权回调页
        mRedirectUrl = Constants.REDIRECT_URL;
        //是否有 用户已经存在 ，是否登录，如果没有传来值的话，默认为false
        isUserExist = getIntent().getBooleanExtra("isUserExist_from_AccountActivity",false);

        // mvp 的presenter
        mWebViewActivityPresent = new WebViewActivityPresentImp(this);
        //初始化 web页面
        initWebView();
    }

    /**
     * 初始化 WebView
     */
    private void initWebView(){
        WebSettings settings = mWebView.getSettings();
        //设置WebView是否允许执行JavaScript脚本，默认false，不允许。
        settings.setJavaScriptEnabled(true);
        //WebView是否保存表单数据，默认值true。
        settings.setSaveFormData(false);
        //重写使用缓存的方式，默认值LOAD_DEFAULT
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //如果希望点击链接继续在当前browser中响应,
        //而不是新开Android的系统browser中响应该链接,必须覆盖 WebView的WebViewClient对象。
        mWebView.setWebViewClient(new WebViewClient(){
            //读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作。
            //你打开一个 url(1) ，会执行onPageStarted , 在 url(1)中点击按钮之类的又打开 url(2),此时还是会执行 onPageStarted
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if(!url.equals("abount:blank")&& url.startsWith(Constants.REDIRECT_URL)){
                    //停止加载。
                    view.stopLoading();
                    //现在是登录成功，但不打开页面，而是调用方法获得 Url中的数据
                    mWebViewActivityPresent.handleUrl(mContext,url);
                    return;
                }
                super.onPageStarted(view, url, favicon);
            }
        });
        mWebView.loadUrl(mLoginUrl);
        //.如果不做任何处理 ,浏览网页,点击系统“Back”键,整个 Browser 会调用 finish()而结束自身,
        // 如果希望浏览的网页回退而不是推出浏览器,需要在当前Activity中处理并消费掉该 Back 事件
        // 使用 goBack()
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        if(mWebView.canGoBack()){
                            mWebView.goBack();
                        }else{
                            //如果 未登录,就关闭自己，回到未登录的界面
                            if(!isUserExist){
                                Intent intent = new Intent(WebViewActivity.this, UnLoginActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 开启 MainActivity,在 mvp/presenter/imp/WebViewActivityPresentImp.java中的handleUrl方法
     * 在 得到  令牌之后调用了 该方法
     */
    @Override
    public void startMainActicity() {
        Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
        //第一次启动
       intent.putExtra("firstStart_from_WebViewActivity",true);
        //如果 账号存在
        if(isUserExist){
            intent.putExtra("isUserExist_from_WebViewActivity",true);
            //Intent.FLAG_ACTIVITY_CLEAR_TOP 的意思是跳转到指定activity之后，销毁之前所有的activity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent);
        finish();
    }
}
