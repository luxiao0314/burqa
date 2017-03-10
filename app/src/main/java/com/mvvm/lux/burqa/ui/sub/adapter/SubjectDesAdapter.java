package com.mvvm.lux.burqa.ui.sub.adapter;

import android.app.Activity;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.SubjectDesListViewModel;
import com.mvvm.lux.burqa.model.response.SubjectDesResponse;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseMultiItemQuickAdapter;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseViewHolder;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/10 16:47
 * @Version
 */
public class SubjectDesAdapter extends BaseMultiItemQuickAdapter<SubjectDesResponse.ComicsBean, BaseViewHolder> {

    private Activity mActivity;
    private SubjectDesResponse mSubjectDesResponse;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param activity
     * @param data               A new list is created out of this one to avoid mutable list
     * @param subjectDesResponse
     */
    public SubjectDesAdapter(Activity activity, List<SubjectDesResponse.ComicsBean> data, SubjectDesResponse subjectDesResponse) {
        super(data);
        mActivity = activity;
        mSubjectDesResponse = subjectDesResponse;
        addItemType(SubjectDesResponse.ComicsBean.DES_TYPE, R.layout.item_subject_des_view);
        addItemType(SubjectDesResponse.ComicsBean.LIST_TYPE, R.layout.item_subject_des_list_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectDesResponse.ComicsBean item, int positions) {
        SubjectDesListViewModel viewModel = new SubjectDesListViewModel(mActivity);
        switch (helper.getItemViewType()) {
            case SubjectDesResponse.ComicsBean.DES_TYPE:
                viewModel.title_img.set(mSubjectDesResponse.getMobile_header_pic());
                viewModel.title.set(mSubjectDesResponse.getTitle());
                viewModel.title_des.set(mSubjectDesResponse.getDescription());
                helper.mDataBinding.setVariable(BR.viewModel, viewModel);
                helper.mDataBinding.executePendingBindings();
                break;
            case SubjectDesResponse.ComicsBean.LIST_TYPE:
                viewModel.img.set(item.getCover());
                viewModel.sub_title.set(item.getName());
                viewModel.des_little.set(item.getRecommend_brief());
                viewModel.des.set(item.getRecommend_reason());
                helper.mDataBinding.setVariable(BR.viewModel, viewModel);
                helper.mDataBinding.executePendingBindings();
                break;
        }
    }

}
