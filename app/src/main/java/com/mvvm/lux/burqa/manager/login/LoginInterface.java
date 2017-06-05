package com.mvvm.lux.burqa.manager.login;

import android.content.Context;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 24/05/2017 17:28
 * @Version
 */
public interface LoginInterface {

    void startLogin(Context context);
    void startBind(Context context);
    void startCheckRealName(Context context);
    void startGesture(Context context);
    void startSuum(Context context);
}
