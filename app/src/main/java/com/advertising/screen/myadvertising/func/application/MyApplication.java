package com.advertising.screen.myadvertising.func.application;

import com.advertising.screen.myadvertising.func.retrofit.IRetrofitAPI;
import com.advertising.screen.myadvertising.func.retrofit.RetrofitHttpUtils;

/**
 * 作者：罗发新
 * 时间：2019/12/6 0006    星期五
 * 邮件：424533553@qq.com
 * 说明： 当前应用的Application
 */
public class MyApplication extends SysApplication {

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
    public void onCreate() {
        super.onCreate();
        RetrofitHttpUtils retrofitHttpUtils = new RetrofitHttpUtils();
        retrofitHttpUtils.init(IRetrofitAPI.BASE_IP_WEB, this);

    }
}
