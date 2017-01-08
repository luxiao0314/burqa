package com.mvvm.lux.burqa.ui.home.adapter.section;

import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.RecomDoubleViewModel;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.framework.manager.recycler.sectioned.StatelessSection;

/**
 * @Description 使用grid实现多条目, 每一个title和单独的item都是grid中的一个条目, 轮播图也是一个
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/7 12:55
 * @Version 1.0.0
 */
public class RecomItemSection extends StatelessSection {

    private RecommendResponse mRecommendResponse;
    private FragmentActivity mActivity;

    public RecomItemSection(RecommendResponse recommendResponse, int resLayout, FragmentActivity activity) {
        super(R.layout.recom_title_section, resLayout);
        mRecommendResponse = recommendResponse;
        mActivity = activity;
    }

    @Override
    public int getContentItemsTotal() {
        return mRecommendResponse.getData().size() % 3 == 0 ? mRecommendResponse.getData().size() : 3;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(ViewDataBinding view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        RecomDoubleViewModel viewModel = new RecomDoubleViewModel(mActivity);
        if (mRecommendResponse.getData().size() == 0)
            viewModel.hide_title.set(true);
        viewModel.head_title.set(mRecommendResponse.getTitle());
        ((HeaderViewHolder) holder).mDataBinding.setVariable(BR.viewModel, viewModel);
        ((HeaderViewHolder) holder).mDataBinding.executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(ViewDataBinding view) {
        return new RecomItemHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecommendResponse.DataBean dataBean = mRecommendResponse.getData().get(position);
        RecomDoubleViewModel viewModel = new RecomDoubleViewModel(mActivity);
        viewModel.img.set(dataBean.getCover());
        viewModel.title.set(dataBean.getTitle());
        viewModel.sub_title.set(dataBean.getSub_title());
        viewModel.obj_id.set(dataBean.getObj_id());
        ((RecomItemHolder) holder).mDataBinding.setVariable(BR.viewModel, viewModel);
        ((RecomItemHolder) holder).mDataBinding.executePendingBindings();
    }

    public class RecomItemHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding mDataBinding;

        public RecomItemHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mDataBinding;

        public HeaderViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }
}
