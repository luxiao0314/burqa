package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;

import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/10 17:16
 * @Version
 */
public class SubjectDesListViewModel extends BaseViewModel {

    public ObservableField<String> title_img = new ObservableField<>();
    public ObservableField<String> img = new ObservableField<>();
    public ObservableField<String> title_des = new ObservableField<>();

    public ObservableField<String> sub_title = new ObservableField<>();
    public ObservableField<String> des_little = new ObservableField<>();
    public ObservableField<String> des = new ObservableField<>();

    public SubjectDesListViewModel(Activity activity) {
        super(activity);
    }
}
