package com.mvvm.lux.framework.config;

import com.mvvm.lux.framework.http.exception.CusException;
import com.mvvm.lux.framework.utils.AESOperator;
import com.mvvm.lux.framework.utils.NetworkUtil;
import com.mvvm.lux.framework.utils.SnackbarUtil;
import com.orhanobut.hawk.Hawk;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Jam on 16-7-6
 * Description:
 * RxJava + Retrofit 的缓存机制
 */
public class RxCache {
    private static String cacheKey;//缓存key

    /**
     * @param <T>
     * @param fromNetwork  从网络获取的Observable
     * @param forceRefresh 是否强制刷新
     * @param key
     * @return
     */
    public static <T> Observable<T> load(Observable<T> fromNetwork, final boolean forceRefresh, String key) {
        cacheKey = AESOperator.encryptSHA256(key);
        //从拦截器获取的url
//        RxBus.init().toObservable(PassUrlEvent.class)
//                .subscribe(new RxSubscriber<PassUrlEvent>() {
//                    @Override
//                    public void onNext(PassUrlEvent passUrlEvent) {
//                        RxCache.cacheKey = passUrlEvent.url;
//                    }
//                });

        Observable<T> fromCache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T cache = Hawk.get(cacheKey);
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onError(new CusException("缓存数据为空"));
                }
            }
        });

        if (!NetworkUtil.isNetworkAvailable()) {
            SnackbarUtil.showMessage("数据加载失败,请重新加载或者检查网络是否链接");
            return fromCache;
        }

        /**
         * 这里的fromNetwork 不需要指定Schedule,在handleRequest中已经变换了
         */
        fromNetwork = fromNetwork.map(new Func1<T, T>() {
            @Override
            public T call(T result) {
                Hawk.put(cacheKey, result); //保存数据
                return result;
            }
        });

        if (forceRefresh) {
            return fromNetwork;
        }

        return Observable.concat(fromCache, fromNetwork)
                .takeFirst(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return t != null;
                    }
                });
    }
}