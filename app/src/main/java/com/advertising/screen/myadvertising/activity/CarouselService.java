//package com.advertising.screen.myadvertising;
//
//import android.annotation.SuppressLint;
//import android.app.Notification;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.graphics.BitmapFactory;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import com.advertising.SysApplication;
//import com.advertising.screen.myadvertising.entity.AdUserBean;
//import com.advertising.screen.myadvertising.entity.AdUserInfo;
//import com.advertising.screen.myadvertising.entity.UserInfoDao;
//import com.alibaba.fastjson.JSON;
//import com.android.volley.VolleyError;
//import com.axecom.smartweight.carouselservice.entity.*;
//import com.axecom.smartweight.my.IConstants;
//import com.luofx.listener.VolleyStringListener;
//import com.luofx.other.tid.UserInfo;
//import com.luofx.utils.DateUtils;
//import com.luofx.utils.MyPreferenceUtils;
//import com.luofx.utils.file.FileUtils;
//import com.luofx.utils.log.MyLog;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 双屏 副屏显示 功能
// * Created by Administrator on 2018/7/25.
// */
//
//public class CarouselService extends Service implements IConstants, VolleyStringListener {
//    //    private ScreenActivity banner = null;
//    private NetworkChangeReceiver networkChangeReceiver;
//    private SysApplication sysApplication;
//    private Context context;
//
//    private ImageDao imageDao;
//    private UserDao userDao;
//    private UserInfoDao userInfoDao;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        //动态接受网络变化的广播接收器
//
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.axecom.iweight.carouselservice.datachange");
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);
//
//        context = this;
//        sysApplication = (SysApplication) getApplication();
//
//        imageDao = new ImageDao(context);
//        userDao = new UserDao(context);
//        userInfoDao = new UserInfoDao(context);
//        questImage();
//
//        //TODO 5分鐘訪問一次  是否有圖片更新
//
//    }
//
//
//    @Override
//    public void onResponseError(VolleyError volleyError, int flag) {
//        MyLog.blue("请求错误");
//    }
//
//    @Override
//    public void onResponse(String response, int flag) {
//        new ImageDownThread(response).start();
//    }
//
//    //为了下载图片资源，开辟一个新的子线程
//    private class ImageDownThread extends Thread {
//        private String response;
//
//        ImageDownThread(String response) {
//            this.response = response;
//        }
//
//        public void run() {
//            AdUserInfo netImageInfo = JSON.parseObject(response, AdUserInfo.class);
//            if (netImageInfo != null) {
//                if (netImageInfo.getStatus() == 0) {
//                    AdUserBean adUserBean = netImageInfo.getData();
//                    if (adUserBean != null) {
//                        String screenImageState = MyPreferenceUtils.getSp(context).getString(IMAGE_STATE, "default");
//                        assert screenImageState != null;
//                        if (!screenImageState.equals(adUserBean.getStatus())) {
//                            downImage(adUserBean);
//                            MyPreferenceUtils.getSp(context).edit().putString(IMAGE_STATE, screenImageState).apply();
//                        }
//                    }
//                }
//            }
//        }
//
//        private void downImage(AdUserBean adUserBean) {
//            String dir = FileUtils.getDownloadDir(context, FileUtils.DOWNLOAD_DIR);
//            //下载图片的路径
//            FileOutputStream fileOutputStream = null;
//            InputStream inputStream = null;
//            try {
//                String baseUrl = adUserBean.getBaseurl();
//                List<AdImageInfo> imageInfos = new ArrayList<>();
//                String prefix = DateUtils.getSampleNo();
//
//
//                //2018.12.18 取消证照图片的下载
//                String licences = adUserBean.getImg1();
//                boolean issuccess = FileUtils.deleteDir(dir);
//                if (licences != null) {
//                    String[] adArray = licences.split(";");
//                    if (adArray.length > 0) {
//                        for (int i = 0; i < adArray.length; i++) {
//                            String netUrl = baseUrl + adArray[i];
//                            URL url = new URL(netUrl);
//                            //再一次打开
//                            inputStream = url.openStream();
//                            String localPath = dir + prefix + "licence" + i + ".png";
//                            File file = new File(localPath);
//                            fileOutputStream = new FileOutputStream(file);
//                            int hasRead;
//                            while ((hasRead = inputStream.read()) != -1) {
//                                fileOutputStream.write(hasRead);
//                            }
//                            AdImageInfo AdImageInfo = new AdImageInfo();
//                            AdImageInfo.setNetPath(netUrl);
//                            AdImageInfo.setLocalPath(localPath);
//                            AdImageInfo.setType(2);
//                            imageInfos.add(AdImageInfo);
//                        }
//                    }
//                }
//
//                String ads = adUserBean.getImg2();
//                if (ads != null) {
//                    String[] adArray = ads.split(";");
//                    if (adArray.length > 0) {
//                        for (int i = 0; i < adArray.length; i++) {
//                            String netUrl = baseUrl + adArray[i];
//                            URL url = new URL(netUrl);
//                            //再一次打开
//                            inputStream = url.openStream();
//                            String localPath = dir + prefix + "ad" + i + ".png";
//                            File file = new File(localPath);
//                            fileOutputStream = new FileOutputStream(file);
//                            int hasRead;
//                            while ((hasRead = inputStream.read()) != -1) {
//                                fileOutputStream.write(hasRead);
//                            }
//                            AdImageInfo AdImageInfo = new AdImageInfo();
//                            AdImageInfo.setNetPath(netUrl);
//                            AdImageInfo.setLocalPath(localPath);
//                            AdImageInfo.setType(1);
//                            imageInfos.add(AdImageInfo);
//                        }
//                    }
//                }
//
//                String photo = adUserBean.getHeadimg();
//                if (photo != null) {
//                    String netUrl = baseUrl + photo;
//                    URL url = new URL(netUrl);
//                    //再一次打开
//                    inputStream = url.openStream();
//                    String localPath = dir + prefix + "photo.jpg";
//                    File file = new File(localPath);
//                    fileOutputStream = new FileOutputStream(file);
//                    int hasRead;
//                    while ((hasRead = inputStream.read()) != -1) {
//                        fileOutputStream.write(hasRead);
//                    }
//                    AdImageInfo AdImageInfo = new AdImageInfo();
//                    AdImageInfo.setNetPath(netUrl);
//                    AdImageInfo.setLocalPath(localPath);
//                    AdImageInfo.setType(0);
//                    imageInfos.add(AdImageInfo);
//                }
//
//                imageDao.deleteAll();
//                imageDao.inserts(imageInfos);
//
//                userDao.deleteAll();
//                adUserBean.setId(1);
//                userDao.insert(adUserBean);
//                Intent intent = new Intent();
//                intent.setAction(BANNER_NOTIFY);
//                sendBroadcast(intent);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (fileOutputStream != null) {
//                        fileOutputStream.close();
//                    }
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    //自定义接受网络变化的广播接收器
//    class NetworkChangeReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            questImage();//进行广播更新
//        }
//    }
//
//    /**
//     * 请求更换图片
//     */
//    private void questImage() {
////        SharedPreferences share = MyPreferenceUtils.getSp(context);
//
//        UserInfo userInfo = userInfoDao.queryById(1);
////        UserInfo userInfo = sysApplication.getUserInfo();
//
//        if (userInfo == null) {
//            return;
//        }
//
//        int shellerid = userInfo.getSellerid();
//        if (shellerid == 0) {
//            return;
//        }
//        if (shellerid > 0) {
//            String url = "http://119.23.43.64/api/smartsz/getadinfo?companyid=";
//            String path = url + shellerid;
////                    String path = url + 1017;
//            sysApplication.volleyStringGet(path, this, 1);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
////        banner.cancel();
////        banner = null;
//        super.onDestroy();
//        restartService();
//        unregisterReceiver(networkChangeReceiver);
//    }
//
//    @Override
//    public boolean stopService(Intent name) {
//        return super.stopService(name);
//    }
//
//    /**
//     * 当轮播 服务关闭之后，自动重启服务
//     */
//    public void restartService() {
//        Intent intent = new Intent(ACTION_START);
//        sendBroadcast(intent);
//    }
//
//    //    START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。
////    随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。
////    如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。
////    START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务。
////    START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，
////    系统会自动重启该服务，并将Intent的值传入。
////    START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
//    @SuppressLint("WrongConstant")
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        flags = START_REDELIVER_INTENT;  //这句话将服务变成了 前台服务（四种方式） ，需要在销毁时一定调用   stopForeground(true);
//        setForeground(intent);
////        startBanner();
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//
//    /**
//     * 设置成后台  服务
//     *
//     * @param intent 返回意图
//     */
//    private void setForeground(Intent intent) {
//        if (intent != null) {
//            Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
//            // 设置PendingIntent
//            builder.setContentIntent(PendingIntent.getService(this, 0, intent, 0)).
//                    setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标).setContentTitle("下拉列表中的Title")// 设置下拉列表里的标题
//                    .setSmallIcon(R.mipmap.ic_launcher)// 设置状态栏内的小图标
////                    .setContentText("要显示的内容")// 设置上下文内容
//                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(pendingIntent);
////            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            Notification notification = builder.build(); // 获取构建好的Notification
//            notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
//
//            //让该service前台运行，避免手机休眠时系统自动杀掉该服务
//            //如果 id 为 0 ，那么状态栏的 notification 将不会显示。
//            //对于通过startForeground启动的service，onDestory方法中需要通过stopForeground(true)来取消前台运行状态
//            startForeground(0, notification);
//        }
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}