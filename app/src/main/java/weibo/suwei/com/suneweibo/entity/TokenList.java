package weibo.suwei.com.suneweibo.entity;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by suwei on 2017/4/9.
 */

public class TokenList {
    public ArrayList<Token> tokenList = new ArrayList<Token>();
    public int total_number;
    public String current_uid;


    public static TokenList parse(String jsonString) {
        Gson gson = new Gson();
        TokenList tokenList = gson.fromJson(jsonString, TokenList.class);
        return tokenList;
    }
}
