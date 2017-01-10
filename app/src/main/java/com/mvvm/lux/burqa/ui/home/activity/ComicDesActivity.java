package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityComicDesBinding;
import com.mvvm.lux.burqa.model.ComicDesViewModel;
import com.mvvm.lux.framework.base.SwipeBackActivity;
import com.mvvm.lux.framework.manager.router.Router;

/**
 * @Description 漫画描述页面
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/8 12:31
 * @Version 1.0.0
 */
public class ComicDesActivity extends SwipeBackActivity {

    private ActivityComicDesBinding mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_comic_des);
        initData();
    }

    private void initData() {
        String obj_id = getIntent().getStringExtra("obj_id");
        mViewModel = new ComicDesViewModel(this, mDataBinding,obj_id);
        mDataBinding.setViewModel((ComicDesViewModel) mViewModel);
    }

    public static void launch(Activity activity, int obj_id) {
        Router.from(activity)
                .putString("obj_id", obj_id + "")
                .to(ComicDesActivity.class)
                .launch();
    }
}
