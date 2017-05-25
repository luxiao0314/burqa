package com.mvvm.lux.framework.http;


import com.google.gson.GsonBuilder;
import com.mvvm.lux.framework.http.authenticator.CusSSLSocketFactory;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description 网络请求工具类 : 修改
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/12/20 18:36
 * @Version 1.1.0
 */
public class RetrofitExcuter {

    private static OkHttpClient mOkHttpClient;

    /**
     * okhttp全局配置,application初始化一次就可以了
     */
    public synchronized static void init() {
        mOkHttpClient = new OkHttpBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //重试机制
                .addLog(true) //用于输出网络请求和结果log的拦截器
//                .addCache(true)
                .addSSLSocketFactory(CusSSLSocketFactory.getUnsafeOkHttpClient())  //不安全证书
                .addHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER) //信任所有证书
                .build();
        //.addCookie(true)    //持久化cookie
        //.addConverterFactory(MyGsonConverterFactory.create())
        //.addInterceptor(new RequestInterceptor())   //拦截所有请求url,添加全局参数和请求头
        //.addSSL(new String[]{}, new int[]{R.raw.geotrust}) //添加SSL证书
        //.addSSLSocketFactory(CusSSLSocketFactory.buildSSLSocketFactory(R.raw.geotrust))
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static Retrofit.Builder create() {
        return new Retrofit.Builder().addConverterFactory(
                GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .serializeNulls()
                        .create()))  //Converter对Call<T>中的T转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //CallAdapter对Call转换为Rxjava的Observable
                .client(mOkHttpClient);
    }
}
