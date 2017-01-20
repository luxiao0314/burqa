package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.framework.utils.StatusBarUtils;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by WangChao on 2016/10/19.
 * 欢迎页
 */

public class SplashActivity extends Activity {


    private Unbinder mBind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mBind = ButterKnife.bind(this);
        StatusBarUtils.hideStatusBar(getWindow(), true);    //不加这个,状态栏会切换闪一下
        setUpSplash();
    }

    private void setUpSplash() {

        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    finishTask();
                });

    }

    private void finishTask() {
        // TODO: 2016/11/6 登录做过后放开
        // startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        SplashActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
