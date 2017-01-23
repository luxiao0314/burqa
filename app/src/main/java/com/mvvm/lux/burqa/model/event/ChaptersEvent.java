package com.mvvm.lux.burqa.model.event;

import com.mvvm.lux.framework.base.BaseEvent;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/22 16:00
 * @Version
 */
public class ChaptersEvent extends BaseEvent {
    public String mChapter_title;
    public String mObjId;
    public boolean mIsCollection;

    public ChaptersEvent(String chapter_title, String objId, boolean isCollection) {
        mChapter_title = chapter_title;
        mObjId = objId;
        mIsCollection = isCollection;
    }
}
