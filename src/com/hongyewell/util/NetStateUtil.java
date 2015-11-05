package com.hongyewell.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetStateUtil {

    private NetStateUtil() {}

    /**
     * 检查设备是否已连接到网络
     * @param context
     * @return
     */
    public static boolean isAvailable(Context context) {
        ConnectivityManager con = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifiInfo.isConnectedOrConnecting() || mobileInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}


























