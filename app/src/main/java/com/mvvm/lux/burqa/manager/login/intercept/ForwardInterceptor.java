package com.mvvm.lux.burqa.manager.login.intercept;

import android.content.Context;

import com.mvvm.lux.framework.config.session.Session;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 20:16
 * @Version
 */
public class ForwardInterceptor implements Interceptor {
    @Override
    public Session.User intercept(Chain chain) {
        Context context = chain.connection();
        Permission permission = chain.getPermission();
//        chain.
        return chain.proceed(permission);
    }
}
