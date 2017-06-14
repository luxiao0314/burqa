package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.app.Activity;

import com.mvvm.lux.framework.manager.router.Router;

/**
 * @Description rn处理activity
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 12/06/2017 11:21 PM
 * @Version 1.0.0
 */
public class MyReactActivity extends BaseReactActivity {

    @Override
    protected String getMainComponentName() {
        return "Comment";
    }

    public static void launch(Activity context, String id) {
        Router.from(context)
                .to(MyReactActivity.class)
                .putString("comment_id", id)
                .launch();
    }
}
