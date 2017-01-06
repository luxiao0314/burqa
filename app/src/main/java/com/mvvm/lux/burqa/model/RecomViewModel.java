package com.mvvm.lux.burqa.model;

import android.databinding.ViewDataBinding;

import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.burqa.ui.home.fragment.RecomFragment;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.utils.OkLogger;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:26
 * @Version
 */
public class RecomViewModel extends BaseViewModel {
    private final RecomFragment mFragment;
    private final ViewDataBinding mDataBinding;

    public RecomViewModel(RecomFragment fragment, ViewDataBinding dataBinding) {
        mFragment = fragment;
        mDataBinding = dataBinding;
        initData();
    }

    private void initData() {
        RetrofitHelper.init()
                .getRecommend()
                .compose(RxHelper.io_main())
                .subscribe(new ProgressSubscriber<RecommendResponse>(ServiceTask.create(mFragment)) {
                    @Override
                    public void onNext(RecommendResponse recommendResponse) {
                        OkLogger.d(recommendResponse.getTitle());
                    }
                });

    }
}
