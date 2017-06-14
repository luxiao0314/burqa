package com.mvvm.lux.burqa.ui.react_native;

import android.app.Activity;

import com.mvvm.lux.burqa.manager.hybrid_rn.BaseReactActivity;
import com.mvvm.lux.framework.manager.router.Router;

/**
 * @Description rn处理activity:评论页面
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 12/06/2017 11:21 PM
 * @Version 1.0.0
 */
public class CommentReactActivity extends BaseReactActivity {

    @Override
    protected String getMainComponentName() {
        return "Comment";
    }

    public static void launch(Activity context, String id) {
        Router.from(context)
                .to(CommentReactActivity.class)
                .putString("comment_id", id)
                .launch();
    }
}
