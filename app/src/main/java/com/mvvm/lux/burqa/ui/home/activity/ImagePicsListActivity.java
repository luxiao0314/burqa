package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Window;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListBinding;
import com.mvvm.lux.burqa.model.ImagePicsListViewModel;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.manager.router.Router;

/**
 * 相册方式浏览图片
 */
public class ImagePicsListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_pics_list);

        ActivityImagePicsListBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_pics_list);
        ImagePicsListViewModel viewModel = new ImagePicsListViewModel(this);

        // 没有任何url时，直接return跳走，UI交互上是用户根本进不来
        Intent intent = getIntent();
        viewModel.obj_id.set(intent.getStringExtra("obj_id"));
        viewModel.chapter_id.set(intent.getStringExtra("chapter_id"));
        viewModel.chapter_title.set(intent.getStringExtra("chapter_title"));
        viewModel.current_position.set(intent.getIntExtra("current_position", 0));
        viewModel.initEvent();
        viewModel.initData();
        dataBinding.setViewModel(viewModel);
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

}
