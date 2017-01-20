package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListBinding;
import com.mvvm.lux.burqa.model.ImagePicsViewModel;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.manager.router.Router;


/**
 * 相册方式浏览图片
 */
public class ImagePicsListActivity extends BaseActivity {

    private ViewDataBinding mDataBinding;
    private ImagePicsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_pics_list);
        init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_pics_list);
        mDataBinding.setVariable(BR.viewModel, mViewModel);
    }

    private void init() {
        mViewModel = new ImagePicsViewModel(this);
        // 没有任何url时，直接return跳走，UI交互上是用户根本进不来
        Intent intent = getIntent();
        mViewModel.obj_id.set(intent.getStringExtra("obj_id"));
        mViewModel.chapter_id.set(intent.getStringExtra("chapter_id"));
        mViewModel.chapter_title.set(intent.getStringExtra("chapter_title"));
        mViewModel.current_position.set(intent.getIntExtra("current_position", 0));
        mViewModel.setDataBinding((ActivityImagePicsListBinding) mDataBinding);
        mViewModel.initEvent();
        mViewModel.initData();
        mDataBinding.setVariable(BR.viewModel, mViewModel);
    }

    public static void launch(Activity activity, String obj_id, int chapter_id, String chapter_title, int position) {
        Router.from(activity)
                .putString("obj_id", obj_id)
                .putString("chapter_id", chapter_id + "")
                .putInt("current_position", position)
                .putString("chapter_title", chapter_title + "")
                .to(ImagePicsListActivity.class)
                .launch();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
