package com.advertising.screen.myadvertising.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.SysApplication;
import com.advertising.screen.myadvertising.entity.AdImageInfo;
import com.advertising.screen.myadvertising.entity.AdUserBean;
import com.advertising.screen.myadvertising.entity.AdUserInfo;
import com.advertising.screen.myadvertising.entity.InspectBean;
import com.advertising.screen.myadvertising.entity.PriceBean;
import com.advertising.screen.myadvertising.entity.ResultInfo;
import com.advertising.screen.myadvertising.entity.dao.AdUserDao;
import com.advertising.screen.myadvertising.entity.dao.ImageDao;
import com.advertising.screen.myadvertising.entity.dao.InspectBeanDao;
import com.advertising.screen.myadvertising.entity.dao.PriceBeanDao;
import com.advertising.screen.myadvertising.help.HttpHelper;
import com.advertising.screen.myadvertising.ui.screen.ScreenActivity;
import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.xuanyuan.library.MyToast;
import com.xuanyuan.library.listener.VolleyListener;
import com.xuanyuan.library.utils.LiveBus;
import com.xuanyuan.library.utils.log.MyLog;
import com.xuanyuan.library.utils.net.MyNetWorkUtils;
import com.xuanyuan.library.utils.storage.MyPreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.advertising.screen.myadvertising.config.IConstants.DATA_BOOTH_NUMBER;
import static com.advertising.screen.myadvertising.config.IConstants.DATA_MARK_ID;
import static com.advertising.screen.myadvertising.config.IConstants.DATA_MARK_NAME;
import static com.advertising.screen.myadvertising.config.IConstants.DEFAULT_ID;
import static com.advertising.screen.myadvertising.config.IConstants.SELLER_ID;
import static com.advertising.screen.myadvertising.config.IConstants.VOLLEY_UPDATE_IMAGE;
import static com.advertising.screen.myadvertising.config.IConstants.VOLLEY_UPDATE_INSPECT;
import static com.advertising.screen.myadvertising.config.IConstants.VOLLEY_UPDATE_PRICE;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_BASE_INFO;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_BASE_INSPECT;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_BASE_PRICE;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_LIVEBUS_KEY;


/**
 * 创建时间：2018/3/7
 * 编写人：罗发新
 * 功能描述 ：进行定时请求功能
 */
public class WorkService extends Service implements VolleyListener {
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onResponse(VolleyError volleyError, int flag) {

    }

