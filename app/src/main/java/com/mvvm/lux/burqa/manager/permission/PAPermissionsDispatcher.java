package com.mvvm.lux.burqa.manager.permission;

import android.support.v4.app.ActivityCompat;

import com.mvvm.lux.framework.base.BaseActivity;
import com.mvvm.lux.framework.manager.dialogs.DialogManager;
import com.mvvm.lux.framework.manager.dialogs.config.DialogType;
import com.mvvm.lux.framework.manager.dialogs.config.ServiceTask;

import java.util.List;

public class PAPermissionsDispatcher {
    private static PAPermissionsDispatcher instance;
    public OnPermissionLisenter permissionLisenter;

    private PAPermissionsDispatcher() {
    }

    public static PAPermissionsDispatcher getInstance() {
        if (instance == null) {
            synchronized (PAPermissionsDispatcher.class) {
                if (instance == null) {
                    instance = new PAPermissionsDispatcher();
                }
            }
        }
        return instance;
    }

    public void setPermissionLisenter(OnPermissionLisenter permissionLisenter) {
        this.permissionLisenter = permissionLisenter;
    }


    public interface OnPermissionLisenter {
        void onPermissionsGranted(int request);

        void onPermissionsDenied();
    }

    public void showPermissionWithCheck(BaseActivity target, int requestCode, String... permissions) {
        PermissionUtils.sortGrantedAndDeniedPermissions(target, permissions);

        if (PermissionUtils.hasSelfPermissions(target, permissions)) {
            permissionLisenter.onPermissionsGranted(requestCode);
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(target, permissions)) {
                List<String> deniedPermissions = PermissionUtils.getDeniedPermissions();
                StringBuilder msg = getUnShowPermissionsMessage(deniedPermissions);
                showRationaleDialog(target, msg.toString(), requestCode, permissions);
            } else {
                ActivityCompat.requestPermissions(target, permissions, requestCode);
            }
        }
    }

    public void onRequestPermissionsResult(BaseActivity target, int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils
                .hasSelfPermissions(target, permissions)) {
            permissionLisenter.onPermissionsDenied();
            return;
        }
        if (PermissionUtils.verifyPermissions(grantResults)) {
            permissionLisenter.onPermissionsGranted(requestCode);
        } else {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(target, permissions)) {
                permissionLisenter.onPermissionsGranted(requestCode);
            } else {
                permissionLisenter.onPermissionsDenied();
            }
        }

    }

    private void showRationaleDialog(final BaseActivity target, final String tips,
                                     final int requestCode, final String[] permissions) {
        target.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                DialogManager.showSimpleDialog(new ServiceTask(target)
                        .setDialogType(DialogType.SIMPLE_THR_BUTTONS_CALLBACKS));

//                DialogManager.showSimpleExcuteDialog(target.getSupportFragmentManager(), tips,"拒绝","允许", new DialogClickInterface() {
//                    @Override
//                    public void onSingleBtnClick() {
//
//                    }
//
//                    @Override
//                    public void onPositiveBtnClick() {
//                        ActivityCompat.requestPermissions(target, permissions, requestCode);
//
//                    }
//
//                    @Override
//                    public void onNegativeBtnClick() {
//                        permissionLisenter.onPermissionsDenied();
//                    }
//                });
            }
        });
    }

    private StringBuilder getUnShowPermissionsMessage(List<String> list) {
        StringBuilder message = new StringBuilder("您已关闭了");
        String permisson;
        boolean hasCALENDAR = false;
        boolean hasCAMERA = false;
        boolean hasCONTACTS = false;
        boolean hasLOCATION = false;
        boolean hasMICROPHONE = false;
        boolean hasPHONE = false;
        boolean hasSENSORS = false;
        boolean hasSMS = false;
        boolean hasSTORAGE = false;

        if (list.size() == 1) {
            permisson = list.get(0);
            if (permisson.contains("CALENDAR")) {
                message.append("日历 ");
            } else if (permisson.contains("CAMERA")) {
                message.append("相机 ");

            } else if (permisson.contains("CONTACTS") || permisson
                    .equals("android.permission.GET_ACCOUNTS")) {
                message.append("通讯录 ");

            } else if (permisson.contains("LOCATION")) {
                message.append("定位 ");

            } else if (permisson.equals("android.permission.RECORD_AUDIO")) {
                message.append("耳麦 ");

            } else if (permisson.contains("PHONE") || permisson.contains("CALL_LOG") || permisson
                    .contains("ADD_VOICEMAIL") || permisson.contains("USE_SIP") || permisson
                    .contains("PROCESS_OUTGOING_CALLS")) {
                message.append("电话 ");

            } else if (permisson.contains("BODY_SENSORS")) {
                message.append("身体传感 ");

            } else if (permisson.contains("SMS") || permisson.contains("RECEIVE_WAP_PUSH")
                    || permisson.contains("RECEIVE_MMS") || permisson
                    .contains("READ_CELL_BROADCASTS")) {
                message.append("短信 ");

            } else if (permisson.contains("STORAGE")) {
                message.append("手机存储 ");

            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                permisson = list.get(i);
                if (permisson.contains("CALENDAR") && !hasCALENDAR) {
                    message.append("日历");
                    hasCALENDAR = true;
                } else if (permisson.contains("CAMERA") && !hasCAMERA) {
                    message.append("相机");
                    hasCAMERA = true;
                } else if (permisson.contains("CONTACTS")
                        || permisson.equals("android.permission.GET_ACCOUNTS")
                        && hasCONTACTS == false) {
                    if (message.indexOf("通讯录") == -1) {
                        message.append("通讯录");
                    }
                    hasCONTACTS = true;
                } else if (permisson.contains("LOCATION") && !hasLOCATION) {
                    message.append("定位");
                    hasLOCATION = true;
                } else if (permisson.equals("android.permission.RECORD_AUDIO") && !hasMICROPHONE) {
                    message.append("耳麦");
                    hasMICROPHONE = true;
                } else if (permisson.contains("PHONE") || permisson.contains("CALL_LOG")
                        || permisson.contains("ADD_VOICEMAIL") || permisson.contains("USE_SIP")
                        || permisson.contains("PROCESS_OUTGOING_CALLS") && !hasPHONE) {
                    if (message.indexOf("电话") == -1) {
                        message.append("电话");
                    }
                    hasPHONE = true;
                } else if (permisson.contains("BODY_SENSORS") && !hasSENSORS) {
                    message.append("身体传感");
                    hasSENSORS = true;
                } else if (permisson.contains("SMS") || permisson.contains("RECEIVE_WAP_PUSH")
                        || permisson.contains("RECEIVE_MMS")
                        || permisson.contains("READ_CELL_BROADCASTS") && !hasSMS) {
                    if (message.indexOf("短信") == -1) {
                        message.append("短信");
                    }
                    hasSMS = true;
                } else if (permisson.contains("STORAGE") && !hasSTORAGE) {
                    if (message.indexOf("手机存储") == -1) {
                        message.append("手机存储");
                    }
                    hasSTORAGE = true;
                }
                if (i < list.size() - 1) {
                    message.append(" ");
                }
            }
        }

        message.append("访问权限，为了保证功能的正常使用，请允许此功能");
        return message;
    }

}
