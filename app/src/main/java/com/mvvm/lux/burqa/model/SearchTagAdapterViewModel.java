package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;

import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/11/17 18:56
 * @Version $VALUE
 */
public class SearchTagAdapterViewModel extends BaseViewModel {

    public ObservableField<String> keyword = new ObservableField<>();

    public SearchTagAdapterViewModel(Activity activity) {
        super(activity);
    }
}
