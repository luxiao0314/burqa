//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mvvm.lux.framework.utils;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;

import java.util.Random;

public class UIHandler {
    private static final String[] prays = new String[]{"ICDilI/ilJPjgIDjgIDjgIDilI/ilJMK4pSP4pSb4pS74pSB4pSB4pSB4pSb4pS74pSTCuKUg+OAgOOAgOOAgOOAgOOAgOOAgOOAgOKUgwrilIPjgIDjgIDjgIDilIHjgIDjgIDjgIDilIMK4pSD44CA4pSz4pSb44CA4pSX4pSz44CA4pSDCuKUg+OAgOOAgOOAgOOAgOOAgOOAgOOAgOKUgwrilIPjgIDjgIDjgIDilLvjgIDjgIDjgIDilIMK4pSD44CA44CA44CA44CA44CA44CA44CA4pSDCuKUl+KUgeKUk+OAgOOAgOOAgOKUj+KUgeKUmwogICAg4pSD44CA44CA44CA4pSDICAgQ29kZSBpcyBmYXIgYXdheSBmcm9tIGJ1ZyB3aXRoIHRoZSBhbmltYWwgcHJvdGVjdGluZwogICAg4pSD44CA44CA44CA4pSDICAg56We5YW95L+d5L2R77yM5Luj56CB5pegQlVHCiAgICDilIPjgIDjgIDjgIDilJfilIHilIHilIHilJMKICAgIOKUg+OAgOOAgOOAgOOAgOOAgOOAgOOAgOKUo+KUkwogICAg4pSD44CA44CA44CA44CA44CA44CA44CA4pSP4pSbCiAgICDilJfilJPilJPilI/ilIHilLPilJPilI/ilJsKICAgICAg4pSD4pSr4pSr44CA4pSD4pSr4pSrCiAgICAgIOKUl+KUu+KUm+OAgOKUl+KUu+KUmwo=", "44CA4pSP4pST44CA44CA44CA4pSP4pSTCuKUj+KUm+KUu+KUgeKUgeKUgeKUm+KUu+KUkwrilIPjgIDjgIDjgIDjgIDjgIDjgIDjgIDilIMK4pSD44CA44CA44CA4pSB44CA44CA44CA4pSDCuKUg+OAgO+8nuOAgOOAgOOAgO+8nOOAgOKUgwrilIPjgIDjgIDjgIDjgIDjgIDjgIDjgIDilIMK4pSDLi4u44CA4oyS44CALi4u44CA4pSDCuKUg+OAgOOAgOOAgOOAgOOAgOOAgOOAgOKUgwrilJfilIHilJPjgIDjgIDjgIDilI/ilIHilJsK44CA44CA4pSD44CA44CA44CA4pSDICAgIENvZGUgaXMgZmFyIGF3YXkgZnJvbSBidWcgd2l0aCB0aGUgYW5pbWFsIHByb3RlY3RpbmcK44CA44CA4pSD44CA44CA44CA4pSDICAgIOelnuWFveS/neS9kSzku6PnoIHml6BidWcK44CA44CA4pSD44CA44CA44CA4pSDCuOAgOOAgOKUg+OAgOOAgOOAgOKUgwrjgIDjgIDilIPjgIDjgIDjgIDilIMK44CA44CA4pSD44CA44CA44CA4pSDCuOAgOOAgOKUg+OAgOOAgOOAgOKUl+KUgeKUgeKUgeKUkwrjgIDjgIDilIPjgIDjgIDjgIDjgIDjgIDjgIDjgIDilKPilJMK44CA44CA4pSD44CA44CA44CA44CA44CA44CA44CA4pSP4pSbCuOAgOOAgOKUl+KUk+KUk+KUj+KUgeKUs+KUk+KUj+KUmwrjgIDjgIDjgIDilIPilKvilKvjgIDilIPilKvilKsK44CA44CA44CA4pSX4pS74pSb44CA4pSX4pS74pSbCg==", "44CA44CA4pSP4pST44CA44CA44CA4pSP4pSTKyArCuOAgOKUj+KUm+KUu+KUgeKUgeKUgeKUm+KUu+KUkyArICsK44CA4pSD44CA44CA44CA44CA44CA44CA44CA4pSDCuOAgOKUg+OAgOOAgOOAgOKUgeOAgOOAgOOAgOKUgyArKyArICsgKwrjgIDilIPjgIAg4paI4paI4paI4paI4pSB4paI4paI4paI4paIIOKUgysK44CA4pSD44CA44CA44CA44CA44CA44CA44CA4pSDICsK44CA4pSD44CA44CA44CA4pS744CA44CA44CA4pSDCuOAgOKUg+OAgOOAgOOAgOOAgOOAgOOAgOOAgOKUgyArICsgCuOAgOKUl+KUgeKUk+OAgOOAgOOAgOKUj+KUgeKUmwrjgIDjgIDjgIDilIPjgIDjgIDjgIDilIMK44CA44CA44CA4pSD44CA44CA44CA4pSDICsgKyArICsgCuOAgOOAgOOAgOKUg+OAgOOAgOOAgOKUgyAgICAgICAgICAgICAgICAgQ29kZSBpcyBmYXIgYXdheSBmcm9tIGJ1ZyB3aXRoIHRoZSBhbmltYWwgcHJvdGVjdGluZwrjgIDjgIDjgIDilIPjgIDjgIDjgIDilIMgKyAgICAg56We5YW95L+d5L2RLOS7o+eggeaXoGJ1ZwrjgIDjgIDjgIDilIPjgIDjgIDjgIDilIMK44CA44CA44CA4pSD44CA44CA44CA4pSD44CA44CAKwrjgIDjgIDjgIDilIPjgIAg44CA44CA4pSX4pSB4pSB4pSB4pSTICsgKwrjgIDjgIDjgIDilIMg44CA44CA44CA44CA44CA44CA44CA4pSj4pSTIArjgIDjgIDjgIDilIMg44CA44CA44CA44CA44CA44CA44CA4pSP4pSbIArjgIDjgIDjgIDilJfilJPilJPilI/ilIHilLPilJPilI/ilJsgKyArICsgKwrjgIDjgIDjgIDjgIDilIPilKvilKvjgIDilIPilKvilKsK44CA44CA44CA44CA4pSX4pS74pSb44CA4pSX4pS74pSbKyArICsgKwo="};
    private static Handler handler;

