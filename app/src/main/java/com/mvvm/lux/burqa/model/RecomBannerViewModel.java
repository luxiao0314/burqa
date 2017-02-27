package com.mvvm.lux.burqa.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.burqa.ui.home.activity.ComicDesActivity;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.manager.hybrid.BrowserActivity;
import com.mvvm.lux.widget.banner.BannerAdapter;
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
    private RecommendResponse mRecommendResponse;

    public RecomBannerViewModel(RecommendResponse recommendResponse, FragmentActivity activity) {
        super(activity);
        mRecommendResponse = recommendResponse;
        initData(recommendResponse);
    }

    private void initData(RecommendResponse recommendResponse) {
        Observable.from(recommendResponse.getData())
                .subscribe((RecommendResponse.DataBean dataBean) -> {
                    BannerEntity bannerEntity = new BannerEntity();
                    bannerEntity.img = dataBean.getCover();
                    bannerEntity.title = dataBean.getTitle();
                    bannerEntity.link = dataBean.getSub_title();
                    bannerList.add(bannerEntity);
                });

    }

    /**
     * 设置ViewPager的Item点击回调事件
     */
    public BannerAdapter.ViewPagerOnItemClickListener onItemListener() {
        return position -> {
            if (position == 0) {
                position = bannerList.size() - 1;
            } else {
                position -= 1;
            }
            RecommendResponse.DataBean dataBean = mRecommendResponse.getData().get(position);
            if (!TextUtils.isEmpty(dataBean.getUrl())) {
                BrowserActivity.launch(mActivity, dataBean.getUrl(), "新闻正文");
            } else {
                ComicDesActivity.launch(mActivity, dataBean.getObj_id() + "");
            }
        };
    }
}
