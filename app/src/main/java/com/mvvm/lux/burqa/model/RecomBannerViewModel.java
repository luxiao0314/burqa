package com.mvvm.lux.burqa.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.widget.banner.BannerEntity;

import rx.Observable;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/7 13:00
 * @Version
 */
public class RecomBannerViewModel extends BaseViewModel {

    public ObservableList<BannerEntity> bannerList = new ObservableArrayList<>();

    public RecomBannerViewModel(RecommendResponse recommendResponse) {
        initData(recommendResponse);
    }

    private void initData(RecommendResponse recommendResponse) {
        Observable.from(recommendResponse.getData())
                .subscribe(dataBean -> {
                    BannerEntity bannerEntity = new BannerEntity();
                    bannerEntity.img = dataBean.getCover();
                    bannerEntity.title = dataBean.getTitle();
                    bannerEntity.link = dataBean.getSub_title();
                    bannerList.add(bannerEntity);
                });

    }
}
