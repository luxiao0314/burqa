package com.mvvm.lux.burqa.ui.home.fragment;

import android.view.LayoutInflater;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.FavoriteViewModel;
import com.mvvm.lux.framework.base.MvvmFragment;

/**
 * @Description 我的收藏
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:14
 * @Version 1.0.0
 */
public class FavoriteFragment extends MvvmFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mViewModel = new FavoriteViewModel(getActivity());
        mDataBinding.setVariable(BR.viewModel,mViewModel);
    }

    public static FavoriteFragment newIntance(){
        return new FavoriteFragment();
    }
}
