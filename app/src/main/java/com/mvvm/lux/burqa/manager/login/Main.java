package com.mvvm.lux.burqa.manager.login;

import com.mvvm.lux.framework.config.session.Session;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 17:30
 * @Version
 */
public class Main {

    private static LoginImpl login;

    public static void main(String[] args) {
        login = new LoginImpl();
        login(login);
    }

    private static void login(LoginInterface loginInterface) {
        Proxy.newProxyInstance(loginInterface.getClass().getClassLoader(), new Class[]{LoginInterface.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                String methodName = method.getName();
                method.invoke(loginInterface,objects);
                Session.getUser().isLogin = true;
                return null;
            }
        });
    }
}
