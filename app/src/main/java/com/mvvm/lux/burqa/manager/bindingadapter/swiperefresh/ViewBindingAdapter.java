package com.mvvm.lux.burqa.manager.bindingadapter.swiperefresh;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.support.v4.widget.SwipeRefreshLayout;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.manager.ReplyCommand;

/**
 * Created by kelin on 16-4-26.
 */
public class ViewBindingAdapter {
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final ReplyCommand onRefreshCommand) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
    }

    /**
     * 双向绑定
     *
     * @param view
     * @return
     */
    @InverseBindingAdapter(attribute = "refreshing", event = "refreshingAttrChanged")
    public static boolean isRefreshing(SwipeRefreshLayout view) {
        return view.isRefreshing();
    }

    /**
     * des:如果设置的属性是true,那么久刷新改控件 : dataBinding已经封装了设置刷新的属性,这里没用到
     * 正在刷新,方法名随意定,参数为控件,注解参数为设置的属性,那么系统就会优先选择自定义的属性
     * 也可以去在attrs里面定义这样的属性
     * <p>
     * 双向绑定有可能会出现死循环，因为当你通过Listener反向设置数据时，数据也会再次发送事件给View。所以我们需要在设置一下避免死循环
     *
     * @param view
     */
    @BindingAdapter("refreshing")
    public static void setRefreshing(SwipeRefreshLayout view, boolean refreshing) {
        if (refreshing != view.isRefreshing()) {
            view.setRefreshing(refreshing);
            view.setColorSchemeColors(view.getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * 处理SwipeRefreshLayout的刷新事件
     * 其中event和method都不是必须的，因为系统会自动生成，写出来是为了更好地了解如何绑定的
     * onRefreshListener : SwipeRefreshLayout 刷新的监听
     * refreshingAttrChanged  : SwipeRefreshLayout刷新的状态,将两者关联起来
     *
     * @param view
     * @param listener
     * @param refreshingAttrChanged
     */
    @BindingAdapter(value = {"onRefreshListener", "refreshingAttrChanged"}, requireAll = false)
    public static void onRefreshing(SwipeRefreshLayout view, SwipeRefreshLayout.OnRefreshListener listener,
                                    InverseBindingListener refreshingAttrChanged) {
        SwipeRefreshLayout.OnRefreshListener newValue = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (listener != null) {
                    if (refreshingAttrChanged != null) {
                        refreshingAttrChanged.onChange();
                    }
                    listener.onRefresh();
                }
            }
        };

        SwipeRefreshLayout.OnRefreshListener oldValue = ListenerUtil.trackListener(view, newValue, R.id.onRefreshListener);
        if (oldValue != null) {
            view.setOnRefreshListener(null);
        }
        view.setOnRefreshListener(newValue);
    }

    @BindingAdapter("onRefreshCommand")
    public static void postDelay(SwipeRefreshLayout swipeRefreshLayout, ReplyCommand<Runnable> replyCommand) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                replyCommand.execute(); //做回调处理
            }
        });
    }

}
