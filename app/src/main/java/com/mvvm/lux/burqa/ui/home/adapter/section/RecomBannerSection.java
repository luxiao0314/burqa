package com.mvvm.lux.burqa.ui.home.adapter.section;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.RecomBannerViewModel;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.framework.manager.recycler.sectioned.StatelessSection;

/**
 * @Description 轮播图
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/7 12:55
 * @Version 1.0.0
 */
public class RecomBannerSection extends StatelessSection {

    private RecomBannerViewModel mViewModel;
    private RecommendResponse mRecommendResponse;

    public RecomBannerSection(RecommendResponse recommendResponse) {
        super(R.layout.recom_banner_section,R.layout.empty);
        mViewModel = new RecomBannerViewModel(recommendResponse);
        mRecommendResponse = recommendResponse;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(ViewDataBinding view) {
        return new RecomBannerHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        ((RecomBannerHolder) holder).mDataBinding.setVariable(BR.viewModel, mViewModel);
        ((RecomBannerHolder) holder).mDataBinding.executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(ViewDataBinding view) {
        return new EmptyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public class RecomBannerHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding mDataBinding;

        public RecomBannerHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(ViewDataBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
