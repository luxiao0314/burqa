package com.mvvm.lux.burqa.ui.sub.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivitySubjectDesBinding;
import com.mvvm.lux.burqa.model.SubjectDesViewModel;
import com.mvvm.lux.framework.base.BaseActivity;

/**
 * @Description 推荐详情页面
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/10 16:29
 * @Version 1.1.0
 */
//@Route(path = "/subject/des")
@Router("subject")
public class SubjectDesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySubjectDesBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_subject_des);
        SubjectDesViewModel viewModel = new SubjectDesViewModel(this);
        viewModel.id.set(getIntent().getStringExtra("id"));
        viewModel.initData();
        dataBinding.setViewModel(viewModel);
    }

    public static void launch(Activity activity, int id) {
        Routers.open(activity, "lux://subject?id=" + id);
//        ARouter.getInstance()
//                .build("/subject/des")
//                .withInt("id", id)
//                .navigation();
    }
}
