package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.github.mzule.activityrouter.annotation.Router;
import com.mvvm.lux.burqa.ui.BurqaApp;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 16/06/2017 11:17
 * @Version
 */
@Router("JsAndroidActivity")
public class JsAndroidActivity extends BaseReactActivity {

    @Override
    protected String getMainComponentName() {
        return getIntent().getStringExtra("jsRouter");
    }

    protected void sendEvent(String eventName, @Nullable WritableMap params) {
        if (((BurqaApp) getApplication()).getReactContext() != null) {
            ((BurqaApp) getApplication()).getReactContext()
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
    }

}
