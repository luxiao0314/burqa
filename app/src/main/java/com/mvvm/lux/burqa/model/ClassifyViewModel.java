package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseQuickAdapter;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.BaseViewHolder;
import com.mvvm.lux.framework.manager.recycler.baserecycleview.loadmore.SimpleLoadMoreView;
import com.mvvm.lux.framework.manager.recycler.itemDecoration.AlphaDividerItemDecoration;
import com.mvvm.lux.widget.emptyview.EmptyView;
import com.mvvm.lux.widget.utils.DisplayUtil;

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
        initView();
    }

    private void initView() {
        mAdapter.setLoadMoreView(new SimpleLoadMoreView());
        mAdapter.setOnLoadMoreListener(this);
    }

    public GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 3);

    public RecyclerView.ItemDecoration itemDecoration = new AlphaDividerItemDecoration(DisplayUtil.dp2px(5));

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = () -> initData(false);

    public BaseQuickAdapter mAdapter = new BaseQuickAdapter<ClassifyResponse, BaseViewHolder>(R.layout.adapter_classify_layout, mResponses) {

        @Override
        protected void convert(BaseViewHolder holder, ClassifyResponse categoryResponse, int positions) {
            mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);  //因为这个adapter会预先加载,所以进来的时候要在在这里设置动画

            ClassifyItemViewModel viewModel = new ClassifyItemViewModel(mActivity);
            viewModel.title.set(categoryResponse.getTitle());
            viewModel.cover.set(categoryResponse.getCover());
            viewModel.author.set(categoryResponse.getAuthors());
            viewModel.id.set(categoryResponse.getId() + "");
            holder.mDataBinding.setVariable(BR.viewModel, viewModel);
        }
    };

    // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = () -> {
        page = 0;
        initData(false);
    };

    @Override
    public void onLoadMoreRequested(int position) {
        page += 1;
        initData(true);
    }

    public void initData(boolean isLoadMore) {
        //"classify/3262/0/1.json"
        RetrofitHelper.init()
                .getClassify("classify/" + tag_id.get() + "/0/" + page + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new RxSubscriber<List<ClassifyResponse>>() {

                    @Override
                    public void onNext(List<ClassifyResponse> responses) {
                        refreshing.set(false);
                        if (isLoadMore) {
                            mAdapter.loadMoreComplete();
                            mAdapter.addData(responses);
                        } else {
                            mAdapter.setNewData(responses);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refreshing.set(false);
                        mAdapter.loadMoreFail();
                    }
                });

    }

}
