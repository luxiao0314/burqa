package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.os.Bundle;
import android.view.KeyEvent;

import com.mvvm.lux.framework.config.MarkAble;
import com.mvvm.lux.framework.manager.router.Router;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 13/06/2017 10:45 PM
 * @Version
 */
public class BaseReactActivity extends ReactActivity implements MarkAble {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getInstanceTag() {
        return this.getClass().getSimpleName() + this.hashCode();
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
