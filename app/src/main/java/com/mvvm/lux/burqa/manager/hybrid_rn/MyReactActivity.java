package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.mvvm.lux.burqa.ui.BurqaApp;

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

    public static void launch(Context context, String id) {
        WritableMap params = Arguments.createMap();
        params.putInt("unread", 1);
        sendEvent("onRefreshMessage", params);

        Intent intent = new Intent(context, MyReactActivity.class);
        intent.putExtra("comment_id", id);
        context.startActivity(intent);
    }

    public static void sendEvent(String eventName, @Nullable WritableMap params) {
        BurqaApp.reactNativeHost
                .getReactInstanceManager()
                .getCurrentReactContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
