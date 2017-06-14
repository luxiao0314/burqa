package com.mvvm.lux.burqa.ui.home.fragment;

import com.mvvm.lux.burqa.manager.hybrid_rn.ReactFragment;

/**
 * @Description 我的收藏
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:14
 * @Version 1.0.0
 */
public class FavoriteFragment extends ReactFragment {
    @Override
    public String getMainComponentName() {
//        return "";
        return "AnimeNewsPages";
    }
//    @Override
//    protected int getLayout() {
//        return R.layout.fragment_favorite;
//    }
//
//    @Override
//    protected void initView(LayoutInflater inflater) {
//        mViewModel = new FavoriteViewModel(getActivity());
//    }
//
//    @Override
//    protected void lazyFetchData() {
//        super.lazyFetchData();
//        ((FavoriteViewModel) mViewModel).initData();
//        mDataBinding.setVariable(BR.viewModel, mViewModel);
//    }
//
    public static FavoriteFragment newIntance(){
        return new FavoriteFragment();
    }
}
