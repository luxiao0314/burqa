package com.mvvm.lux.framework.manager.dialogs.config;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.mvvm.lux.framework.config.MarkAble;
import com.mvvm.lux.framework.manager.dialogs.interfaces.ProgressCancelListener;

/**
 * Created by iceman on 16/10/27 16:01
 * 邮箱：xubin865@pingan.com.cn
 */

public class BaseTask {
    protected TagConfig tagConfig;
    private MarkAble mMarkAble;
    private DialogExchangeModel.DialogExchangeModelBuilder dialogBuilder;
    private String[] itemString;

    public BaseTask(MarkAble markAble) {
        mMarkAble = markAble;
        tagConfig = new TagConfig();
        dialogBuilder = DialogExchangeModel.init(DialogType.PROGRESS_THREE);
    }

    public String getTag() {
        return tagConfig.getTag();
    }

    /**
     * 是否显示进度条传给UiConfit
     * @param showProcess
     * @return
     */
    public BaseTask setShowProcess(boolean showProcess) {
        dialogBuilder.isShowProcess = showProcess;
        return this;
    }

    /**
     * 是否可以取消进度条
     * @param cancelable
     * @return
     */
    public BaseTask setCancelable(boolean cancelable) {
        dialogBuilder.isCancelable = cancelable;
        return this;
    }

    /**
     * 进度条显示内容
     * @param progressContent
     * @return
     */
    public BaseTask setProgressContent(String progressContent) {
        dialogBuilder.progressContent = progressContent;
        return this;
    }

    public String getProgressContent(){
        return dialogBuilder.progressContent;
    }

    /**
     * 异常信息吐司
     * @param showErrorToast
     * @return
     */
    public BaseTask setShowErrorToast(boolean showErrorToast) {
        dialogBuilder.showErrorToast = showErrorToast;
        return this;
    }

    public BaseTask setDialogType(int dialogType){
        dialogBuilder.dialogType = dialogType;
        return this;
    }

    public int getDialogType(){
        return dialogBuilder.dialogType;
    }

    public TagConfig getTagConfig() {
        return tagConfig;
    }

    public void setTagConfig(TagConfig tagConfig) {
        this.tagConfig = tagConfig;
    }

    public DialogExchangeModel getDialogExchangeModel() {
        return dialogBuilder.build();
    }

    public void setDialogExchangeModel(DialogExchangeModel.DialogExchangeModelBuilder dialogBuilder) {
        this.dialogBuilder = dialogBuilder;
    }

    public FragmentManager getFragmentManager(){
        if (mMarkAble instanceof AppCompatActivity) {
            return ((FragmentActivity) mMarkAble).getSupportFragmentManager();
        } else if (mMarkAble instanceof Fragment) {
            return ((Fragment) mMarkAble).getChildFragmentManager();
        }
        return null;
    }

    public Activity getContext(){
        if (mMarkAble instanceof AppCompatActivity) {
            return ((FragmentActivity) mMarkAble);
        } else if (mMarkAble instanceof Fragment) {
            return ((Fragment) mMarkAble).getActivity();
        }
        return null;
    }

    public BaseTask setItem(String[] strings) {
        dialogBuilder.itemString = strings;
        return this;
    }

    public BaseTask setRequestCode(int requestCode) {
        dialogBuilder.requestCode = requestCode;
        return this;
    }

    public BaseTask setOnCancelProgress(ProgressCancelListener progressCancelListener) {
        dialogBuilder.progressCancelListener = progressCancelListener;
        return this;
    }
}
