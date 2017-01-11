package com.mvvm.lux.burqa.ui.home.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.RecomDoubleItemViewModel;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.framework.manager.recycler.AbsRecyclerViewAdapter;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/11 13:10
 * @Version
 */
public class RecomItemListAdapter extends AbsRecyclerViewAdapter {

    private Activity mActivity;
    private List<RecommendResponse.DataBean> mDataList;

    public RecomItemListAdapter(Activity activity, RecyclerView recyclerView, List<RecommendResponse.DataBean> dataList) {
        super(recyclerView);
        mActivity = activity;
        mDataList = dataList;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.section_recom_double, parent, false);
        return new ClickableViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        RecommendResponse.DataBean dataBean = mDataList.get(position);
        RecomDoubleItemViewModel viewModel = new RecomDoubleItemViewModel(mActivity);
        viewModel.img.set(dataBean.getCover());
        viewModel.title.set(dataBean.getTitle());
        holder.getParentView().setVariable(BR.viewModel,viewModel);
        holder.getParentView().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
