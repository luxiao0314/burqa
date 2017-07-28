package com.mvvm.lux.framework.manager.dialogs.config;

/**
 * Created by iceman on 16/10/28 16:30
 * 邮箱：xubin865@pingan.com.cn
 * 包含每个task的tag信息.
 */

public class TagConfig {
    /**
     * 上下文
     */
    public String contextTag;
    /**
     * 主要标识,由具体内容决定
     */
    public String primaryTag = "default";
    /**
     * 时间戳
     */
    public long timeTag;

    public TagConfig() {
        timeTag = System.currentTimeMillis();
    }


    public String getTag() {
        return contextTag + "-" + primaryTag + "-" + timeTag;
    }

}
