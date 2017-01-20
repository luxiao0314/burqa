package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.framework.base.BaseViewModel;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/11 12:59
 * @Version
 */
public class RecomItemListViewModel extends BaseViewModel {

    private Activity mActivity;

    public RecomItemListViewModel(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 6);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 3;
            }
        });
        return layoutManager;
    }
}
