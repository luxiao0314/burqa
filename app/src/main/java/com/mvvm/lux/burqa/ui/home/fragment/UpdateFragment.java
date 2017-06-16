package com.mvvm.lux.burqa.ui.home.fragment;

import android.support.v4.app.Fragment;

import com.mvvm.lux.burqa.manager.hybrid_rn.ReactFragment;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 14/06/2017 14:27
 * @Version
 */
public class UpdateFragment extends ReactFragment {

    @Override
    public String getMainComponentName() {
        return "UpdatePages";
    }

    public static Fragment newInstance() {
        return new UpdateFragment();
    }

}
