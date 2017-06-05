package com.mvvm.lux.burqa.manager.login.intercept;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 19:20
 * @Version
 */
public class Router {

    private final List<Interceptor> interceptors = new ArrayList<>();

    public List<Interceptor> interceptors() {
        return interceptors;
    }

    public Router addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
        return this;
    }

    public void build(Context context) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(interceptors());
        interceptors.add(new LoginInterceptor());
        interceptors.add(new BindInterceptor());
        interceptors.add(new ForwardInterceptor());

        Permission permission = new Permission();
        RealInterceptorChain chain = new RealInterceptorChain(context,permission,0,interceptors);
        chain.proceed(permission);
    }
}
