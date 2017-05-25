package com.mvvm.lux.framework.http;

import android.content.Context;

import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.http.authenticator.HttpsFactroy;
import com.mvvm.lux.framework.http.cookie.CookieManger;
import com.mvvm.lux.framework.http.interceptor.BaseInterceptor;
import com.mvvm.lux.framework.http.interceptor.CacheInterceptor;
import com.mvvm.lux.framework.http.interceptor.HttpLoggingInterceptor;
import com.mvvm.lux.framework.utils.CommonUtils;

import java.io.File;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;


/**
 * @Description okhttp.builder的配置信息,只用配置一次
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/12/20 18:24
 * @Version 1.0.1
 */
public class OkHttpBuilder {

    private static final int DEFAULT_TIMEOUT = 5;
    private static final int DEFAULT_MAXIDLE_CONNECTIONS = 5;
    private static final long DEFAULT_KEEP_ALIVEDURATION = 8;
    private static final long caheMaxSize = 100 * 1024 * 1024;

    private Boolean isLog = false;
    private Boolean isCookie = false;
    private Boolean isCache = false;
    private HostnameVerifier hostnameVerifier;
    private Context context;
    private CookieManger cookieManager;
    private Cache cache = null;
    private Proxy proxy;
    private File httpCacheDirectory;
    private SSLSocketFactory sslSocketFactory;
    private ConnectionPool connectionPool;
    private static OkHttpClient.Builder okHttpBuilder;
    private static OkHttpClient mOkHttpClient;

    public OkHttpBuilder() {
        okHttpBuilder = new OkHttpClient.Builder();
        this.context = BaseApplication.getAppContext();
    }

    public OkHttpBuilder connectTimeout(int timeout) {
        return connectTimeout(timeout, TimeUnit.SECONDS);
    }

    public OkHttpBuilder writeTimeout(int timeout) {
        return writeTimeout(timeout, TimeUnit.SECONDS);
    }

    public OkHttpBuilder readTimeout(int timeout) {
        return readTimeout(timeout, TimeUnit.SECONDS);
    }

    public OkHttpBuilder addLog(boolean isLog) {
        this.isLog = isLog;
        return this;
    }

    public OkHttpBuilder addCookie(boolean isCookie) {
        this.isCookie = isCookie;
        return this;
    }

    public OkHttpBuilder addCache(boolean isCache) {
        this.isCache = isCache;
        return this;
    }

    public OkHttpBuilder proxy(Proxy proxy) {
        okHttpBuilder.proxy(CommonUtils.checkNotNull(proxy, "proxy == null"));
        return this;
    }

    public OkHttpBuilder writeTimeout(int timeout, TimeUnit unit) {
        if (timeout != -1) {
            okHttpBuilder.writeTimeout(timeout, unit);
        } else {
            okHttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    public OkHttpBuilder readTimeout(int timeout, TimeUnit unit) {
        if (timeout != -1) {
            okHttpBuilder.readTimeout(timeout, unit);
        } else {
            okHttpBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    public OkHttpBuilder connectionPool(ConnectionPool connectionPool) {
        if (connectionPool == null) throw new NullPointerException("connectionPool == null");
        this.connectionPool = connectionPool;
        return this;
    }

    public OkHttpBuilder connectTimeout(int timeout, TimeUnit unit) {
        if (timeout != -1) {
            okHttpBuilder.connectTimeout(timeout, unit);
        } else {
            okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        return this;
    }

    public OkHttpBuilder addHeader(Map<String, String> headers) {
        okHttpBuilder.addInterceptor(new BaseInterceptor((CommonUtils.checkNotNull(headers, "header == null"))));
        return this;
    }

    public OkHttpBuilder addParameters(Map<String, String> parameters) {
        okHttpBuilder.addInterceptor(new BaseInterceptor((CommonUtils.checkNotNull(parameters, "parameters == null"))));
        return this;
    }

    public OkHttpBuilder addInterceptor(Interceptor interceptor) {
        okHttpBuilder.addInterceptor(CommonUtils.checkNotNull(interceptor, "interceptor == null"));
        return this;
    }

    public OkHttpBuilder authenticator(Authenticator authenticator) {
        okHttpBuilder.authenticator(authenticator);
        return this;
    }

    public OkHttpBuilder cookieManager(CookieManger cookie) {
        if (cookie == null) throw new NullPointerException("cookieManager == null");
        this.cookieManager = cookie;
        return this;
    }

    public OkHttpBuilder addSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }

    public OkHttpBuilder addHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public OkHttpBuilder addSSL(String[] hosts, int[] certificates) { //certificates:证书
        if (hosts == null) throw new NullPointerException("hosts == null");
        if (certificates == null) throw new NullPointerException("ids == null");

        addSSLSocketFactory(HttpsFactroy.getSSLSocketFactory(context, certificates));
        addHostnameVerifier(HttpsFactroy.getHostnameVerifier(hosts));
        return this;
    }

    public OkHttpBuilder addNetworkInterceptor(Interceptor interceptor) {
        okHttpBuilder.addNetworkInterceptor(interceptor);
        return this;
    }

    public OkHttpBuilder addCache(Cache cache) {
        int maxStale = 60 * 60 * 24 * 3;
        return addCache(cache, maxStale);
    }

    public OkHttpBuilder addCache(Cache cache, final long cacheTime) {
        addCache(cache, String.valueOf(cacheTime));
        return this;
    }

    private OkHttpBuilder addCache(Cache cache, final String cacheControlValue) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor(context, cacheControlValue);
        addNetworkInterceptor(cacheInterceptor);   //在线缓存:添加网络拦截器
        addInterceptor(cacheInterceptor);  //离线缓存:添加应用拦截器
        this.cache = cache;
        return this;
    }

    public OkHttpBuilder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
        okHttpBuilder.retryOnConnectionFailure(retryOnConnectionFailure);
        return this;
    }

    public synchronized OkHttpClient build() {

        if (okHttpBuilder == null) throw new IllegalStateException("okHttpBuilder required.");

        if (proxy != null)
            okHttpBuilder.proxy(proxy);

        if (httpCacheDirectory == null)
            httpCacheDirectory = new File(context.getCacheDir(), "http_cache");

        if (isCache)
            addCache(new Cache(httpCacheDirectory, caheMaxSize), caheMaxSize);

        if (cache != null)
            okHttpBuilder.cache(cache);

        if (isLog)
            okHttpBuilder.addInterceptor(new HttpLoggingInterceptor("logger"));

        if (isCookie && cookieManager == null)
            okHttpBuilder.cookieJar(new CookieManger(context));

        if (cookieManager != null)
            okHttpBuilder.cookieJar(cookieManager);

        if (sslSocketFactory != null)
            okHttpBuilder.sslSocketFactory(sslSocketFactory);

        if (hostnameVerifier != null)
            okHttpBuilder.hostnameVerifier(hostnameVerifier);

        if (connectionPool == null)
            connectionPool = new ConnectionPool(DEFAULT_MAXIDLE_CONNECTIONS, DEFAULT_KEEP_ALIVEDURATION, TimeUnit.SECONDS);

        okHttpBuilder.connectionPool(connectionPool);

        mOkHttpClient = okHttpBuilder.build();
        return mOkHttpClient;
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}