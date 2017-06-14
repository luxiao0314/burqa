package com.mvvm.lux.burqa.ui.home.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.FragmentUpdateBinding;
import com.mvvm.lux.burqa.manager.hybrid_rn.JsReactPackage;
import com.mvvm.lux.burqa.model.UpdateViewModel;
import com.mvvm.lux.burqa.ui.BurqaApp;
import com.mvvm.lux.framework.base.MvvmFragment;

import static com.facebook.react.common.ApplicationHolder.getApplication;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 14/06/2017 14:27
 * @Version
 */
public class UpdateFragment extends MvvmFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_update;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        mViewModel = new UpdateViewModel(getActivity());
        ReactRootView reactRootView = new ReactRootView(BurqaApp.getAppContext());
        ReactInstanceManager reactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")//对应index.android.js
                .addPackage(new MainReactPackage())
                .addPackage(new JsReactPackage())
                //.setUseDeveloperSupport(BuildConfig.DEBUG) //开发者支持，BuildConfig.DEBUG的值默认是false，无法使用开发者菜单
                .setUseDeveloperSupport(true) //开发者支持,开发的时候要设置为true，不然无法使用开发者菜单
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        //这里的ReactNativeView对应index.android.js中AppRegistry.registerComponent('iShiWuPai', () => Root)的iShiWuPai
        reactRootView.startReactApplication(reactInstanceManager, "iShiWuPai", null);
        ((FragmentUpdateBinding) mDataBinding).rnUpdatePage.addView(reactRootView);
    }

    public static Fragment newInstance() {
        return new UpdateFragment();
    }
}
