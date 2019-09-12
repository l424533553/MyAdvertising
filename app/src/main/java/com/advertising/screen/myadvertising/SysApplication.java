package com.advertising.screen.myadvertising;

import android.annotation.SuppressLint;
import android.content.Context;
import com.advertising.screen.myadvertising.my.helper.DesBCBHelper;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xuanyuan.library.base.application.BaseVolleyApplication;
import com.xuanyuan.library.utils.log.MyLog;


/**
 * Created by Longer on 2016/10/26.
 */
public class SysApplication extends BaseVolleyApplication {

    @SuppressLint("StaticFieldLeak")
    private static SysApplication sInstance;
    public static Context getInstance() {
        return sInstance;
    }

    public DesBCBHelper getDesBCBHelper() {
        return DesBCBHelper.getmInstants();
    }

    @Override
    protected boolean isOpenCrashHandler() {
        return false;
    }

    @Override
    protected boolean isDebugMode() {
        return false;
    }

    @Override
    protected boolean isOpenBugly() {
        return false;
    }

    @Override
    protected boolean isOpenVolley() {
        return true;
    }

    @Override
    protected boolean isOpenRetryPolicy() {
        return true;
    }

    @Override
    public void onCreate() {// 程序的入口方法
        super.onCreate();
        MyLog.logTest("测试   onCreate  Application ");
        sInstance = this;
        LiveEventBus.get().config().supportBroadcast(this).lifecycleObserverAlwaysActive(true);
    }
}
