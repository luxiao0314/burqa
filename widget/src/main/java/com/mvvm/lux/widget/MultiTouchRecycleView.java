package com.mvvm.lux.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/20 13:21
 * @Version
 */
public class MultiTouchRecycleView extends RecyclerView {
    public MultiTouchRecycleView(Context context) {
        super(context);
    }

    public MultiTouchRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean mIsDisallowIntercept = false;

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (ev.getPointerCount() > 1 && mIsDisallowIntercept) {
                    requestDisallowInterceptTouchEvent(false);
                    boolean handled = super.dispatchTouchEvent(ev);
                    requestDisallowInterceptTouchEvent(true);
                    return handled;
                }
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
