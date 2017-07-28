package com.mvvm.lux.framework.http;

import com.mvvm.lux.framework.utils.Logger;
import com.mvvm.lux.framework.utils.NetworkUtil;
import com.mvvm.lux.framework.utils.SnackbarUtil;

import rx.Subscriber;

/**
 * NovateSubscriber
 *
 * @param <T>
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        if (!NetworkUtil.isNetworkAvailable()) {
            SnackbarUtil.showMessage("数据加载失败,请重新加载或者检查网络是否链接");
        } else {
            SnackbarUtil.showMessage(e.getMessage());
        }
        Logger.e(e.getMessage());
    }
}