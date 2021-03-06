package com.mvvm.lux.burqa.model;

import android.databinding.ObservableBoolean;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.base.SectionedRecyclerViewAdapter;
import com.mvvm.lux.burqa.databinding.FragmentRecomBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.burqa.ui.home.adapter.section.RecomBannerSection;
import com.mvvm.lux.burqa.ui.home.adapter.section.RecomItemListSection;
import com.mvvm.lux.burqa.ui.home.adapter.section.RecomItemSection;
import com.mvvm.lux.burqa.ui.home.fragment.RecomFragment;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.widget.emptyview.EmptyView;

import java.util.List;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:26
 * @Version
 */
public class RecomViewModel extends BaseViewModel {
    private final RecomFragment mFragment;
    private final FragmentRecomBinding mDataBinding;

    public ObservableBoolean refreshing = new ObservableBoolean(false);

    public RecomViewModel(RecomFragment fragment, FragmentRecomBinding dataBinding) {
        super(fragment.getActivity());
        mFragment = fragment;
        mDataBinding = dataBinding;
        refreshing.set(true);
    }

    public ObservableBoolean showEmpty = new ObservableBoolean(false);

    public SectionedRecyclerViewAdapter mAdapter;

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = this::initData;

    // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = this::initData;

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(mFragment.getActivity(), 6);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_FOOTER:
                        return 6;
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_ITEM_LOADED:
                        return 2;
                    default:
                        return 6;
                }
            }
        });
        return layoutManager;
    }

    public void initData() {
        RetrofitHelper.init()
                .getRecommend()
                .compose(RxHelper.handleErr())
                .subscribe(new RxSubscriber<List<RecommendResponse>>() {

                    @Override
                    public void onNext(List<RecommendResponse> recommendResponse) {
                        mAdapter = new SectionedRecyclerViewAdapter();
                        mAdapter.addSection(new RecomBannerSection(recommendResponse.get(0), mFragment.getActivity())); //大图推荐
                        mAdapter.addSection(new RecomItemSection(recommendResponse.get(1), mFragment.getActivity()));   //近期必看  三个条目
                        mAdapter.addSection(new RecomItemListSection(recommendResponse.get(2), mFragment.getActivity()));   //火热专题  两个条目
                        mAdapter.addSection(new RecomItemSection(recommendResponse.get(3), mFragment.getActivity()));   //大师
                        mAdapter.addSection(new RecomItemSection(recommendResponse.get(4), mFragment.getActivity()));   //国漫
                        mAdapter.addSection(new RecomItemListSection(recommendResponse.get(5), mFragment.getActivity()));   //美漫
                        mAdapter.addSection(new RecomItemSection(recommendResponse.get(6), mFragment.getActivity()));   //热门
                        mAdapter.addSection(new RecomItemListSection(recommendResponse.get(7), mFragment.getActivity()));   //条漫
                        mAdapter.addSection(new RecomItemSection(recommendResponse.get(8), mFragment.getActivity()));   //最新
//                        mAdapter.addSection(new RecomItemSection(recommendResponse.get(9), mFragment.getActivity()));
                        mDataBinding.recyclerView.setAdapter(mAdapter); //加载完成之后要设置adapter,一定要记住
                        refreshing.set(false);
                        showEmpty.set(false);
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
