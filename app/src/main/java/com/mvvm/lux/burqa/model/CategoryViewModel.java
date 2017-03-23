package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.FragmentCategoryBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.CategoryResponse;
import com.mvvm.lux.burqa.model.response.CategoryResponse1;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseQuickAdapter;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseViewHolder;
import com.mvvm.lux.widget.emptyview.EmptyView;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:35
 * @Version
 */
public class CategoryViewModel extends BaseViewModel {

    public ObservableBoolean refreshing = new ObservableBoolean(false);
    public ObservableBoolean showEmpty = new ObservableBoolean(false);
    private FragmentCategoryBinding mDataBinding;

    public CategoryViewModel(Activity activity, FragmentCategoryBinding dataBinding) {
        super(activity);
        mDataBinding = dataBinding;
        refreshing.set(true);
        mDataBinding.recyclerView.getItemAnimator().setChangeDuration(0);
        ((SimpleItemAnimator)mDataBinding.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public GridLayoutManager layoutManager() {
        return new GridLayoutManager(mActivity, 3);
    }

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = this::initData;

    // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = this::initData;

    public void initData() {
        RetrofitHelper.init()
                .getCategory()
                .compose(RxHelper.handleVirtualData(CategoryResponse1.class))
                .subscribe(new RxSubscriber<CategoryResponse1>() {
                    @Override
                    public void onNext(CategoryResponse1 categoryResponse1) {
                        refreshing.set(false);
                        mDataBinding.recyclerView.setAdapter(new MyBaseQuickAdapter(R.layout.adapter_category_layout, categoryResponse1.getBody()));
                    }
                });
//                .subscribe(new RxSubscriber<List<CategoryResponse>>() {
//
//                    @Override
//                    public void onNext(List<CategoryResponse> categoryResponses) {
//                        refreshing.set(false);
//                        mDataBinding.recyclerView.setAdapter(new MyBaseQuickAdapter(R.layout.adapter_category_layout, categoryResponses));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        showEmpty.set(true);
//                        refreshing.set(false);
//                    }
//                });
    }

    public class MyBaseQuickAdapter extends BaseQuickAdapter<CategoryResponse, BaseViewHolder> {

        public MyBaseQuickAdapter(int layoutResId, List<CategoryResponse> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, CategoryResponse categoryResponse, int position) {
            CategoryItemViewModel viewModel = new CategoryItemViewModel(mActivity);
            viewModel.title.set(categoryResponse.getTitle());
            viewModel.cover.set(categoryResponse.getCover());
            viewModel.tag_id.set(getData().get(position).getTag_id() + "");
            holder.mDataBinding.setVariable(BR.viewModel, viewModel);
        }
    }

}
