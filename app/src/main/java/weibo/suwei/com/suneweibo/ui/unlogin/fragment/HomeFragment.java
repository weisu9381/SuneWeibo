package weibo.suwei.com.suneweibo.ui.unlogin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import weibo.suwei.com.suneweibo.R;

/**
 * Created by suwei on 2017/4/1.
 */

public class HomeFragment extends Fragment {
    private ImageView mCircleView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //第三个参数的意思是 ：是否把 选取的视图R.layout.XXX 添加到 ViewGroup中,false表示不添加
        View mView = inflater.inflate(R.layout.unlogin_mainfragment_layout,container,false);
        mCircleView = (ImageView) mView.findViewById(R.id.circleView);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.endlessrotate);
        //设置动画变化速度
        LinearInterpolator lin = new LinearInterpolator();
        animation.setInterpolator(lin);
        mCircleView.setAnimation(animation);
        return mView;
    }
}
