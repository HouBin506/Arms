package com.herenit.arms.base.adapter.lv.base;


import com.herenit.arms.base.adapter.lv.ViewHolder;

/**
 * Created by HouBin on 2018/8/3.
 */

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);
}
