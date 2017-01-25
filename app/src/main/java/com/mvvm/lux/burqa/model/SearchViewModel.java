package com.mvvm.lux.burqa.model;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivitySearchBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.HotResponse;
import com.mvvm.lux.burqa.model.response.SearchResponse;
import com.mvvm.lux.burqa.ui.home.activity.SearchActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.manager.recycler.recyclerview.CommonAdapter;
import com.mvvm.lux.framework.manager.recycler.recyclerview.base.ViewHolder;
import com.mvvm.lux.framework.manager.recycler.recyclerview.wrapper.LoadMoreWrapper;
import com.mvvm.lux.widget.emptyview.EmptyView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 17:04
 * @Version
 */
public class SearchViewModel extends BaseViewModel implements LoadMoreWrapper.OnLoadMoreListener {

    private String keyword;

    private ObservableList<HotResponse> mHotResponses = new ObservableArrayList<>();

    public ObservableBoolean showSearch = new ObservableBoolean(false);

    public ObservableBoolean showEmpty = new ObservableBoolean(false);

    private ObservableList<SearchResponse> mSearchResponses = new ObservableArrayList<>();
    private int page;
    private SearchActivity mSearchActivity;
    private ActivitySearchBinding mDataBinding;

    public SearchViewModel(SearchActivity activity, ActivitySearchBinding dataBinding) {
        super(activity);
        mSearchActivity = activity;
        mDataBinding = dataBinding;
    }

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = this::initData;

//    public RecyclerView.ItemDecoration itemDecoration(){
//        return new DividerGridItemDecoration(mActivity);
//    }

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

    public void initData() {
        mHotResponses.clear();
        //search/hot/0.json
        RetrofitHelper.init()
                .getHot("search/hot/0.json")
                .compose(RxHelper.io_main())
                .subscribe(new RxSubscriber<List<HotResponse>>() {

                    @Override
                    public void onNext(List<HotResponse> hotResponses) {
                        int count = hotResponses.size() < 20 ? hotResponses.size() : 20;
                        for (int i = 0; i < count; i++) {
                            mHotResponses.add(hotResponses.get(i));
                        }
                        chaptersAdapter.notifyDataChanged();
                    }
                });
    }

    public void search() {
        mSearchResponses.clear();
        //search/show/0/%E4%B8%80%E6%8B%B3%E8%B6%85%E4%BA%BA/0.json :搜索结果
        RetrofitHelper.init()
                .getSearch(keyword)
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<List<SearchResponse>>(ServiceTask.create(mSearchActivity)) {

                    @Override
                    public void onNext(List<SearchResponse> searchResponses) {
                        if (searchResponses.size() == 0) {
                            showEmpty.set(true);
                        } else {
                            if (searchResponses.size() > 10) {
                                mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading)
                                        .setOnLoadMoreListener(SearchViewModel.this);
                            }
                            showEmpty.set(false);
                            mSearchResponses.addAll(searchResponses);
                            mLoadMoreWrapper.notifyDataSetChanged();
                        }
                    }
                });
    }

    public LoadMoreWrapper mLoadMoreWrapper = new LoadMoreWrapper<>(new CommonAdapter<SearchResponse>(mActivity, R.layout.adapter_classify_layout, mSearchResponses) {
        @Override
        protected void convert(ViewHolder holder, SearchResponse categoryResponse, int position) {
            ClassifyItemViewModel viewModel = new ClassifyItemViewModel(mActivity);
            viewModel.title.set(categoryResponse.getTitle());
            viewModel.cover.set(categoryResponse.getCover());
            viewModel.author.set(categoryResponse.getAuthors());
            viewModel.id.set(categoryResponse.getId() + "");
            holder.mDataBinding.setVariable(BR.viewModel, viewModel);
        }
    });

    @Override
    public void onLoadMoreRequested(int position) {
        page++;
        initData();
    }

    /**
     * 连载
     */
    public TagAdapter chaptersAdapter = new TagAdapter<HotResponse>(mHotResponses) {

        @Override
        public View getView(FlowLayout parent, int position, HotResponse dataBean) {
            ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.layout_tag_item, parent, false);
            TagAdapterViewModel viewModel = new TagAdapterViewModel(mActivity);
            viewModel.keyword.set(dataBean.getName());
            dataBinding.setVariable(BR.viewModel, viewModel);
            return dataBinding.getRoot();
        }
    };

    /**
     * 流式布局item点击事件
     */
    public TagFlowLayout.OnTagClickListener mOnChaptersClickListener = (view, position, parent) -> {
        keyword = mHotResponses.get(position).getName();
        mDataBinding.etSearch.setText(keyword);
        showSearch.set(true);
        search();
        return true;
    };

    public View.OnClickListener onSearch() {
        return view -> {
            showSearch.set(true);
            keyword = mDataBinding.etSearch.getText().toString();
            if (!TextUtils.isEmpty(keyword))
                search();
        };
    }
}
