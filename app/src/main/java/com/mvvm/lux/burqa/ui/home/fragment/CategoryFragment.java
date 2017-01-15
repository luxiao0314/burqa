package com.mvvm.lux.burqa.ui.home.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.FragmentCategoryBinding;
import com.mvvm.lux.burqa.model.CategoryViewModel;
import com.mvvm.lux.framework.base.MvvmFragment;

/**
 * @Description 分类
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:27
 * @Version
 */
public class CategoryFragment extends MvvmFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mViewModel = new CategoryViewModel(getActivity(), (FragmentCategoryBinding) mDataBinding);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        ((CategoryViewModel) mViewModel).initData();
        mDataBinding.setVariable(BR.viewModel, mViewModel);
    }

    public static Fragment newInstance() {
        return new CategoryFragment();
    }
}
