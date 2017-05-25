package com.mvvm.lux.framework.http.download;

import android.annotation.SuppressLint;
import android.util.Log;

import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.http.RxSubscriber;


/**
 * DownSubscriber
 * Created by Tamic on 2016-08-03.
 */
public class DownSubscriber <ResponseBody extends okhttp3.ResponseBody> extends RxSubscriber<ResponseBody> {
    private DownLoadCallBack callBack;
    private String path;
    private String name;

    public DownSubscriber(String path, String name, DownLoadCallBack callBack) {
        this.path = path;
        this.name = name;
        this.callBack = callBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onCompleted() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onError(final Throwable e) {
        Log.d( DownLoadManager.TAG, "DownSubscriber:>>>> onError:" + e.getMessage());
        callBack.onError(e);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onNext(ResponseBody responseBody) {

        Log.d(NovateDownLoadManager.TAG, "DownSubscriber:>>>> onNext");

        NovateDownLoadManager.getInstance(callBack)
                .writeResponseBodyToDisk(path, name, BaseApplication.getAppContext(), responseBody);

    }
}
