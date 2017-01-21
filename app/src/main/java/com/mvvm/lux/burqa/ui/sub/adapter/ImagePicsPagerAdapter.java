package com.mvvm.lux.burqa.ui.sub.adapter;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.ui.sub.ImagePicDialogFragment;
import com.mvvm.lux.widget.utils.DisplayUtil;

import java.util.ArrayList;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;
import progress.CircleProgress;
import progress.enums.CircleStyle;

public class ImagePicsPagerAdapter extends PagerAdapter {
    private FragmentActivity mContext = null;
    private ArrayList<String> mUrls;
    private int mPosition;
    private PhotoDraweeView mPhotoView;

    public ImagePicsPagerAdapter(FragmentActivity context, ArrayList<String> urls) {
        this.mContext = context;
        this.mUrls = urls;
    }

    public int getCount() {
        return mUrls == null ? 0 : mUrls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mPosition = position;
        String url = mUrls.get(position);
        View contentview = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, container, false);
        mPhotoView = (PhotoDraweeView) contentview.findViewById(R.id.photoview);
        mPhotoView.setOnViewTapListener(mOnPhotoTapListener);

        new CircleProgress  //加载圆形进度条
                .Builder()
                .setStyle(CircleStyle.FAN)
                .setProgressColor(mContext.getResources().getColor(R.color.white_trans))
                .setCustomText((position + 1) + "")
                .setTextSize(DisplayUtil.dp2px(14))
                .setCircleRadius(DisplayUtil.dp2px(20))
                .build()
                .injectFresco(mPhotoView);

        mPhotoView.setPhotoUri(Uri.parse(url));
        container.addView(contentview);
        return contentview;
    }

    private OnViewTapListener mOnPhotoTapListener = (view, x, y) -> {
        if (view instanceof PhotoDraweeView) {
            PhotoDraweeView photoView = (PhotoDraweeView) view;
            if (photoView.getScale() > photoView.getMinimumScale()) {
                photoView.setScale(photoView.getMinimumScale(), true);
            } else {
                ImagePicDialogFragment.show(mContext, mUrls.size(), mPosition);
            }
        }
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}