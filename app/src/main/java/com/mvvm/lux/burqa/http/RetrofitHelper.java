package com.mvvm.lux.burqa.http;


import com.mvvm.lux.framework.http.RetrofitExcuter;
import com.mvvm.lux.framework.http.interceptor.CreateInterceptor;
import com.mvvm.lux.framework.http.interceptor.UserAgentInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @Description 网络请求类
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/12/6 22:02
 * @Version 1.0.1
 */
public class RetrofitHelper {

    /**
     * 使用默认url和默认serviceApi
     *
     * @return
     */
    public static ApiService init() {
        //动态添加拦截器
        OkHttpClient okHttpClient = RetrofitExcuter.getOkHttpClient()
                .newBuilder()
                .addInterceptor(new CreateInterceptor())    //拦截401,403这样的状态码
                .addInterceptor(new UserAgentInterceptor())
                .build();
        Retrofit retrofit = RetrofitExcuter.create()
                .client(okHttpClient)
                .baseUrl(UrlConfig.DMZJ)
                .build();
        return retrofit.create(ApiService.class);
    }
}