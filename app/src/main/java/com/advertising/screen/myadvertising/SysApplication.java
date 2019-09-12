package com.advertising.screen.myadvertising;

import android.annotation.SuppressLint;
import com.xuanyuan.library.help.security.DesBCBHelper;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xuanyuan.library.base.application.BaseVolleyApplication;
import com.xuanyuan.library.utils.log.MyLog;


/**
 * Created by Longer on 2016/10/26.
 */
@SuppressLint("Registered")
public class SysApplication extends BaseVolleyApplication {
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

        LiveEventBus.get().config().supportBroadcast(this).lifecycleObserverAlwaysActive(true);
    }
}
