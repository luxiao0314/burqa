package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.mvvm.lux.burqa.model.event.ChaptersEvent;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.burqa.ui.home.adapter.ChaptersAdapter;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.rx.RxBus;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/25 18:5
 * @Version
 */
public class TagChapterAdapterViewModel extends BaseViewModel {

    public ObservableField<String> keyword = new ObservableField<>();

    public ObservableField<String> chapter_title = new ObservableField<>();

    public ObservableField<Integer> chapter_id = new ObservableField<>();

    public ObservableField<Integer> position = new ObservableField<>();

    public ObservableField<Integer> obj_id = new ObservableField<>();

    public ObservableField<String> cover = new ObservableField<>();

    private ChaptersAdapter mChaptersAdapter;

    public TagChapterAdapterViewModel(Activity activity, ChaptersAdapter chaptersAdapter) {
        super(activity);
        mChaptersAdapter = chaptersAdapter;
    }

    /**
     * 章节的点击事件
     */
    public View.OnClickListener mOnClickListener = view -> {
        ImagePicsListActivity.launch(mActivity,
                chapter_id.get(),
                position.get(),
                "",
                obj_id.get() + "",
                title.get(),
                cover.get());
        mChaptersAdapter.setSelectPosition(position.get());
        RxBus.init().postSticky(new ChaptersEvent(chapter_title.get(), obj_id.get() + ""));
    };
}
