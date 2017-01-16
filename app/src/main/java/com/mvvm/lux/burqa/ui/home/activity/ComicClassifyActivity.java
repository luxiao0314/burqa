package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityComicClassifyBinding;
import com.mvvm.lux.burqa.model.ComicClassifyViewModel;
import com.mvvm.lux.framework.base.SwipeBackActivity;
import com.mvvm.lux.framework.manager.router.Router;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 10:13
 * @Version
 */
public class ComicClassifyActivity extends SwipeBackActivity {

    private ActivityComicClassifyBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_comic_classify);
        initData();
    }

    private void initData() {
        String tag_id = getIntent().getExtras().getString("tag_id");
        String title = getIntent().getExtras().getString("title");
        ComicClassifyViewModel mViewModel = new ComicClassifyViewModel(this,mDataBinding);
        mViewModel.tag_id.set(tag_id);
        mViewModel.title.set(title);
        mViewModel.initData();
        mDataBinding.setViewModel(mViewModel);
    }

    public static void launch(Activity activity, String tag_id, String title) {
        Router.from(activity)
                .putString("tag_id", tag_id)
                .putString("title", title)
                .to(ComicClassifyActivity.class)
                .launch();
    }
}