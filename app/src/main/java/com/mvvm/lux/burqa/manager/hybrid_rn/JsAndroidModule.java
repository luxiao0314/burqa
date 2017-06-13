package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.app.Activity;
import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * @Description 用于处理和rn的js传值
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 13/06/2017 11:29
 * @Version
 */
public class JsAndroidModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "JsAndroidModule";

    public JsAndroidModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    /**
     * 使用callback的方式传值.成功
     *
     * @param successBack
     * @param erroBack
     */
    @ReactMethod
    public void jsActivity(Callback successBack, Callback erroBack) {
        try {
            Activity currentActivity = getCurrentActivity();
            String result = currentActivity.getIntent().getStringExtra("comment_id");//会有对应数据放入
            if (TextUtils.isEmpty(result)) result = "No Data";
            successBack.invoke(result);
        } catch (Exception e) {
            erroBack.invoke(e.getMessage());
        }
    }
}
