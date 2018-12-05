package com.aliwh.android.quickyicha.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络情况工具类
 *
 * @author bigtiger
 */
public class NetworkUtil {

    /**
     * 是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isNetworkEnabled(Context context) {
        return isWifiEnabled(context) || is3GEnabled(context);
    }

    /**
     * 是否为4g网络
     *
     * @param context
     * @return
     */
    public static boolean is3GEnabled(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 是否有wifi
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
