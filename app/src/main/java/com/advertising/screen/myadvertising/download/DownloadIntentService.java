package com.advertising.screen.myadvertising.download;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.help.NotificationHelpter;
import com.advertising.screen.myadvertising.net.retrofit.DownloadCallBack;
import com.advertising.screen.myadvertising.net.retrofit.RetrofitHttp;
import com.luofx.utils.apk.MyApkUtils;

import java.io.File;


/**
 * 创建时间：2018/3/7
 * 编写人：damon
 * 功能描述 ：
 */
public class DownloadIntentService extends IntentService {

    private static final String TAG = "DownloadIntentService";
    private NotificationManager mNotifyManager;
    private Notification mNotification; //消息通知栏

    public DownloadIntentService() {
        super("InitializeService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        String downloadUrl = intent.getExtras().getString("download_url");
        final int downloadId = intent.getExtras().getInt("download_id");
        String mDownloadFileName = intent.getExtras().getString("download_file");

        final File file = new File(Constant_APK.APP_ROOT_PATH + Constant_APK.DOWNLOAD_DIR + mDownloadFileName);
        long range = 0;
        int progress = 0;

        //TODO  重要代码 ，
//        if (file.exists()) {// 检查文件 是否已经存在
//            range = SPDownloadUtil.getInstance().get(downloadUrl, 0);
//            progress = (int) (range * 100 / file.length());
//            if (range == file.length()) {
//                installApp(file);
//                return;
//            }
//        }



        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");

        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationHelpter notificationHelpter = new NotificationHelpter(this);
        mNotification = notificationHelpter.getNotification(mNotifyManager, remoteViews, downloadId);

        RetrofitHttp.getInstance().downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onStartDownload() {//开始下载
                // 开始准备下载
            }

            @Override
            public void onProgress(int progress) {
                remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
                remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
                mNotifyManager.notify(downloadId, mNotification);
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "下载完成");
                mNotifyManager.cancel(downloadId);
                String authority = "com.collect.user_luo.mycollect.fileprovider";
                MyApkUtils.installApp(DownloadIntentService.this, authority, file);
            }

            @Override
            public void onError(String msg) {
                mNotifyManager.cancel(downloadId);
                Log.d(TAG, "下载发生错误--" + msg);
            }
        });
    }


}
