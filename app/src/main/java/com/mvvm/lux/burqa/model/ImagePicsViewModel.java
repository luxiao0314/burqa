package com.mvvm.lux.burqa.model;

import android.content.pm.ActivityInfo;
import android.databinding.ObservableField;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListBinding;
import com.mvvm.lux.burqa.databinding.ActivityImagePicsListLandBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.db.RealmHelper;
import com.mvvm.lux.burqa.model.event.ProgressEvent;
import com.mvvm.lux.burqa.model.event.SwitchModeEvent;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.burqa.ui.sub.adapter.ImagePicsListAdapter;
import com.mvvm.lux.burqa.ui.sub.adapter.ImagePicsPagerAdapter;
import com.mvvm.lux.framework.base.BaseEvent;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.rx.RxBus;
import com.mvvm.lux.widget.utils.DisplayUtil;

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
    public ObservableField<String> cover = new ObservableField<>();
    public ObservableField<String> chapters = new ObservableField<>();
    public ObservableField<String> chapter_title = new ObservableField<>();
    public ObservableField<String> adver_tv = new ObservableField<>();  // 18/50
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> network_status = new ObservableField<>();
    public ObservableField<Integer> current_position = new ObservableField<>(0); //从FlowLayout传递过来的默认的position
    public ObservableField<Integer> pageLimit = new ObservableField<>(3);
    public ObservableField<Integer> tag_position = new ObservableField<>();
    private ArrayList<String> mUrls = new ArrayList<>();
    private ActivityImagePicsListBinding mDataBinding;
    private ActivityImagePicsListLandBinding mDataLandBinding;
    private ImagePicsListActivity mImagePicsListActivity;
    private ImagePicsPagerAdapter mPagerAdapter;
    private ImagePicsListAdapter mListAdapter;
    private int mComic_id;

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
        if (DisplayUtil.isPortrait()) {
            mImagePicsListActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);   //设置横屏
        } else {
            mImagePicsListActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   //设置竖屏
        }
    }

    public void initData() {
        mUrls.clear();
        addSubscribe(RetrofitHelper.init()
                .getChapter("chapter/" + obj_id.get() + "/" + chapter_id.get() + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<ComicPageResponse>(ServiceTask.create(mImagePicsListActivity)) {
                    @Override
                    public void onNext(ComicPageResponse comicPageResponse) {
                        chapter_title.set(comicPageResponse.getTitle());    //"title": "第61话",
                        mComic_id = comicPageResponse.getComic_id();
                        mUrls.addAll(comicPageResponse.getPage_url());
                        if (!checkIllegal())
                            mActivity.finish();
                        refreshPosition(current_position.get());
                    }
                }));
    }

    private boolean checkIllegal() {
        return mUrls != null && mUrls.size() > 0;
    }

    private void refreshPosition(int position) {
        getPagerAdapter().notifyDataSetChanged();
        getCommonAdapter().notifyDataSetChanged();
        current_position.set(position);
        adver_tv.set((position + 1) + "/" + mUrls.size());
        if (mDataBinding != null)
            mDataBinding.pager.setCurrentItem(position, false);
        if (mDataLandBinding != null)
            mDataLandBinding.recyclerView.scrollToPosition(position);
        getPagerAdapter().currentPosition = position;
        getCommonAdapter().currentPosition = position;
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
            mPagerAdapter = new ImagePicsPagerAdapter(mImagePicsListActivity, mUrls, chapter_title.get());
        }
        return mPagerAdapter;
    }

    //------------------------  横向recycleView  -------------------------//

    public ImagePicsListAdapter getCommonAdapter() {
        if (mListAdapter == null) {
            mListAdapter = new ImagePicsListAdapter(mImagePicsListActivity, R.layout.adapter_image_pics_list_land, mUrls, chapter_title.get());
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

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    current_position.set(lastItemPosition);
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

    @Override
    public void detachView() {
        super.detachView();
        //销毁当前页面的时候存一下
        ClassifyResponse response = new ClassifyResponse();
        response.setId(mComic_id);  //对应漫画id
        response.setTitle(title.get()); //标题
        response.setCover(cover.get()); //图片url
        response.setAuthors(chapter_title.get());   //12页/30话
        response.setChapter_title(chapter_title.get());   //12页/30话
        response.setTime(System.currentTimeMillis());   //当前时间戳,用于排序
        response.setPagePosition(current_position.get()); //当前页面的position
        response.setTagPosition(tag_position.get()); //当tagLoyout的position
        response.setChapter_id(chapter_id.get()); //当tagLotout的position
        response.setChapters(chapters.get());   //是连载还是番剧
        RealmHelper.getInstance().insertClassifyList(response);
    }

    public void getLocalData() {
        int pagePosition = RealmHelper.getInstance()
                .queryPagePosition(Integer.parseInt(obj_id.get()), tag_position.get());
        current_position.set(pagePosition);
    }

    public void setDataBinding(ActivityImagePicsListLandBinding dataBinding) {
        mDataLandBinding = dataBinding;
    }

    public void refresh() {
        refreshPosition(current_position.get());
    }
}
