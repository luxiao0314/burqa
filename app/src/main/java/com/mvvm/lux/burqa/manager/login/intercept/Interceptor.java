package com.mvvm.lux.burqa.manager.login.intercept;

import android.content.Context;

import com.mvvm.lux.framework.config.session.Session;

public interface Interceptor {
//  Response intercept(Chain chain);  //传给下一个response
//
//  interface Chain {
//    Request request();  //用于获取上一个request拦截之后的请求
//
//    Response proceed(Request request);  //用于获取上一个response拦截之后的响应
//
//    Connection connection();
//  }

  Session.User intercept(Chain chain); //状态

  interface Chain{
    Permission getPermission(); //获取权限
    Session.User proceed(Permission permission); //处理状态
    Context connection(); //处理状态
  }
}