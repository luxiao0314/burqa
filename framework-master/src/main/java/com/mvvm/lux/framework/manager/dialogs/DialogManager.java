package com.mvvm.lux.framework.manager.dialogs;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.AbsListView;

import com.mvvm.lux.framework.manager.dialogs.config.BaseTask;
import com.mvvm.lux.framework.manager.dialogs.config.DialogExchangeModel;
import com.mvvm.lux.framework.manager.dialogs.config.DialogType;

/**
 * @Description dialogFragment管理类
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2016/12/16 21:39
 * @Version
 */
public class DialogManager {

    public static final int REQUEST_PROGRESS = 1;
    public static final int REQUEST_LIST_SIMPLE = 9;
    public static final int REQUEST_LIST_MULTIPLE = 10;
    public static final int REQUEST_LIST_SINGLE = 11;
    public static final int REQUEST_SIMPLE_DIALOG = 42;

    /**
     * progress dialog
     * @param baseTask
     * @return
     */
    public static DialogFragment showProgressDialog(BaseTask baseTask){
        return ProgressDialogFragment.createBuilder(baseTask)
                .setMessage(baseTask.getProgressContent())
                .setRequestCode(REQUEST_PROGRESS)
                .setProgressType(2)
                .useThemeType(3)    //进度条主题:背景透明
                .setCancelableOnTouchOutside(false) //点击屏幕不取消
                .showAllowingStateLoss();
    }

    /**
     * listDialog
     * @param baseTask
     * @return
     */
    public static DialogFragment showListDialog(BaseTask baseTask){
        int dialogType = baseTask.getDialogExchangeModel().getDialogType();
        if (dialogType == DialogType.LIST_DIALOG) {
            return listDialog(baseTask);
        } else if (dialogType == DialogType.SINGLE_CHOICE_LIST_DIALOG) {
            return singleChoiceListDialog(baseTask);
        } else if (dialogType == DialogType.MULTIPLE_CHOICE_LIST_DIALOG) {
            return multipleChoiceListDialog(baseTask);
        }
        return null;
    }

    /**
     * Simple dialog: only message
     * @param baseTask
     * @return
     */
    public static DialogFragment showSimpleDialog(BaseTask baseTask){
        int dialogType = baseTask.getDialogExchangeModel().getDialogType();
        if (dialogType == DialogType.SIMPLE_ONLY_MESSAGE) {
            return onlyMessage(baseTask);
        } else if (dialogType == DialogType.SIMPLE_LONG_MESSAGE_AND_TITLE) {
            return longMessageAndTitle(baseTask);
        } else if (dialogType == DialogType.SIMPLE_THR_BUTTONS_CALLBACKS) {
            return thrButtonCallbacks(baseTask);
        } else if (dialogType == DialogType.SIMPLE_STACKED_BUTTONS) {
            return stackedButtons(baseTask);
        }
        return null;
    }

    public static void showCustomDialog(FragmentActivity activity){
        JayneHatDialogFragment.show(activity);
    }

    private static DialogFragment multipleChoiceListDialog(BaseTask baseTask) {
        return ListDialogFragment
                .createBuilder(baseTask)
                .setTitle("Your favorite character:")
                .setItems(new String[]{"Jayne", "Malcolm", "Kaylee", "Wash", "Zoe", "River"})
                .setRequestCode(REQUEST_LIST_MULTIPLE)
                .setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE)
                .setCheckedItems(new int[]{1, 3})
                .show();
    }

    private static DialogFragment singleChoiceListDialog(BaseTask baseTask) {
        return ListDialogFragment
                .createBuilder(baseTask)
                .setTitle("Your favorite character:")
                .setItems(new String[]{"Jayne", "Malcolm", "Kaylee", "Wash", "Zoe", "River"})
                .setRequestCode(REQUEST_LIST_SINGLE)
                .setChoiceMode(AbsListView.CHOICE_MODE_SINGLE)
                .show();
    }

    private static DialogFragment listDialog(BaseTask baseTask) {
        DialogExchangeModel.DialogExchangeModelBuilder builder = baseTask.getDialogExchangeModel().dialogExchangeModelBuilder;
        return ListDialogFragment
                .createBuilder(baseTask)
                .setTitle("Your favorite character:")
                .setItems(builder.itemString)
                .setRequestCode(builder.requestCode)
                .show();
    }

    private static DialogFragment stackedButtons(BaseTask baseTask) {
        return SimpleDialogFragment.createBuilder(baseTask)
                .setMessage("How will you decide?")
                .setPositiveButtonText("Time for some thrillin' heroics!")
                .setNegativeButtonText("Misbehave")
                .setNeutralButtonText("Keep flying").show();
    }

    private static DialogFragment thrButtonCallbacks(BaseTask baseTask) {
        return SimpleDialogFragment.createBuilder(baseTask)
                .setTitle("Do you like this quote?")
                .setMessage("Jayne: \"Shiny. Let's be bad guys.\"")
                .setPositiveButtonText("Love")
                .setNegativeButtonText("Hate")
                .setNeutralButtonText("WTF?")
                .setRequestCode(REQUEST_SIMPLE_DIALOG)
                .show();
    }

    private static DialogFragment longMessageAndTitle(BaseTask baseTask) {
        return SimpleDialogFragment.createBuilder(baseTask)
                .setTitle("More Firefly quotes:").setMessage
                        ("Wash: \"Psychic, though? That sounds like something out of science fiction.\"\n\nZoe: \"We live" +
                                " " +
                                "in a space ship, dear.\"\nWash: \"Here lies my beloved Zoe, " +
                                ("my autumn flower ... somewhat less attractive now that she's all corpsified and gross" +
                                        ".\"\n\nRiver Tam: \"Also? I can kill you with my brain.\"\n\nKayle: \"Going on a year now, nothins twixed my neathers not run on batteries.\" \n" +
                                        "Mal: \"I can't know that.\" \n" +
                                        "Jayne: \"I can stand to hear a little more.\"\n\nWash: \"I've been under fire before. " +
                                        "Well ... I've been in a fire. Actually, I was fired. I can handle myself.\""))
                .setNegativeButtonText("Close")
                .show();
    }

    private static DialogFragment onlyMessage(BaseTask baseTask) {
       return SimpleDialogFragment.createBuilder(baseTask)
                .setMessage("Love. Can know all the math in the \'verse but take a boat in the air that you don\'t " +
                        "love? She\'ll shake you off just as sure as a turn in the worlds. Love keeps her in the air when " +
                        "she oughtta fall down...tell you she\'s hurtin\' \'fore she keens...makes her a home.").show();
    }

}
