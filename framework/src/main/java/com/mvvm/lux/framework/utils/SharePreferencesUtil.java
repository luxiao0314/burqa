package com.mvvm.lux.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mvvm.lux.framework.BaseApplication;


/**
 * Created by xubin on 2016/2/19.
 */
public class SharePreferencesUtil {
    public static final String DEFAULT_SHARE_NODE = "default_sharepreference";

    public static void saveInt(String node, String key, int value) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(node, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveInt(String key, int value) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(DEFAULT_SHARE_NODE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveBoolean(String node, String key, boolean value) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(node, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveBoolean(String key, boolean value) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(DEFAULT_SHARE_NODE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveString(String node, String key, String value) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(node, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveString(String key, String value) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(DEFAULT_SHARE_NODE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveMultiString(String[] key, String[] value) {
        if (key == null || value == null || key.length != value.length) {
            Logger.e("key和value长度不同!");
            return;
        }
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(DEFAULT_SHARE_NODE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < key.length; i++) {
            editor.putString(key[i], value[i]);
        }
        editor.commit();
    }

    public static int getInt(String node, String key, int defaultvalue) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(node, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultvalue);
    }

    public static int getInt(String key, int defaultvalue) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(DEFAULT_SHARE_NODE, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultvalue);
    }

    public static boolean getBoolean(String node, String key, boolean defaultvalue) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(node, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultvalue);
    }

    public static boolean getBoolean(String key, boolean defaultvalue) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(DEFAULT_SHARE_NODE, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultvalue);
    }

    public static String getString(String node, String key, String defaultvalue) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(node, Context.MODE_PRIVATE);
        return sp.getString(key, defaultvalue);
    }

    public static String getString(String key, String defaultvalue) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(DEFAULT_SHARE_NODE, Context.MODE_PRIVATE);
        return sp.getString(key, defaultvalue);
    }

    public static String[] getMultiString(String[] key) {
        if (key == null) {
            Logger.e("key为null!");
            return null;
        }
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(DEFAULT_SHARE_NODE, Context.MODE_PRIVATE);
        String[] values = new String[key.length];
        for (int i = 0; i < key.length; i++) {
            values[i] = sp.getString(key[i], null);
        }
        return values;

    }

}
