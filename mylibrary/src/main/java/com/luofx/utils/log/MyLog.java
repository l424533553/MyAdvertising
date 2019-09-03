package com.luofx.utils.log;

import android.util.Log;

/**
 * Created by User_luo on 2018/3/28.
 */

public class MyLog {

    public static final boolean DEBUG = true;

    public static void e(String tag, String message) {
        Log.e(tag, message);
    }

    public static void mylog(String msg) {
        logTest(msg);

//        Log.i("123456", msg);
    }

    public static void myInfo(String msg) {
        logTest(msg);

//        Log.i("123456", msg);
    }

    public static void logTest(String msg) {
        Log.i("111111", msg);
    }


    public static void bluelog(String msg) {
        Log.i("666666", msg);
    }

    public static void blue(String msg) {
        Log.i("7777777", msg);
    }
}
