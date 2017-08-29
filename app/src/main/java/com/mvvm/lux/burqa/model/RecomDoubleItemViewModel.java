package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.burqa.ui.sub.activity.SubjectDesActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.widget.sonic.BrowsersActivity;

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

    public ObservableField<Integer> type = new ObservableField<>();

    public ObservableField<String> url = new ObservableField<>();

    public View.OnClickListener mOnClickListener = view -> {
        switch (type.get()) {
            case 6:
                BrowsersActivity.launch(mActivity, url.get());
//                BrowserActivity.launch(mActivity, url.get(), title.get());
                break;
            case 5:
                SubjectDesActivity.launch(mActivity,obj_id.get());
                break;
            case 1:
                ComicDesActivity.launch(mActivity, obj_id.get() + "");
                break;
        }
    };
}
