package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/11 13:21
 * @Version
 */
public class RecomDoubleItemViewModel extends BaseViewModel {

    public RecomDoubleItemViewModel(Activity activity) {
        super(activity);
    }

    public ObservableField<String> img = new ObservableField<>();

    public ObservableField<Integer> obj_id = new ObservableField<>();

    public View.OnClickListener mOnClickListener = view -> {
        ComicDesActivity.launch(mActivity, obj_id.get()+"");
    };
}
