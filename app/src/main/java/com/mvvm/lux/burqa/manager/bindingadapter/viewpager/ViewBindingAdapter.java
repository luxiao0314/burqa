package com.mvvm.lux.burqa.manager.bindingadapter.viewpager;

import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.mvvm.lux.burqa.manager.ReplyCommand;


/**
 * Created by kelin on 16-6-1.
 */
public class ViewBindingAdapter {
    @BindingAdapter(value = {"onPageScrolledCommand", "onPageSelectedCommand", "onPageScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final ViewPager viewPager,
                                             final ReplyCommand<ViewPagerDataWrapper> onPageScrolledCommand,
                                             final ReplyCommand<Integer> onPageSelectedCommand,
                                             final ReplyCommand<Integer> onPageScrollStateChangedCommand) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int state;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageScrolledCommand != null) {
                    onPageScrolledCommand.execute(new ViewPagerDataWrapper(position, positionOffset, positionOffsetPixels, state));
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (onPageSelectedCommand != null) {
                    onPageSelectedCommand.execute(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.state = state;
                if (onPageScrollStateChangedCommand != null) {
                    onPageScrollStateChangedCommand.execute(state);
                }
            }
        });

    }

    public static class ViewPagerDataWrapper {
        public float positionOffset;
        public float position;
        public int positionOffsetPixels;
        public int state;

        public ViewPagerDataWrapper(float position, float positionOffset, int positionOffsetPixels, int state) {
            this.positionOffset = positionOffset;
            this.position = position;
            this.positionOffsetPixels = positionOffsetPixels;
            this.state = state;
        }
    }

    @BindingAdapter("pageAdapter")
    public static void setPageAdapter(ViewPager viewPager, android.support.v4.app.FragmentPagerAdapter adapter) {
        if (adapter != null) {
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(3);
            viewPager.setOffscreenPageLimit(4); //设置默认加载多少页
        }
    }

    @BindingAdapter({"pageAdapter"})
    public static void setPageAdapter(ViewPager viewPager, PagerAdapter adapter) {
        if (adapter != null) {
            viewPager.setAdapter(adapter);
        }
    }

    @BindingAdapter("setOffscreenPageLimit")
    public static void setPageAdapter(ViewPager viewPager, int pageLimit) {
        viewPager.setOffscreenPageLimit(pageLimit);
    }
}
