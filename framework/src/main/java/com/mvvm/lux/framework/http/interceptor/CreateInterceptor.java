package com.mvvm.lux.framework.http.interceptor;


import android.support.annotation.NonNull;

import com.mvvm.lux.framework.config.PassUrlEvent;
import com.mvvm.lux.framework.http.exception.ServerException;
import com.mvvm.lux.framework.rx.RxBus;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CreateInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return response(chain);
    }

    @NonNull
    private Response response(Chain chain) throws IOException {
        Request request = chain.request();
        RxBus.init().postSticky(new PassUrlEvent(String.valueOf(request.url())));
        Response response = chain.proceed(request);  //如果401了，会先执行TokenAuthenticator
        switch (response.code()) {
            case 202:    //请求成功，但没有处理
                throw new ServerException(202, response.header("Retry-After"));
            case 401:   //返回为token失效
                throw new ServerException(401, response.message());
            case 403:   //绑卡失败
                throw new ServerException(403, response.message());
        }
        return response;
    }
}