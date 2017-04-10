package weibo.suwei.com.suneweibo.ui.login.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;

import weibo.suwei.com.suneweibo.R;
import weibo.suwei.com.suneweibo.mvp.presenter.HomeFragmentPresenter;
import weibo.suwei.com.suneweibo.mvp.presenter.imp.HomeFragmentPresentImp;
import weibo.suwei.com.suneweibo.mvp.view.HomeFragmentView;
import weibo.suwei.com.suneweibo.ui.login.home.webitem.WeiboAdapter;
import weibo.suwei.com.suneweibo.widget.HeaderAndFooterRecyclerViewAdapter;

/**
 * Created by suwei on 2017/4/3.
 */

public class HomeFragment extends Fragment implements HomeFragmentView{
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mGroupName;
    private LinearLayout mGroup;
    private boolean isUserExist;
    private ArrayList<Status> mDatas;
    private WeiboAdapter mWeiboAdapter;
    private Context mContext;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private HomeFragmentPresenter mHomeFragmentPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //通过 newInstance() 传来了 是否有用户存在的“真假”，并存到了 Argument中,先取出
        //这里没有 提供 空参数的构造函数，如果在 MainActivity中使用 new HomeFragment();
        //getArguments().getBoolean("isUserExist_from_AccountActivity")找不到对应的值，会出错！！！！
        isUserExist = getArguments().getBoolean("isUserExist_from_AccountActivity");
        mContext = getContext();
        mHomeFragmentPresenter = new HomeFragmentPresentImp(this);

        mView = inflater.inflate(R.layout.fragment_home_login, container, false);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefreshLayout);
        //在build.gradle添加依赖: compile 'com.android.support:recyclerview-v7:25.3.1'
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);

        //获取顶部bar 中间的文本信息"首页", 以及点击首页的group
        //在 方法  setGroupName 设置顶部bar中间要显示的 用户名
        mGroupName = (TextView) mView.findViewById(R.id.topbar_groupName);
        mGroup = (LinearLayout) mView.findViewById(R.id.topbar_group);

        initRecyclerView();
        initSwipeRefreshLayout();
        initGroupWindow();
        return mView;
    }
    /**
     *  通过newInstance 的方法，将 “当前是否有用户存在”的真假 传进来
     */
    public static HomeFragment newInstance(boolean isUserExist_from_AccountActivity){
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isUserExist_from_AccountActicity",isUserExist_from_AccountActivity);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    /**
     *初始化 RecyclerView
     */
    public void initRecyclerView(){
        mDatas = new ArrayList<Status>();
        //WeiboAdapter 是 加载了 原创微博和 转发微博的内容
        mWeiboAdapter = new WeiboAdapter(mDatas,mContext);

        //现在要把 WeiboAdapter 的内容  显示在  RecyclerView组件上
        //因为 RecyclerView 的真正Adapter是 HeaderAndFooterRecyclerViewAdapter
        //把 mWeiboAdapter 作为参数 传进去, 在 HeaderAndFooterRecyclerViewAdapter中的onCreateViewHolder中
        //调用了 WeiboAdapter的onCreateViewHolder
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mWeiboAdapter);
        //往 mRecycler 中 添加 LayoutManager 和 Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }
    /**
     * 初始化 SwipeFreshLayout
     */
    public void initSwipeRefreshLayout(){
        mHomeFragmentPresenter.refreshUserName(mContext);
    }

    /**
     * 初始化 顶部bar中间的 “我的分组”
     */
    public void initGroupWindow(){}

    @Override
    public void showRecyclerView() {

    }

    @Override
    public void hideRecyclerView() {

    }


   @Override
    public void setGroupName(String userName) {
        mGroupName.setText(userName);
       //如果 所在的 组件 不显示的话，设置显示
       if(mGroup.getVisibility()!=View.VISIBLE){
           mGroup.setVisibility(View.VISIBLE);
       }
    }
}
