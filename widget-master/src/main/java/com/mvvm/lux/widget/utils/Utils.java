package com.mvvm.lux.widget.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/11/2
 * Time: 09:07
 * 工具类
 */
public class Utils {

    public static void convertActivityToTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertActivityToTranslucentAfterL(activity);
        } else {
            convertActivityToTranslucentBeforeL(activity);
        }
    }

    /**
     * Calling the convertToTranslucent method on platforms before Android 5.0
     */
    public static void convertActivityToTranslucentBeforeL(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method method = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, new Object[] {
                    null
            });
        } catch (Throwable t) {
        }
    }

    /**
     * Calling the convertToTranslucent method on platforms after Android 5.0
     */
    private static void convertActivityToTranslucentAfterL(Activity activity) {
        try {
            Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent",
                    translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, null, options);
        } catch (Throwable t) {
        }
    }

    public static void vibrate(long duration, Activity activity) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }

    public static ImageView getItemImageView(Context context, @DrawableRes int placeholderResId) {
        return getItemImageView(context, placeholderResId, ImageView.ScaleType.CENTER_CROP);
    }

    public static ImageView getItemImageView(Context context, @DrawableRes int placeholderResId, ImageView.ScaleType scaleType) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(placeholderResId);
        imageView.setClickable(true);
        imageView.setScaleType(scaleType);
        return imageView;
    }

    public static void resetPageTransformer(List<? extends View> views) {
        if (views == null) {
            return;
        }

        for (View view : views) {
            view.setVisibility(View.VISIBLE);
            ViewCompat.setAlpha(view, 1);
            ViewCompat.setPivotX(view, view.getMeasuredWidth() * 0.5f);
            ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
            ViewCompat.setTranslationX(view, 0);
            ViewCompat.setTranslationY(view, 0);
            ViewCompat.setScaleX(view, 1);
            ViewCompat.setScaleY(view, 1);
            ViewCompat.setRotationX(view, 0);
            ViewCompat.setRotationY(view, 0);
            ViewCompat.setRotation(view, 0);
        }
    }

    public static Drawable getDrawble(Context context, @DrawableRes int id){
        return ContextCompat.getDrawable(context,id);
    }

    public static int getColor(Context context,@ColorRes int id){
        return  ContextCompat.getColor(context,id);
    }

    public static String getString(Context context,@StringRes int id){
        return  context.getResources().getString(id);
    }

    public static <T extends View> T findViewById(View v, int id) {
        return (T) v.findViewById(id);
    }
}
