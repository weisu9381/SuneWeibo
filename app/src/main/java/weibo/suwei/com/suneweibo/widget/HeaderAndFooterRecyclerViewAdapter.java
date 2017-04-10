package weibo.suwei.com.suneweibo.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * RecyclerView使用的真正的 Adapter ,   下拉刷新，上拉加载
 * Created by suwei on 2017/4/5.
 */

public class HeaderAndFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 1;

    private RecyclerView.Adapter mInnerAdapter;
    private ArrayList<View> mHeaderViews = new ArrayList<View>();
    private ArrayList<View> mFooterViews = new ArrayList<View>();

    public HeaderAndFooterRecyclerViewAdapter() {
    }

    public HeaderAndFooterRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        //传来 RecyclerView.Adapter , 为了给adapter加个registerAdapterDataObserver，监听插入/删除/移动
        //如果 原来的 mInnerAdapter 存在，就 注销AdapterDataObserver
        if (mInnerAdapter != null) {
            mInnerAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        this.mInnerAdapter = adapter;
        mInnerAdapter.registerAdapterDataObserver(mDataObserver);
    }

    /**
     * Observer观察者 模式，是移动端开发常用的设计模式
     */
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            //数据发生改变，刷新
            notifyDataSetChanged();
        }

        /**
         * 从positionStart位置开始，到itemCount范围内的 item 改变 了
         */
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + mHeaderViews.size(), itemCount);
        }

        /**
         * positionStart 到 positionStart+itemCount 范围内的item是 新插入进来的
         */
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + mHeaderViews.size(), itemCount);
        }

        /**
         * Removed  被移除
         */
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + mHeaderViews.size(), itemCount);
        }

        /**
         * Moved 移动： 从 fromPosition开始的 itemCount 个item 移动到 从toPosition开始的itemCount个位置
         */
        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int numCount = getHeaderViewsCount();
            onItemRangeMoved(fromPosition + numCount, toPosition + numCount, itemCount);
        }
    };

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    /**
     * 自定义 ViewHolder, 其作用是减少一直调用 findViewById
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * item显示类型, 一个RecyclerView混杂了多种不同viewType的 ViewHolder
     * 按原作者的意思: viewType 小于 Integer.MIN_VALUE 的是 HeaderView
     * 大于等于 Integer.MIN_VALUE+1 , 小于 Integer.MAX_VALUE/2 的是  FooterView
     * 大于 Integer.MAX_VALUE/2 的是 其它的 View
     * ？？？？？？？？？？如此分类的 依据是什么 ？？？？？？？？？？？
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int numCount = getHeaderViewsCount();
        if (viewType < TYPE_HEADER_VIEW + numCount) {
            return new MyViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
            return new MyViewHolder(mFooterViews.get(viewType - TYPE_HEADER_VIEW));
        } else {
            return mInnerAdapter.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
        }
    }

    /**
     * 数据的绑定显示
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numCount = getHeaderViewsCount();
        if (position >= numCount && position < numCount + mInnerAdapter.getItemCount()) {
            mInnerAdapter.onBindViewHolder(holder, position - numCount);
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + getFooterViewsCount() + mInnerAdapter.getItemCount();
    }

    /**
     * ？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
     *
     */
    @Override
    public int getItemViewType(int position) {
        int numCount = getHeaderViewsCount();
        int innerCount = mInnerAdapter.getItemCount();
        if (position < numCount) {
            return TYPE_HEADER_VIEW + position;
        } else if (position >= numCount && position < numCount + innerCount) {

            int innerItemViewType = mInnerAdapter.getItemViewType(position - numCount);
            if (innerItemViewType >= Integer.MAX_VALUE / 2) {
                throw new IllegalArgumentException("your adapter's return value of getViewTypeCount() must < Integer.MAX_VALUE / 2");
            }
            return innerItemViewType + Integer.MAX_VALUE / 2;
        } else {
            return TYPE_FOOTER_VIEW + position - numCount - innerCount;
        }
    }
}
