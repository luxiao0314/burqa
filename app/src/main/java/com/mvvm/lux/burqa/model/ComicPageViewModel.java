package com.mvvm.lux.burqa.model;

import android.databinding.ObservableField;

import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicPageActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.utils.Logger;

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
                        Logger.e(comicPageResponse.getTitle());
                    }
                });
    }
}
