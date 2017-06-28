package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.app.Activity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactRootView;

import java.util.Map;

/**
 * 预加载工具类
 * Created by Song on 2017/5/10.
 */
public class ReactNativePreLoader {

    private static final Map<String, ReactRootView> CACHE = new ArrayMap<>();

    /**
     * 初始化ReactRootView，并缓存
     *
     * @param activity
     * @param componentName
     * @param launchOptions
     */
    public static ReactRootView preLoad(Activity activity, String componentName, Bundle launchOptions) {
        // 1.创建ReactRootView
        ReactRootView rootView = new ReactRootView(activity);
        rootView.startReactApplication(
                ((ReactApplication) activity.getApplication()).getReactNativeHost().getReactInstanceManager(),
                componentName,
                launchOptions);

        // 2.添加到缓存
        CACHE.put(componentName, rootView);
        return rootView;
    }

    /**
     * 获取ReactRootView
     *
     * @param componentName
     * @return
     */
    public static ReactRootView get(String componentName) {
        return CACHE.get(componentName);
    }

    /**
     * 获取ReactRootView
     *
     * @param componentName
     * @return
     */
    public static void put(String componentName,ReactRootView reactRootView) {
        CACHE.put(componentName,reactRootView);
    }

    /**
     * 从当前界面移除 ReactRootView
     *
     * @param component
     */
    public static void deatchView(String component) {
        try {
            ReactRootView rootView = get(component);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } catch (Throwable e) {
            Log.e("ReactNativePreLoader", e.getMessage());
        }
    }

}