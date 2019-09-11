package com.advertising;

import android.annotation.SuppressLint;
import android.content.Context;
import com.axecom.smartweight.my.helper.DesBCBHelper;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.luofx.base.MyBaseApplication;
import com.luofx.utils.log.MyLog;


/**
 * Created by Longer on 2016/10/26.
 */
public class SysApplication extends MyBaseApplication {

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
        MyLog.logTest("测试   onCreate  Application ");
        sInstance = this;
        LiveEventBus.get().config().supportBroadcast(this).lifecycleObserverAlwaysActive(true);
    }
}
