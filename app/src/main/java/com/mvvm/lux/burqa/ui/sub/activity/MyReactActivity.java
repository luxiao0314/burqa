package com.mvvm.lux.burqa.ui.sub.activity;

import android.content.Context;
import android.content.Intent;

import com.facebook.react.ReactActivity;
/**
 * @Description rn处理activity
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 12/06/2017 11:21 PM
 * @Version 1.0.0
 */
public class MyReactActivity extends ReactActivity {

    @Override
    protected String getMainComponentName() {
        return "iShiWuPai";
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MyReactActivity.class);
        context.startActivity(intent);
    }
}
