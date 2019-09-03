package com.luofx.utils.common;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;

/**
 * 说明：
 * 作者：User_luo on 2018/5/18 16:48
 * 邮箱：424533553@qq.com
 */
public class MyUtils {

    public static String getIMEI(Context context) {
        // 94:a1:a2:a4:70:66
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String mac = "";
        if (wm != null) {
            mac = wm.getConnectionInfo().getMacAddress();
        }
        return mac;
    }



    // 二进制转十六进制
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }

//    /**
//     * 获取Soap_URL 地址
//     */
//    public static String getSoapIP(Context context) {
//        SharedPreferences sharedPreferences = MySharedPreference.getMySharedPreferencesUser(context);
//        String IP = sharedPreferences.getString(IP_URL, "114.119.10.233");
//        return IP;
//    }

    /**
     * 获取Soap_URL 地址
     */
    public static String getSoapIP(@NonNull String IP, int port) {
        if (IP.length() > 0) {
            return "http://" + IP + ":" + port + "/sxinterface.asmx";
        }
        return null;
    }


}
