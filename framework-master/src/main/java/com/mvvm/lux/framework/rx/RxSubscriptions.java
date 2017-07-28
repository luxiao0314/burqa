package com.mvvm.lux.framework.rx;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 管理 CompositeSubscription
 * <p>
 * Created by YoKeyword on 16/7/19.
 */
public class RxSubscriptions {
    private static RxBus mRxBus = RxBus.init();
    private static CompositeSubscription mSubscriptions = new CompositeSubscription();
    private static Map<Class<?>, Observable<?>> mObservables = new HashMap<>();// 管理观察源

    public static boolean isUnsubscribed() {
        return mSubscriptions.isUnsubscribed();
    }

    public static void add(Subscription s) {
        if (s != null) {
            mSubscriptions.add(s);
        }
    }

    public static void remove(Subscription s) {
        if (s != null) {
            mSubscriptions.remove(s);
        }
    }

    public void on(Class<?> eventType, Action1<Object> action1) {
        Observable<?> mObservable = mRxBus.toObservableSticky(eventType);
        mObservables.put(eventType, mObservable);
        mSubscriptions.add(mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }));
    }

    public static void clear() {
        mSubscriptions.clear();
        for (Map.Entry<Class<?>, Observable<?>> entry : mObservables.entrySet())
            mRxBus.removeStickyEvent(entry.getKey());// 移除观察
    }

    public static void unsubscribe() {
        mSubscriptions.unsubscribe();
    }

    public static boolean hasSubscriptions() {
        return mSubscriptions.hasSubscriptions();
    }
}
