package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.FragmentCategoryBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.CategoryResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.manager.recycler.itemDecoration.AlphaDividerItemDecoration;
import com.mvvm.lux.framework.manager.recycler.recyclerview.CommonAdapter;
import com.mvvm.lux.framework.manager.recycler.recyclerview.base.ViewHolder;
import com.mvvm.lux.widget.emptyview.EmptyView;
import com.mvvm.lux.widget.utils.DisplayUtil;

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
    private ObservableList<CategoryResponse> mCategoryResponses = new ObservableArrayList<>();

    public CategoryViewModel(Activity activity, FragmentCategoryBinding dataBinding) {
        super(activity);
        mDataBinding = dataBinding;
        refreshing.set(true);
    }

    public RecyclerView.ItemDecoration itemDecoration = new AlphaDividerItemDecoration(DisplayUtil.dp2px(5));

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 6);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
        return layoutManager;
    }

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = this::initData;

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener() {
        return this::initData;  // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    }

    public void initData() {
        RetrofitHelper.init()
                .getCategory()
                .compose(RxHelper.io_main())
                .subscribe(new RxSubscriber<List<CategoryResponse>>() {

                    @Override
                    public void onNext(List<CategoryResponse> categoryResponses) {
                        mCategoryResponses.clear();
                        mCategoryResponses.addAll(categoryResponses);   //必须写成ObservableList才能刷新
                        refreshing.set(false);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    public CommonAdapter<CategoryResponse> mAdapter = new CommonAdapter<CategoryResponse>(mActivity, R.layout.adapter_category_layout, mCategoryResponses) {
        @Override
        protected void convert(ViewHolder holder, CategoryResponse categoryResponse, int position) {
            CategoryItemViewModel viewModel = new CategoryItemViewModel(mActivity);
            viewModel.title.set(categoryResponse.getTitle());
            viewModel.cover.set(categoryResponse.getCover());
            viewModel.tag_id.set(mCategoryResponses.get(position).getTag_id()+"");
            holder.mDataBinding.setVariable(BR.viewModel, viewModel);
        }
    };

}
