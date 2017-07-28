package com.mvvm.lux.framework.http;

import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.config.ConfigLoader;
import com.mvvm.lux.framework.http.base.BaseResponse;
import com.mvvm.lux.framework.http.exception.FormatException;
import com.mvvm.lux.framework.http.exception.RetrofitException;
import com.mvvm.lux.framework.http.exception.ServerException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * @Description 处理返回结果, 成功直接返回, 失败抛异常.
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/12/14 18:58
 * @Version
 */
public class HandleResultFuc<T> implements Func1<BaseResponse<T>, Observable<T>> {
    @Override
    public Observable<T> call(BaseResponse<T> baseResponse) {
        try {
            //空指针异常
            if (baseResponse == null) {
                throw new NullPointerException();
            }
            //格式转换异常
            if (ConfigLoader.isFormat(BaseApplication.getAppContext()) && baseResponse.getData() == null & baseResponse.getResult() == null) {
                throw new FormatException();
            }

            //类似于code == 200,1,0 就表示请求成功
            if (baseResponse.isOk()) {
                if (null != baseResponse.getRet())
                    return createData(baseResponse.getRet());
                if (null != baseResponse.getBody())
                    return createData(baseResponse.getBody());
                if (null != baseResponse.getData())
                    return createData(baseResponse.getData());
                return createData(baseResponse.getResult());
            }
            //请求失败抛异常
            else {
                String msg = baseResponse.getMsg() != null ? baseResponse.getMsg() : baseResponse.getError() != null
                        ? baseResponse.getError() : baseResponse.getMessage() != null ? baseResponse.getMessage() : "api未知异常";

                //服务器异常
                ServerException serverException = new ServerException(baseResponse.getCode(), msg);
                throw new RuntimeException(RetrofitException.handleException(serverException));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(RetrofitException.handleException(new FormatException()));
        }
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}