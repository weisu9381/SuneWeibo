package weibo.suwei.com.suneweibo.entity;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

/**
 * 多个 用户  WeiboSDK中User集合
 * Created by suwei on 2017/4/9.
 */

public class UserList {
    private ArrayList<User> userList = new ArrayList<User>();
    public UserList parse(String jsonString){
        if(TextUtils.isEmpty(jsonString)){
            return null;
        }
        return new Gson().fromJson(jsonString,UserList.class);
    }
}
