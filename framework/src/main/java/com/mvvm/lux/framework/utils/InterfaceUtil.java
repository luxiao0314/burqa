package com.mvvm.lux.framework.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.mvvm.lux.framework.config.MarkAble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description 接口工具类, 用于实现监听的解耦, 类只用实现接口就可以了
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/14 11:00
 * @Version 1.0.0
 */
public class InterfaceUtil {

    @SuppressWarnings("unchecked")
    protected static <T> List<T> getListeners(Class<T> listenerInterface, MarkAble markAble) {
        List<T> listeners = new ArrayList<>(2);
        if (markAble instanceof Fragment) {
            Fragment targetFragment = ((Fragment) markAble).getTargetFragment();
            if (targetFragment != null && listenerInterface.isAssignableFrom(targetFragment.getClass())) {
                listeners.add((T) targetFragment);
            }
        }
        if (markAble instanceof Activity) {
            if (listenerInterface.isAssignableFrom(((Activity) markAble).getClass())) {
                listeners.add((T) markAble);
            }
        }
        return Collections.unmodifiableList(listeners);
    }

}
