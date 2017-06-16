package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.mvvm.lux.burqa.BuildConfig;
import com.mvvm.lux.burqa.ui.BurqaApp;
import com.mvvm.lux.framework.base.MvvmFragment;
import com.mvvm.lux.framework.http.fresco.ImageLoaderConfig;

import static com.mvvm.lux.framework.BaseApplication.getAppContext;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 14/06/2017 17:18
 * @Version
 */
public abstract class ReactFragment extends MvvmFragment {

    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    // This method returns the name of our top-level component to show
    public abstract String getMainComponentName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mReactRootView = new ReactRootView(context);
//        mReactInstanceManager = ((BurqaApp) getActivity().getApplication()).getReactNativeHost().getReactInstanceManager();

        MainPackageConfig.Builder configBuilder = new MainPackageConfig.Builder();
        configBuilder.setFrescoConfig(ImageLoaderConfig.getImagePipelineConfig(getAppContext()));

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getActivity().getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage(configBuilder.build()))
                .addPackage(new JsReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = mReactRootView;
        return rootView;
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        mReactRootView.startReactApplication(
                mReactInstanceManager,
                getMainComponentName(),
                null
        );
    }

    protected void sendEvent(String eventName, @Nullable WritableMap params) {
        if (((BurqaApp) getActivity().getApplication()).getReactContext() != null) {
            ((BurqaApp) getActivity().getApplication()).getReactContext()
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
    }

    @Override
    protected int getLayout() {
        return 0;
    }
}
