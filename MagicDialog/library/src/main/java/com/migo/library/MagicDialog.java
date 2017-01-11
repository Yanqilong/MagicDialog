package com.migo.library;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.migo.library.deletate.DialogDelegate;
import com.migo.library.util.ScreenSizeUtils;

/**
 * Magic dialog, a common dialog for adapter all situation, you only need to customize layout and logic.
 *
 * Author：wave
 * Date：2016/10/17
 * Description：
 */
public class MagicDialog extends Dialog {

    private Context mContext;
    private SparseArray<View> mViews;
    private DialogDelegate mDialogDelegate;
    private Builder mBuilder;

    public MagicDialog(Builder builder) {
        super(builder.getContext());
        this.mBuilder = builder;
        this.mContext = builder.getContext();
        mDialogDelegate = builder.getDialogDelegate();
        mViews = new SparseArray<View>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //no title window
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //init layout
        setContentView(mDialogDelegate.getDialogViewLayoutId());

        //init window
        initWindow();

        //user handle
        mDialogDelegate.onCreate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }



    public static class Builder {

        /**
         * 上下文对象
         */
        private Context context;

        /**
         * 窗口宽度
         */
        private float width;

        /**
         * 窗口高度
         */
        private float height;

        /**
         * 位置
         */
        private int gravity;

        /**
         * 窗口外围透明度
         */
        private float dimAmount;

        /**
         * 窗口透明度
         */
        private float alpha;

        /**
         * 相对偏移X
         */
        private int relativeOffsetX;

        /**
         * 相对偏移Y
         */
        private int relativeOffsetY;

        /**
         * 窗口样式
         */
        private int backgroundDrawableResource;

        /**
         * 回调函数
         */
        private DialogDelegate dialogDelegate;

        /**
         * 点击窗口之外是否可以取消
         */
        private boolean cancelable;

        public Builder(Context context) {
            this.context = context;
            width = 0.9f;
            height = WindowManager.LayoutParams.MATCH_PARENT;
            gravity = Gravity.CENTER;
            dimAmount = 0.5f;
            alpha = 1.0f;
            relativeOffsetX = 0;
            relativeOffsetY = 0;
            backgroundDrawableResource = -1;
            cancelable = true;
        }

        public Context getContext() {
            return context;
        }

        public float getWidth() {
            return width;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public float getHeight() {
            return height;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public int getGravity() {
            return gravity;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public float getDimAmount() {
            return dimAmount;
        }

        public Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public float getAlpha() {
            return alpha;
        }

        public Builder setAlpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public int getRelativeOffsetX() {
            return relativeOffsetX;
        }

        public Builder setRelativeOffsetX(int relativeOffsetX) {
            this.relativeOffsetX = relativeOffsetX;
            return this;
        }

        public int getRelativeOffsetY() {
            return relativeOffsetY;
        }

        public Builder setRelativeOffsetY(int relativeOffsetY) {
            this.relativeOffsetY = relativeOffsetY;
            return this;
        }

        public int getBackgroundDrawableResource() {
            return backgroundDrawableResource;
        }

        public Builder setBackgroundDrawableResource(int backgroundDrawableResource) {
            this.backgroundDrawableResource = backgroundDrawableResource;
            return this;
        }

        public DialogDelegate getDialogDelegate() {
            return dialogDelegate;
        }

        public Builder setDialogDelegate(DialogDelegate dialogDelegate) {
            this.dialogDelegate = dialogDelegate;
            return this;
        }

        public boolean isCancelable() {
            return cancelable;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public MagicDialog build() {
            return new MagicDialog(this);
        }

    }


    private void initWindow() {
        //调整窗体
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = mBuilder.getGravity();
        params.dimAmount = mBuilder.getDimAmount();
        params.alpha = mBuilder.getAlpha();
        params.width = (int) (ScreenSizeUtils.getInstance(mContext).getScreenWidth() * mBuilder.getWidth());
        if(WindowManager.LayoutParams.MATCH_PARENT != mBuilder.getHeight()) {
            params.width = (int) (ScreenSizeUtils.getInstance(mContext).getScreenHeight() * mBuilder.getHeight());
        }
        if(mBuilder.getBackgroundDrawableResource() != -1) {
            window.setBackgroundDrawableResource(mBuilder.getBackgroundDrawableResource());
        }
        if(mBuilder.getGravity() == Gravity.BOTTOM
                || mBuilder.getGravity() == Gravity.TOP
                || mBuilder.getGravity() == Gravity.CENTER_VERTICAL) {

            params.y = mBuilder.getRelativeOffsetY();

        } else if(mBuilder.getGravity() == Gravity.RIGHT
                || mBuilder.getGravity() == Gravity.LEFT
                || mBuilder.getGravity() == Gravity.CENTER_HORIZONTAL) {

            params.x = mBuilder.getRelativeOffsetX();

        } else {
            params.x = mBuilder.getRelativeOffsetX();
            params.y = mBuilder.getRelativeOffsetY();
        }
        window.setAttributes(params);

        if(mBuilder.cancelable) {
            setCanceledOnTouchOutside(true);
        } else {
            setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public MagicDialog setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * The unit is sp
     *
     * @param viewId
     * @param size
     * @return
     */
    public MagicDialog setTextSize(int viewId, float size) {
        TextView tv = getView(viewId);
        tv.setTextSize(size);
        return this;
    }

    public MagicDialog setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public MagicDialog setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public MagicDialog setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public MagicDialog setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public MagicDialog setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public MagicDialog setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public MagicDialog setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    @SuppressLint("NewApi")
    public MagicDialog setAlpha(int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public MagicDialog setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public MagicDialog linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public MagicDialog setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public MagicDialog setProgress(int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public MagicDialog setProgress(int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public MagicDialog setMax(int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public MagicDialog setRating(int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public MagicDialog setRating(int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public MagicDialog setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public MagicDialog setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public MagicDialog setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    public MagicDialog setAllCaps(int viewId, boolean allCaps) {
        TextView textView = getView(viewId);
        textView.setAllCaps(allCaps);
        return this;
    }

    /**
     * About listener
     */
    public MagicDialog setOnClickListener(int viewId,
                                         View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public MagicDialog setOnTouchListener(int viewId,
                                         View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public MagicDialog setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

}
