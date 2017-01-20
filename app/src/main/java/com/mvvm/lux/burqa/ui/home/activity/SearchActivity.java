package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivitySearchBinding;
import com.mvvm.lux.burqa.model.SearchViewModel;
import com.mvvm.lux.framework.base.SwipeBackActivity;
import com.mvvm.lux.framework.manager.router.Router;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 17:03
 * @Version
 */
public class SearchActivity extends SwipeBackActivity {
    private ActivitySearchBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initData();
    }

    private void initData() {
        SearchViewModel mViewModel = new SearchViewModel(this, mDataBinding);
        mViewModel.initData();
        mDataBinding.setViewModel(mViewModel);
    }

    public static void launch(Activity activity) {
        Router.from(activity)
                .to(SearchActivity.class)
                .launch();
    }
}
