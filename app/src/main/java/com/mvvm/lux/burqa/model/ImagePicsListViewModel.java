package com.mvvm.lux.burqa.model;

import android.databinding.ObservableField;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.event.ProgressEvent;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.burqa.ui.home.adapter.ImagePicsPagerAdapter;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.rx.RxBus;
import com.mvvm.lux.framework.utils.DateUtil;
import com.mvvm.lux.framework.utils.NetworkUtil;

import java.util.ArrayList;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/18 15:00
 * @Version
 */
public class ImagePicsListViewModel extends BaseViewModel {

    public ObservableField<String> obj_id = new ObservableField<>();
    public ObservableField<String> chapter_id = new ObservableField<>();
    public ObservableField<String> chapter_title = new ObservableField<>();
    public ObservableField<String> adver_tv = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> network_status = new ObservableField<>();
    public ObservableField<Integer> current_position = new ObservableField<>();
    public ObservableField<Integer> pageLimit = new ObservableField<>(3);

    private ArrayList<String> mUrls = new ArrayList<>();
    private ImagePicsListActivity mImagePicsListActivity;

    public ImagePicsPagerAdapter mPagerAdapter = new ImagePicsPagerAdapter((FragmentActivity) mActivity, mUrls);

    public ImagePicsListViewModel(ImagePicsListActivity imagePicsListActivity) {
        super(imagePicsListActivity);
        mImagePicsListActivity = imagePicsListActivity;
    }

    public void initEvent() {
        RxBus.init().toObservable(ProgressEvent.class)
                .subscribe(progressEvent -> {
                    refreshPosition(progressEvent.mProgress);
                });
    }

    public void initData() {
        time.set(DateUtil.getCurrentTime(DateUtil.DATETIME_PATTERN_6_2));
        network_status.set(NetworkUtil.getAPNType(mActivity));

        RetrofitHelper.init()
                .getChapter("chapter/" + obj_id.get() + "/" + chapter_id.get() + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<ComicPageResponse>(ServiceTask.create(mImagePicsListActivity)) {
                    @Override
                    public void onNext(ComicPageResponse comicPageResponse) {
                        mUrls.addAll(comicPageResponse.getPage_url());
                        if (!checkIllegal())
                            mActivity.finish();
                        refreshPosition(current_position.get());
                        mPagerAdapter.notifyDataSetChanged();
                    }
                });
    }

    private boolean checkIllegal() {
        return mUrls != null && mUrls.size() > 0;
    }

    private void refreshPosition(int position) {
        adver_tv.set((position + 1) + "/" + mUrls.size());
        current_position.set(position);
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerAdapter.onPageSelectedPostion(position);
                refreshPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }
}
