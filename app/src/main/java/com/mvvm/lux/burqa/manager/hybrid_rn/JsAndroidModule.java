package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.app.Activity;
import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.github.mzule.activityrouter.router.Routers;
import com.mvvm.lux.framework.manager.IActivityManager;
import com.mvvm.lux.framework.manager.router.Router;
import com.mvvm.lux.framework.utils.Logger;

/**
 * @Description 用于处理和rn的js传值
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 13/06/2017 11:29
 * @Version
 */
public class JsAndroidModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "JsAndroid";

    public JsAndroidModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    /**
     * 使用callback的方式传值.成功:传公共String
     *
     * @param successBack
     * @param erroBack
     */
    @ReactMethod
    public void jumpToJs(Callback successBack, Callback erroBack) {
        try {
            Activity currentActivity = getCurrentActivity();
            if (currentActivity != null) {
                String result = currentActivity.getIntent().getStringExtra("commonKey");//会有对应数据放入
                if (TextUtils.isEmpty(result)) result = "No Data";
                successBack.invoke(result);
            }
        } catch (Exception e) {
            erroBack.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void passTojs(Callback successBack, Callback erroBack) {
        try {
            Activity currentActivity = getCurrentActivity();
            if (currentActivity != null) {
                String title = currentActivity.getIntent().getStringExtra("title");//会有对应数据放入
                String obj_id = currentActivity.getIntent().getStringExtra("obj_id");//会有对应数据放入
                successBack.invoke(title, obj_id);
            }
        } catch (Exception e) {
            erroBack.invoke(e.getMessage());
        }
    }

    /**
     * rn跳转到native页面方法
     */
    @ReactMethod
    public void jumpToActivity(String message) {
        try {
            Logger.e(message);
            //fragment下getCurrentActivity()获取不到
            Routers.open(IActivityManager.instance.currentActivity(), message);
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "不能打开Activity : " + e.getMessage());
        }
    }

    /**
     * 返回到native页面
     */
    @ReactMethod
    public void backToNative() {
        Router.pop(getCurrentActivity());
    }
}
