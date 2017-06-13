package com.mvvm.lux.burqa.manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.github.mzule.activityrouter.router.SimpleRouterCallback;
import com.mvvm.lux.burqa.ui.home.activity.LoginActivity;
import com.mvvm.lux.burqa.ui.sub.activity.ErrorStackActivity;
import com.mvvm.lux.burqa.ui.sub.activity.NotFoundActivity;
import com.mvvm.lux.framework.config.session.Session;
import com.mvvm.lux.framework.utils.SnackbarUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 13/06/2017 16:04
 * @Version
 */
public class MySimpleRouterCallback extends SimpleRouterCallback {

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
}
