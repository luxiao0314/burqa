package com.mvvm.lux.framework.base;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mvvm.lux.framework.manager.router.Router;

import java.io.Serializable;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/11/16 17:09
 * @Version $VALUE
 */
public class BaseViewModel extends BaseObservable implements Serializable {

    private CompositeSubscription mCompositeSubscription;

    public ObservableField<String> title = new ObservableField<>();

    public ObservableBoolean downloadImg = new ObservableBoolean(false);

    public Activity mActivity;

    public BaseViewModel(Activity activity) {
        mActivity = activity;
    }

    public View.OnClickListener back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Router.pop(mActivity);
        }
    };

    public RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        //ImageLoader.pauseLoader();
                        if (!Fresco.getImagePipeline().isPaused()) {
                            Fresco.getImagePipeline().pause();
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //ImageLoader.resumeLoader();
                        if (Fresco.getImagePipeline().isPaused()) {
                            Fresco.getImagePipeline().resume();
                        }
                        break;
                }
            }
        };
    }

    private void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    protected void detachView() {
        unSubscribe();
    }
}
