package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;

import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/1 10:45
 * @Version
 */
public class GameViewItemViewModel extends BaseViewModel {

    public ObservableField<String> create_time = new ObservableField<>();
    public ObservableField<String> img = new ObservableField<>();

    public GameViewItemViewModel(Activity activity) {
        super(activity);
    }
}
