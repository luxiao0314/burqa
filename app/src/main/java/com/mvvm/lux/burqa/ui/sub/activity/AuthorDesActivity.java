package com.mvvm.lux.burqa.ui.sub.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityAuthorDesBinding;
import com.mvvm.lux.burqa.model.AuthorDesViewModel;
import com.mvvm.lux.framework.base.BaseActivity;

@Router("authordes")
public class AuthorDesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthorDesBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_author_des);
        AuthorDesViewModel viewModel = new AuthorDesViewModel(this);
        String obj_id = getIntent().getStringExtra("obj_id");
        viewModel.obj_id.set(obj_id);
        viewModel.initData();
        dataBinding.setViewModel(viewModel);
    }

    public static void launch(Activity activity, String obj_id) {
        Routers.open(activity, "lux://authordes?obj_id=" + obj_id);
    }
}
