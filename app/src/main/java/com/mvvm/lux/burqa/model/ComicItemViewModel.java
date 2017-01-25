package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.LayoutInflater;
import android.view.View;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ComicChapterLayoutBinding;
import com.mvvm.lux.burqa.databinding.SectionComicItemBinding;
import com.mvvm.lux.burqa.model.event.TagSelectEvent;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.rx.RxBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/9 16:59
 * @Version
 */
public class ComicItemViewModel extends BaseViewModel {


    public ObservableField<String> last_updatetime = new ObservableField<>();

    public ObservableField<String> show_more = new ObservableField<>("查看全部章节");

    public ObservableField<Integer> objId = new ObservableField<>();

    public ObservableBoolean showAll = new ObservableBoolean(false);

    private SectionComicItemBinding mDataBinding;
    private ComicResponse mResponse;

    public ComicItemViewModel(Activity activity, SectionComicItemBinding dataBinding, ComicResponse comicResponse) {
        super(activity);
        mDataBinding = dataBinding;
        mResponse = comicResponse;
    }

    public void initChapters() {
        getSimpleChapter(mResponse);
    }

    public View.OnClickListener mOnClickListener = view -> {
        if (showAll.get()) {
            getSimpleChapter(mResponse);
            showAll.set(false);
            show_more.set("查看全部章节");
        } else {
            getMoreChapters(mResponse);
            showAll.set(true);
            show_more.set("收起章节");
        }
    };

    private void getMoreChapters(ComicResponse response) {
        mDataBinding.llFlowlayout.removeAllViews();
        for (int i = 0; i < response.getChapters().size(); i++) {
            ComicResponse.ChaptersBean chaptersBean = response.getChapters().get(i);
            List<ComicResponse.ChaptersBean.DataBean> data = chaptersBean.getData();
            ComicChapterLayoutBinding comicChapterBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.comic_chapter_layout, null, false);
            ComicChapterViewModel viewModel = new ComicChapterViewModel(mActivity);
            viewModel.objId.set(response.getId());
            viewModel.title.set(response.getTitle());
            viewModel.cover.set(response.getCover());
            if (i == 0)
                viewModel.last_updatetime.set(last_updatetime.get());
            viewModel.data.addAll(data);
            viewModel.chapters_title.set(chaptersBean.getTitle() + "(话)");
            comicChapterBinding.setViewModel(viewModel);
            mDataBinding.llFlowlayout.addView(comicChapterBinding.getRoot());
        }
    }

    private List<ComicResponse.ChaptersBean.DataBean> chaptersListLess = new ArrayList<>();

    private void getSimpleChapter(ComicResponse response) {
        chaptersListLess.clear();
        mDataBinding.llFlowlayout.removeAllViews();
        ComicResponse.ChaptersBean chaptersBean = response.getChapters().get(0);
        List<ComicResponse.ChaptersBean.DataBean> data = chaptersBean.getData();
        int count = data.size() < 12 ? chaptersBean.getData().size() : 12;
        for (int i = 0; i < count; i++) {
            chaptersListLess.add(data.get(i));
        }
        ComicChapterLayoutBinding comicChapterBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.comic_chapter_layout, null, false);
        ComicChapterViewModel viewModel = new ComicChapterViewModel(mActivity);
        viewModel.objId.set(response.getId());
        viewModel.cover.set(response.getCover());
        viewModel.last_updatetime.set(last_updatetime.get());
        viewModel.data.addAll(chaptersListLess);
        viewModel.chapters_title.set(chaptersBean.getTitle() + "(话)");
        comicChapterBinding.setViewModel(viewModel);
        mDataBinding.llFlowlayout.addView(comicChapterBinding.getRoot());
    }

    public void getLocalData(ClassifyResponse classifyResponse) {
//        if (classifyResponse != null) {
//            if (OTHER_CHAPTERS.equals(classifyResponse.getChapters())) {
//                mDataBinding.otherChaptersFlow.setAdapter(chaptersOtherAdapter);
//                chaptersOtherAdapter.setSelectedList(classifyResponse.getTagPosition());
//            } else {
//                mDataBinding.chaptersFlow.setAdapter(chaptersAdapter);
//                chaptersAdapter.setSelectedList(classifyResponse.getTagPosition()); //设置tag默认给选中记录
//            }
//            RxBus.init().postSticky(new ChaptersEvent(classifyResponse.getChapter_title(), obj_id.get(), classifyResponse.isCollection()));
//        }
    }

    public void initEvent() {
        RxBus.init()
                .toObservableSticky(TagSelectEvent.class)
                .compose(RxHelper.io_main())
                .subscribe(tagSelectEvent -> {
                    if (tagSelectEvent.mId == objId.get()) {
//                        mDataBinding.chaptersFlow.setAdapter(chaptersAdapter);
//                        chaptersAdapter.setSelectedList(tagSelectEvent.mTagPosition); //设置tag默认给选中记录
                    }
                });
    }
}
