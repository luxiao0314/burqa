package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mzule.activityrouter.annotation.ClassHello;
import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityComicDesBinding;
import com.mvvm.lux.burqa.model.ComicDesViewModel;
import com.mvvm.lux.framework.base.SwipeBackActivity;
import com.mvvm.lux.widget.swipeback.SwipeBackLayout;

/**
 * @Description 漫画描述页面
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/8 12:31
 * @Version 1.0.0
 */
@Router("comicDes")
@ClassHello("hello")
public class ComicDesActivity extends SwipeBackActivity {

    private ActivityComicDesBinding mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_comic_des);
        initData();
        setEdge(SwipeBackLayout.EDGE_ALL);
    }

    private void initData() {
        String obj_id = getIntent().getStringExtra("obj_id");
        mViewModel = new ComicDesViewModel(this, mDataBinding, obj_id);
        mDataBinding.setViewModel((ComicDesViewModel) mViewModel);
    }

    public static void launch(Activity activity, String obj_id) {
        Routers.open(activity, "lux://comicDes?obj_id=" + obj_id);
    }
}
