package com.mvvm.lux.burqa.http;


import com.mvvm.lux.framework.http.RetrofitExcuter;

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
        return RetrofitExcuter.create()
                .baseUrl(UrlConfig.DMZJ)
                .build()
                .create(ApiService.class);
    }
}