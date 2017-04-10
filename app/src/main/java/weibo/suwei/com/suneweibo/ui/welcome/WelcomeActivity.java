package weibo.suwei.com.suneweibo.ui.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import weibo.suwei.com.suneweibo.R;
import weibo.suwei.com.suneweibo.entity.AccessTokenKeeper;
import weibo.suwei.com.suneweibo.ui.login.MainActivity;
import weibo.suwei.com.suneweibo.ui.unlogin.activity.UnLoginActivity;

/**
 * Created by suwei on 2017/3/30.
 */

public class WelcomeActivity extends Activity{
    private Intent mStartIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

        //AccessTokenKeeper的readAccessToken()方法返回 Oauth2AccessToken
        //isSessionValid()是 Oauth2AccessToken中的方法，判断 AccessToken是否有效
        if(AccessTokenKeeper.readAccessToken(this).isSessionValid()){
            mStartIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        }else{
            mStartIntent = new Intent(WelcomeActivity.this, UnLoginActivity.class);
        }
        //timer.schedule(task, time); time为Date类型：在指定时间执行一次（不周期）。
        //timer.schedule(task, delay); delay 为long类型：从现在起过delay毫秒之后执行一次（不周期）
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendMessage(Message.obtain());
            }
        },500);
    }

    //引入的是  android.os.Handler
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startActivity(mStartIntent);
            //结束当前WelcomeActivity,调用finish()系统会执行回调函数 onDestroy()
            //不能直接使用 onDestroy(),因为这是系统自己调用的回调函数
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        //参数传递null 表示移除所有的 回调callbacks 和 信息Messages
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
