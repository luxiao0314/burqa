package com.mvvm.lux.burqa.model;

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
import com.mvvm.lux.burqa.model.event.ChaptersEvent;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicPageResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.burqa.ui.sub.adapter.ImagePicsListAdapter;
import com.mvvm.lux.burqa.ui.sub.adapter.ImagePicsPagerAdapter;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.exception.CusException;
import com.mvvm.lux.framework.http.exception.RetrofitException;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.rx.RxBus;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;

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
    private boolean misScrolled;

    public ImagePicsViewModel(ImagePicsListActivity imagePicsListActivity, ActivityImagePicsListBinding dataBinding) {
        super(imagePicsListActivity);
        mImagePicsListActivity = imagePicsListActivity;
        mDataBinding = dataBinding;
    }

    public LinearLayoutManager linearLayoutManager() {
        return new LinearLayoutManager(mImagePicsListActivity);
    }

    public void initData() {
        addSubscribe(RetrofitHelper.init()
                .getChapter("chapter/" + obj_id.get() + "/" + chapter_id.get() + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<ComicPageResponse>(ServiceTask.create(mImagePicsListActivity)) {
                    @Override
                    public void onNext(ComicPageResponse comicPageResponse) {
                        result(comicPageResponse, current_position.get());
                    }
                }));
    }

    private void result(ComicPageResponse comicPageResponse, Integer position) {
        mUrls.clear();
        chapter_title.set(comicPageResponse.getTitle());    //"title": "第61话",
        mComic_id = comicPageResponse.getComic_id();
        mUrls.addAll(comicPageResponse.getPage_url());
        if (!checkIllegal())
            mActivity.finish();
        refreshPosition(position);
    }

    private boolean checkIllegal() {
        return mUrls != null && mUrls.size() > 0;
    }

    public void refreshPosition(int position) {
        getPagerAdapter().notifyDataSetChanged();
        getCommonAdapter().notifyDataSetChanged();

        getPagerAdapter().chapter_title = chapter_title.get();
        getCommonAdapter().chapter_title = chapter_title.get();
        getPagerAdapter().currentPosition = position;
        getCommonAdapter().currentPosition = position;
        current_position.set(position);
        adver_tv.set((position + 1) + "/" + mUrls.size());
        if (mDataBinding != null)
            mDataBinding.pager.setCurrentItem(position, false);
        if (mDataLandBinding != null)
            mDataLandBinding.recyclerView.scrollToPosition(position);
    }

    public ViewPager.OnPageChangeListener onPageChange() {
        return this;
    }

    @Override
    public void onPageSelected(int position) {
        getPagerAdapter().currentPosition = position;
        current_position.set(position);
        adver_tv.set((position + 1) + "/" + mUrls.size());
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

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    getCommonAdapter().currentPosition = lastItemPosition;
                    current_position.set(lastItemPosition);
                    adver_tv.set((lastItemPosition + 1) + "/" + mUrls.size());
                }
            }
        };
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 当滑向viewPager最后一页时,切换为下一章节
     *
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                misScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                misScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (mDataBinding.pager.getCurrentItem() == mDataBinding.pager.getAdapter().getCount() - 1 && !misScrolled) {
                    initNextData();
                }
                misScrolled = true;
                break;
        }
    }

    private void initNextData() {
        String url = "comic/" + obj_id.get() + ".json";
        RetrofitHelper.init()
                .getComic(url)
                .compose(RxHelper.handleErr())
                .flatMap(new Func1<ComicResponse, Observable<ComicPageResponse>>() {
                    @Override
                    public Observable<ComicPageResponse> call(ComicResponse comicResponse) {
                        tag_position.set(tag_position.get() - 1);
                        if (tag_position.get() < 0) {
                            return Observable.error(RetrofitException.handleException(new CusException("已经看完咯O(∩_∩)O~")));
                        }
                        ComicResponse.ChaptersBean.DataBean dataBean = comicResponse.getChapters()
                                .get(0)
                                .getData()
                                .get(tag_position.get());
                        int chapter_id = dataBean.getChapter_id();
                        ImagePicsViewModel.this.chapter_id.set(String.valueOf(chapter_id));
                        chapter_title.set(dataBean.getChapter_title());
                        RxBus.init().postSticky(new ChaptersEvent(dataBean.getChapter_title(), obj_id.get() + ""));   //续看 按钮
                        return RetrofitHelper.init()
                                .getChapter("chapter/" + obj_id.get() + "/" + chapter_id + ".json")
                                .compose(RxHelper.handleErr());
                    }
                })
                .subscribe(new ProgressSubscriber<ComicPageResponse>(ServiceTask.create(mImagePicsListActivity)) {
                    @Override
                    public void onNext(ComicPageResponse comicPageResponse) {
                        result(comicPageResponse, 0);
                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        //销毁当前页面的时候存一下
        saveLocal();
    }

    private void saveLocal() {
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

    /**
     * 获取的dataBinding不对,导致setCurrentPosition使用的是之前的控件,说所以不执行,而且需要一定的延迟才能设置成功
     *
     * @param dataBinding
     */
    public void setDataLandBinding(ActivityImagePicsListLandBinding dataBinding) {
        mDataLandBinding = dataBinding;
    }

    public void setDataBinding(ActivityImagePicsListBinding dataBinding) {
        mDataBinding = dataBinding;
    }
}
