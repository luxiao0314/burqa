package com.mvvm.lux.framework.manager.dialogs.config;

import android.view.Gravity;

import com.mvvm.lux.framework.manager.dialogs.interfaces.ProgressCancelListener;

import java.io.Serializable;

public class DialogExchangeModel {
    public DialogExchangeModelBuilder dialogExchangeModelBuilder;

    public DialogExchangeModel(DialogExchangeModelBuilder DialogExchangeModelBuilder) {
        this.dialogExchangeModelBuilder = DialogExchangeModelBuilder;
    }

    public int getDialogType() {
        return dialogExchangeModelBuilder.dialogType;
    }

    public CharSequence getDialogContext() {
        return dialogExchangeModelBuilder.dialogContext;
    }

    public String getPostiveText() {
        return dialogExchangeModelBuilder.postiveText;
    }

    public String getNegativeText() {
        return dialogExchangeModelBuilder.negativeText;
    }

    public String getTag() {
        return dialogExchangeModelBuilder.tag;
    }

    public String getSingleText() {
        return dialogExchangeModelBuilder.singleText;
    }

    public boolean isBackable() {
        return dialogExchangeModelBuilder.isCancelable;
    }

    public int getGravity() {
        return dialogExchangeModelBuilder.gravity;
    }

    public boolean isShowProcess(){
        return dialogExchangeModelBuilder.isShowProcess;
    }

    public boolean isShowErrorToast(){
        return dialogExchangeModelBuilder.showErrorToast;
    }

    public String getProgressContent(){
        return dialogExchangeModelBuilder.progressContent;
    }

    public DialogClickInterface getClickInterface() {
        return dialogExchangeModelBuilder.clickInterface;
    }

    public static DialogExchangeModelBuilder init(int dialogType){
        return new DialogExchangeModelBuilder(dialogType);
    }

    public static class DialogExchangeModelBuilder implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = -3685432164096360693L;
        /**
         * 弹出框类型
         */
        public int dialogType = DialogType.PROGRESS_ONE;    //默认为loading
        /**
         * 弹出框内容
         */
        private CharSequence dialogContext = "";
        /**
         * 是否显示标题
         */
        private boolean hasTitle = true;
        /**
         * 确认按键
         */
        private String postiveText = "";
        /**
         * 取消按键
         */
        private String negativeText = "";
        /**
         * 单按键
         */
        private String singleText = "";
        /**
         * tag
         */
        private String tag = "dialog";
        /**
         * 是否可取消
         */
        public boolean isCancelable = true;

        /**
         * 是否显示转圈
         */
        public boolean isShowProcess = true;

        /**
         * loadin提示信息
         */
        public String progressContent;

        /**
         * 出现错误是否显示toast
         */
        public boolean showErrorToast;

        private transient DialogClickInterface clickInterface;

        private int gravity = Gravity.CENTER;

        public String[] itemString = new String[]{"Jayne", "Malcolm", "Kaylee", "Wash", "Zoe", "River"};

        public int requestCode;
        /**
         * 取消dialog的监听
         */
        public ProgressCancelListener progressCancelListener;

        public DialogExchangeModelBuilder(int dialogType) {
            this.dialogType = dialogType;
        }

        public DialogExchangeModelBuilder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public DialogExchangeModelBuilder setDialogType(int dialogType) {
            this.dialogType = dialogType;
            return this;
        }

        public DialogExchangeModelBuilder setDialogContext(CharSequence dialogContext) {
            this.dialogContext = dialogContext;
            return this;
        }

        public DialogExchangeModelBuilder setShowProcess(Boolean isShowProcess) {
            this.isShowProcess = isShowProcess;
            return this;
        }

        public DialogExchangeModelBuilder setShowErrorToast(Boolean showErrorToast) {
            this.showErrorToast = showErrorToast;
            return this;
        }

        public DialogExchangeModelBuilder setProgressContent(String progressContent) {
            this.progressContent = progressContent;
            return this;
        }

        public DialogExchangeModelBuilder setHasTitle(boolean hasTitle) {
            this.hasTitle = hasTitle;
            return this;
        }

        public DialogExchangeModelBuilder setPostiveText(String postiveText) {
            this.postiveText = postiveText;
            return this;
        }

        public DialogExchangeModelBuilder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public DialogExchangeModelBuilder setSingleText(String singleText) {
            this.singleText = singleText;
            return this;
        }

        public DialogExchangeModelBuilder setCancelable(boolean isBackable) {
            this.isCancelable = isBackable;
            return this;
        }

        public DialogExchangeModelBuilder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public DialogExchangeModelBuilder setClickInterface(DialogClickInterface clickInterface) {
            this.clickInterface = clickInterface;
            return this;
        }

        public DialogExchangeModel build() {
            return new DialogExchangeModel(this);
        }

    }
}
