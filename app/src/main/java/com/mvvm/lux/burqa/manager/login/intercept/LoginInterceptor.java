package com.mvvm.lux.burqa.manager.login.intercept;

import android.content.Intent;

import com.mvvm.lux.burqa.ui.home.activity.LoginActivity;
import com.mvvm.lux.framework.config.session.Session;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 17:59
 * @Version
 */
public class LoginInterceptor implements Interceptor {

    private Session.User user;

    @Override
    public Session.User intercept(Chain chain) {
        Permission router = chain.getPermission();
        if (router.needLogin) {
            chain.connection().startActivity(new Intent(chain.connection(), LoginActivity.class));
            user = Session.getUser();
            user.isLogin = true;
            Session.saveUserinfo();
        }
        return chain.proceed(router);
    }
}
