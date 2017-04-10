package weibo.suwei.com.suneweibo.mvp.model;

import android.content.Context;

/**
 * Created by suwei on 2017/4/5.
 */

public interface StatusListModel {
    interface OnDataFinishListener{
        void onMoreData();
        void onDataInFinishLoad();
        void onDataFinish();
        void onError();
    }
    public void friendsTimeLine(Context context,OnDataFinishListener onDataFinishListener);
}
