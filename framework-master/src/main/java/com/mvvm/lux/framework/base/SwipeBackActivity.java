package com.mvvm.lux.framework.base;

import android.os.Bundle;
import android.view.View;

import com.mvvm.lux.framework.utils.Utils;
import com.mvvm.lux.widget.swipeback.SwipeBackActivityBase;
import com.mvvm.lux.widget.swipeback.SwipeBackActivityHelper;
import com.mvvm.lux.widget.swipeback.SwipeBackLayout;


/**
 * @Description 1、想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager
 * 只需要调用SwipeBackLayout的setViewPager()方法即可
 * 2、设置activity的主题为android:theme="@style/CustomTransparent
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/4 15:02
 * @Version
 */
public class SwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    public void setEdge(int edgeFlags) {
        getSwipeBackLayout().setEdgeTrackingEnabled(edgeFlags);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
