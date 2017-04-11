package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.SectionComicDesBinding;
import com.mvvm.lux.burqa.model.db.RealmHelper;
import com.mvvm.lux.burqa.model.event.ChaptersEvent;
import com.mvvm.lux.burqa.model.event.TagSelectEvent;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.rx.RxBus;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/8 14:44
 * @Version
 */
public class ComicHeaderViewModel extends BaseViewModel {

    public ObservableField<String> cover = new ObservableField<>(); //img url

    public ObservableField<String> hot_num = new ObservableField<>();   //战斗力

    public ObservableField<String> authors = new ObservableField<>();   //作者

    public ObservableField<String> description = new ObservableField<>();   //描述

    public ObservableField<String> subscribe_num = new ObservableField<>();   //订阅

    public ObservableField<String> chapter_title = new ObservableField<>("开始阅读");   //章节

    public ObservableField<String> islong = new ObservableField<>();   //是否为长文章:2为长文章

    public ObservableField<Integer> maxLines = new ObservableField<>(2);

    public ObservableField<Integer> src = new ObservableField<Integer>(R.drawable.detail_intr_expand);

    private ComicResponse comicResponse;

    private ClassifyResponse classifyResponse;

    private SectionComicDesBinding dataBinding;

    public ComicHeaderViewModel(Activity activity, ComicResponse comicResponse, ClassifyResponse classifyResponse, ViewDataBinding dataBinding) {
        super(activity);
        this.comicResponse = comicResponse;
        this.dataBinding = (SectionComicDesBinding) dataBinding;
        this.classifyResponse = classifyResponse;
        initEvent(comicResponse.getId() + "");
        initCollection();
    }

    private void initCollection() {
        boolean isCollection = RealmHelper.getInstance().queryCollectionId(comicResponse.getId());
        if (!isCollection) {
            dataBinding.collect.setSelected(false);
            dataBinding.collect.setText("添加收藏");
        } else {
            dataBinding.collect.setSelected(true);
            dataBinding.collect.setText("已收藏");
        }
    }

    private void initEvent(String objId) {
        addSubscribe(RxBus.init()
                .toObservableSticky(ChaptersEvent.class)
                .subscribe(chaptersEvent -> {
                    if (objId.equals(chaptersEvent.mObjId)) {
                        if (TextUtils.isEmpty(chaptersEvent.mChapter_title)) {
                            chapter_title.set("开始阅读");
                        } else {
                            chapter_title.set("续看 " + chaptersEvent.mChapter_title);
                        }
                    }
                }));
    }

    //章节的点击事件
    public View.OnClickListener onChapterClickListener() {
        return view -> {
            if (classifyResponse == null) {
                List<ComicResponse.ChaptersBean.DataBean> data = comicResponse.getChapters().get(0).getData();
                ImagePicsListActivity.launch(mActivity,
                        data.get(data.size() - 1).getChapter_id(),    //最后一个漫画的id,即最新卷
                        data.size() - 1,  //默认选中第一个
                        "chapters",
                        comicResponse.getId() + "",
                        comicResponse.getTitle(),
                        comicResponse.getCover());
                chapter_title.set("续看 " + data.get(data.size() - 1).getChapter_title());
                RxBus.init().postSticky(new TagSelectEvent(data.size() - 1, comicResponse.getId()));
            } else {
                ImagePicsListActivity.launch(mActivity,
                        Integer.parseInt(classifyResponse.getChapter_id()),
                        classifyResponse.getTagPosition(),
                        classifyResponse.getChapters(),
                        comicResponse.getId() + "",
                        classifyResponse.getTitle(),
                        classifyResponse.getCover());
            }
        };
    }

    //收藏的点击事件
    public View.OnClickListener onCollectClickListener() {
        return view -> {
            boolean isCollection = RealmHelper.getInstance().queryCollectionId(comicResponse.getId());
            if (!isCollection) {
                RealmHelper.getInstance().insertCollection(comicResponse.getId(), true);
                dataBinding.collect.setSelected(true);
                dataBinding.collect.setText("已收藏");
            } else {
                RealmHelper.getInstance().insertCollection(comicResponse.getId(), false);
                dataBinding.collect.setSelected(false);
                dataBinding.collect.setText("添加收藏");
            }
        };
    }

    private boolean loadMore = true;
    public View.OnClickListener loadMoreDes = view -> {
        if (loadMore) {
            loadMore = false;
            maxLines.set(10);
            src.set(R.drawable.detail_intr_close);
        } else {
            src.set(R.drawable.detail_intr_expand);
            loadMore = true;
            maxLines.set(2);
        }
    };


}
