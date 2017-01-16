package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.mvvm.lux.burqa.BR;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivitySearchBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.HotResponse;
import com.mvvm.lux.burqa.model.response.SearchResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.manager.recycler.itemDecoration.AlphaDividerItemDecoration;
import com.mvvm.lux.framework.manager.recycler.recyclerview.CommonAdapter;
import com.mvvm.lux.framework.manager.recycler.recyclerview.base.ViewHolder;
import com.mvvm.lux.framework.manager.recycler.recyclerview.wrapper.LoadMoreWrapper;
import com.mvvm.lux.widget.utils.DisplayUtil;
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

    private ObservableList<SearchResponse> mSearchResponses = new ObservableArrayList<>();
    private int page;

    public SearchViewModel(Activity activity, ActivitySearchBinding dataBinding) {
        super(activity);
    }

    public RecyclerView.ItemDecoration itemDecoration = new AlphaDividerItemDecoration(DisplayUtil.dp2px(mActivity, 5));

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
//                        mHotResponses.addAll(hotResponses);
                        chaptersAdapter.notifyDataChanged();
                    }
                });
    }

    public void search() {
        //search/show/0/%E4%B8%80%E6%8B%B3%E8%B6%85%E4%BA%BA/0.json :搜索结果
        RetrofitHelper.init()
                .getSearch("search/show/0/ " + keyword + "/" + page + ".json")
                .compose(RxHelper.io_main())
                .subscribe(new RxSubscriber<List<SearchResponse>>() {

                    @Override
                    public void onNext(List<SearchResponse> searchResponses) {
                        mSearchResponses.addAll(searchResponses);
                        mLoadMoreWrapper.notifyDataSetChanged();
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
    }).setLoadMoreView(R.layout.default_loading).setOnLoadMoreListener(this);

    @Override
    public void onLoadMoreRequested() {
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
//        ImagePicsListActivity.launch(mActivity, mHotResponses.get(0).getId(), mHotResponses.get(0).getName(), position);
        return true;
    };

    public TextWatcher onTextChanged(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public View.OnClickListener onSearch(){
        return view -> {

        };
    }
}
