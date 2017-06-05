package com.mvvm.lux.burqa.manager.login.intercept;

import android.content.Intent;

import com.mvvm.lux.burqa.ui.home.activity.LoginActivity;
import com.mvvm.lux.framework.config.session.Session;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 19:51
 * @Version
 */
public class BindInterceptor implements Interceptor {
    @Override
    public Session.User intercept(Chain chain) {
        Permission permission = chain.getPermission();
        if (permission.needBind) {
            chain.connection().startActivity(new Intent(chain.connection(), LoginActivity.class));
            Session.User user = Session.getUser();
            user.isLogin = true;
            Session.saveUserinfo();
        }
        return chain.proceed(permission);
    }
}
