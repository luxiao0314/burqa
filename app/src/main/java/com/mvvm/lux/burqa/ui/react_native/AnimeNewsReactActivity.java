package com.mvvm.lux.burqa.ui.react_native;

import android.app.Activity;

import com.mvvm.lux.burqa.manager.hybrid_rn.BaseReactActivity;
import com.mvvm.lux.framework.manager.router.Router;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 14/06/2017 10:24 PM
 * @Version
 */
public class AnimeNewsReactActivity extends BaseReactActivity {

    @Override
    protected String getMainComponentName() {
        return "AnimeNewsPages";
    }

    public static void launch(Activity context) {
        Router.from(context)
                .to(AnimeNewsReactActivity.class)
                .launch();
    }
}
