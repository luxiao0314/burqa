package com.mvvm.lux.burqa.manager.bindingadapter.fresco;

import android.databinding.BindingAdapter;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.widget.utils.DisplayUtil;

import lib.lhh.fiv.library.FrescoImageView;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/1 12:43
 * @Version
 */
public class ViewBindingAdapter {

    @BindingAdapter({"imageUrl"})
    public static void setUrl(FrescoImageView imageView, String url) {
        //添加图片统一配置
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setTapToRetryEnabled(true)
                .build();

        RoundingParams roundingParams = imageView.getRoundingParams()
                .setBorderWidth(DisplayUtil.dp2px(imageView.getContext(), 0.5f))   //设置边框
                .setBorderColor(imageView.getResources().getColor(R.color.gray_80));

        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
        hierarchy.setFadeDuration(1000);
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
        hierarchy.setPlaceholderImage(R.drawable.default_bg, ScalingUtils.ScaleType.FIT_XY);
        imageView.setHierarchy(hierarchy);
        imageView.setRoundingParmas(roundingParams);
        imageView.setController(controller);
    }

    @BindingAdapter({"circleImageUrl"})
    public static void circleImageUrl(FrescoImageView imageView, String url) {
        //添加图片统一配置
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setTapToRetryEnabled(true)
                .build();

        RoundingParams roundingParams = imageView.getRoundingParams()
                .setBorderWidth(DisplayUtil.dp2px(imageView.getContext(), 0.5f))
                .setBorderColor(imageView.getResources().getColor(R.color.gray_80))
                .setRoundAsCircle(true);

        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
        hierarchy.setFadeDuration(1000);
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        hierarchy.setPlaceholderImage(R.drawable.default_circle_bg, ScalingUtils.ScaleType.FIT_XY);
        imageView.setHierarchy(hierarchy);
        imageView.asCircle();
        imageView.setRoundingParmas(roundingParams);
        imageView.setController(controller);
    }

}