    /**
     * 保存 副屏图片地址
     */
    private void saveSecondImageUrl(final AdUserBean adUserBean) {
        sysApplication.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if (adUserBean != null) {
                    MyPreferenceUtils.getSp().edit()
                            .putInt(DATA_MARK_ID, adUserBean.getMarketid())
                            .putString(DATA_MARK_NAME, adUserBean.getMarketname())
                            .putString(DATA_BOOTH_NUMBER, adUserBean.getCompanyno())
                            .apply();

                    // 更新时间
                    adUserBean.setId(1);
                    AdUserDao.getInstance().updateOrInsert(adUserBean);
                    ImageDao.getInstance().deleteAll();

                    /* 图片修改   *******************/
                    String baseUrl = adUserBean.getBaseurl();//开头路径
                    List<AdImageInfo> imageInfos = new ArrayList<>();

                    String ads = adUserBean.getAd();
                    if (ads != null) {
                        String[] adArray = ads.split(";");
                        if (adArray.length > 0) {
                            // 限制长度最长 为8
                            int piclength;
                            if (adArray.length > 8) {
                                piclength = 8;
                            } else {
                                piclength = adArray.length;
                            }

                            for (int i = 0; i < piclength; i++) {
                                String comUrl = adArray[i].replace(" ", "");
                                if (!TextUtils.isEmpty(comUrl)) {
                                    String netUrl = baseUrl + comUrl;
                                    AdImageInfo AdImageInfo = new AdImageInfo();
                                    AdImageInfo.setNetPath(netUrl);
                                    AdImageInfo.setType(1);
                                    imageInfos.add(AdImageInfo);
                                }
                            }
                        }
                    }

                    String photo = adUserBean.getPhoto();
                    if (photo != null) {
                        String comUrl = photo.replace(" ", "");
                        if (!TextUtils.isEmpty(comUrl)) {
                            String netUrl = baseUrl + comUrl;
                            AdImageInfo AdImageInfo = new AdImageInfo();
                            AdImageInfo.setNetPath(netUrl);
                            AdImageInfo.setType(0);
                            imageInfos.add(AdImageInfo);
                        }
                    }

                    String licences = adUserBean.getLicence();
                    if (licences != null) {
                        String[] adArray = licences.split(";");
                        if (adArray.length > 0) {
                            // 限制长度最长 为8
                            int piclength;
                            if (adArray.length > 4) {
                                piclength = 4;
                            } else {
                                piclength = adArray.length;
                            }

                            for (int i = 0; i < piclength; i++) {
                                String comUrl = adArray[i].replace(" ", "");
                                if (!TextUtils.isEmpty(comUrl)) {
                                    String netUrl = baseUrl + comUrl;
                                    AdImageInfo AdImageInfo = new AdImageInfo();
                                    AdImageInfo.setNetPath(netUrl);
                                    AdImageInfo.setType(2);
                                    imageInfos.add(AdImageInfo);
                                }
                            }
                        }
                    }
                    ImageDao.getInstance().inserts(imageInfos);
                }
                LiveBus.post(NOTIFY_LIVEBUS_KEY, String.class, NOTIFY_BASE_INFO);
            }
        });
    }

    @Override
    public void onResponse(final JSONObject jsonObject, int flag) {
        try {
            switch (flag) {
                case VOLLEY_UPDATE_IMAGE:
                    AdUserInfo resultAd = JSON.parseObject(jsonObject.toString(), AdUserInfo.class);
                    if (resultAd != null && resultAd.getStatus() == 0) {
                        AdUserBean adUserBean = resultAd.getData();
                        if (adUserBean == null) {
                            AdUserDao.getInstance().deleteAll();
                            ImageDao.getInstance().deleteAll();
                            LiveBus.post(NOTIFY_LIVEBUS_KEY, String.class, NOTIFY_BASE_INFO);
                            MyToast.showError(context, "未获取到配置信息！");
                        } else {
                            saveSecondImageUrl(adUserBean);
                        }
                    }
                    break;
                case VOLLEY_UPDATE_PRICE:
                    sysApplication.getThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            ResultInfo resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo.class);
                            if (resultInfo.getStatus() == 0) {
                                List<PriceBean> list = JSON.parseArray(resultInfo.getData(), PriceBean.class);
                                PriceBeanDao.getInstance().deleteAll();
                                PriceBeanDao.getInstance().insert(list);
                            }
                            LiveBus.post(NOTIFY_LIVEBUS_KEY, String.class, NOTIFY_BASE_PRICE);
                        }
                    });
                    break;
                case VOLLEY_UPDATE_INSPECT:
                    sysApplication.getThreadPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            ResultInfo resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo.class);
                            if (resultInfo.getStatus() == 0) {
                                List<InspectBean> list = JSON.parseArray(resultInfo.getData(), InspectBean.class);
                                InspectBeanDao.getInstance().deleteAll();
                                InspectBeanDao.getInstance().insert(list);
                            } else {
                                InspectBeanDao.getInstance().deleteAll();
                            }
                            LiveBus.post(NOTIFY_LIVEBUS_KEY, String.class, NOTIFY_BASE_INSPECT);
                        }
                    });
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyBinder extends Binder {
        public WorkService getMyService() {
            return WorkService.this;
        }
    }

    private boolean isCommonInfo;
    private boolean isMessageInfo;

    //    private Handler handler;
    private Context context;
    private SysApplication sysApplication;

    /**
     * 更新副屏证件照及广告图
     */
    private void upSecondImage() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).httpQuestImageEx22(this, sellerId, VOLLEY_UPDATE_IMAGE);
        }
    }

    /**
     * 更新所有商品
     */
    private void upPrice() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).questPrice(this, sellerId, VOLLEY_UPDATE_PRICE);
        }
    }

    /**
     * 获取检测结果
     */
    private void upInspect() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).questInspect(this, sellerId, VOLLEY_UPDATE_INSPECT);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.logTest(" Service onCreate ");
        context = this;
        sysApplication = (SysApplication) getApplication();

        // 请求检测和价格数据
        sysApplication.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                while (!isMessageInfo) {
                    try {
                        upPrice();
                        Thread.sleep(2 * 1000);
                        upInspect();
                        Thread.sleep(10 * 60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        sysApplication.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                while (!isCommonInfo) {
                    try {
                        upSecondImage();
                        Thread.sleep(30 * 60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        isCommonInfo = true;
        isMessageInfo = true;
        super.unbindService(conn);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isCommonInfo = true;
        isMessageInfo = true;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        isCommonInfo = true;
        isMessageInfo = true;
        MyLog.logTest(" Service onDestroy ");
        super.onDestroy();
    }

    /**
     * @param intent  内容Intent
     * @param flags   旗标
     * @param startId  开始id
     * @return    返回的开始命令的id
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // 在API11之后构建Notification的方式
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, ScreenActivity.class);

        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.layout_dialog_sellerid);// 获取remoteViews（参数一：包名；参数二：布局资源）
        builder.setContentIntent(PendingIntent.
                getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("下拉列表中的Title") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("要显示的内容") // 设置上下文内容
//                .setview(remoteViews)//  时光倒流
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        //构建好Notifycation，设置为默认的声音。开始前台的服务。
        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(110, notification);// 开始前台服务
        return super.onStartCommand(intent, flags, startId);
    }


}
