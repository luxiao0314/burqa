package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;

import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/18 17:42
 * @Version
 */
public class ImagePicsItemViewModel extends BaseViewModel {

    public ObservableField<String> url = new ObservableField<>();

    public ImagePicsItemViewModel(Activity activity) {
        super(activity);
    }
}
