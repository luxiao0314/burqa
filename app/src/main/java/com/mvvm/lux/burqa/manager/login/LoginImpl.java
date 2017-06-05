package com.mvvm.lux.burqa.manager.login;

import android.content.Context;
import android.content.Intent;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 17:29
 * @Version
 */
public class LoginImpl implements LoginInterface {

    private final boolean needLogin;
    private boolean isLogin;

    public LoginImpl() {
        needLogin = true;
    }

    @Override
    public void startLogin(Context context) {
        if (needLogin) {
            context.startActivity(new Intent());
            isLogin = true;
        }
    }

    @Override
    public void startBind(Context context) {

    }

    @Override
    public void startCheckRealName(Context context) {

    }

    @Override
    public void startGesture(Context context) {

    }

    @Override
    public void startSuum(Context context) {

    }
}
