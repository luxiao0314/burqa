package com.mvvm.lux.framework.http;

import android.support.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.mvvm.lux.framework.utils.CommonUtils;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @Description retrofit.builder的配置信息
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/12/20 18:24
 * @Version 1.0.1
 */
public class RetrofitBuilder {

    private String baseUrl;
    private okhttp3.Call.Factory callFactory;
    private Converter.Factory converterFactory;
    private CallAdapter.Factory callAdapterFactory;
    private Retrofit.Builder retrofitBuilder;

    public RetrofitBuilder() {
        retrofitBuilder = new Retrofit.Builder();
    }

    @NonNull
    public RetrofitBuilder client(OkHttpClient client) {
        retrofitBuilder.client(CommonUtils.checkNotNull(client, "client == null"));
        return this;
    }

    public RetrofitBuilder callFactory(okhttp3.Call.Factory factory) {
        this.callFactory = CommonUtils.checkNotNull(factory, "factory == null");
        return this;
    }

    public RetrofitBuilder baseUrl(String baseUrl) {
        this.baseUrl = CommonUtils.checkNotNull(baseUrl, "init == null");
        return this;
    }

    public RetrofitBuilder addConverterFactory(Converter.Factory factory) {
        this.converterFactory = factory;
        return this;
    }

    public RetrofitBuilder addCallAdapterFactory(CallAdapter.Factory factory) {
        this.callAdapterFactory = factory;
        return this;
    }

    public Retrofit build() {

        if (baseUrl == null) throw new IllegalStateException("Base URL required.");

        if (retrofitBuilder == null) throw new IllegalStateException("retrofitBuilder required.");

        if (callFactory != null)
            retrofitBuilder.callFactory(callFactory);

        if (callAdapterFactory == null)
            callAdapterFactory = RxJavaCallAdapterFactory.create();

        if (converterFactory == null)
            converterFactory = GsonConverterFactory.create(new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .serializeNulls()
                    .create());

        retrofitBuilder.baseUrl(baseUrl)
                .addConverterFactory(converterFactory)  //Converter对Call<T>中的T转换
                .addCallAdapterFactory(callAdapterFactory)  //CallAdapter对Call转换为Rxjava的Observable
                .client(OkHttpBuilder.getOkHttpClient());

        return retrofitBuilder.build();
    }
}