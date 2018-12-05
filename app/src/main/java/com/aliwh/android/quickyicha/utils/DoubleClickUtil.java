package com.aliwh.android.quickyicha.utils;


/**
 * 防止多次快速点击
 * @author Kevin
 * @包名  com.cmsz.android.baymax.util
 * @时间  2015-12-10下午4:08:27
 */
public class DoubleClickUtil {
	private static long lastClickTime;

	// 避免按钮重复点击
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		lastClickTime = time;
		if (timeD < 1000) { // 1秒中之内只允许点击1次
			return true;
		}
		return false;
	}
}
