/**
 * Summary: js脚本所能执行的函数空间
 * Version 1.0
 * Date: 13-11-20
 * Time: 下午4:40
 * Copyright: Copyright (c) 2013
 */

package com.mvvm.lux.framework.manager.hybrid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.mvvm.lux.framework.utils.AESOperator;
import com.mvvm.lux.framework.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//HostJsScope中需要被JS调用的函数，必须定义成public static，且必须包含WebView这个参数
public class HostJsScope {

    private static String key = "paszsiappaszsiap";
    private static String srcData = "{'thirdPartyUserId':'333222222','name':'张三','idType':'1','idNo':'231181199011073781','sex':'F','birthDay':'1988-10-10','mobile':'13838637689','accessCode':'AeliwpeTdke'}";

    /**
     * 短暂气泡提醒
     *
     * @param webView 浏览器
     * @param message 提示信息
     */
    public static void toast(WebView webView, String message) {
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 可选择时间长短的气泡提醒
     *
     * @param webView    浏览器
     * @param message    提示信息
     * @param isShowLong 提醒时间方式
     */
    public static void toast(WebView webView, String message, int isShowLong) {
        Toast.makeText(webView.getContext(), message, isShowLong).show();
    }

    /**
     * 弹出记录的测试JS层到Java层代码执行损耗时间差
     *
     * @param webView   浏览器
     * @param timeStamp js层执行时的时间戳
     */
    public static void testLossTime(WebView webView, long timeStamp) {
        timeStamp = System.currentTimeMillis() - timeStamp;
        alert(webView, String.valueOf(timeStamp));
    }

    /**
     * 系统弹出提示框
     *
     * @param webView 浏览器
     * @param message 提示信息
     */
    public static void alert(WebView webView, String message) {
        // 构建一个Builder来显示网页中的alert对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(webView.getContext());
        builder.setTitle("系统消息");
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    public static void alert(WebView webView, int msg) {
        alert(webView, String.valueOf(msg));
    }

    public static void alert(WebView webView, boolean msg) {
        alert(webView, String.valueOf(msg));
    }

    /**
     * 获取设备IMSI
     *
     * @param webView 浏览器
     * @return 设备IMSI
     */
    public static String getIMSI(WebView webView) {
        return ((TelephonyManager) webView.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    /**
     * 获取用户系统版本大小
     *
     * @param webView 浏览器
     * @return 安卓SDK版本
     */
    public static int getOsSdk(WebView webView) {
        return Build.VERSION.SDK_INT;
    }

    //---------------- 界面切换类 ------------------

    /**
     * 结束当前窗口
     *
     * @param view 浏览器
     */
    public static void goBack(WebView view) {
        if (view.getContext() instanceof Activity) {
            ((Activity) view.getContext()).finish();
        }
    }

    /**
     * 传入Json对象
     *
     * @param view 浏览器
     * @param jo   传入的JSON对象
     * @return 返回对象的第一个键值对
     */
    public static String passJson2Java(WebView view, JSONObject jo) {
        Iterator iterator = jo.keys();
        String res = null;
        if (iterator.hasNext()) {
            try {
                String keyW = (String) iterator.next();
                res = keyW + ": " + jo.getString(keyW);
            } catch (JSONException je) {

            }
        }
        return res;
    }

    /**
     * 将传入Json对象直接返回
     *
     * @param view 浏览器
     * @param jo   传入的JSON对象
     * @return 返回对象的第一个键值对
     */
    public static JSONObject retBackPassJson(WebView view, JSONObject jo) {
        return jo;
    }

    public static int overloadMethod(WebView view, int val) {
        return val;
    }

    public static String overloadMethod(WebView view, String val) {
        return val;
    }

    public static class RetJavaObj {
        public int intField;
        public String strField;
        public boolean boolField;
    }

    public static List<RetJavaObj> retJavaObject(WebView view) {
        RetJavaObj obj = new RetJavaObj();
        obj.intField = 1;
        obj.strField = "mine str";
        obj.boolField = true;
        List<RetJavaObj> rets = new ArrayList<RetJavaObj>();
        rets.add(obj);
        return rets;
    }

    public static void delayJsCallBack(WebView view, int ms, final String backMsg, final JsCallback jsCallback) {
        TaskExecutor.scheduleTaskOnUiThread(ms * 1000, new Runnable() {
            @Override
            public void run() {
                try {
                    jsCallback.apply(backMsg);
                } catch (JsCallback.JsCallbackException je) {
                    je.printStackTrace();
                }
            }
        });
    }

    public static long passLongType(WebView view, long i) {
        return i;
    }

    public static void imageClick(WebView view, String imgUrl, String position) {
        ArrayList<String> urls = new ArrayList<>();
        urls.add(imgUrl);
        ImagePicsListActivity.entryGallery(view.getContext(), urls, Integer.parseInt(position));
    }

    /**
     * 传递的图片集合
     * @param view
     * @param imgUrl
     * @param position
     */
    public static void imageClick(WebView view, String imgUrl, int position) {
        ArrayList<String> urls = new ArrayList<>();
        String[] split = imgUrl.split(",");
        for (String s : split) {
            urls.add(s);
        }
        ImagePicsListActivity.entryGallery(view.getContext(), urls, position);
    }

    /**
     * 传递单张图片
     * @param view
     * @param imgUrl
     */
    public static void imageClick(WebView view, String imgUrl) {
        if (!imgUrl.contains("http")) return;
        ArrayList<String> urls = new ArrayList<>();
        urls.add(imgUrl);
        ImagePicsListActivity.entryGallery(view.getContext(), urls, 0);
    }

    /* ----------------------   可以实现交互了,必须多一个webView参数,必须是用public static   ------------------------- */

    /**
     * 获取登录的个人信息
     */
    public static String getPreBindUserInfo(WebView webView) {
        String encrypt = null;
        try {
            encrypt = AESOperator.getInstance().encrypt(srcData, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypt;
    }

    /**
     * 当结束登录时调用
     *
     * @param suumUserId
     */
    public static void finishLogin(WebView webView, String suumUserId) {
        Logger.e(suumUserId);
    }

    public static String getToken(WebView webView) {
        return "4c8d1c74333d42cba55fa2774387d35e";
    }

    public static String getBusinessId(WebView webView) {
        return "userId" + "suumId";
    }
}