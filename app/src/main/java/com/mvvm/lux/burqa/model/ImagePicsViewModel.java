package com.mvvm.lux.burqa.model;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.ObservableField;
import android.graphics.drawable.Animatable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.event.BaseEvent;
import com.mvvm.lux.burqa.model.event.ProgressEvent;
import com.mvvm.lux.burqa.model.event.SwitchModeEvent;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.burqa.ui.home.adapter.ImagePicsPagerAdapter;
import com.mvvm.lux.burqa.ui.sub.ImagePicDialogFragment;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.manager.recycler.recyclerview.CommonAdapter;
import com.mvvm.lux.framework.manager.recycler.recyclerview.base.ViewHolder;
import com.mvvm.lux.framework.rx.RxBus;
import com.mvvm.lux.framework.utils.DateUtil;
import com.mvvm.lux.framework.utils.NetworkUtil;
import com.mvvm.lux.widget.utils.DisplayUtil;

import java.util.ArrayList;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;
import progress.CircleProgress;
import progress.enums.CircleStyle;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/18 15:00
 * @Version
 */
public class ImagePicsViewModel extends BaseViewModel {

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
    private ActivityImagePicsListBinding mDataBinding;

    public ImagePicsViewModel(ImagePicsListActivity imagePicsListActivity) {
        super(imagePicsListActivity);
        mImagePicsListActivity = imagePicsListActivity;
    }

    public void initEvent() {
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

    public void setDataBinding(ActivityImagePicsListBinding dataBinding) {
        mDataBinding = dataBinding;
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
            public void onPageScrollStateChanged(int scrollState) {
            }
        };
    }

    public ImagePicsPagerAdapter mPagerAdapter = new ImagePicsPagerAdapter((FragmentActivity) mActivity, mUrls);

    public CommonAdapter mCommonAdapter = new CommonAdapter<String>(mActivity, R.layout.adapter_image_pics_list_land, mUrls) {

        private SimpleDraweeView mPhotoView;

        @Override
        protected void convert(ViewHolder holder, String url, int position) {
            mPhotoView = holder.getView(R.id.image_land);
//            mPhotoView.setOnPhotoTapListener(mOnPhotoTapListener);
            new CircleProgress  //加载圆形进度条
                    .Builder()
                    .setStyle(CircleStyle.FAN)
                    .setProgressColor(mContext.getResources().getColor(R.color.orange_trans))
                    .setCustomText((position + 1) + "")
                    .setCircleRadius(DisplayUtil.dp2px(15))
                    .build()
                    .injectFresco(mPhotoView);

            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(url)
                    .setOldController(mPhotoView.getController())
                    .setControllerListener(mControllerListener)
                    .build();
            mPhotoView.setController(controller);
        }

        ControllerListener<ImageInfo> mControllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                int width = imageInfo.getWidth();
                int height = imageInfo.getHeight();
//                mPhotoView.update(screenHeight, height);
                mPhotoView.setAspectRatio((float) width / height);
            }
        };

        OnPhotoTapListener mOnPhotoTapListener = (view, x, y) -> {
            if (view instanceof PhotoDraweeView) {
                PhotoDraweeView photoView = (PhotoDraweeView) view;
                if (photoView.getScale() > photoView.getMinimumScale()) {
                    photoView.setScale(photoView.getMinimumScale(), true);
                } else {
                    ImagePicDialogFragment.show(mImagePicsListActivity, mUrls.size(), current_position.get());
                }
            }
        };
    };

    public RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
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
