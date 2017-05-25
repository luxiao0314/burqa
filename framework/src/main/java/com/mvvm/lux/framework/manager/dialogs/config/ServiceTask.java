package com.mvvm.lux.framework.manager.dialogs.config;

import com.mvvm.lux.framework.config.MarkAble;

/**
 * Created by iceman on 16/11/21.
 */

public class ServiceTask extends BaseTask {
    public DataConfig dataConfig;
    private ServiceCallback callback;

    public static ServiceTask create(MarkAble markAble){
        return new ServiceTask(markAble);
    }

    public ServiceTask(MarkAble markAble) {
        super(markAble);
        dataConfig = new DataConfig();
    }

    public ServiceTask setUseVirtualData(boolean useVirtualData) {
        dataConfig.useVirtualData = useVirtualData;
        return this;
    }

    public TagConfig getTagConfig() {
        return tagConfig;
    }

    public void setTagConfig(TagConfig tagConfig) {
        this.tagConfig = tagConfig;
    }

    public ServiceCallback getCallback() {
        return callback;
    }

    public BaseTask setCallback(ServiceCallback callback) {
        this.callback = callback;
        return this;
    }
}
