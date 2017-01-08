package com.mvvm.lux.burqa.ui.home.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.FragmentRecomBinding;
import com.mvvm.lux.burqa.model.RecomViewModel;
import com.mvvm.lux.framework.base.MvvmFragment;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:24
 * @Version
 */
public class RecomFragment extends MvvmFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_recom;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mViewModel = new RecomViewModel(this, (FragmentRecomBinding) mDataBinding);
    }

    @Override
    protected void lazyFetchData() {
        ((RecomViewModel)mViewModel).initData();
        mDataBinding.setVariable(BR.viewModel,mViewModel);
    }

    public static Fragment newInstance() {
        return new RecomFragment();
    }
}
