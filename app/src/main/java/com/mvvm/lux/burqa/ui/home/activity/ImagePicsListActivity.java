package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListBinding;
import com.mvvm.lux.burqa.model.ImagePicsViewModel;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.manager.router.Router;
import com.mvvm.lux.framework.utils.DateUtil;
import com.mvvm.lux.framework.utils.NetworkUtil;


/**
 * @Description 相册方式浏览图片
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/21 15:38
 * @Version 1.0.0
 */
public class ImagePicsListActivity extends BaseActivity {

    private ViewDataBinding mDataBinding;
    private ImagePicsViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.image_pic_fullscreen);
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
        mViewModel = new ImagePicsViewModel(this, (ActivityImagePicsListBinding) mDataBinding);
        // 没有任何url时，直接return跳走，UI交互上是用户根本进不来
        Intent intent = getIntent();
        mViewModel.obj_id.set(intent.getStringExtra("obj_id"));
        mViewModel.chapter_id.set(intent.getStringExtra("chapter_id"));
        mViewModel.title.set(intent.getStringExtra("title"));
        mViewModel.cover.set(intent.getStringExtra("cover"));
        mViewModel.current_position.set(intent.getIntExtra("current_position", 0));
        mViewModel.time.set(DateUtil.getCurrentTime(DateUtil.DATETIME_PATTERN_6_2));
        mViewModel.network_status.set(NetworkUtil.getAPNType(this));
        mViewModel.initData();
        mDataBinding.setVariable(BR.viewModel, mViewModel);
    }

    public static void launch(Activity activity, String obj_id, int chapter_id, String title, String cover, int position) {
        Router.from(activity)
                .putString("obj_id", obj_id)
                .putString("chapter_id", chapter_id + "")
                .putInt("current_position", position)
                .putString("title", title + "")
                .putString("cover", cover)
                .to(ImagePicsListActivity.class)
                .launch();
    }
}
