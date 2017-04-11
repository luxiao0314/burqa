package com.mvvm.lux.burqa.model;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.AbsListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.base.SectionedRecyclerViewAdapter;
import com.mvvm.lux.burqa.databinding.ActivityComicDesBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.db.RealmHelper;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.burqa.ui.home.adapter.section.ComicCommentSection;
import com.mvvm.lux.burqa.ui.home.adapter.section.ComicHeaderSection;
import com.mvvm.lux.burqa.ui.home.adapter.section.ComicItemSection;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.widget.utils.DisplayUtil;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/8 14:06
 * @Version
 */
public class ComicDesViewModel extends BaseViewModel {
    private ComicDesActivity mActivity;
    private ActivityComicDesBinding mDataBinding;
    private String mObjId;
    private SectionedRecyclerViewAdapter mAdapter;
    private ClassifyResponse mClassifyResponse;
    private int mDistanceY;

    public ComicDesViewModel(ComicDesActivity activity, ActivityComicDesBinding dataBinding, String obj_id) {
        super(activity);
        mActivity = activity;
        mDataBinding = dataBinding;
        mObjId = obj_id;
        initLocalData();
        initData();
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 1);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        return layoutManager;
    }

    private void initLocalData() {
        mClassifyResponse = RealmHelper.getInstance()
                .queryTagResponse(Integer.parseInt(mObjId));
    }

    private void initData() {
        String url = "comic/" + mObjId + ".json";
        RetrofitHelper.init()
                .getComic(url)
                .compose(RxHelper.handleErr())
                .subscribe(new ProgressSubscriber<ComicResponse>(ServiceTask.create(mActivity)) {
                    @Override
                    public void onNext(ComicResponse comicResponse) {
                        title.set(comicResponse.getTitle());
                        mAdapter = new SectionedRecyclerViewAdapter();
                        mAdapter.addSection(new ComicHeaderSection(mActivity, comicResponse, mClassifyResponse));
                        mAdapter.addSection(new ComicItemSection(mActivity, comicResponse, mClassifyResponse));
                        mAdapter.addSection(new ComicCommentSection(mActivity, comicResponse));
                        mDataBinding.recyclerView.setAdapter(mAdapter);
                    }
                });
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

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //滑动的距离
                mDistanceY += dy;
                Toolbar toolbar = mDataBinding.include.toolbar;
                //toolbar的高度
                int toolbarHeight = DisplayUtil.getScreenWidth(mActivity) / 2 - 50;

                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 152, 20));  //toolBar背景无法设置为透明...??
                } else {
                    //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
                    //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
                    //将标题栏的颜色设置为完全不透明状态
                    toolbar.setBackgroundResource(R.color.colorPrimary);
                }

            }
        };
    }
}
