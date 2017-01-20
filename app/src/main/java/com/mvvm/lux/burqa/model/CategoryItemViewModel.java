package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.mvvm.lux.burqa.ui.home.activity.ComicClassifyActivity;
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
    public ObservableField<String> tag_id = new ObservableField<>();

    public CategoryItemViewModel(Activity activity) {
        super(activity);
    }

    public View.OnClickListener mOnClickListener = view -> {
        ComicClassifyActivity.launch(mActivity,tag_id.get(),title.get());
    };

}
