package com.mvvm.lux.framework.manager.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


/**
 * Internal base builder that holds common values for all dialog fragment builders.
 *
 * @author Tomas Vondracek
 */
public abstract class BaseDialogBuilder<T extends BaseDialogBuilder<T>> {

    public final static String ARG_REQUEST_CODE = "request_code";
    public final static String ARG_CANCELABLE_ON_TOUCH_OUTSIDE = "cancelable_oto";
    public final static String DEFAULT_TAG = "simple_dialog";
    private String mTag = DEFAULT_TAG;
    public final static int DEFAULT_REQUEST_CODE = -42;
    public int mRequestCode = DEFAULT_REQUEST_CODE;
    public static String ARG_USE_THEME_TYPE = "arg_use_theme_type";
    protected final Context mContext;
    protected final FragmentManager mFragmentManager;
    protected final Class<? extends BaseDialogFragment> mClass;
    private Fragment mTargetFragment;
    private boolean mCancelable = true;
    private boolean mCancelableOnTouchOutside = true;   //默认点击屏幕取消dialog
    private boolean mUseDarkTheme = false;
    private int mThemeType = 1;

    public BaseDialogBuilder(Context context, FragmentManager fragmentManager, Class<? extends BaseDialogFragment> clazz) {
        mFragmentManager = fragmentManager;
        mContext = context.getApplicationContext();
        mClass = clazz;
    }

    protected abstract T self();

    protected abstract Bundle prepareArguments();

    public T setCancelable(boolean cancelable) {
        mCancelable = cancelable;
        return self();
    }

    public T setCancelableOnTouchOutside(boolean cancelable) {
        mCancelableOnTouchOutside = cancelable;
        if (cancelable) {
            mCancelable = cancelable;
        }
        return self();
    }

    public T setTargetFragment(Fragment fragment, int requestCode) {
        mTargetFragment = fragment;
        mRequestCode = requestCode;
        return self();
    }

    public T setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return self();
    }

    public T setTag(String tag) {
        mTag = tag;
        return self();
    }

    public T useThemeType(int themeType) {
        mThemeType = themeType;
        return self();
    }

    private BaseDialogFragment create() {
        final Bundle args = prepareArguments();

        final BaseDialogFragment fragment = (BaseDialogFragment) Fragment.instantiate(mContext, mClass.getName(), args);

        args.putBoolean(ARG_CANCELABLE_ON_TOUCH_OUTSIDE, mCancelableOnTouchOutside);

        args.putInt(ARG_USE_THEME_TYPE, mThemeType);

        if (mTargetFragment != null) {
            fragment.setTargetFragment(mTargetFragment, mRequestCode);
        } else {
            args.putInt(ARG_REQUEST_CODE, mRequestCode);
        }
        fragment.setCancelable(mCancelable);
        return fragment;
    }

    public DialogFragment show() {
        BaseDialogFragment fragment = create();
        fragment.show(mFragmentManager, mTag);
        return fragment;
    }

    /**
     * Like show() but allows the commit to be executed after an activity's state is saved. This
     * is dangerous because the commit can be lost if the activity needs to later be restored from
     * its state, so this should only be used for cases where it is okay for the UI state to change
     * unexpectedly on the user.
     * IllegalStateException : Can not perform this action after onSaveInstanceState()报该异常的时候使用此show
     */
    public DialogFragment showAllowingStateLoss() {
        BaseDialogFragment fragment = create();
        fragment.showAllowingStateLoss(mFragmentManager, mTag);
        return fragment;
    }
}
