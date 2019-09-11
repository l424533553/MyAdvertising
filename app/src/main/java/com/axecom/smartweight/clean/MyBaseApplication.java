//package com.advertising;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.net.wifi.WifiManager;
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.support.multidex.MultiDex;
//import android.support.v4.app.ActivityCompat;
//import android.telephony.TelephonyManager;
//import com.android.volley.toolbox.Volley;
//import com.luofx.config.IBaseConstants;
//import com.luofx.entity.Deviceinfo;
//import com.luofx.entity.DeviceInfoDao;
//import com.luofx.listener.OkHttpListener;
//import com.luofx.other.tid.UserInfo;
//import com.luofx.utils.MyPreferenceUtils;
//
//
//import java.io.IOException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * 说明：
// * 作者：User_luo on 2018/7/24 13:52
// * 邮箱：424533553@qq.com
// * 需要导入Volley.jar 或者  远程依赖
// */
//@SuppressLint("Registered")
//public class MyBaseApplication extends VolleyApplication implements OkHttpListener, IBaseConstants {
//    private Context context;
//    //  线程池  记得要关闭
//    protected ExecutorService threadPool;
//    protected ExecutorService singleThread;
//
//    public ExecutorService getSingleThread() {
//        return singleThread;
//    }
//
//    public ExecutorService getThreadPool() {
//        return threadPool;
//    }
//
//    private UserInfo userInfo;
//
//    public UserInfo getUserInfo() {
//        return userInfo;
//    }
//
//    /**
//     * 设备信息
//     */
//    private Deviceinfo deviceinfo;
//
//    public Deviceinfo getDeviceinfo() {
//        return deviceinfo;
//    }
//
//    public void setDeviceinfo(Deviceinfo deviceinfo) {
//        this.deviceinfo = deviceinfo;
//    }
//
//    public Context getContext() {
//        return context;
//    }
//
//
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        this.context = this;
//        queues = Volley.newRequestQueue(getApplicationContext());
//        threadPool = Executors.newFixedThreadPool(4);
//        singleThread = Executors.newSingleThreadExecutor();
//        readyDevice();
//
//
//        //TODO
////        // 异常处理，不需要处理时注释掉这两句即可！
////        CrashHandler crashHandler = CrashHandler.getInstance();
////        // 注册crashHandler
////        crashHandler.init(getApplicationContext());
//
//    }
//
//
//    /**
//     * 检测 设备信息
//     */
//    private void readyDevice() {
//        // 检查获取信息
//        boolean hasDevice = MyPreferenceUtils.getBoolean(context, IS_HAS_DEVICE);
//        if (!hasDevice) {
//            getHardwareInfo();
//            MyPreferenceUtils.setBoolean(context, IS_HAS_DEVICE, true);
//        } else {
//            DeviceInfoDao deviceInfoDao = new DeviceInfoDao(context);
//            Deviceinfo deviceinfo = deviceInfoDao.queryById(1);
//            setDeviceinfo(deviceinfo);
//        }
//    }
//
//    /**
//     * 获取硬件信息
//     */
//    private void getHardwareInfo() {
//        TelephonyManager phone = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        @SuppressLint("HardwareIds")
//        String mac = wifi.getConnectionInfo().getMacAddress();   // mac 地址
//        int phonetype = phone.getPhoneType();  //  手机类型
//        String model = Build.MODEL; // ****  手机型号
//        String sdk = String.valueOf(Build.VERSION.SDK_INT); // 系统版本值
//
//        String brand = Build.BRAND;
//        String release = phone.getDeviceSoftwareVersion();// 系统版本 ,
//        int networktype = phone.getNetworkType();   // 网络类型
//        String networkoperatorname = phone.getNetworkOperatorName();   // 网络类型名
//        String radioVersion = Build.getRadioVersion();   // 固件版本
//
//        // 设备信息
//        Deviceinfo deviceinfo = new Deviceinfo(release, sdk, brand, model, networkoperatorname, networktype, phonetype, mac, radioVersion);
//        DeviceInfoDao deviceInfoDao = new DeviceInfoDao(context);
//        deviceInfoDao.insert(deviceinfo);
//        setDeviceinfo(deviceinfo);
//    }
//
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        //关闭线程池
//        threadPool.shutdown();
//    }
//
//    @Override
//    public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
//
//    }
//
//    @Override
//    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
//
//    }
//}
