package com.mvvm.lux.burqa.ui.home.adapter.section;

import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.base.StatelessSection;
import com.mvvm.lux.burqa.databinding.SectionRecomListBinding;
import com.mvvm.lux.burqa.model.RecomDoubleViewModel;
import com.mvvm.lux.burqa.model.RecomItemListViewModel;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.burqa.ui.home.adapter.RecomItemListAdapter;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/11 12:54
 * @Version
 */
public class RecomItemListSection extends StatelessSection {

    private final RecommendResponse mRecommendResponse;
    private final FragmentActivity mActivity;

    public RecomItemListSection(RecommendResponse recommendResponse, FragmentActivity activity) {
        super(R.layout.section_recom_title, R.layout.section_recom_list, R.layout.empty);
        mRecommendResponse = recommendResponse;
        mActivity = activity;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
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
    public RecyclerView.ViewHolder getFooterViewHolder(ViewDataBinding view) {
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        SectionRecomListBinding dataBinding = ((FooterViewHolder) holder).mDataBinding;
        List<RecommendResponse.DataBean> mRecommendResponseData = mRecommendResponse.getData();
        RecomItemListViewModel viewModel = new RecomItemListViewModel(mActivity);
        dataBinding.recyclerView.setAdapter(new RecomItemListAdapter(mActivity, mRecommendResponseData));
        dataBinding.setVariable(BR.viewModel,viewModel);
        dataBinding.executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(ViewDataBinding view) {
        return new RecomBannerSection.EmptyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mDataBinding;

        public HeaderViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = dataBinding;
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        private SectionRecomListBinding mDataBinding;

        public FooterViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            mDataBinding = (SectionRecomListBinding) dataBinding;
        }
    }
}
