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
import com.mvvm.lux.burqa.databinding.ActivityComicClassifyBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.ClassifyResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.manager.recycler.itemDecoration.AlphaDividerItemDecoration;
import com.mvvm.lux.framework.manager.recycler.recyclerview.CommonAdapter;
import com.mvvm.lux.framework.manager.recycler.recyclerview.base.ViewHolder;
import com.mvvm.lux.framework.manager.recycler.recyclerview.wrapper.LoadMoreWrapper;
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
public class ClassifyViewModel extends BaseViewModel implements LoadMoreWrapper.OnLoadMoreListener {

    public ObservableField<String> tag_id = new ObservableField<>();
    public ObservableBoolean refreshing = new ObservableBoolean(false);
    public ObservableBoolean showEmpty = new ObservableBoolean(false);
    private ObservableList<ClassifyResponse> mResponses = new ObservableArrayList<>();
    private int page = 0;
    private ActivityComicClassifyBinding mDataBinding;

    public ClassifyViewModel(Activity activity, ActivityComicClassifyBinding dataBinding) {
        super(activity);
        mDataBinding = dataBinding;
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
        mResponses.clear();
        return this::initData;  // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    }

    public void initData() {
        //"classify/3262/0/1.json"
        RetrofitHelper.init()
                .getClassify("classify/" + tag_id.get() + "/0/" + page + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new RxSubscriber<List<ClassifyResponse>>() {

                    @Override
                    public void onNext(List<ClassifyResponse> responses) {
                        refreshing.set(false);
                        mResponses.addAll(responses);
                        mLoadMoreWrapper.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refreshing.set(false);
                    }
                });

    }

    public LoadMoreWrapper mLoadMoreWrapper = new LoadMoreWrapper<>(new CommonAdapter<ClassifyResponse>(mActivity, R.layout.adapter_classify_layout, mResponses) {
        @Override
        protected void convert(ViewHolder holder, ClassifyResponse categoryResponse, int position) {
            ClassifyItemViewModel viewModel = new ClassifyItemViewModel(mActivity);
            viewModel.title.set(categoryResponse.getTitle());
            viewModel.cover.set(categoryResponse.getCover());
            viewModel.author.set(categoryResponse.getAuthors());
            viewModel.id.set(categoryResponse.getId() + "");
            holder.mDataBinding.setVariable(BR.viewModel, viewModel);
        }
    }).setLoadMoreView(R.layout.default_loading).setOnLoadMoreListener(this);

    @Override
    public void onLoadMoreRequested() {
        page++;
        initData();
    }
}
