package com.mvvm.lux.burqa.ui.sub.adapter;

import android.graphics.drawable.Animatable;
import android.support.v4.app.FragmentActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.ui.sub.ImagePicDialogFragment;
import com.mvvm.lux.framework.manager.recycler.recyclerview.CommonAdapter;
import com.mvvm.lux.framework.manager.recycler.recyclerview.base.ViewHolder;
import com.mvvm.lux.widget.utils.DisplayUtil;

import java.util.List;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;
import progress.CircleProgress;
import progress.enums.CircleStyle;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/20 12:54
 * @Version
 */
public class ImagePicsListAdapter extends CommonAdapter<String> {

    private FragmentActivity content;
    private PhotoDraweeView mPhotoView;
    private String chapterTitle;
    public int currentPosition;

    public ImagePicsListAdapter(FragmentActivity context, int layoutId, List<String> urls, String chapterTitle) {
        super(context, layoutId, urls);
        this.chapterTitle = chapterTitle;
        this.content = context;
    }

    @Override
    protected void convert(ViewHolder holder, String url, int position) {
        mPhotoView = holder.getView(R.id.image_land);
        new CircleProgress  //加载圆形进度条
                .Builder()
                .setStyle(CircleStyle.FAN)
                .setProgressColor(mContext.getResources().getColor(R.color.white_trans))
                .setCustomText((position + 1) + "")
                .setTextSize(DisplayUtil.dp2px(18))
                .setCircleRadius(DisplayUtil.dp2px(30))
                .build()
                .injectFresco(mPhotoView);

        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setOldController(mPhotoView.getController())
                .setControllerListener(mControllerListener)
                .build();
        mPhotoView.setOnViewTapListener(mOnPhotoTapListener);
        mPhotoView.setController(controller);
        mPhotoView.setEnableDraweeMatrix(false);    //因为和recycleView事件冲突暂时禁掉缩放效果
    }

    private ControllerListener<ImageInfo> mControllerListener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            if (imageInfo != null)
                mPhotoView.setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());  //设置宽高比例
        }
    };

    private OnViewTapListener mOnPhotoTapListener = (view, x, y) -> {
        if (view instanceof PhotoDraweeView) {
            PhotoDraweeView photoView = (PhotoDraweeView) view;
            if (photoView.getScale() > photoView.getMinimumScale()) {
                photoView.setScale(photoView.getMinimumScale(), true);
            } else {
                ImagePicDialogFragment.show(content, mDatas.size(), currentPosition, chapterTitle);
            }
        }
    };
}
