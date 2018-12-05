package com.aliwh.android.quickyicha.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
获取当前Application版本
 * @author Brian
2015年10月27日 下午11:10:26
 */
public class VersionUtils {
	/*
	 * 获取版本号
	 * return String VersionName
	 * */
		public static String getVerionName(Context context){
			PackageManager pm = context.getPackageManager();
			try {
				PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
				return info.versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		/*
		 * 获取版内部版本
		 * return int VersionCode
		 * */
		public static int getVersionCode(Context context){
			PackageManager pm = context.getPackageManager();
			try {
				PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
				//返回Code值
				return info.versionCode;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			return -1;
		}
}
