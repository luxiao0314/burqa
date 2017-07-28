package com.mvvm.lux.framework.manager.dialogs.config;

import com.mvvm.lux.framework.http.exception.Throwable;

/**
 * Created by iceman on 15/6/22.
 */
public interface ServiceCallback {
    void onTaskStart(String serverTag);

    void onTaskSuccess(String serverTag);

    void onTaskFail(Throwable error, String serverTag);
}
