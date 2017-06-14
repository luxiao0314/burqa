package com.mvvm.lux.burqa.manager.hybrid_rn;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 13/06/2017 18:16
 * @Version
 */
public class RnEvent {

    public void sendEvent(String id) {
        WritableMap params = Arguments.createMap();
        params.putString("unread", id);
//        BurqaApp.reactNativeHost
//                .getReactInstanceManager()
//                .getCurrentReactContext()
//                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit("onRefreshMessage", params);
    }
}
