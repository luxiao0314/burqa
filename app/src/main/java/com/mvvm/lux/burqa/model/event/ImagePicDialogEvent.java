package com.mvvm.lux.burqa.model.event;

import com.mvvm.lux.framework.base.BaseEvent;

/**
 * @Description ImagePicDialogFragment的点击设置事件
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/18 17:15
 * @Version
 */
public class ImagePicDialogEvent extends BaseEvent {


    public static final String SWITCH_SCREEN_MODE = "switchScreenMode";
    public static final String SETTING = "setting";

    public String mType;

    public ImagePicDialogEvent(String type) {
        mType = type;
    }
}
