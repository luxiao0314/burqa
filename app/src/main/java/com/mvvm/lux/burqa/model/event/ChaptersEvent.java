package com.mvvm.lux.burqa.model.event;

import com.mvvm.lux.framework.base.BaseEvent;

/**
 * @Description 续看按钮提示事件
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/22 16:00
 * @Version
 */
public class ChaptersEvent extends BaseEvent {
    public String mChapter_title;
    public String mObjId;

    public ChaptersEvent(String chapter_title, String objId) {
        mChapter_title = chapter_title;
        mObjId = objId;
    }
}
