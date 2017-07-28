package com.mvvm.lux.framework.http;


import com.google.gson.Gson;
import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.config.FrameWorkConfig;
import com.mvvm.lux.framework.config.RxCache;
import com.mvvm.lux.framework.http.base.BaseResponse;
import com.mvvm.lux.framework.http.exception.RetrofitException;
import com.mvvm.lux.framework.utils.FileUtil;

import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Jam on 16-6-12
 * Description: Rx 一些巧妙的处理
 */
public class RxHelper {

    /**
     * 异常处理变压器
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseResponse<T>, T> handleErrTransformer() {
        return new Observable.Transformer<BaseResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResponse<T>> observable) {
                return observable
                        .flatMap(new <T>HandleResultFuc())    //将RxSubscriber中服务器异常处理换到这里,在RxSubscriber中处理onstart(),onCompleted().onError,onNext()
                        .compose(io_main()) //处理线程切换,注销Observable
                        .onErrorResumeNext(httpResponseFunc())//判断异常
                        .retryWhen(new RetryFuc(3, 2 * 1000)) //重试次数,重试间隔
                        .retryWhen(new TimeOutRetry());  //token过期的重试,有问题
            }
        };
    }

    /**
     * 使用假数据,读取的是assets中的json数据
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> handleVirtualData(final Class<T> clazz) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                String filepath = "virtualdata" + "/" + clazz.getSimpleName();
                String response = FileUtil.getJson(BaseApplication.getAppContext(), filepath);
                Gson gson = new Gson();
                T data = gson.fromJson(response, clazz);
                return HandleResultFuc.createData(data);
            }
        };
    }

    /**
     * 异常处理变压器
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> handleErr() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return (Observable<T>) observable
                        .compose(io_main()) //处理线程切换,注销Observable
                        .onErrorResumeNext(httpResponseFunc());//判断异常
            }
        };
    }

    /**
     * 根据responseCode,处理异常,类似于捕获了异常
     *
     * @param <T>
     */
    private static <T> Func1<Throwable, Observable<T>> httpResponseFunc() {
        return new Func1<Throwable, Observable<T>>() {
            @Override
            public Observable<T> call(Throwable throwable) {
                return Observable.error(RetrofitException.handleException(throwable));
            }
        };
    }

    /**
     * 截取发射的数据去做缓存
     * 如果直接使用Transformer变压器,rxCache.load()方法会优先拦截器执行
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> cache(final String cacheKey) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(final Observable<T> tObservable) {
                return RxCache.load(tObservable, true, cacheKey);
            }
        };
    }

    /**
     * 调度器,切换线程和注销Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> io_main() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * token过期,登录超时的重新连接
     */
    public static class TimeOutRetry implements Func1<Observable<? extends Throwable>, Observable> {

        @Override
        public Observable call(Observable<? extends Throwable> observable) {
            return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                @Override
                public Observable<?> call(Throwable throwable) {
                    if (throwable instanceof TimeoutException) {
                        //登录超时,重新登录
                        FrameWorkConfig.frameworkSupport.onSessionInvaild();
                    }
                    return Observable.error(throwable);
                }
            });
        }
    }

}