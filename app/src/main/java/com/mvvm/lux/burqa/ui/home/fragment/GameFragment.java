package com.mvvm.lux.burqa.ui.home.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.GameViewModel;
import com.mvvm.lux.framework.base.MvvmFragment;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:32
 * @Version
 */
public class GameFragment extends MvvmFragment{
    @Override
    protected int getLayout() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mViewModel = new GameViewModel(getActivity());
        mDataBinding.setVariable(BR.viewModel,mViewModel);
    }

    public static Fragment newInstance() {
        return new GameFragment();
    }
}
