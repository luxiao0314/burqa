package com.mvvm.lux.framework.http.interceptor;

import android.util.Log;

import com.google.gson.Gson;
import com.mvvm.lux.framework.http.base.BaseResponse;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @Description token过期的拦截器,还有问题,也没接口测试
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/11/28 16:08
 * @Version 1.0.0
 */
public class TokenInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // try the request
        Response originalResponse = chain.proceed(request);

        /**通过如下的办法曲线取到请求完成的数据
         *
         * 原本想通过  originalResponse.body().string()
         * 去取到请求完成的数据,但是一直报错,不知道是okhttp的bug还是操作不当
         *
         * 然后去看了okhttp的源码,找到了这个曲线方法,取到请求完成的数据后,根据特定的判断条件去判断token过期
         */
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);
        BaseResponse baseResponse = new Gson().fromJson(bodyString, BaseResponse.class);

        Log.d("body", "body---------->" + bodyString);

        /***************************************/
        if (baseResponse.isOk()){//根据和服务端的约定判断token过期

            //取出本地的refreshToken
            String refreshToken = "sssgr122222222";

            // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
            /*RetrofitService service = RetrofitExcuter.init(BaseUrl.LIVE_BASE_URL);
            Call<String> call = service.refreshToken(refreshToken);

            //要用retrofit的同步方式
            String newToken = call.execute().body();*/

            // build a new request and modify it accordingly using the new token
            Request newRequest = request.newBuilder().header("token", "")
                    .build();

            // retry the request
            originalResponse.body().close();
            return chain.proceed(newRequest);
        }

        // otherwise just pass the original response on
        return originalResponse;
    }
}