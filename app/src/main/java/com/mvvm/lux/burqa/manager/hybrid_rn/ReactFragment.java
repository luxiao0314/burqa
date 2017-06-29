package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.mvvm.lux.burqa.BuildConfig;
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
        mReactRootView = ReactNativePreLoader.get(getMainComponentName());
        if (mReactRootView == null) {
            mReactRootView = new ReactRootView(getActivity());
            ReactNativePreLoader.put(getMainComponentName(), mReactRootView);
        }
        return mReactRootView;
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        mReactRootView.startReactApplication(
                mReactInstanceManager,
                getMainComponentName(),
                null);
    }

    @Override
    protected int getLayout() {
        return 0;
    }
}