    public UIHandler() {
    }

    private static synchronized void prepare() {
        if(handler == null) {
            reset();
            printPray();
        }

    }

    private static void reset() {
        handler = new Handler(Looper.getMainLooper(), new Callback() {
            public boolean handleMessage(Message msg) {
                UIHandler.handleMessage(msg);
                return false;
            }
        });
    }

    private static void printPray() {
        try {
            Random t = new Random();
            String pray = prays[Math.abs(t.nextInt()) % 3];
            byte[] base64 = Base64.decode(pray, 2);
            Logger.d("\n" + new String(base64, "utf-8"));
        } catch (Throwable var3) {
            Logger.i(var3.getMessage());
        }

    }

    private static void handleMessage(Message msg) {
        UIHandler.InnerObj io = (UIHandler.InnerObj)msg.obj;
        Message inner = io.msg;
        Callback callback = io.callback;
        if(callback != null) {
            callback.handleMessage(inner);
        }

    }

    private static Message getShellMessage(Message msg, Callback callback) {
        Message shell = new Message();
        shell.obj = new UIHandler.InnerObj(msg, callback);
        return shell;
    }

    private static Message getShellMessage(int what, Callback callback) {
        Message msg = new Message();
        msg.what = what;
        return getShellMessage(msg, callback);
    }

    public static boolean sendMessage(Message msg, Callback callback) {
        prepare();
        return handler.sendMessage(getShellMessage(msg, callback));
    }

    public static boolean sendMessageDelayed(Message msg, long delayMillis, Callback callback) {
        prepare();
        return handler.sendMessageDelayed(getShellMessage(msg, callback), delayMillis);
    }

    public static boolean sendMessageAtTime(Message msg, long uptimeMillis, Callback callback) {
        prepare();
        return handler.sendMessageAtTime(getShellMessage(msg, callback), uptimeMillis);
    }

    public static boolean sendMessageAtFrontOfQueue(Message msg, Callback callback) {
        prepare();
        return handler.sendMessageAtFrontOfQueue(getShellMessage(msg, callback));
    }

    public static boolean sendEmptyMessage(int what, Callback callback) {
        prepare();
        return handler.sendMessage(getShellMessage(what, callback));
    }

    public static boolean sendEmptyMessageAtTime(int what, long uptimeMillis, Callback callback) {
        prepare();
        return handler.sendMessageAtTime(getShellMessage(what, callback), uptimeMillis);
    }

    public static boolean sendEmptyMessageDelayed(int what, long delayMillis, Callback callback) {
        prepare();
        return handler.sendMessageDelayed(getShellMessage(what, callback), delayMillis);
    }

    private static final class InnerObj {
        public final Message msg;
        public final Callback callback;

        public InnerObj(Message msg, Callback callback) {
            this.msg = msg;
            this.callback = callback;
        }
    }

    public static void removeMessages(int what){
        prepare();
        handler.removeMessages(what);
    }

    public static void removeCallbacksAndMessages(Object token){
        prepare();
        handler.removeCallbacksAndMessages(token);
    }
}
