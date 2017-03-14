package com.mvvm.lux.burqa.model;

import android.databinding.ObservableField;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.burqa.ui.sub.activity.AuthorDesActivity;
import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/7 21:31
 * @Version
 */
public class RecomDoubleViewModel extends BaseViewModel {

    public RecomDoubleViewModel(FragmentActivity activity) {
        super(activity);
    }

    public ObservableField<String> img = new ObservableField<>();

    public ObservableField<String> sub_title = new ObservableField<>();

    public ObservableField<Integer> type = new ObservableField<>();

    public ObservableField<String> head_title = new ObservableField<>();

    public ObservableField<Boolean> hide_title = new ObservableField<>(false);

    public ObservableField<Integer> obj_id = new ObservableField<>();

    public View.OnClickListener mOnClickListener = view -> {
        switch (type.get()) {
            case 8:
                ComicDesActivity.launch(mActivity, obj_id.get()+"");
                break;
            case 9:
                AuthorDesActivity.launch(mActivity,obj_id.get()+"");
                break;
            case 1:
            case 0:
                ComicDesActivity.launch(mActivity, obj_id.get()+"");
                break;
        }
    };
}
