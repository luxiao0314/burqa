package com.mvvm.lux.burqa.model;

import android.databinding.ObservableField;

import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/7 21:31
 * @Version
 */
public class RecomDoubleViewModel extends BaseViewModel {

    public ObservableField<String> img = new ObservableField<>();

    public ObservableField<String> title = new ObservableField<>();

    public ObservableField<String> sub_title = new ObservableField<>();

    public ObservableField<String> head_title = new ObservableField<>();

    public ObservableField<Boolean> hide_title = new ObservableField<>(false);

}
