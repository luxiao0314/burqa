package com.mvvm.lux.burqa.ui.sub.adapter;

import android.app.Activity;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.SubjectDesListViewModel;
import com.mvvm.lux.burqa.model.response.SubjectDesResponse;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseQuickAdapter;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseViewHolder;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/10 16:47
 * @Version
 */
public class SubjectDesAdapter extends BaseQuickAdapter<SubjectDesResponse.ComicsBean, BaseViewHolder> {

    private Activity mActivity;

    public SubjectDesAdapter(Activity activity, List<SubjectDesResponse.ComicsBean> data) {
        super(R.layout.item_subject_des_list_view, data);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectDesResponse.ComicsBean item, int positions) {
        SubjectDesListViewModel viewModel = new SubjectDesListViewModel(mActivity);
        viewModel.img.set(item.getCover());
        viewModel.obj_id.set(item.getId());
        viewModel.sub_title.set(item.getName());
        viewModel.des_little.set(item.getRecommend_brief());
        viewModel.des.set(item.getRecommend_reason());
        helper.mDataBinding.setVariable(BR.viewModel, viewModel);
        helper.mDataBinding.executePendingBindings();
    }
}
