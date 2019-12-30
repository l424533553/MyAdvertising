package com.advertising.screen.myadvertising.func.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.xuanyuan.jarlibrary.R;
import com.xuanyuan.library.apk_update.download.NotificationHelpter;
import com.xuanyuan.library.listener.DownloadCallBack;
import com.xuanyuan.library.net.retrofit.RetrofitHttp;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 创建时间：2019/9/12
 * 编写人：damon
 * 功能描述 ： 下载IntentService
 */
public class DownloadApkService extends IntentService implements DownloadCallBack {

    private static final String TAG = "DownloadApkService";
    private NotificationManager mNotifyManager;
    private Notification mNotification; //消息通知栏

    public DownloadApkService() {
        super("InitializeService");
    }

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private int downloadId = 1;
    private RemoteViews remoteViews;
    private File file;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        startDown(intent);
    }

    private void startDown(Intent intent) {
        String downloadUrl = intent.getStringExtra("download_url");
        String mDownloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1);

        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mDownloadFileName);

        final long range = 0;
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

        //
        remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");

        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationHelpter notificationHelpter = new NotificationHelpter(this);
        mNotification = notificationHelpter.getNotification(mNotifyManager, remoteViews, downloadId);

        RetrofitHttp.getInstance().downloadFile(range, downloadUrl, file, DownloadApkService.this);

    }


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
        if (file == null) {
            return;
        }

        installApp(file);

//        onSmartInstall(file);
//        insallApk(context);
    }

    /**
     * 无障碍 安装法
     */
    public void onSmartInstall(File file) {
        String apkPath = file.getPath();
        if (TextUtils.isEmpty(apkPath)) {
            Toast.makeText(this, "请选择安装包！", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }

    private void insallApk(Context context) {
//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "pp.apk";
//        File apkFile = new File(path);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(apkFile);
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
    }

    public static String execCommand(File file) {
        String apkPath = file.getPath();
        Process process;
        InputStream errIs;
        InputStream inIs;
        String result;

        try {
            process = new ProcessBuilder().command("pm", "install", "-f", apkPath).start();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }

            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            result = new String(baos.toByteArray());

            inIs.close();
            errIs.close();
            process.destroy();
        } catch (IOException e) {
            result = e.getMessage();
        }

        return result;
    }


    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     * 要安装的apk文件的路径
     *
     * @return 安装成功返回true，安装失败返回false。
     */
    public boolean install(File file) {
        String apkPath = file.getPath();
//        String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "pp.apk";
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder msg = new StringBuilder();
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg.append(line);
            }
            Log.d("TAG", "install msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.toString().contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 该方法测试过可靠可行
     *
     * @return
     */
    private boolean installApp(File file) {
        String apkPath = file.getPath();
        String packageName = getPackageName();
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        try {
            process = new ProcessBuilder("pm", "install", "-i", packageName, "-r", apkPath).start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (Exception e) {

        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {

            }
            if (process != null) {
                process.destroy();
            }
        }
        Log.e("result", "" + errorMsg.toString());
        //如果含有“success”认为安装成功
        return successMsg.toString().equalsIgnoreCase("success");
    }


    @Override
    public void onError(String msg) {
        mNotifyManager.cancel(downloadId);
        Log.d(TAG, "下载发生错误--" + msg);
    }

}
//	pkg: /storage/emulated/0/Download/20191121135808666.apk
//            Failure [INSTALL_FAILED_UPDATE_INCOMPATIBLE]
