package com.advertising;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import com.axecom.smartweight.my.helper.DesBCBHelper;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.luofx.base.MyBaseApplication;
import com.luofx.other.tid.UserInfo;


/**
 * Created by Longer on 2016/10/26.
 */
public class SysApplication extends MyBaseApplication {
    private static Handler mHandler;

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


    public static Handler getHandler() {
        return mHandler;
    }

    public static String uuid = "1234567890";

    @SuppressLint("StaticFieldLeak")
    private static SysApplication sInstance;

    public static Context getInstance() {
        return sInstance;
    }
    public DesBCBHelper getDesBCBHelper() {
        return DesBCBHelper.getmInstants();
    }


    @Override
    public void onCreate() {// 程序的入口方法
        super.onCreate();
        // 2.主线程的Handler
        mHandler = new Handler();
        sInstance = this;

        LiveEventBus.get().config().supportBroadcast(this).lifecycleObserverAlwaysActive(true);
//        Intent intentService = new Intent(getApplicationContext(), CarouselService.class);
//        startService(intentService);
//        modularUnit();
    }


    /**
     * 初始化模块单元
     */
    private void modularUnit() {

    }


}
