package com.mvvm.lux.burqa.manager.login.intercept;

import android.content.Context;

import com.mvvm.lux.framework.config.session.Session;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 18:08
 * @Version
 */
public class RealInterceptorChain implements Interceptor.Chain {
    private Context context;
    private Permission permission;
    private List<Interceptor> interceptors;
    private int index;

    public RealInterceptorChain(Context context, Permission permission, int index, List<Interceptor> interceptors) {
        this.index = index;
        this.context = context;
        this.permission = permission;
        this.interceptors = interceptors;
    }

    @Override
    public Permission getPermission() {
        return permission;
    }

    /**
     * 调用下一个interceptor
     * @param permission
     * @return
     */
    @Override
    public Session.User proceed(Permission permission) {
        return proceed(permission,interceptors);
    }

    private Session.User proceed(Permission permission, List<Interceptor> interceptors) {
        if (permission.needLogin != Session.isLogin()) {
            return null;
        }
        if (index >= interceptors.size()) throw new AssertionError();
        // Call the next interceptor in the chain.
        RealInterceptorChain next = new RealInterceptorChain(
                context, permission, index + 1, interceptors);
        Interceptor interceptor = interceptors.get(index);
        return interceptor.intercept(next);
    }

    @Override
    public Context connection() {
        return context;
    }
}
