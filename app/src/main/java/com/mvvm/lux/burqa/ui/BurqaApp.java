package com.mvvm.lux.burqa.ui;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;
import com.facebook.soloader.SoLoader;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.RouterCallbackProvider;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.config.BurqaFrameworkSupport;
import com.mvvm.lux.burqa.manager.MySimpleRouterCallback;
import com.mvvm.lux.burqa.manager.hybrid_rn.MyReactNativeHost;
import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.config.FrameWorkConfig;
import com.mvvm.lux.widget.emptyview.LoadingLayout;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/3 14:49
 * @Version
 */
public class BurqaApp extends BaseApplication implements RouterCallbackProvider, ReactApplication {

    private ReactNativeHost reactNativeHost;
    private ReactContext mReactContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initEmptyView();
        ARouter.openDebug();
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        FrameWorkConfig.frameworkSupport = new BurqaFrameworkSupport();
        SoLoader.init(this, /* native exopackage */ false);
        reactNativeHost = new MyReactNativeHost(this);
        registerReactInstanceEventListener();
    }

    private void initEmptyView() {
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.drawable.define_error)
                .setEmptyImage(R.drawable.define_empty)
                .setNoNetworkImage(R.drawable.define_nonetwork)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150, 40)
                .setAllPageBackgroundColor(R.color.window_background);
//        .setLoadingPageLayout(R.layout.define_loading_page)
    }

    @Override
    public RouterCallback provideRouterCallback() {
        return new MySimpleRouterCallback();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return reactNativeHost;
    }

    private void registerReactInstanceEventListener() {
        reactNativeHost.getReactInstanceManager().addReactInstanceEventListener(mReactInstanceEventListener);
    }

    private void unRegisterReactInstanceEventListener() {
        reactNativeHost.getReactInstanceManager().removeReactInstanceEventListener(mReactInstanceEventListener);
    }

    private final ReactInstanceManager.ReactInstanceEventListener mReactInstanceEventListener = new ReactInstanceManager.ReactInstanceEventListener() {
        @Override
        public void onReactContextInitialized(ReactContext context) {
            mReactContext = context;
        }
    };

    public ReactContext getReactContext() {
        return mReactContext;
    }
}
