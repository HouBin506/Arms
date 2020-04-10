package com.herenit.arms.base.adapter.lv.base;

import android.support.v4.util.SparseArrayCompat;

import com.herenit.arms.base.adapter.lv.ViewHolder;
import com.herenit.arms.utils.Preconditions;

/**
 * Created by HouBin on 2018/8/3.
 */

public class ItemViewDelegateManager<T> {

    SparseArrayCompat<ItemViewDelegate> delegates = new SparseArrayCompat<>();

    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    /**
     * 个人觉得，这个方法最好不要调用，因为delegates集合，默认是按照当前元素的索引作为key存储的
     * 如果传入viewType作为key，容易发生错误
     *
     * @param viewType
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        Preconditions.checkNotNull(delegate, "%s can not be null", ItemViewDelegate.class.getName());
        int indexOfRemove = delegates.indexOfValue(delegate);
        if (indexOfRemove >= 0) {
            delegates.removeAt(indexOfRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int viewType) {
        int indexOfRemove = delegates.indexOfKey(viewType);
        if (indexOfRemove >= 0) {
            delegates.removeAt(indexOfRemove);
        }
        return this;
    }

    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int x = delegatesCount - 1; x >= 0; x--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(x);
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(x);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 此方法最终是由Adapter的getView方法调取的
     *
     * @param holder
     * @param item
     * @param position
     */
    public void convert(ViewHolder holder, T item, int position) {
        int delegatesCount = delegates.size();
        for (int x = 0; x < delegatesCount; x++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(x);
            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(int viewType) {
        return delegates.get(viewType).getItemViewLayoutId();
    }

    /**
     * 个人认为此方法的返回值不一定准确，除非delegates集合<int viewType,ItemViewDelegate<T>>的key是按照
     * 从0开始递增，也就是根据集合的索引
     *
     * @param delegate
     * @return
     */
    public int getItemViewType(ItemViewDelegate delegate) {
        return delegates.indexOfValue(delegate);
    }

    public ItemViewDelegate<T> getItemViewDelegate(T item, int position) {
        int delegatesCount = delegates.size();
        for (int x = delegatesCount - 1; x >= 0; x--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(x);
            if (delegate.isForViewType(item, position))
                return delegate;
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }

    public ItemViewDelegate<T> getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }
}
