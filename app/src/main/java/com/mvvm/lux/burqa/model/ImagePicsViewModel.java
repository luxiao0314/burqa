package com.mvvm.lux.burqa.model;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.ObservableField;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.event.BaseEvent;
import com.mvvm.lux.burqa.model.event.ProgressEvent;
import com.mvvm.lux.burqa.model.event.SwitchModeEvent;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.burqa.ui.sub.adapter.ImagePicsListAdapter;
import com.mvvm.lux.burqa.ui.sub.adapter.ImagePicsPagerAdapter;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.rx.RxBus;

import java.util.ArrayList;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/18 15:00
 * @Version
 */
public class ImagePicsViewModel extends BaseViewModel implements ViewPager.OnPageChangeListener {

    public ObservableField<String> obj_id = new ObservableField<>();
    public ObservableField<String> chapter_id = new ObservableField<>();
    public ObservableField<String> chapter_title = new ObservableField<>();
    public ObservableField<String> adver_tv = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> network_status = new ObservableField<>();
    public ObservableField<Integer> current_position = new ObservableField<>(); //从FlowLayout传递过来的默认的position
    public ObservableField<Integer> pageLimit = new ObservableField<>(3);
    private ArrayList<String> mUrls = new ArrayList<>();
    private ActivityImagePicsListBinding mDataBinding;
    private ImagePicsListActivity mImagePicsListActivity;
    private ImagePicsPagerAdapter mPagerAdapter;
    private ImagePicsListAdapter mListAdapter;

    public ImagePicsViewModel(ImagePicsListActivity imagePicsListActivity, ActivityImagePicsListBinding dataBinding) {
        super(imagePicsListActivity);
        mImagePicsListActivity = imagePicsListActivity;
        mDataBinding = dataBinding;
        initEvent();
    }

    private void initEvent() {
        RxBus.init().toObservable(BaseEvent.class)
                .subscribe(baseEvent -> {
                    if (baseEvent instanceof ProgressEvent) {
                        refreshPosition(((ProgressEvent) baseEvent).mProgress);
                    } else if (baseEvent instanceof SwitchModeEvent) {
                        switchMode();
                    }
                });
    }

    public LinearLayoutManager linearLayoutManager() {
        return new LinearLayoutManager(mImagePicsListActivity);
    }

    private void switchMode() {
        int mCurrentOrientation = mImagePicsListActivity.getResources().getConfiguration().orientation;
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            mImagePicsListActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);   //设置横屏
        } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            mImagePicsListActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   //设置竖屏
        }
    }

    public void initData() {
        RetrofitHelper.init()
                .getChapter("chapter/" + obj_id.get() + "/" + chapter_id.get() + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<ComicPageResponse>(ServiceTask.create(mImagePicsListActivity)) {
                    @Override
                    public void onNext(ComicPageResponse comicPageResponse) {
                        mUrls.addAll(comicPageResponse.getPage_url());
                        if (!checkIllegal())
                            mActivity.finish();
                        mPagerAdapter.notifyDataSetChanged();
                        refreshPosition(current_position.get());
                    }
                });
    }

    private boolean checkIllegal() {
        return mUrls != null && mUrls.size() > 0;
    }

    private void refreshPosition(int position) {
        adver_tv.set((position + 1) + "/" + mUrls.size());
        mDataBinding.pager.setCurrentItem(position);
    }

    public ViewPager.OnPageChangeListener onPageChange() {
        return this;
    }

    @Override
    public void onPageSelected(int position) {
        refreshPosition(position);
    }

    public ImagePicsPagerAdapter getPagerAdapter() {
        if (mPagerAdapter == null) {
            mPagerAdapter = new ImagePicsPagerAdapter(mImagePicsListActivity, mUrls);
        }
        return mPagerAdapter;
    }

    //------------------------  横向recycleView  -------------------------//

    public ImagePicsListAdapter getCommonAdapter() {
        if (mListAdapter == null) {
            mListAdapter = new ImagePicsListAdapter(mImagePicsListActivity, R.layout.adapter_image_pics_list_land, mUrls);
        }
        return mListAdapter;
    }

    public RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        //ImageLoader.pauseLoader();
                        if (!Fresco.getImagePipeline().isPaused()) {
                            Fresco.getImagePipeline().pause();
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //ImageLoader.resumeLoader();
                        if (Fresco.getImagePipeline().isPaused()) {
                            Fresco.getImagePipeline().resume();
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
