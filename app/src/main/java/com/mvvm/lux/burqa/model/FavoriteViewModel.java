package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.model.db.RealmHelper;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseQuickAdapter;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseViewHolder;
import com.mvvm.lux.widget.emptyview.EmptyView;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:23
 * @Version
 */
public class FavoriteViewModel extends BaseViewModel {

    public ObservableBoolean refreshing = new ObservableBoolean(false);
    public ObservableBoolean showEmpty = new ObservableBoolean(false);
    private ObservableList<ClassifyResponse> mCategoryResponses = new ObservableArrayList<>();

    public FavoriteViewModel(Activity activity) {
        super(activity);
        refreshing.set(true);
        mAdapter.isFirstOnly(false);
    }

//    public RecyclerView.ItemDecoration itemDecoration(){
//        return new DividerGridItemDecoration(mActivity);
//    }

    public GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 3);

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = this::initData;

    public BaseQuickAdapter mAdapter = new BaseQuickAdapter<ClassifyResponse, BaseViewHolder>(R.layout.adapter_classify_layout, mCategoryResponses) {

        @Override
        protected void convert(BaseViewHolder holder, ClassifyResponse categoryResponse, int positions) {
            mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);  //因为这个adapter会预先加载,所以进来的时候要在在这里设置动画

            ClassifyItemViewModel viewModel = new ClassifyItemViewModel(mActivity);
            viewModel.title.set(categoryResponse.getTitle());
            viewModel.cover.set(categoryResponse.getCover());
            viewModel.author.set(categoryResponse.getAuthors());
            viewModel.id.set(categoryResponse.getId() + "");
            holder.mDataBinding.setVariable(BR.viewModel, viewModel);
        }
    };

    // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = this::initData;

    public void initData() {
        List<ClassifyResponse> classifyResponses = RealmHelper.getInstance().queryCollectionList();
        refreshing.set(false);
        mCategoryResponses.addAll(classifyResponses);
        mAdapter.setNewData(classifyResponses);
    }
}
