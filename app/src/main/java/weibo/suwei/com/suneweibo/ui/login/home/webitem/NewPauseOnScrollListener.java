package weibo.suwei.com.suneweibo.ui.login.home.webitem;

import android.support.v7.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by suwei on 2017/4/4.
 */

public class NewPauseOnScrollListener extends RecyclerView.OnScrollListener{
    private ImageLoader imageLoader;
    private Boolean pauseOnScroll;
    private Boolean pauseOnSettling;
    private RecyclerView.OnScrollListener mListener;

    //使用ImageLoader的pauseonscrolllistener方法可以设置为listview滑动停止后加载图片，这个是解决卡顿很好的方法
    //pauseOnScroll和pauseOnFling都设置为true，表示滑动停止后才进行加载图片
    //listView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
    //这里 RecyclerView使用的不是 pauseOnFling 而是 pauseOnSettling
    public NewPauseOnScrollListener(ImageLoader imageLoader,boolean pauseOnScroll,boolean pauseOnSettling){
        this.imageLoader = imageLoader;
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnSettling = pauseOnSettling;
    }
    public NewPauseOnScrollListener(ImageLoader imageLoader,boolean pauseOnScroll,boolean pauseOnSetting,
                                    RecyclerView.OnScrollListener mListener){
        this(imageLoader,pauseOnScroll,pauseOnSetting);
        this.mListener = mListener;
    }

    /**
     * RecyclerView.OnScrollListener监听器会回调两个方法：
     * onScrollStateChanged : 滚动状态变化时回调,onScrolled : 滚动时回调
     * newState有： 屏幕停止滚动：SCROLL_STATE_IDLE = 0;
     * 正在被外部拖拽,一般为用户正在用手指滚动：SCROLL_STATE_DRAGGING = 1;（手指拖着屏幕滚动）
     * 自动滚动开始：SCROLL_STATE_SETTLING = 2; (手指用力一拽，屏幕会自动滚动起来)
     * @param recyclerView
     * @param newState
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch(newState){
            case RecyclerView.SCROLL_STATE_IDLE:
                //屏幕停止滚动的时候，恢复图片加载
                imageLoader.resume();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                //屏幕滚动的时候，暂停图片加载
                imageLoader.pause();
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                imageLoader.pause();
                break;
        }
        if(mListener != null){
            mListener.onScrollStateChanged(recyclerView,newState);
        }
    }
    /**
     * 正在滚动的状态
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(mListener != null){
            mListener.onScrolled(recyclerView,dx,dy);
        }
    }
}
