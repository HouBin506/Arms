package com.herenit.arms.base.adapter.rv.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * Created by HouBin on 2018/8/8.
 */

public class WrapperUtils {


    public interface SpanSizeCallback {
        int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position);
    }

    /**
     * 在使用RecyclerView时候，会遇到这样的情况：当时用GridLayoutManager时，设置空布局，空布局会只占用某一个item（也就是不能居中显示）
     * 解决这样的一个问题，就会使用到GridLayoutManager.SpanSizeLookup
     * <p>
     * getSpanSize的SpanSize为多少，就表示占用几个Item。
     * gridLayoutManager.getSpanCount()表示我们一开始设置的一行几个Item数量
     *
     * @param innerAdapter
     * @param recyclerView
     * @param callback
     */
    public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter, RecyclerView recyclerView, SpanSizeCallback callback) {
        innerAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    /**
     * 当RecyclerView使用瀑布流（StaggeredGridLayoutManager）时，可以通过侧方法设置Item是否要占据总宽度或者总高度，
     * 当瀑布流中某个Item需要横穿的场景时，可用此方法
     *
     * @param holder
     */
    public static void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) lp;
            layoutParams.setFullSpan(true);
        }
    }
}
