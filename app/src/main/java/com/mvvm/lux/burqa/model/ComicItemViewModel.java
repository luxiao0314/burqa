package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.SectionComicItemBinding;
import com.mvvm.lux.burqa.model.db.RealmHelper;
import com.mvvm.lux.burqa.model.event.ChaptersEvent;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.rx.RxBus;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/9 16:59
 * @Version
 */
public class ComicItemViewModel extends BaseViewModel {

    public static final String CHAPTERS = "chapters";

    public static final String OTHER_CHAPTERS = "other_chapters";

    public ObservableField<String> chapters_title = new ObservableField<>();

    public ObservableField<String> last_updatetime = new ObservableField<>();

    public ObservableField<String> obj_id = new ObservableField<>();

    public ObservableField<String> show_more = new ObservableField<>("查看全部章节");

    public ObservableList<ComicResponse.ChaptersBean.DataBean> chaptersList = new ObservableArrayList<>();  //原始全部集合

    public ObservableList<ComicResponse.ChaptersBean.DataBean> chaptersListLess = new ObservableArrayList<>();  //显示部分集合

    public ObservableList<ComicResponse.ChaptersBean.DataBean> tempList = new ObservableArrayList<>();  //临时集合

    public ObservableList<ComicResponse.ChaptersBean.DataBean> chaptersOther = new ObservableArrayList<>();

    public ObservableField<String> chapters_other_title = new ObservableField<>();

    public ObservableField<String> cover = new ObservableField<>();

    public ObservableBoolean showAll = new ObservableBoolean(true);

    private SectionComicItemBinding mDataBinding;

    public ComicItemViewModel(Activity activity, SectionComicItemBinding dataBinding) {
        super(activity);
        mDataBinding = dataBinding;
    }

    public void getLocalData() {
        ClassifyResponse classifyResponse = RealmHelper.getInstance()
                .queryTagResponse(Integer.parseInt(obj_id.get()));
        if (classifyResponse != null) {
            if (CHAPTERS.equals(classifyResponse.getChapters())) {
                mDataBinding.chaptersFlow.setAdapter(chaptersAdapter);
                chaptersAdapter.setSelectedList(classifyResponse.getTagPosition()); //设置tag默认给选中记录
            } else {
                mDataBinding.otherChaptersFlow.setAdapter(chaptersOtherAdapter);
                chaptersOtherAdapter.setSelectedList(classifyResponse.getTagPosition());
            }
            RxBus.init().postSticky(new ChaptersEvent(classifyResponse.getChapter_title(), obj_id.get()));
        }
    }

    /**
     * 连载
     */
    public TagAdapter chaptersAdapter = new TagAdapter<ComicResponse.ChaptersBean.DataBean>(tempList) {

        @Override
        public View getView(FlowLayout parent, int position, ComicResponse.ChaptersBean.DataBean dataBean) {
            ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.layout_tags_item, parent, false);
            TagAdapterViewModel viewModel = new TagAdapterViewModel(mActivity);
            viewModel.keyword.set(dataBean.getChapter_title());
            dataBinding.setVariable(BR.viewModel, viewModel);
            return dataBinding.getRoot();
        }
    };

    /**
     * 番外篇
     */
    public TagAdapter chaptersOtherAdapter = new TagAdapter<ComicResponse.ChaptersBean.DataBean>(chaptersOther) {

        @Override
        public View getView(FlowLayout parent, int position, ComicResponse.ChaptersBean.DataBean dataBean) {
            ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.layout_tags_item, parent, false);
            TagAdapterViewModel viewModel = new TagAdapterViewModel(mActivity);
            viewModel.keyword.set(dataBean.getChapter_title());
            dataBinding.setVariable(BR.viewModel, viewModel);
            return dataBinding.getRoot();
        }
    };

    /**
     * 流式布局item点击事件
     */
    public TagFlowLayout.OnTagClickListener mOnChaptersClickListener = (view, position, parent) -> {
        RxBus.init().postSticky(new ChaptersEvent(chaptersList.get(position).getChapter_title(),obj_id.get()));
        RealmHelper.getInstance()
                .insertTagPosition(Integer.parseInt(obj_id.get()),  // "obj_id": 39504,
                        position, CHAPTERS,   //连载 : 番剧
                        chaptersList.get(position).getChapter_title()); //"chapter_title": "2卷",
        ImagePicsListActivity.launch(mActivity,
                chaptersList.get(position).getChapter_id(),
                obj_id.get(),
                title.get(),
                cover.get());
        return true;
    };

    public TagFlowLayout.OnTagClickListener mOnOtherChaptersClickListener = (view, position, parent) -> {
        RxBus.init().postSticky(new ChaptersEvent(chaptersList.get(position).getChapter_title(), obj_id.get()));
        RealmHelper.getInstance()
                .insertTagPosition(Integer.parseInt(obj_id.get()),
                        position, OTHER_CHAPTERS,
                        chaptersList.get(position).getChapter_title());
        ImagePicsListActivity.launch(mActivity,
                chaptersOther.get(position).getChapter_id(),
                obj_id.get(),
                title.get(),
                cover.get());
        return true;
    };

    public View.OnClickListener mOnClickListener = view -> {
        tempList.clear();
        if (showAll.get()) {
            tempList.addAll(chaptersList);
            mDataBinding.otherChaptersFlow.setVisibility(View.VISIBLE);
            mDataBinding.titleOther.setVisibility(View.VISIBLE);
            showAll.set(false);
            show_more.set("收起章节");
        } else {
            tempList.addAll(chaptersListLess);
            mDataBinding.otherChaptersFlow.setVisibility(View.GONE);
            mDataBinding.titleOther.setVisibility(View.GONE);
            showAll.set(true);
            show_more.set("查看全部章节");
        }
        chaptersAdapter.notifyDataChanged();
    };
}
