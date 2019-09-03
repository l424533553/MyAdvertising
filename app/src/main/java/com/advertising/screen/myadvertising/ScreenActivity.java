package com.advertising.screen.myadvertising;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.advertising.SysApplication;
import com.advertising.screen.myadvertising.adapter.InspectAdapter;
import com.advertising.screen.myadvertising.adapter.PriceAdapter;
import com.advertising.screen.myadvertising.databinding.ScreenMainBinding;
import com.advertising.screen.myadvertising.download.WorkService;
import com.advertising.screen.myadvertising.entity.*;
import com.advertising.screen.myadvertising.help.LiveBus;
import com.advertising.screen.myadvertising.help.MyImageLoader;
import com.advertising.screen.myadvertising.view.MarqueeTextviewNofocus;
import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.axecom.smartweight.my.IConstants;
import com.axecom.smartweight.my.entity.InspectBean;
import com.axecom.smartweight.my.entity.InspectBeanDao;
import com.axecom.smartweight.my.entity.PriceBean;
import com.axecom.smartweight.my.entity.PriceBeanDao;
import com.bumptech.glide.Glide;
import com.luofx.listener.VolleyStringListener;
import com.luofx.utils.common.MyToast;
import com.luofx.utils.log.MyLog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import static com.advertising.screen.myadvertising.IEventBus.*;

/**
 * Created by Administrator on 2018/7/20.
 */
public class ScreenActivity extends AppCompatActivity implements IConstants, VolleyStringListener {
    /**
     * 上下文内容
     */
    private Context context;
    /**
     * 系统设置 application，
     */
    private SysApplication myApplication;

    /**
     * VM 隐藏状态栏
     */
    private void hideState() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    private ScreenMainBinding binding;
    private ObservableArrayList<PriceBean> priceBeans = new ObservableArrayList<>();
    private ObservableArrayList<InspectBean> inspectBeans = new ObservableArrayList<>();
    private Banner mBanner;

