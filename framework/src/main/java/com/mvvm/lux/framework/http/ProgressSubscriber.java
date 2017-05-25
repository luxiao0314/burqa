package com.mvvm.lux.framework.http;

import android.support.v4.app.DialogFragment;

import com.mvvm.lux.framework.manager.dialogs.DialogManager;
import com.mvvm.lux.framework.manager.dialogs.config.BaseTask;
import com.mvvm.lux.framework.manager.dialogs.interfaces.ProgressCancelListener;

/**
 * @Description 带progress的subscriber
 * 1,dialog取消的时候需要取消网络请求
 * //TODO 2,请求和生命周期绑定,activity销毁的时候取消网络请求
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/12/16 20:38
 * @Version
 */
public abstract class ProgressSubscriber<T> extends RxSubscriber<T> implements ProgressCancelListener {

    private BaseTask mServiceTask;
    private DialogFragment mDialogFragment;

    public ProgressSubscriber(BaseTask serviceTask) {
        if (serviceTask != null)
            serviceTask.setOnCancelProgress(this);
        mServiceTask = serviceTask;
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    private void showProgressDialog() {
        //不知道为什么activity会destroy了, 所以这里一直报错
        if (mDialogFragment == null && mServiceTask != null && !mServiceTask.getContext().isDestroyed()) {
            mDialogFragment = DialogManager.showProgressDialog(mServiceTask);
        } else {
            dismissProgressDialog();
        }
    }

    private void dismissProgressDialog() {
        if (mDialogFragment != null) {
            mDialogFragment.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
