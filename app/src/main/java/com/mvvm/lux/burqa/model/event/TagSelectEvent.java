package com.mvvm.lux.burqa.model.event;

import com.mvvm.lux.framework.base.BaseEvent;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/23 18:09
 * @Version
 */
public class TagSelectEvent extends BaseEvent {
    public int mTagPosition;
    public int mId;

    public TagSelectEvent(int tagPosition, int id) {

        mTagPosition = tagPosition;
        mId = id;
    }
}
