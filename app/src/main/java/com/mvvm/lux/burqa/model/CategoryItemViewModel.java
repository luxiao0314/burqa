package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;

import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/15 23:31
 * @Version
 */
public class CategoryItemViewModel extends BaseViewModel {

    public ObservableField<String> cover = new ObservableField<>();

    public CategoryItemViewModel(Activity activity) {
        super(activity);
    }
}
