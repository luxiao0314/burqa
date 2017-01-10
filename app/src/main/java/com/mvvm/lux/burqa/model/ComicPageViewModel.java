package com.mvvm.lux.burqa.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicPageActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/10 15:23
 * @Version
 */
public class ComicPageViewModel extends BaseViewModel {

    public ObservableField<String> obj_id = new ObservableField<>();

    public ObservableField<String> chapter_id = new ObservableField<>();

    public ObservableField<String> current_position = new ObservableField<>();

    public ObservableList<String> page_url = new ObservableArrayList<>();

    private ComicPageActivity mActivity;

    public ComicPageViewModel(ComicPageActivity activity) {
        super(activity);
        mActivity = activity;
        initData();
    }

    private void initData() {
        RetrofitHelper.init()
                .getChapter("chapter/" + obj_id + "/" + chapter_id + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<ComicPageResponse>(ServiceTask.create(mActivity)) {
                    @Override
                    public void onNext(ComicPageResponse comicPageResponse) {
                        page_url.addAll(comicPageResponse.getPage_url());
                    }
                });
    }
}
