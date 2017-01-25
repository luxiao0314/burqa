package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.CategoryResponse;
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
    private ObservableList<CategoryResponse> mCategoryResponses = new ObservableArrayList<>();

    public CategoryViewModel(Activity activity) {
        super(activity);
        refreshing.set(true);
        mAdapter.isFirstOnly(false);
    }

    public GridLayoutManager layoutManager(){
        return new GridLayoutManager(mActivity, 3);
    };

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = this::initData;

    public BaseQuickAdapter mAdapter = new BaseQuickAdapter<CategoryResponse, BaseViewHolder>(R.layout.adapter_category_layout, mCategoryResponses) {

        @Override
        protected void convert(BaseViewHolder holder, CategoryResponse categoryResponse, int position) {
            mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
            CategoryItemViewModel viewModel = new CategoryItemViewModel(mActivity);
            viewModel.title.set(categoryResponse.getTitle());
            viewModel.cover.set(categoryResponse.getCover());
            viewModel.tag_id.set(mCategoryResponses.get(position).getTag_id() + "");
            holder.mDataBinding.setVariable(BR.viewModel, viewModel);
        }
    };

    // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = this::initData;

    public void initData() {
        RetrofitHelper.init()
                .getCategory()
                .compose(RxHelper.io_main())
                .subscribe(new RxSubscriber<List<CategoryResponse>>() {

                    @Override
                    public void onNext(List<CategoryResponse> categoryResponses) {
                        refreshing.set(false);
                        mCategoryResponses.addAll(categoryResponses);
                        mAdapter.setNewData(categoryResponses);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showEmpty.set(true);
                        refreshing.set(false);
                    }
                });
    }

}
