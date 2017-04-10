package weibo.suwei.com.suneweibo.ui.unlogin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import weibo.suwei.com.suneweibo.R;

/**
 * Created by suwei on 2017/4/1.
 */

public class MessageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.unlogin_messagefragment_layout,container,false);
        return mView;
    }
}
