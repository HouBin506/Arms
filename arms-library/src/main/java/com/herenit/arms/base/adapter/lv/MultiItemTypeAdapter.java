package com.herenit.arms.base.adapter.lv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.herenit.arms.base.adapter.lv.base.ItemViewDelegate;
import com.herenit.arms.base.adapter.lv.base.ItemViewDelegateManager;

import java.util.List;

/**
 * Created by HouBin on 2018/8/6.
 */

public class MultiItemTypeAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;

    private ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> delegate) {
        mItemViewDelegateManager.addDelegate(delegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.getItemViewDelegateCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get((position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate<T> delegate = mItemViewDelegateManager.getItemViewDelegate(mDatas.get(position), position);
        int layoutId = delegate.getItemViewLayoutId();
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            holder = new ViewHolder(mContext, convertView, parent, position);
            holder.mLayoutId = layoutId;
            onViewHolderCreated(holder, holder.getConvertView());
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

    protected void convert(ViewHolder holder, T t, int position) {
        mItemViewDelegateManager.convert(holder, t, position);
    }

    public void onViewHolderCreated(ViewHolder holder, View convertView) {
    }
}
