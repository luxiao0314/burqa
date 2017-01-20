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
public class ComicCommentListViewModel extends BaseViewModel {

    public ObservableField<String> avatar = new ObservableField<>();

    public ObservableField<String> nickname = new ObservableField<>();

    public ObservableField<String> createtime = new ObservableField<>();

    public ObservableField<String> content = new ObservableField<>();

    public ComicCommentListViewModel(Activity activity) {
        super(activity);
    }
}
