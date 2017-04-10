package weibo.suwei.com.suneweibo.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * 该类定义了 微博授权时所需要的参数
 * Token 令牌   Access Token 访问令牌
 * @author SINA
 * @since 2013-10-07
 */

public class AccessTokenKeeper {
    //保存到 SharePreferences
    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";
    //往 PREFERENCES_NAME 文件中保存的 键值对:
    // Oauth2AccessToken 封装了 uid , access_token 访问令牌,
    // expires_in 访问令牌过期时间点,为long 型, refresh_token 刷新访问令牌
    private static final String KEY_UID = "uid";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    /**
     * 保存 Oauth2AccessToken 对象到 SharedPreferences
     *
     * @param context 应用程序上下文环境
     * @param token Oauth2AccessToken对象
     */
    public static void writeAccessToken(Context context,Oauth2AccessToken token){
        if(context == null || token == null){
            return;
        }
        //Context.MODE_PRIVATE 默认操作模式，私有数据，只能被应用本身访问，写入的内容会覆盖原文件的内容
        //Context.MODE_APPEND 会检查文件是否存在，存在就往文件追加内容，否则就创建新文件
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_UID,token.getUid());
        editor.putString(KEY_ACCESS_TOKEN,token.getToken());
        editor.putLong(KEY_EXPIRES_IN,token.getExpiresTime());
        editor.putString(KEY_REFRESH_TOKEN,token.getRefreshToken());
        //commit() 有返回值，apply()没有返回值，apply()失败了是不会报错的
        //apply()写入文件的操作是 异步的，会把 Runnable放到线程池 中执行，
        // 而commit()写入文件的操作是在当前线程同步执行的，
        // 如果先后 apply()很多次，会以最后一次apply()为准，
        //如果 commit()的时候，前面有commit()未结束，或者前面有apply()正在 异步提交，这个commit()会阻塞
        editor.apply();
    }

    /**
     * 从 SharedPreferences 读取 Oauth2AccessToken信息
     *
     * @param context 应用程序上下文环境
     */
    public static Oauth2AccessToken readAccessToken(Context context){
        if(context == null){
            return null;
        }
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_APPEND);
        token.setUid(pref.getString(KEY_UID,""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN,""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN,0));
        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN,""));
        return token;
    }
    /**
     * 清空 SharedPreferences 中 Oauth2AccessToken信息
     *
     * @param context 应用程序上下文环境
     */
    public static void clear(Context context){
        if(context == null){
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME,Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
