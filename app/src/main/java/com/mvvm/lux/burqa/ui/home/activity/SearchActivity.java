package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivitySearchBinding;
import com.mvvm.lux.burqa.model.SearchViewModel;
import com.mvvm.lux.framework.base.SwipeBackActivity;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 17:03
 * @Version
 */
@Router("search")
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
        Routers.open(activity,"lux://search");
    }
}
