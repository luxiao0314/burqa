package com.mvvm.lux.burqa.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.github.mzule.activityrouter.router.RouterCallback;
import com.github.mzule.activityrouter.router.RouterCallbackProvider;
import com.github.mzule.activityrouter.router.SimpleRouterCallback;
import com.mvvm.lux.burqa.ui.home.activity.SearchActivity;
import com.mvvm.lux.burqa.ui.sub.activity.ErrorStackActivity;
import com.mvvm.lux.burqa.ui.sub.activity.NotFoundActivity;
import com.mvvm.lux.framework.BaseApplication;

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
                if (uri.toString().contains("login")) {
                    context.startActivity(new Intent(context, SearchActivity.class));
                    return true;
                }
                return false;
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
}
