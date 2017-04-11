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
    public ObservableField<Integer> obj_id = new ObservableField<>();

    public SubjectDesListViewModel(Activity activity) {
        super(activity);
    }

    public View.OnClickListener mOnClickListener = view -> {
        ComicDesActivity.launch(mActivity, obj_id.get() + "");
    };
}
