package com.mvvm.lux.burqa.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;

import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.widget.bgabanner.BGABanner;
import com.mvvm.lux.widget.sonic.BrowsersActivity;

import rx.Observable;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/7 13:00
 * @Version
 */
public class RecomBannerViewModel extends BaseViewModel {

    public ObservableList<String> bannerList = new ObservableArrayList<>();
    private RecommendResponse mRecommendResponse;

    public RecomBannerViewModel(RecommendResponse recommendResponse, FragmentActivity activity) {
        super(activity);
        mRecommendResponse = recommendResponse;
        initData(recommendResponse);
    }

    private void initData(RecommendResponse recommendResponse) {
        Observable.from(recommendResponse.getData())
                .subscribe((RecommendResponse.DataBean dataBean) -> {
                    bannerList.add(dataBean.getCover());
                });

    }

    /**
     * 设置ViewPager的Item点击回调事件
     */
    public BGABanner.Delegate<CardView, String> onItemListener() {
        return (banner, itemView, model, position) -> {
            RecommendResponse.DataBean dataBean = mRecommendResponse.getData().get(position);
            if (!TextUtils.isEmpty(dataBean.getUrl())) {
//                BrowserActivity.launch(mActivity, dataBean.getUrl(), "新闻正文","imageClick");
                BrowsersActivity.launch(mActivity, dataBean.getUrl());
            } else {
                ComicDesActivity.launch(mActivity, dataBean.getObj_id() + "");
            }
        };
    }
}
