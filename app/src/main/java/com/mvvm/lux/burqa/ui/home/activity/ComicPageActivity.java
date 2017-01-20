package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityComicPageBinding;
import com.mvvm.lux.burqa.model.ComicPageViewModel;
import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.manager.router.Router;

/**
 * @Description 看漫画页面
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/10 15:20
 * @Version 1.0.0
 */
public class ComicPageActivity extends BaseActivity {

    private ActivityComicPageBinding mDatabinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabinding = DataBindingUtil.setContentView(this, R.layout.activity_comic_page);
        initData();
    }

    private void initData() {
        ComicPageViewModel viewModel = new ComicPageViewModel(this);
        Intent intent = getIntent();
        viewModel.obj_id.set(intent.getStringExtra("obj_id"));
        viewModel.chapter_id.set(intent.getStringExtra("chapter_id"));
        int position = Integer.parseInt(intent.getStringExtra("current_position"));
        viewModel.current_position.set((position + 1) + "/" + position);
        mDatabinding.setViewModel(viewModel);
    }

    public static void launch(Activity activity, String obj_id, int chapter_id, int position) {
        Router.from(activity)
                .putString("obj_id", obj_id)
                .putString("chapter_id", chapter_id + "")
                .putString("current_position", position + "")
                .to(ComicPageActivity.class)
                .launch();
    }
}
