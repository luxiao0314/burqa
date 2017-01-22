package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.mvvm.lux.burqa.model.event.ChaptersEvent;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.rx.RxBus;

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

    public ComicHeaderViewModel(Activity activity, String objId) {
        super(activity);
        initEvent(objId);
    }

    private void initEvent(String objId) {
        RxBus.init()
                .toObservableSticky(ChaptersEvent.class)
                .subscribe(chaptersEvent -> {
                    if (objId.equals(chaptersEvent.mObjId))
                        if (TextUtils.isEmpty(chaptersEvent.mChapter_title)) {
                            chapter_title.set("开始阅读");
                        } else {
                            chapter_title.set("续看 " + chaptersEvent.mChapter_title);
                        }
                });
    }
}
