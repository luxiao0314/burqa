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
 * @Date 2017/1/16 10:34
 * @Version
 */
public class ClassifyItemViewModel extends BaseViewModel {

    public ObservableField<String> cover = new ObservableField<>();

    public ObservableField<String> author = new ObservableField<>();

    public ObservableField<String> id = new ObservableField<>();

    public ClassifyItemViewModel(Activity activity) {
        super(activity);
    }

    public View.OnClickListener mOnClickListener = view -> {
        ComicDesActivity.launch(mActivity,id.get());
    };
}
