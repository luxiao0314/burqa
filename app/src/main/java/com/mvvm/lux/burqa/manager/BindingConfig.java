package com.mvvm.lux.burqa.manager;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.ObservableList;
import android.databinding.adapters.ListenerUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.widget.banner.BannerEntity;
import com.mvvm.lux.widget.banner.BannerView;
import com.mvvm.lux.widget.emptyview.EmptyView;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import lib.lhh.fiv.library.FrescoImageView;

/**
 * @Description 自定义dataBinding属性
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/11/16 21:48
 * @Version $VALUE
 */
public class BindingConfig {
    /* --------------------------  recycleView  ----------------------------*/

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
        }
    }

    /**
     * 处理SwipeRefreshLayout的刷新事件
     * 其中event和method都不是必须的，因为系统会自动生成，写出来是为了更好地了解如何绑定的
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

    @BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);    //高度不变,提高性能
    }

    @BindingAdapter({"addItemDecoration"})
    public static void addItemDecoration(RecyclerView view, RecyclerView.ItemDecoration itemDecoration) {
        if (itemDecoration != null)
            view.addItemDecoration(itemDecoration);
    }

    @BindingAdapter({"addOnItemClick"})
    public static void addOnItemClick(RecyclerView view, RecyclerViewItemClickSupport.OnItemClickListener listener) {
        RecyclerViewItemClickSupport.addTo(view).setOnItemClickListener(listener);
    }

    /* ------------------------ 双向绑定 http://www.jianshu.com/p/05b9838a1949 ---------------------------- */
    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, int visiblity) {
        view.setVisibility(visiblity);
//        ? View.VISIBLE : View.GONE
    }

    @BindingAdapter("visibilityAttrChanged")
    public static void OnVisibility(View view, InverseBindingListener attrChange) {

    }

    /* ------------------------ other ---------------------------- */

    @BindingAdapter("imageUrl")
    public static void serImageView(FrescoImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            imageView.loadView("", R.drawable.article_default_image);
        } else {
            imageView.loadView(url, R.drawable.default_bg);
        }
    }

    /**
     * 发现页面流式布局adapter
     *
     * @param tagFlowLayout
     * @param adapter
     */
    @BindingAdapter("adapter")
    public static void setAdapter(TagFlowLayout tagFlowLayout, TagAdapter adapter) {
        if (adapter != null) {
            tagFlowLayout.setAdapter(adapter);
        }
    }

    /**
     * 流式布局的item电击事件
     *
     * @param tagFlowLayout
     * @param listener
     */
    @BindingAdapter("OnTagClick")
    public static void setOnTagClick(TagFlowLayout tagFlowLayout, TagFlowLayout.OnTagClickListener listener) {
        if (listener != null) {
            tagFlowLayout.setOnTagClickListener(listener);
        }
    }

    @BindingAdapter("OnTagSelect")
    public static void OnTagSelect(TagFlowLayout tagFlowLayout, TagFlowLayout.OnSelectListener listener) {
        if (listener != null) {
            tagFlowLayout.setOnSelectListener(listener);
        }
    }


    @BindingAdapter("pageAdapter")
    public static void setPageAdapter(ViewPager viewPager, android.support.v4.app.FragmentPagerAdapter adapter) {
        if (adapter != null) {
            viewPager.setAdapter(adapter);
        }
    }

    @BindingAdapter("setTabIcon")
    public static void setTabIcon(TabLayout tabLayout, ObservableList<Integer> resIcon) {
        for (Integer icon : resIcon) {
            tabLayout.addTab(tabLayout.newTab().setIcon(icon));
        }
    }

    @BindingAdapter("initBanner")
    public static void initBanner(BannerView bannerView, ObservableList<BannerEntity> bannerEntities) {
        bannerView.delayTime(5).build(bannerEntities);
    }


    @BindingAdapter("reload")
    public static void reload(EmptyView emptyView, EmptyView.ReloadOnClickListener listener) {
        if (listener != null)
            emptyView.reload(listener);
    }


    @InverseBindingMethods({
            @InverseBindingMethod(type = CompoundButton.class, attribute = "android:checked"),
    })//1.这里需要双向绑定的是checked属性，event和method都省略了。
    public static class CompoundButtonBindingAdapter {
        //2.设置什么时候调用event
        @BindingAdapter(value = {"android:onCheckedChanged", "android:checkedAttrChanged"},
                requireAll = false)
        public static void setListeners(CompoundButton view, final CompoundButton.OnCheckedChangeListener listener,
                                        final InverseBindingListener attrChange) {
            if (attrChange == null) {
                view.setOnCheckedChangeListener(listener);
            } else {
                view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (listener != null) {
                            listener.onCheckedChanged(buttonView, isChecked);
                        }
                        attrChange.onChange();
                    }
                });
            }
        }
    }

    //3.我们在layout中使用双向绑定
   /* <CheckBox
    android:id="@+id/checkbox"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:checked="@={user.checked}"/>*/

    //4.layout的binding类中自动生成的InverseBindingListener实现。
    // Inverse Binding Event Handlers
    /*private android.databinding.InverseBindingListener checkboxandroidCheck = new android.databinding.InverseBindingListener() {
        @Override
        public void onChange() {//这段逻辑其实就是用来更新user实体类中的checked字段的
            // Inverse of user.checked.get()
            //         is user.checked.set((java.lang.Boolean) callbackArg_0)
            boolean callbackArg_0 = checkbox.isChecked();//其实就是method
            // localize variables for thread safety
            // user.checked != null
            boolean checkedUserObjectnul = false;
            // user.checked
            android.databinding.ObservableField<java.lang.Boolean> checkedUser = null;
            // user
            lxf.androiddemos.model.UserEntity user = mUser;
            // user.checked.get()
            java.lang.Boolean CheckedUser1 = null;
            // user != null
            boolean userObjectnull = false;

            userObjectnull = (user) != (null);
            if (userObjectnull) {
                checkedUser = user.checked;

                checkedUserObjectnul = (checkedUser) != (null);
                if (checkedUserObjectnul) {
                    checkedUser.set((java.lang.Boolean) (callbackArg_0));
                }
            }
        }
    };*/

}
