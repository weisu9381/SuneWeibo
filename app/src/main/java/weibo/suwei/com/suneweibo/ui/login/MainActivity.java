package weibo.suwei.com.suneweibo.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import weibo.suwei.com.suneweibo.R;
import weibo.suwei.com.suneweibo.ui.login.discover.DiscoverFragment;
import weibo.suwei.com.suneweibo.ui.login.home.HomeFragment;
import weibo.suwei.com.suneweibo.ui.login.message.MessageFragment;
import weibo.suwei.com.suneweibo.ui.login.profile.ProfileFragment;


public class MainActivity extends AppCompatActivity {
    //底部bar表示
    private static final String HOME_FRAGMENT = "home";
    private static final String MESSAGE_FRAGMENT = "message";
    private static final String DISCOVERY_FRAGMENT = "discovery";
    private static final String PROFILE_FRAGMENT = "profile";
    //当前处于哪个 底部Bar
    private String mCurrentIndex;

    // 底部 bar对应的 Fragment
    private HomeFragment mHomeFragment;
    private MessageFragment mMessageFragment;
    private DiscoverFragment mDiscoverFragment;
    private ProfileFragment mProfileFragment;

    private Context mContext;
    private TextView mHomeTab;
    private TextView mMessageTab;
    private TextView mDiscoveryTab;
    private TextView mProfileTab;
    private ImageView mPostTab;

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private boolean isUserExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mFragmentManager = getSupportFragmentManager();
        //获得底部Bar
        mHomeTab = (TextView) findViewById(R.id.tv_home);
        mMessageTab = (TextView) findViewById(R.id.tv_message);
        mDiscoveryTab = (TextView) findViewById(R.id.tv_discovery);
        mProfileTab = (TextView) findViewById(R.id.tv_profile);
        //底部 发微博钮
        mPostTab = (ImageView) findViewById(R.id.iv_post);
        //从 AccountActivity 传来的intent中的值:当前是否有用户存在，如果没有的话默认false
        isUserExist = getIntent().getBooleanExtra("isUserExist_from_AccountActivity",false);
        //如果是从奔溃中恢复，需要加载之前的缓存
        if(savedInstanceState!=null){
            restoreFragment(savedInstanceState);
        }else{
            //初次加载的时候，显示的是  HomeFragment
            setTabFragment(HOME_FRAGMENT);
        }

        setUpListener();
    }
    /**
     *  Activity被销毁的时候，要记录当前处于哪个页面
     *  初次加载的是 HomeFragement
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("index",mCurrentIndex);
        super.onSaveInstanceState(outState);
    }

    /**
     * 如果是从奔溃中恢复，需要加载之前的缓存
     */
    public void restoreFragment(Bundle savadInstanceState){
        mCurrentIndex = savadInstanceState.getString("index");
        mHomeFragment = (HomeFragment) mFragmentManager.findFragmentByTag(HOME_FRAGMENT);
        mMessageFragment = (MessageFragment) mFragmentManager.findFragmentByTag(MESSAGE_FRAGMENT);
        mDiscoverFragment = (DiscoverFragment) mFragmentManager.findFragmentByTag(DISCOVERY_FRAGMENT);
        mProfileFragment = (ProfileFragment) mFragmentManager.findFragmentByTag(PROFILE_FRAGMENT);
        setTabFragment(mCurrentIndex);
    }
    /**
     * 底部bar五个按钮的相应事件
     */
    public void setUpListener(){
        mHomeTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabFragment(HOME_FRAGMENT);
            }
        });
    }
    /**
     * 显示指定的 Fragement , 如果选项卡 已经在当前页面，执行alreadyAtFragment
     */
    public void setTabFragment(String index){
        mFragmentTransaction = mFragmentManager.beginTransaction();
        hideAllFragments(mFragmentTransaction);
        switch (index){
            case HOME_FRAGMENT:
                showHomeFragment();
                break;
            case MESSAGE_FRAGMENT:
                showMessageFragment();
                break;
            case DISCOVERY_FRAGMENT:
                showDiscoveryFragment();
                break;
            case PROFILE_FRAGMENT:
                showProfileFragment();
                break;
        }
        mCurrentIndex = index;
        mFragmentTransaction.commit();
    }
    /**
     * 显示 HomeFragment
     */
    public void showHomeFragment(){
        mHomeTab.setSelected(true);

        if(mHomeFragment==null){
            mHomeFragment = HomeFragment.newInstance(isUserExist);

            mFragmentTransaction.add(R.id.contentLayout,mHomeFragment,HOME_FRAGMENT);
        }else{
            mFragmentTransaction.show(mHomeFragment);
        }
    }
    /**
     * 显示 MessageFragment
     */
    public void showMessageFragment(){

    }
    /**
     * 显示 DiscoveryFragment
     */
    public void showDiscoveryFragment(){

    }
    /**
     * 显示 ProfileFragment
     */
    public void showProfileFragment(){

    }

    /**
     * 隐藏所有的 fragment
     */
    public void hideAllFragments(FragmentTransaction transaction){
        if(mHomeFragment!=null){
            transaction.hide(mHomeFragment);
        }
        if(mMessageFragment!=null){
            transaction.hide(mMessageFragment);
        }
        if(mDiscoverFragment!=null){
            transaction.hide(mDiscoverFragment);
        }
        if(mProfileFragment!=null){
            transaction.hide(mProfileFragment);
        }
        mHomeTab.setSelected(false);
        mMessageTab.setSelected(false);
        mDiscoveryTab.setSelected(false);
        mProfileTab.setSelected(false);
    }

}
