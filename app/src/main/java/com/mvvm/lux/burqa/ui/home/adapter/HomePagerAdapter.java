package com.mvvm.lux.burqa.ui.home.adapter;

import android.databinding.ObservableList;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.mvvm.lux.burqa.ui.home.fragment.CategoryFragment;
import com.mvvm.lux.burqa.ui.home.fragment.FavoriteFragment;
import com.mvvm.lux.burqa.ui.home.fragment.GameFragment;
import com.mvvm.lux.burqa.ui.home.fragment.RecomFragment;
import com.mvvm.lux.burqa.ui.home.fragment.UpdateFragment;

/**
 * Created by WangChao on 2016/10/20.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private final Fragment[] mFragments;
    private FragmentActivity mActivity;
    private ObservableList<Integer> mResIcon;

    public HomePagerAdapter(FragmentActivity activity, ObservableList<Integer> resIcon) {
        super(activity.getSupportFragmentManager());
        mActivity = activity;
        mResIcon = resIcon;
        mFragments = new Fragment[mResIcon.size()];
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments[position] == null) {
            switch (position) {
                case 0:
                    mFragments[position] = FavoriteFragment.newIntance();
                    break;
                case 1:
                    mFragments[position] = RecomFragment.newInstance();
                    break;
                case 2:
                    mFragments[position] = CategoryFragment.newInstance();
                    break;
                case 3:
                    mFragments[position] = GameFragment.newInstance();
                    break;
                case 4:
                    mFragments[position] = UpdateFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mResIcon.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = ContextCompat.getDrawable(mActivity, mResIcon.get(position));
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
