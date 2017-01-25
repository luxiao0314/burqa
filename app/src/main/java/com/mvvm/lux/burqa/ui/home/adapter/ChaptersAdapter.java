package com.mvvm.lux.burqa.ui.home.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.LayoutTagsItemBinding;
import com.mvvm.lux.burqa.model.TagChapterAdapterViewModel;
import com.mvvm.lux.burqa.model.response.ComicResponse;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/15 15:37
 * @Version
 */
public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.TagViewHolder> {

    private Activity mActivity;
    private ObservableList<ComicResponse.ChaptersBean.DataBean> mData;
    private int mObjId;
    private String mCover;
    private int position;

    public ChaptersAdapter(Activity activity,
                           ObservableList<ComicResponse.ChaptersBean.DataBean> data,
                           Integer objId,
                           String cover) {
        mActivity = activity;
        mData = data;
        mObjId = objId;
        mCover = cover;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutTagsItemBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.layout_tags_item, parent, false);
        return new TagViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {
        TagChapterAdapterViewModel viewModel = new TagChapterAdapterViewModel(mActivity, this);
        ComicResponse.ChaptersBean.DataBean dataBean = mData.get(position);
        viewModel.keyword.set(dataBean.getChapter_title());
        viewModel.chapter_title.set(dataBean.getChapter_title());
        viewModel.position.set(position);
        viewModel.obj_id.set(mObjId);
        viewModel.cover.set(mCover);
        viewModel.chapter_id.set(dataBean.getChapter_id());
        holder.mDataBinding.setVariable(BR.viewModel, viewModel);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setSelectPosition(int position) {
        this.position = position;
    }

    public class TagViewHolder extends RecyclerView.ViewHolder {

        public LayoutTagsItemBinding mDataBinding;

        public TagViewHolder(LayoutTagsItemBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }
}
