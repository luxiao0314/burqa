package com.mvvm.lux.framework.config;

import com.mvvm.lux.framework.base.BaseEvent;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/29 13:45
 * @Version
 */
public class PassUrlEvent extends BaseEvent {

    public String url;

    public PassUrlEvent(String url) {
        this.url = url;
    }
}
