package com.mvvm.lux.burqa.manager.hybrid_rn;

import com.github.mzule.activityrouter.annotation.Router;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 16/06/2017 11:17
 * @Version
 */
@Router("jsAndroidActivity")
public class JsAndroidActivity extends BaseReactActivity {

    @Override
    protected String getMainComponentName() {
        return getIntent().getStringExtra("jsRouter");
    }
}
