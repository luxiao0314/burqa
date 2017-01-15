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

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.RR;
import com.mvvm.lux.burqa.databinding.SectionComicItemBinding;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ImagePicsListActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.utils.SnackbarUtil;
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

    public ObservableField<String> chapters_title = new ObservableField<>();

    public ObservableField<String> last_updatetime = new ObservableField<>();

    public ObservableField<String> obj_id = new ObservableField<>();

    public ObservableField<String> show_more = new ObservableField<>("查看全部章节");

    public ObservableList<ComicResponse.ChaptersBean.DataBean> chaptersList = new ObservableArrayList<>();  //原始全部集合

    public ObservableList<ComicResponse.ChaptersBean.DataBean> chaptersListLess = new ObservableArrayList<>();  //显示部分集合

    public ObservableList<ComicResponse.ChaptersBean.DataBean> tempList = new ObservableArrayList<>();  //临时集合

    public ObservableList<ComicResponse.ChaptersBean.DataBean> chaptersOther = new ObservableArrayList<>();

    public ObservableField<String> chapters_other_title = new ObservableField<>();

    public ObservableBoolean showAll = new ObservableBoolean(true);

    private SectionComicItemBinding mDataBinding;

    public ComicItemViewModel(Activity activity, SectionComicItemBinding dataBinding) {
        super(activity);
        mDataBinding = dataBinding;
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
//        ComicPageActivity.launch(mActivity,obj_id.get(),chaptersList.get(position).getChapter_id(),position);
        ImagePicsListActivity.launch(mActivity,obj_id.get(),chaptersList.get(position).getChapter_id(),position);
        return true;
    };

    public TagFlowLayout.OnTagClickListener mOnOtherChaptersClickListener = (view, position, parent) -> {
        SnackbarUtil.showMessage("I am " + chaptersOther.get(position).getChapter_title() + "被电击了Ｏ(≧口≦)Ｏ");
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
