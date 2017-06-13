package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.mvvm.lux.burqa.ui.BurqaApp;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 13/06/2017 18:16
 * @Version
 */
public class RnEvent {

    public void sendEvent(String eventName, @Nullable WritableMap params) {
        BurqaApp.reactNativeHost
                .getReactInstanceManager()
                .getCurrentReactContext()
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
