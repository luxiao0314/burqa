package com.mvvm.lux.burqa.manager.hybrid_rn;

import android.app.Application;

import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.mvvm.lux.burqa.BuildConfig;
import com.mvvm.lux.framework.http.fresco.ImageLoaderConfig;

import java.util.Arrays;
import java.util.List;

import static com.mvvm.lux.framework.BaseApplication.getAppContext;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 13/06/2017 14:29
 * @Version
 */
public class MyReactNativeHost extends ReactNativeHost {

    public MyReactNativeHost(Application application) {
        super(application);
    }

    @Override
    public boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
        //设置rn的图片加载采用android中fresco的,图片采用防盗链处理
        MainPackageConfig.Builder configBuilder = new MainPackageConfig.Builder();
        configBuilder.setFrescoConfig(ImageLoaderConfig.getImagePipelineConfig(getAppContext()));
        return Arrays.<ReactPackage>asList(new MainReactPackage(configBuilder.build()), new JsReactPackage());
    }
}
