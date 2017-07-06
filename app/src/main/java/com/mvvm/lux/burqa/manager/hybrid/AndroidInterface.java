package com.mvvm.lux.burqa.manager.hybrid;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.just.library.AgentWeb;
import com.mvvm.lux.framework.utils.SnackbarUtil;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 06/07/2017 4:49 PM
 * @Version
 */
public class AndroidInterface {
    private AgentWeb agent;
    private Context context;

    public AndroidInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;
    }

    private Handler deliver = new Handler(Looper.getMainLooper());

    @JavascriptInterface
    public void callAndroid(final String msg) {

        deliver.post(new Runnable() {
            @Override
            public void run() {
                Log.i("Info", "main Thread:" + Thread.currentThread());
                Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @JavascriptInterface
    public String passJs() {
        return "传给js的值";
    }

    /**
     * @param message 提示信息
     */
    @JavascriptInterface
    public void alert(String message) {
        // 构建一个Builder来显示网页中的alert对话框
        SnackbarUtil.showMessage(message);
    }

    @JavascriptInterface
    public void alert(int msg) {
        alert(String.valueOf(msg));
    }

    @JavascriptInterface
    public void alert(boolean msg) {
        alert(String.valueOf(msg));
    }
}
