package com.mvvm.lux.burqa.ui.home.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityComicDesBinding;
import com.mvvm.lux.burqa.model.ComicDesViewModel;
import com.mvvm.lux.framework.base.SwipeBackActivity;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/8 12:31
 * @Version
 */
public class ComicDesActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityComicDesBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_comic_des);
        mViewModel = new ComicDesViewModel(this, dataBinding);
        dataBinding.setViewModel((ComicDesViewModel) mViewModel);
    }
}