    private List<Object> imagePath = new ArrayList<>();
    private ArrayList<String> imageTitle = new ArrayList<>();
    private ArrayList<String> paths = new ArrayList<>();


    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            switch (s) {
                case NOTIFY_BASE_INFO:
                    MyLog.logTest("====   更新基础信息");
                    updateBaseInfo(false);
                    break;
                case NOTIFY_BASE_PRICE:
                    MyLog.logTest("====   更新价格信息");
                    updateBasePrice();
                    break;
                case NOTIFY_BASE_INSPECT:
                    MyLog.logTest("====   更新检测信息");
                    updateBaseInspect();
                    break;
                default:
                    break;
            }
        }
    };

    /***
     *
     * @param savedInstanceState  越小
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideState();
        LiveBus.observe(NOTIFY_LIVEBUS_KEY, String.class, this, observer);
        binding = DataBindingUtil.setContentView(this, R.layout.screen_main);
        myApplication = (SysApplication) getApplication();
        context = this;

        initHandler();
        initAdapter();
        initData();

        initBanner();
        updateBaseInfo(true);


        handler.sendEmptyMessageDelayed(NOTIFY_DATA_MOVE, 5000);

    }


    public void initBannerData() {
        List<AdImageInfo> photos = ImageDao.getInstance(context).queryPhoto();
        if (photos != null && photos.size() > 0) {
            String photo = photos.get(0).getNetPath();
//            RequestOptions requestOptions = new RequestOptions();
//            //忽略 警告 ，不使用内存 ,这样就失去了意义了
//            requestOptions.skipMemoryCache(true) // 不使用内存缓存
//                    .diskCacheStrategy(DiskCacheStrategy.NONE); // 不使用磁盘缓存

            Glide.with(context).load(photo).error(R.mipmap.head).into(binding.layoutMain.ivPhoto);
//            Glide.with(context).load(photo).apply(requestOptions).into(ivPhoto);
        } else {
            binding.layoutMain.ivPhoto.setImageResource(R.mipmap.head);
        }


        List<AdImageInfo> list = ImageDao.getInstance(context).queryAll();
        if (list == null || list.size() == 0) {
            imagePath.clear();
            imageTitle.clear();
            imagePath.add(R.drawable.img1);
            imagePath.add(R.drawable.img2);
            imagePath.add(R.drawable.img3);

            imageTitle.add("市场图一");
            imageTitle.add("市场图二");
            imageTitle.add("市场图三");
            mBanner.setImages(imagePath);
        } else {
            paths.clear();
            imageTitle.clear();
            for (AdImageInfo image : list) {
                paths.add(image.getNetPath());
                imageTitle.add(image.getTitle());
            }
            mBanner.setImages(paths);
        }
        mBanner.setBannerTitles(imageTitle);

        mBanner.setDelayTime(5000);
        mBanner.start();
    }

    private void initBanner() {
        MyImageLoader mMyImageLoader = new MyImageLoader();
        mBanner = binding.banner;
        //设置样式，里面有很多种样式可以自己都看看效果
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(mMyImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        mBanner.setBannerAnimation(Transformer.Default);
        //轮播图片的文字
//        mBanner.setBannerTitles(imageTitle);
        //设置轮播间隔时间
        mBanner.setDelayTime(9000);
        //设置是否为自动轮播，默认是true
        mBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
//        mBanner.setImages(imagePath);
//        mBanner.setImages(imagePath);// 这是资源id集合

        //开始调用的方法，启动轮播图。//轮播图的监听
//        mBanner.setOnBannerListener(this).start();
    }

    /**
     * 更新  基础信息
     */
    private void updateBaseInfo(boolean isFirst) {
        List<AdUserBean> userBeans = AdUserDao.getInstance(context).queryAll();
        if (userBeans != null && userBeans.size() > 0) {
            AdUserBean userBean = userBeans.get(0);
            binding.layoutMain.setAdUserBean(userBean);
            binding.setAdUserBean(userBean);
            info.clear();
//            info.add(userBean.getAdcontent());
//            info.add(userBean.getAdcontent());
//            if (isFirst) {
//                binding.marqueeView.startWithList(info, R.anim.anim_in, R.anim.anim_out);
//            }
//            binding.mvContent.setContent(userBean.getAdcontent());

            binding.marquee .setText(userBean.getAdcontent());
            // 初始化
            binding.marquee .init(getWindowManager());
            // 设置滚动方向
            binding.marquee .setScrollDirection(MarqueeTextviewNofocus.RIGHT_TO_LEFT);
            // 设置滚动速度
            binding.marquee .setScrollMode(MarqueeTextviewNofocus.SCROLL_NORM);

        }

        initBannerData();
    }

    private PriceAdapter priceAdapter;
    private int priceIndex;
    private InspectAdapter inspectAdapter;
    private int inspectIndex;

    private void initAdapter() {



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.ilData.rvPrice.setLayoutManager(linearLayoutManager);
        binding.ilData.rvCheck.setLayoutManager(new LinearLayoutManager(context));
        priceAdapter = new PriceAdapter(priceBeans);
        inspectAdapter = new InspectAdapter(inspectBeans);
        binding.ilData.rvPrice.setAdapter(priceAdapter);
        binding.ilData.rvCheck.setAdapter(inspectAdapter);

        updateBasePrice();
        updateBaseInspect();

    }

    /**
     * 更新检测结果
     */
    private void updateBaseInspect() {
        inspectBeans.clear();
        inspectBeans.addAll(InspectBeanDao.getInstance(getBaseContext()).queryAll());
    }

    /**
     * 更新价格
     */
    private void updateBasePrice() {
        priceBeans.clear();
        priceBeans.addAll(PriceBeanDao.getInstance(getBaseContext()).queryAll());
        if (priceBeans.size() == 0) {
            binding.ilData.llPrice.setVisibility(View.GONE);
        } else {
            binding.ilData.llPrice.setVisibility(View.VISIBLE);
        }
    }


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            WorkService workService = ((WorkService.MyBinder) iBinder).getMyService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        LiveBus.removeObserver(NOTIFY_LIVEBUS_KEY, String.class, observer);
    }

    /**
     * 数据 handler
     */
    private Handler handler;

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case NOTIFY_DATA_MOVE:
                        int priceCount = priceAdapter.getItemCount();
                        if (priceCount > 0) {
                            if (priceIndex == priceCount - 1) {
                                priceIndex = 0;
                            } else {
                                priceIndex += 6;
                            }

                            if (priceIndex >= priceCount - 1) {
                                priceIndex = priceCount - 1;
                            }
                            binding.ilData.rvPrice.scrollToPosition(priceIndex);
                        }


                        int inspectCount = inspectAdapter.getItemCount();
                        if (inspectCount > 0) {
                            if (inspectIndex == inspectCount - 1) {
                                inspectIndex = 0;
                            } else {
                                inspectIndex += 6;
                            }

                            if (inspectIndex >= inspectCount - 1) {
                                inspectIndex = inspectCount - 1;
                            }
                            binding.ilData.rvCheck.scrollToPosition(inspectIndex);
                        }

                        handler.sendEmptyMessageDelayed(NOTIFY_DATA_MOVE, 5000);

                        break;
                    case NOTIFY_SUCCESS:
                        initData();
                        break;
                    case NOTIFY_JUMP:
                        break;
                    case FAILED://每隔几分钟循环访问一次，无内容
                        MyToast.showError(context, "Data信息为null，请检查后台配置");
                        break;
                }
                return false;
            }
        });
    }

    /**
     * @param context 上下文
     * @return 唯一标识 mac
     */
    @SuppressLint("HardwareIds")
    public String getIMEI(Context context) {
        // 94:a1:a2:a4:70:66
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String mac = "";
        if (wm != null) {
            mac = wm.getConnectionInfo().getMacAddress();
        }
        return mac;
    }


    @Override
    public void onResponseError(VolleyError volleyError, int flag) {
        MyToast.toastShort(context, "网络请求失败");
    }

    @Override
    public void onResponse(String response, int flag) {
        switch (flag) {
            case 2:
                MyLog.mylog("===" + System.currentTimeMillis() + "");
                ApkInfo apkInfo = JSON.parseObject(response, ApkInfo.class);
                MyLog.mylog("===" + System.currentTimeMillis() + "");

                if (apkInfo == null) {
                    return;
                }
                if (apkInfo.getStatus() != 0) {
                    return;
                }
                if (apkInfo.getData() == null) {
                    return;
                }
                String url = apkInfo.getData().getFilepath();// 網絡下載地址


                break;
            default:
                break;
        }
    }


    //为了下载图片资源，开辟一个新的子线程
 /*   private class ImageDownThread extends Thread {
        private String response;
        private boolean isDownLicences;// 是否下载证照图

        ImageDownThread(String response) {
            this.response = response;
        }

        public void run() {
            AdUserInfo netImageInfo = JSON.parseObject(response, AdUserInfo.class);
            if (netImageInfo != null) {
                if (netImageInfo.getStatus() == 0) {
                    AdUserBean adUserBean = netImageInfo.getData();
                    if (adUserBean != null) {
                        String screenImageState = MyPreferenceUtils.getSp(context).getString(IMAGE_STATE, "default");
                        assert screenImageState != null;
                        if (!screenImageState.equals(adUserBean.getStatus())) {
                            downImage(adUserBean);
                            MyPreferenceUtils.getSp(context).edit().putString(IMAGE_STATE, screenImageState).apply();
                        }
                    } else {
                        handler.sendEmptyMessage(FAILED);
                    }
                }
            }
        }

        private void downImage(AdUserBean adUserBean) {
            String dir = FileUtils.getDownloadDir(context, FileUtils.DOWNLOAD_DIR);
            //下载图片的路径
            FileOutputStream fileOutputStream = null;
            InputStream inputStream = null;
            try {
                String baseUrl = adUserBean.getBaseurl();
                List<AdImageInfo> imageInfos = new ArrayList<>();
                String prefix = DateUtils.getSampleNo();
                //2018.12.18 取消证照图片的下载
                String licences = adUserBean.getLicence();
                boolean issuccess = FileUtils.deleteDir(dir);
                if (licences != null) {
                    String[] adArray = licences.split(";");
                    if (adArray.length > 0) {
                        for (int i = 0; i < adArray.length; i++) {
                            if (StringUtils.isEmpty(adArray[i])) {
                                continue;
                            }
                            String netUrl = baseUrl + adArray[i];
                            URL url = new URL(netUrl);
                            //再一次打开
                            inputStream = url.openStream();
                            String localPath = dir + prefix + "licence" + i + ".png";
                            File file = new File(localPath);
                            fileOutputStream = new FileOutputStream(file);
                            int hasRead;
                            while ((hasRead = inputStream.read()) != -1) {
                                fileOutputStream.write(hasRead);
                            }
                            AdImageInfo AdImageInfo = new AdImageInfo();
                            AdImageInfo.setNetPath(netUrl);
                            AdImageInfo.setLocalPath(localPath);
                            AdImageInfo.setType(2);
                            imageInfos.add(AdImageInfo);
                        }
                    }
                }

                String ads = adUserBean.getAd();
                if (ads != null) {
                    String[] adArray = ads.split(";");
                    if (adArray.length > 0) {
                        for (int i = 0; i < adArray.length; i++) {
                            String netUrl = baseUrl + adArray[i];
                            URL url = new URL(netUrl);
                            //再一次打开
                            inputStream = url.openStream();
                            String localPath = dir + prefix + "ad" + i + ".png";
                            File file = new File(localPath);
                            fileOutputStream = new FileOutputStream(file);
                            int hasRead;
                            while ((hasRead = inputStream.read()) != -1) {
                                fileOutputStream.write(hasRead);
                            }
                            AdImageInfo AdImageInfo = new AdImageInfo();
                            AdImageInfo.setNetPath(netUrl);
                            AdImageInfo.setLocalPath(localPath);
                            AdImageInfo.setType(1);
                            imageInfos.add(AdImageInfo);
                        }
                    }
                }

                String photo = adUserBean.getPhoto();
                if (photo != null) {
                    String netUrl = baseUrl + "assets/files/" + photo;
                    URL url = new URL(netUrl);
                    //再一次打开
                    inputStream = url.openStream();
                    String localPath = dir + prefix + "photo.jpg";
                    File file = new File(localPath);
                    fileOutputStream = new FileOutputStream(file);
                    int hasRead;
                    while ((hasRead = inputStream.read()) != -1) {
                        fileOutputStream.write(hasRead);
                    }
                    AdImageInfo AdImageInfo = new AdImageInfo();
                    AdImageInfo.setNetPath(netUrl);
                    AdImageInfo.setLocalPath(localPath);
                    AdImageInfo.setType(0);
                    imageInfos.add(AdImageInfo);
                }

                imageDao.deleteAll();
                imageDao.inserts(imageInfos);
                userDao.deleteAll();
                adUserBean.setId(1);
                userDao.insert(adUserBean);
                handler.sendEmptyMessage(NOTIFY_SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/


    /**
     * 路径
     */
    private UserDao userDao;
    private List<String> info = new ArrayList<>();

    /**
     * VM 初始化数据
     */
    private void initData() {
        userDao = new UserDao(context);
        // 启动服务
        Intent intent = new Intent(this, WorkService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }


}
