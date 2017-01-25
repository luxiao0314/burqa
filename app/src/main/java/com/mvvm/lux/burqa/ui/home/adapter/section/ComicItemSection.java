package com.mvvm.lux.burqa.ui.home.adapter.section;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.base.StatelessSection;
import com.mvvm.lux.burqa.databinding.SectionComicItemBinding;
import com.mvvm.lux.burqa.model.ComicItemViewModel;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.burqa.model.response.ComicResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.framework.utils.DateUtil;


/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/9 16:56
 * @Version
 */
public class ComicItemSection extends StatelessSection {
    private final ComicDesActivity mActivity;
    private final ComicResponse mComicResponse;
    private ClassifyResponse mClassifyResponse;

    public ComicItemSection(ComicDesActivity activity, ComicResponse comicResponse, ClassifyResponse classifyResponse) {
        super(R.layout.section_comic_item);
        mActivity = activity;
        mComicResponse = comicResponse;
        mClassifyResponse = classifyResponse;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(ViewDataBinding view) {
        return new ItemViewHoder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicItemViewModel viewModel = new ComicItemViewModel(mActivity, (SectionComicItemBinding) ((ItemViewHoder) holder).mDataBinding,mComicResponse);
        viewModel.obj_id.set(mComicResponse.getId());
        viewModel.title.set(mComicResponse.getTitle());
        viewModel.last_updatetime.set("更新: " + DateUtil.getStringTime(mComicResponse.getLast_updatetime()));

        //初始化章节item
        viewModel.initChapters();
        viewModel.getLocalData(mClassifyResponse);
        viewModel.initEvent();
        ((ItemViewHoder) holder).mDataBinding.setVariable(BR.viewModel, viewModel);
        ((ItemViewHoder) holder).mDataBinding.executePendingBindings();
    }

    private class ItemViewHoder extends RecyclerView.ViewHolder {
        private ViewDataBinding mDataBinding;

        public ItemViewHoder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }
}
