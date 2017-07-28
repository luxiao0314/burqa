package com.mvvm.lux.framework.config.session;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mvvm.lux.framework.config.FrameworkConstants;
import com.mvvm.lux.framework.config.LocalBroadcast;


/**
 * Created by iceman on 16/4/7 11:39
 * 邮箱：xubin865@pingan.com.cn
 */
public class LoginManager {

    public interface LoginCallback {
        void login();
    }

    private LoginCallback loginCallback;

    public void setLoginCallback(LoginCallback callback) {
        this.loginCallback = callback;
    }


    protected LoginManager() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (loginCallback != null) {
                    loginCallback.login();
                }
                loginCallback = null;
            }
        };
        LocalBroadcast.registerLocalReceiver(receiver, FrameworkConstants.ActionKeys.LOGIN);
    }
}
