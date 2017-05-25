package com.mvvm.lux.framework.utils;

import android.content.Context;
import android.widget.Toast;

import com.mvvm.lux.framework.BaseApplication;

/**
 * @Description 单例Toast
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/3/9 14:28
 * @Version
 */
public class ToastManager {

	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	/**
	 * 弹吐司,指定时间间隔内不能弹出重复信息.
	 * @param context
	 * @param s
     */
	public static void showMessage(Context context, String s) {
		if (toast == null) {
			toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	/**
	 * 弹吐司,指定时间间隔内不能弹出重复信息.
	 * @param s
	 */
	public static void showMessage(String s) {
		if (toast == null) {
			toast = Toast.makeText(BaseApplication.getAppContext(), s, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	public static void showMessage(String s, int type) {
		if (toast == null) {
			toast = Toast.makeText(BaseApplication.getAppContext(), s, type);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > type) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oneTime = twoTime;
	}

	public static void showMessage(int resId) {
		showMessage(BaseApplication.getAppContext(), BaseApplication.getAppContext().getString(resId));
	}

}
