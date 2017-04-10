package weibo.suwei.com.suneweibo.mvp.view;

/**
 * Created by suwei on 2017/4/4.
 */

public interface HomeFragmentView {
    /**
     * 显示，隐藏 RecyclerView
     */
    public void showRecyclerView();
    public void hideRecyclerView();
    /**
     * 设置 顶部栏的 分组名（初始中间写着“我的首页”）
     */
    public void setGroupName(String userName);
}
