package com.mvvm.lux.framework.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.lux.framework.config.MarkAble;
import com.mvvm.lux.framework.http.RetrofitExcuter;
import com.mvvm.lux.framework.utils.Logger;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * Description: MvvmFragment
 * Creator: yxc
 * date: 2016/9/7 11:40
 */
public abstract class MvvmFragment<T extends BaseViewModel> extends SupportFragment implements MarkAble{

    private final String TAG = getClass().getSimpleName();
    protected T mViewModel;
    protected Context mContext;
    protected View rootView;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    protected ViewDataBinding mDataBinding;

    @Override
    public void onAttach(Context mContext) {
        super.onAttach(mContext);
        Logger.d(this.getClass().getName(), getName() + "------>onAttach");
        if (mContext != null) {
            this.mContext = mContext;
        } else {
            this.mContext = getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(this.getClass().getName(), getName() + "------>onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(this.getClass().getName(), getName() + "------>onCreateView");
        if (rootView == null) {
            mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
            rootView = mDataBinding.getRoot();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        initView(inflater);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(this.getClass().getName(), getName() + "------>onActivityCreated");
        initEvent();
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(this.getClass().getName(), getName() + "------>onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(this.getClass().getName(), getName() + "------>onResume");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(this.getClass().getName(), getName() + "------>onViewCreated");
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(this.getClass().getName(), getName() + "------>onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(this.getClass().getName(), getName() + "------>onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(this.getClass().getName(), getName() + "------>onDestroyView");
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;
        mViewModel.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(this.getClass().getName(), getName() + "------>onDestroy");
        RetrofitExcuter.getOkHttpClient().dispatcher().cancelAll();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(this.getClass().getName(), getName() + "------>onDetach");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.v(TAG, getClass().getName() + "------>isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }
    }

    @Override
    public String getInstanceTag() {
        return this.getClass().getSimpleName() + this.hashCode();
    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected void lazyFetchData() {
        Log.v(TAG, getClass().getName() + "------>lazyFetchData");
    }

    public String getName() {
        return MvvmFragment.class.getName();
    }

    protected abstract int getLayout();

    protected void initView(LayoutInflater inflater) {}

    protected void initEvent() {}

}
