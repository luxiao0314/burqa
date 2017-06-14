package com.mvvm.lux.burqa.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.view.View;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityMainBinding;
import com.mvvm.lux.burqa.ui.home.activity.SearchActivity;
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
            R.drawable.selector_tab_category, R.drawable.selector_tab_game,R.drawable.ic_drawer_history};

    public HomePagerAdapter mHomePagerAdapter;

    public MainViewModel(FragmentActivity activity, ActivityMainBinding dataBinding) {
        super(activity);
        mActivity = activity;
        mDataBinding = dataBinding;
        initData();
    }

    private void initData() {
        resIcon.clear();
        for (int icon : icons) {
            resIcon.add(icon);
        }
        mHomePagerAdapter = new HomePagerAdapter(mActivity, resIcon);
        mDataBinding.include.tabs.setupWithViewPager(mDataBinding.include.viewPager);
    }

    public View.OnClickListener mOnClickListener = view -> toggleDrawer();

    public View.OnClickListener mOnSearchClickListener = view -> {
        SearchActivity.launch(mActivity);
    };

    private void toggleDrawer() {
        if (mDataBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDataBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDataBinding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

}
