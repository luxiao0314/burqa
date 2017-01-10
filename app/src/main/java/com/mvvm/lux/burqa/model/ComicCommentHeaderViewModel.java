package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;

import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/10 10:21
 * @Version
 */
public class ComicCommentHeaderViewModel extends BaseViewModel {

    public ObservableField<String> comment_counts = new ObservableField<>();

    public ComicCommentHeaderViewModel(Activity activity) {
        super(activity);
    }
}
