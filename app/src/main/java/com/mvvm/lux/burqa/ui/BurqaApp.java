package com.mvvm.lux.burqa.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.RouterCallbackProvider;
import com.github.mzule.activityrouter.router.SimpleRouterCallback;
import com.mvvm.lux.burqa.ui.home.activity.LoginActivity;
import com.mvvm.lux.burqa.ui.sub.activity.ErrorStackActivity;
import com.mvvm.lux.burqa.ui.sub.activity.NotFoundActivity;
import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/3 14:49
 * @Version
 */
public class BurqaApp extends BaseApplication implements RouterCallbackProvider {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public RouterCallback provideRouterCallback() {
        return new SimpleRouterCallback() {
            @Override
            public boolean beforeOpen(Context context, Uri uri) {
                return permissions(context, uri);
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

    private boolean permissions(Context context, Uri uri) {
        if (uri.toString().contains("needLogin")) {   //如果需要登录就跳转到登录页面
            context.startActivity(new Intent(context, LoginActivity.class));
            return true;
        }
        if (uri.toString().contains("needBind")) {
//            context.startActivity(new Intent(context, BindActivity.class));
            return true;
        }
        if (uri.toString().contains("needGesture")) {
//            context.startActivity(new Intent(context, GestureActivity.class));
            return true;
        }
        if (uri.toString().contains("needV2")) {
//            context.startActivity(new Intent(context, V2AuthActivity.class));
            return true;
        }
        //lux://comicDes?isOpen=true
        if (!getString(uri)) {   //如果需要登录就跳转到登录页面
            ToastUtil.showToast("暂未开放");
            return true;
        }
        return false;
    }

    @NonNull
    private boolean getString(Uri uri) {
        String url = uri.toString();
        Map<String, String> map = new HashMap<>();
        String substring = url.substring(url.indexOf("?"), url.length());
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
}
