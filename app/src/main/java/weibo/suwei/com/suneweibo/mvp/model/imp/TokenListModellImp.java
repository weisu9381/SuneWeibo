package weibo.suwei.com.suneweibo.mvp.model.imp;

import android.content.Context;

import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import weibo.suwei.com.suneweibo.entity.AccessTokenKeeper;
import weibo.suwei.com.suneweibo.entity.Token;
import weibo.suwei.com.suneweibo.entity.TokenList;
import weibo.suwei.com.suneweibo.mvp.model.TokenListModel;
import weibo.suwei.com.suneweibo.utils.SDCardUtils;

/**
 * 登录列表缓存  TokenList
 * Created by suwei on 2017/4/7.
 */

public class TokenListModellImp implements TokenListModel {
    //TokenList要 缓存 到的 文件 的路径
    private String dir = SDCardUtils.getSDCardPath()+ "/bsune/";
    //TokenList 要缓存到的 文件名
    private String fileName = "登录列表缓存.txt";
    /**
     * 添加 Token , 即添加账号
     */
    @Override
    public void addToken(Context context,String token,String expires_in,String refresh_token,String uid){
        Gson gson = new Gson();
        //保存 一个Token信息
        Token mToken = new Token(token,expires_in,refresh_token,uid);
        //从 登录列表缓存.txt 中读取 TokenList
        TokenList tokenList = TokenList.parse(SDCardUtils.get(context, SDCardUtils.getSDCardPath() + "/suneWeibo/", "登录列表缓存.txt"));
        if(tokenList==null || tokenList.tokenList.size()==0){
            tokenList = new TokenList();
        }
        //重复登录的话，就不添加 Token
        for (int i = 0; i < tokenList.tokenList.size(); i++) {
            if(tokenList.tokenList.get(i).uid.equals(uid)){
                return;
            }
        }
        //否则的话，就 把 TokenList的内容 保存到 “登录列表缓存.txt”中
        tokenList.tokenList.add(mToken);
        tokenList.total_number = tokenList.tokenList.size();
        tokenList.current_uid = uid;
        SDCardUtils.put(context, SDCardUtils.getSDCardPath() + "/suneWeibo/", "登录列表缓存.txt", gson.toJson(tokenList));

        //最后 更新 在 SharedPreferences中保存的 Token信息
        updateAccessToken(context,token,expires_in,refresh_token,uid);
    }
    /**
     * 更新在 SharedPreferences中保存的 Token信息, 更新为 当前的账号
     */
    public void updateAccessToken(Context context,String token,String expires_in,String refresh_token,String uid){
        Oauth2AccessToken mAccessToken = new Oauth2AccessToken();
        mAccessToken.setToken(token);
        mAccessToken.setExpiresIn(expires_in);
        mAccessToken.setRefreshToken(refresh_token);
        mAccessToken.setUid(uid);
        AccessTokenKeeper.writeAccessToken(context,mAccessToken);
    }

}
