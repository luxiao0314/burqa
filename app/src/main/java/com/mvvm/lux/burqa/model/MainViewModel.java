package com.mvvm.lux.burqa.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.app.FragmentActivity;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityMainBinding;
import com.mvvm.lux.burqa.ui.home.adapter.HomePagerAdapter;
import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 11:01
 * @Version
 */
public class MainViewModel extends BaseViewModel {

    private FragmentActivity mActivity;
    private ActivityMainBinding mDataBinding;

    public ObservableList<Integer> resIcon = new ObservableArrayList<>();

    private int[] icons = {R.drawable.selector_tab_favorite, R.drawable.selector_tab_recom,
            R.drawable.selector_tab_category, R.drawable.selector_tab_game};

    public HomePagerAdapter mHomePagerAdapter;

    public MainViewModel(FragmentActivity activity, ActivityMainBinding dataBinding) {
        mActivity = activity;
        mDataBinding = dataBinding;
        initData();
    }

    private void initData() {
        for (int icon : icons) {
            resIcon.add(icon);
        }
        mHomePagerAdapter = new HomePagerAdapter(mActivity, resIcon);
        mDataBinding.include.tabs.setupWithViewPager(mDataBinding.include.viewPager);
    }

}
