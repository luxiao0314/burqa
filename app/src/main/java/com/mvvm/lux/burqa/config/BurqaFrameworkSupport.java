package com.mvvm.lux.burqa.config;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.mvvm.lux.burqa.ui.home.activity.LoginActivity;
import com.mvvm.lux.framework.config.FrameworkSupport;
import com.mvvm.lux.framework.config.session.Session;
import com.mvvm.lux.framework.config.session.SessionState;
import com.mvvm.lux.framework.manager.dialogs.DialogManager;
import com.mvvm.lux.framework.manager.dialogs.config.BaseTask;
import com.mvvm.lux.framework.utils.SnackbarUtil;

import static com.mvvm.lux.framework.BaseApplication.getAppContext;

/**
 * @Description framework通信类.扩展了更多方法
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/9 14:17
 * @Version 1.0.0
 */
public class BurqaFrameworkSupport implements FrameworkSupport {
    @Override
    public void onSessionInvaild() {
        Session.logout();
        SnackbarUtil.showMessage("您的账号已在别处登录,请重新登录");
        Intent it = new Intent(getAppContext(), LoginActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getAppContext().startActivity(it);
    }

    @Override
    public void onCardInValid() {
        Session.bindout();
        SnackbarUtil.showMessage("您的卡已被其他账号绑定,请重新绑定");
        Intent it = new Intent(getAppContext(), LoginActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getAppContext().startActivity(it);
    }

    @Override
    public SessionState getSessionState() {
        if (Session.isBindCard()) {
            return SessionState.BIND;
        } else if (Session.isLogin()) {
            return SessionState.LOGIN;
        } else {
            return SessionState.GUEST;
        }
    }

    @Override
    public String getPhoneNumber() {
        return Session.getUser().phoneNumber;
    }

    @Override
    public String getToken() {
        return Session.getToken();
    }

    @Override
    public void goToActivity(FragmentActivity activity, int id, String url, String params) {

    }

    @Override
    public String getAppType() {
        return "1";
    }

    @Override
    public DialogFragment showNetworkProcessDialog(BaseTask taskExchangeModel) {
        return DialogManager.showProgressDialog(taskExchangeModel);
    }

    @Override
    public void onTokenInvalid() {

    }
}