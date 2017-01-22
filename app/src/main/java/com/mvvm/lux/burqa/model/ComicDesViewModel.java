package com.mvvm.lux.burqa.model;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.base.SectionedRecyclerViewAdapter;
import com.mvvm.lux.burqa.databinding.ActivityComicDesBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.burqa.ui.home.adapter.section.ComicCommentSection;
import com.mvvm.lux.burqa.ui.home.adapter.section.ComicHeaderSection;
import com.mvvm.lux.burqa.ui.home.adapter.section.ComicItemSection;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;

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

    public ComicDesViewModel(ComicDesActivity activity, ActivityComicDesBinding dataBinding, String obj_id) {
        super(activity);
        mActivity = activity;
        mDataBinding = dataBinding;
        mObjId = obj_id;
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

    private void initData() {
        String url = "comic/" + mObjId + ".json";
        RetrofitHelper.init()
                .getComic(url)
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<ComicResponse>(ServiceTask.create(mActivity)) {
                    @Override
                    public void onNext(ComicResponse comicResponse) {
                        title.set(comicResponse.getTitle());
                        mAdapter = new SectionedRecyclerViewAdapter();
                        mAdapter.addSection(new ComicHeaderSection(mActivity, comicResponse,mObjId));
                        mAdapter.addSection(new ComicItemSection(mActivity, comicResponse, mObjId));
                        mAdapter.addSection(new ComicCommentSection(mActivity, comicResponse));
                        mDataBinding.recyclerView.setAdapter(mAdapter);
                    }
                });
    }
}
