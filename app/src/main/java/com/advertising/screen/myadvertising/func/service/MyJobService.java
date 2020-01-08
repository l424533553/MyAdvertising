package com.advertising.screen.myadvertising.func.service;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.advertising.screen.myadvertising.common.iinterface.IEventBus;
import com.xuanyuan.library.utils.LiveBus;
import com.xuanyuan.library.utils.log.MyLog;

public class MyJobService extends JobService implements IEventBus {
    @Override
    public boolean onStartJob(JobParameters params) {
        MyLog.sysLog("MyJobService","开始了onStartJob");
        LiveBus.post(NOTIFY_LIVEBUS_KEY, String.class, NOTIFY_WHILE_DATA);
        jobFinished(params,true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
