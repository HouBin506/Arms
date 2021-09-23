package com.herenit.arms.base.adapter.lv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * 存储Item的View
 * <p>
 * Created by HouBin on 2018/8/3.
 */

public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    protected int mPosition;
    protected int mLayoutId;

    public ViewHolder(Context context, View itemView, ViewGroup parent, int position) {
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArray<>();
        //向Adapter那样，将ViewHolder作为tag设置给convertView
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            ViewHolder holder = new ViewHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 判断View是否初始化
     *
     * @param viewId
     * @return
     */
    public boolean isViewInit(int viewId) {
        View view = mViews.get(viewId);
        return view != null;
    }

    /**
     * 通过控件的Id获取控件，如果没有则加入mViews
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(@NonNull int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public void updatePosition(int position) {
        this.mPosition = position;
    }

    public int getItemPosition() {
        return mPosition;
    }

    /**************************辅助方法*******************************/

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor) {
        TextView textView = getView(viewId);
        textView.setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView textView = getView(viewId);
        textView.setTextColor(ContextCompat.getColor(mContext, textColorRes));
        return this;
    }

    /**
     * 设置控件透明度
     *
     * @param viewId
     * @param alpha
     * @return
     */
    public ViewHolder setAlpha(int viewId, float alpha) {
        View view = getView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAlpha(alpha);
        } else {//Android 3.0之前的版本，不支持setAlpha方法，可以使用动画的方法替代，容易使控件出现无法点击、隐藏的效果
            AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(0);
            animation.setFillAfter(true);
            view.startAnimation(animation);
        }
        return this;
    }

    public ViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * Linkify是一个辅助类，可以自动的在TextView或其派生类中，通过RegEx（正则表达式）模式匹配
     * 来创建超链接，当TextView的内容匹配并生成超链接后，TextView内容下面就会出现下划线，单击
     * 则可以触发相应的操作。如拨号，打开浏览器。
     * <p>
     * 默认匹配所有超链接类型
     *
     * @param viewId
     * @return
     */
    public ViewHolder linkify(int viewId) {
        TextView textView = getView(viewId);
        Linkify.addLinks(textView, Linkify.ALL);
        return this;
    }

    /**
     * 设置TextView 字体
     * <p>
     * setTypeface：设置字体样式
     * setPaintFlags：可以设置一些特性，会对TextView进行重绘，
     * setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG)：通过按位或者运算符"|"可以
     * 将TextView的原本属性和Paint.SUBPIXEL_TEXT_FLAG一块设置。
     * 比如设置
     * 中划线（删除线）：Paint. STRIKE_THRU_TEXT_FLAG
     * 下划线：Paint. UNDERLINE_TEXT_FLAG
     * 抗锯齿：Paint.ANTI_ALIAS_FLAG
     *
     * @param typeface
     * @param viewIds
     * @return
     */
    public ViewHolder setTypeface(Typeface typeface, @NonNull int... viewIds) {
        for (int viewId : viewIds) {
            TextView textView = getView(viewId);
            textView.setTypeface(typeface);
            //Paint.SUBPIXEL_TEXT_FLAG 使文本的亚像素定位的绘图标志
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setMax(max);
        progressBar.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setMax(max);
        return this;
    }

    /**
     * RatingBar：Android自带的一个评分控件，可以使用该控件做等级划分，星星形状显示，也可以为半星级别，
     * RatingBar是基于SeekBar和ProgressBar的扩展
     *
     * @param viewId
     * @param rating
     * @return
     */
    public ViewHolder setRating(int viewId, float rating) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setMax(max);
        ratingBar.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * Checkable 选项控件的基类
     *
     * @param viewId
     * @param checked
     * @return
     */
    public ViewHolder setChecked(int viewId, boolean checked) {
        Checkable checkable = getView(viewId);
        checkable.setChecked(checked);
        return this;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        View view = getView(viewId);
        view.setOnClickListener(onClickListener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener onTouchListener) {
        View view = getView(viewId);
        view.setOnTouchListener(onTouchListener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener onLongClickListener) {
        View view = getView(viewId);
        view.setOnLongClickListener(onLongClickListener);
        return this;
    }

}
