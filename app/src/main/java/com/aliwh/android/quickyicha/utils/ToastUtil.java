package com.aliwh.android.quickyicha.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast帮助类
 * 
 * @author Kevin com.cmsz.android.baymax.util 2015-10-29下午2:03:22
 */
public class ToastUtil {
	private static Toast mToast = null;

	public static void showToast(Context context, String msg) {
		// if (mToast == null) {
		// mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		// } else {
		// mToast.setText(msg);
		// }
		// mToast.show();
		if (mToast != null) {
			mToast.setText(msg);
		} else {
			if (context == null) {
				return;
			}
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		mToast.show();

	}
}
