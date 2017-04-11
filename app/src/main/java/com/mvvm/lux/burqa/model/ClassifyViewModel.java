package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.db.RealmHelper;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseQuickAdapter;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseViewHolder;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.loadmore.SimpleLoadMoreView;
import com.mvvm.lux.widget.emptyview.EmptyView;

import java.util.List;


/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 10:15
 * @Version
 */
public class ClassifyViewModel extends BaseViewModel implements BaseQuickAdapter.RequestLoadMoreListener {

    public ObservableField<String> tag_id = new ObservableField<>();
    public ObservableBoolean refreshing = new ObservableBoolean(true);
    public ObservableBoolean showEmpty = new ObservableBoolean(false);
    private ObservableList<ClassifyResponse> mResponses = new ObservableArrayList<>();
    private int page = 0;

    public ClassifyViewModel(Activity activity) {
        super(activity);
    }

    public void initView() {
//        mAdapter.isFirstOnly(false);
        if (!TextUtils.isEmpty(tag_id.get())) { //历史记录没有加载更多
            mAdapter.setLoadMoreView(new SimpleLoadMoreView());
            mAdapter.setOnLoadMoreListener(this);
        }
    }

    public GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 3);

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = () -> initData(false);

    // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> {
        page = 0;
        initData(false);
    };

    public BaseQuickAdapter mAdapter = new BaseQuickAdapter<ClassifyResponse, BaseViewHolder>(R.layout.adapter_classify_layout, mResponses) {

        @Override
        protected void convert(BaseViewHolder holder, ClassifyResponse categoryResponse, int positions) {
//            mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);  //因为这个adapter会预先加载,所以进来的时候要在在这里设置动画

            ClassifyItemViewModel viewModel = new ClassifyItemViewModel(mActivity);
            viewModel.title.set(categoryResponse.getTitle());
            viewModel.cover.set(categoryResponse.getCover());
            viewModel.author.set(categoryResponse.getAuthors());
            viewModel.id.set(categoryResponse.getId() + "");
            holder.mDataBinding.setVariable(BR.viewModel, viewModel);
        }
    };

    @Override
    public void onLoadMoreRequested(int position) {
        page += 1;
        initData(true);
    }

    public void initData(boolean isLoadMore) {

        //如果都为空,就是查看本地历史
        if (TextUtils.isEmpty(tag_id.get())) {
            queryHistoryData(isLoadMore);
        } else {
            queryClassifyFromNet(isLoadMore);
        }
    }

    private void queryClassifyFromNet(final boolean isLoadMore) {
        //"classify/3262/0/1.json"
        RetrofitHelper.init()
                .getClassify("classify/" + tag_id.get() + "/0/" + page + ".json")
                .compose(RxHelper.handleErr())
                .subscribe(new RxSubscriber<List<ClassifyResponse>>() {

                    @Override
                    public void onNext(List<ClassifyResponse> responses) {
                        ClassifyViewModel.this.onNext(responses, isLoadMore);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refreshing.set(false);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    private void queryHistoryData(boolean isLoadMore) {
        List<ClassifyResponse> responses = RealmHelper.getInstance().queryHistoryList();
        onNext(responses, isLoadMore);
    }

    private void onNext(List<ClassifyResponse> responses, boolean isLoadMore) {
        refreshing.set(false);
        if (isLoadMore) {
            mAdapter.addData(responses);
            mAdapter.loadMoreComplete();
        } else {
            mAdapter.setNewData(responses);
        }
    }

}
