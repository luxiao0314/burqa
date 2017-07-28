package com.mvvm.lux.framework.http.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.utils.NetworkUtil;
import com.mvvm.lux.framework.utils.SnackbarUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description 如果我们的服务器支持缓存，那么Response中的头文件中会有Cache-Control: max-age=xxx这样的字段。
 * 这里的public意思是可以无条件的缓存该响应，max-age是你缓存的最大存放的时间。比如你设置了6分钟的最大缓存时间，
 * 那么6分钟内他会读取缓存，但超过这个时间则缓存失效。具体的响应标头大家可以自行查询。
 * 如果我们的服务器不支持缓存，也就是响应头没有对应字段，那么我们可以使用网络拦截器实现：
 * <p>
 * 但是在实际开发中有些接口需要保证数据的实时性，那么我们就不能统一配置，这时可以这样：
 * @Headers("Cache-Control: public, max-age=时间秒数")
 * @GET("weilu/test") Observable<Test> getData();
 * <p>
 * 我自己找了一些配置，大家可以根据个人需求使用：
 * 不需要缓存：Cache-Control: no-cache或Cache-Control: max-age=0
 * 如果想先显示数据，在请求。（类似于微博等）：Cache-Control: only-if-cached
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/11/24 15:30
 * @Version 1.0.0
 */

      /*1. noCache 不使用缓存，全部走网络

        2. noStore 不使用缓存，也不存储缓存

        3. onlyIfCached 只使用缓存

        4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

        5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言

        6. minFresh 设置有效时间，依旧如上

        7. FORCE_NETWORK 只走网络

        8. FORCE_CACHE 只走缓存*/

public class CacheInterceptor implements Interceptor {

    private final Context mContext;
    private final String mCacheControlValue;
    private String maxStale = String.valueOf(60 * 60 * 6);

    public CacheInterceptor(Context context, String cacheControlValue) {
        mContext = context;
        mCacheControlValue = cacheControlValue;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetworkUtil.isNetworkAvailable()) {
            //无网络下强制使用缓存，无论缓存是否过期,此时该请求实际上不会被发送出去。没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存）
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();

            BaseApplication.lastActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SnackbarUtil.showMessage("数据加载失败,请重新加载或者检查网络是否链接");
                }
            });
        }

        Response response = chain.proceed(request);
        if (NetworkUtil.isNetworkAvailable()) {
            //有网络情况下，读取接口上面@Headers中的配置,你可以在这里进行统一的设置(拦截header进行追加)
            String cacheControl = request.cacheControl().toString();    //获取header的cacheControl
            //服务端设置了缓存策略，则使用服务端缓存策略
            if (!TextUtils.isEmpty(cacheControl)) {
                return response.newBuilder()
                        .removeHeader("Pragma") //清除头信息,因为服务器如果不支持,会返回一些干扰信息,不清楚无法生成
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheControl)//.header("Cache-Control","max-age=3600")
                        .build();
            } else { //服务端未设置缓存策略，则客户端自行设置
                int maxAge = 60; //有网失效一分钟
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-age=" + maxAge)
                        .build();
            }
        } else {//无网络
            maxStale = TextUtils.isEmpty(mCacheControlValue) ? maxStale : mCacheControlValue;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
