package weibo.suwei.com.suneweibo.ui.unlogin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import weibo.suwei.com.suneweibo.R;
import weibo.suwei.com.suneweibo.entity.Constants;
import weibo.suwei.com.suneweibo.ui.unlogin.fragment.DiscoverFragment;
import weibo.suwei.com.suneweibo.ui.unlogin.fragment.HomeFragment;
import weibo.suwei.com.suneweibo.ui.unlogin.fragment.MessageFragment;
import weibo.suwei.com.suneweibo.ui.unlogin.fragment.ProfileFragment;

public class UnLoginActivity extends AppCompatActivity {
    //当前 选中 的 bottomBar
    private int mCurrentIndex;
    //按 bottomBar 显示的 Fragment ,  中间的 + 提示“未登录”故没有 0X003
    private static final int HOME_FRAGMENT = 0X001;
    private static final int MESSAGE_FRAGMENT = 0X002;
    private static final int DISCOVERY_FRAGMENT = 0X004;
    private static final int PROFILE_FRAGMENT = 0X005;

    private Context mContext;
    //以下 5个 为 bottomBar : 主页，消息，+，发现，我
    private RelativeLayout mHomeTab, mMessageTab, mDiscoveryTab, mProfileTab;
    private ImageView mPostTab;

    private FragmentManager mFragmentManager;
    //碎片: 显示除 bottomBar以外的界面
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private DiscoverFragment mDiscoverFragment;
    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unlogin_mainactivity_layout);
        mContext = this;
        mHomeTab = (RelativeLayout) findViewById(R.id.home_layout);
        mMessageTab = (RelativeLayout) findViewById(R.id.message_layout);
        mDiscoveryTab = (RelativeLayout) findViewById(R.id.discovery_layout);
        mProfileTab = (RelativeLayout) findViewById(R.id.profile_layout);
        mPostTab = (ImageView) findViewById(R.id.iv_post);
        //在 app包下的 FragmentManager 用 getFragmentManager() 获得
        //在 v4 包下的 FragmentManager 用 getSupportFragmentManager()获得
        mFragmentManager = getSupportFragmentManager();

        //显示  主页 的 Fragment
        setTabFragment(HOME_FRAGMENT);
        //自己写的 点击 BottomBar 相应事件
        onClickListener();
    }

    /**
     * 点击bottomBar 显示的 Fragment
     */
    public void setTabFragment(int index) {
        //对Fragement的操作都是通过 FragmentTransaction 来执行的
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //自定义方法
        hideAllFragment(transaction);
        switch (index) {
            case HOME_FRAGMENT:
                mHomeTab.setSelected(true);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.contentLayout, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }
                mCurrentIndex = HOME_FRAGMENT;
                break;
            case MESSAGE_FRAGMENT:
                mMessageTab.setSelected(true);
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    transaction.add(R.id.contentLayout, mMessageFragment);
                } else {
                    transaction.show(mMessageFragment);
                }
                mCurrentIndex = MESSAGE_FRAGMENT;
                break;
            case DISCOVERY_FRAGMENT:
                mDiscoveryTab.setSelected(true);
                if (mDiscoverFragment == null) {
                    mDiscoverFragment = new DiscoverFragment();
                    transaction.add(R.id.contentLayout, mDiscoverFragment);
                } else {
                    transaction.show(mDiscoverFragment);
                }
                mCurrentIndex = DISCOVERY_FRAGMENT;
                break;
            case PROFILE_FRAGMENT:
                mProfileTab.setSelected(true);
                if (mProfileFragment == null) {
                    mProfileFragment = new ProfileFragment();
                    transaction.add(R.id.contentLayout, mProfileFragment);
                } else {
                    transaction.show(mProfileFragment);
                }
                mCurrentIndex = PROFILE_FRAGMENT;
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有的 Fragment ,并将 bottomBar 设置为未选中状态
     */
    public void hideAllFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }
        if (mDiscoverFragment != null) {
            transaction.hide(mDiscoverFragment);
        }
        if (mProfileFragment != null) {
            transaction.hide(mProfileFragment);
        }
        mHomeTab.setSelected(false);
        mMessageTab.setSelected(false);
        mDiscoveryTab.setSelected(false);
        mProfileTab.setSelected(false);
    }

    /**
     * 点击 BottomBar
     */
    public void onClickListener() {
        mHomeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(HOME_FRAGMENT);
            }
        });
        mMessageTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(MESSAGE_FRAGMENT);
            }
        });
        mDiscoveryTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(DISCOVERY_FRAGMENT);
            }
        });
        mProfileTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(PROFILE_FRAGMENT);
            }
        });
        mPostTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     *在 toolbar_home_unlogin.xml 等 顶部topBar中的“注册”“登录”
     * 以及 unlogin_mainactivity_layout 等 “注册”“登录”
     * 都添加了 android:clickable="true" android:onClick="openLoginWebView"
     * 通过 intent 将 Contants中的 authurl 要打开的网页地址 传递给 WebViewActivity
     */
    public void openLoginWebView(View v){
        Intent intent = new Intent(mContext,WebViewActivity.class);
        intent.putExtra("url_from_UnLoginActivity", Constants.authurl);
        startActivity(intent);
        finish();
    }
}
