package com.mvvm.lux.burqa.ui.home.adapter;

import android.graphics.drawable.Animatable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.ui.sub.ImagePicDialogFragment;
import com.mvvm.lux.widget.utils.DisplayUtil;

import java.util.ArrayList;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;
import progress.CircleProgress;
import progress.enums.CircleStyle;

public class ImagePicsPagerAdapter extends PagerAdapter implements OnPhotoTapListener {
    private FragmentActivity mContext = null;
    private ArrayList<String> mUrls;
    private int mPosition;

    public ImagePicsPagerAdapter(FragmentActivity context, ArrayList<String> urls) {
        this.mContext = context;
        this.mUrls = urls;
    }

    public int getCount() {
        return mUrls == null ? 0 : mUrls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String imageURL = mUrls.get(position);
        View contentview = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, container, false);
        final PhotoDraweeView photoView = (PhotoDraweeView) contentview.findViewById(R.id.photoview);
        photoView.setOnPhotoTapListener(this);

        new CircleProgress  //加载圆形进度条
                .Builder()
                .setStyle(CircleStyle.FAN)
                .setProgressColor(mContext.getResources().getColor(R.color.orange_trans))
                .setCustomText((position + 1) + "")
                .setCircleRadius(DisplayUtil.dp2px(15))
                .build()
                .injectFresco(photoView);

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(imageURL);
        controller.setOldController(photoView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                photoView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        photoView.setController(controller.build());
        container.addView(contentview);
        return contentview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    @Override
    public void onPhotoTap(View view, float x, float y) {
        if (view instanceof PhotoDraweeView) {
            PhotoDraweeView photoView = (PhotoDraweeView) view;
            if (photoView.getScale() > photoView.getMinimumScale()) {
                photoView.setScale(photoView.getMinimumScale(), true);
            } else {
                ImagePicDialogFragment.show(mContext, mUrls.size(), mPosition);
            }
        }
    }

    public void onPageSelectedPostion(int position) {
        mPosition = position;
    }
}