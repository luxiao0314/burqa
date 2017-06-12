package com.mvvm.lux.burqa.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.RouterCallbackProvider;
import com.github.mzule.activityrouter.router.SimpleRouterCallback;
import com.mvvm.lux.burqa.BuildConfig;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.config.BurqaFrameworkSupport;
import com.mvvm.lux.burqa.ui.home.activity.LoginActivity;
import com.mvvm.lux.burqa.ui.sub.activity.ErrorStackActivity;
import com.mvvm.lux.burqa.ui.sub.activity.NotFoundActivity;
import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.config.FrameWorkConfig;
import com.mvvm.lux.framework.config.session.Session;
import com.mvvm.lux.framework.http.fresco.ImageLoaderConfig;
import com.mvvm.lux.framework.utils.SnackbarUtil;
import com.mvvm.lux.widget.emptyview.LoadingLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/3 14:49
 * @Version
 */
public class BurqaApp extends BaseApplication implements RouterCallbackProvider, ReactApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initEmptyView();
        ARouter.openDebug();
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        FrameWorkConfig.frameworkSupport = new BurqaFrameworkSupport();
        SoLoader.init(this, /* native exopackage */ false);
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
        return new SimpleRouterCallback() {
            @Override
            public boolean beforeOpen(Context context, Uri uri) {
                return permissions(context, uri.toString());
            }

            @Override
            public void notFound(Context context, Uri uri) {
                context.startActivity(new Intent(context, NotFoundActivity.class));
            }

            @Override
            public void error(Context context, Uri uri, Throwable e) {
                context.startActivity(ErrorStackActivity.makeIntent(context, uri, e));
            }
        };
    }

    private boolean permissions(Context context, String url) {
        if (url.contains("needLogin")) {   //如果需要登录就跳转到登录页面
            if (!Session.isLogin()) {
                context.startActivity(new Intent(context, LoginActivity.class));
                return true;
            }
        }
        if (url.contains("needBind")) {
            return true;
        }
        //lux://comicDes?isOpen=true
        if (url.contains("isOpen") && !isOpen(url)) {
            SnackbarUtil.showMessage("暂未开放");
            return true;
        }
        return false;
    }

    @NonNull
    private boolean isOpen(String url) {
        Map<String, String> map = new HashMap<>();
        String substring = url.substring(url.indexOf("?") + 1, url.length());
        if (!url.contains("&")) {
            String[] split = substring.split("=");
            map.put(split[0], split[1]);
        } else {
            String[] split = substring.split("&");
            for (String s : split) {
                String[] sp = s.split("=");
                map.put(sp[0], sp[1]);
            }
        }
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            if ("isOpen".equals(stringStringEntry.getKey())) {
                if ("true".equals(stringStringEntry.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * react native相关
     */
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            //设置rn的图片加载采用android中fresco的,图片采用防盗链处理
            MainPackageConfig.Builder configBuilder = new MainPackageConfig.Builder();
            configBuilder.setFrescoConfig(ImageLoaderConfig.getImagePipelineConfig(getAppContext()));
            return Arrays.<ReactPackage>asList(new MainReactPackage(configBuilder.build()));
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

}
