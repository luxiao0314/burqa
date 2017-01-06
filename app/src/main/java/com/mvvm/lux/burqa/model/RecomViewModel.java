package com.mvvm.lux.burqa.model;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.RecommendResponse;
import com.mvvm.lux.burqa.ui.home.fragment.RecomFragment;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.ProgressSubscriber;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;
import com.mvvm.lux.framework.utils.Logger;

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
                .subscribe(new ProgressSubscriber<List<RecommendResponse>>(ServiceTask.create(mFragment)) {
                    @Override
                    public void onNext(List<RecommendResponse> recommendResponse) {
                        Logger.d(recommendResponse.get(0).getTitle());
                    }
                });

    }

    public View.OnClickListener mOnClickListener = v -> initData();
}
