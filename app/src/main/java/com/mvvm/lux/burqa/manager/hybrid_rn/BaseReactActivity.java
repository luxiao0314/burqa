package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.react.ReactActivity;
import com.mvvm.lux.framework.BaseApplication;
import com.mvvm.lux.framework.config.MarkAble;
import com.mvvm.lux.framework.http.RetrofitExcuter;
import com.mvvm.lux.framework.manager.router.Router;
import com.mvvm.lux.framework.utils.Logger;

import io.realm.Realm;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 13/06/2017 10:45 PM
 * @Version
 */
public class BaseReactActivity extends ReactActivity implements MarkAble{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(this.getClass().getName(), this.getClass().getName() + "------>onCreate");
        init();
    }

    private void init() {
        setTranslucentStatus(true);
        BaseApplication.getAppContext().registerActivity(this);
    }

    @Override
    public String getInstanceTag() {
        return this.getClass().getSimpleName() + this.hashCode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(this.getClass().getName(), this.getClass().getName() + "------>onDestroy");
        if (!Realm.getDefaultInstance().isClosed()) {
            Realm.getDefaultInstance().close();
        }
        BaseApplication.getAppContext().unregisterActivity(this);
        RetrofitExcuter.getOkHttpClient().dispatcher().cancelAll();
    }


    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Router.pop(this);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
