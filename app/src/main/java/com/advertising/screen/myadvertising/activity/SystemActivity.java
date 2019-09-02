//package com.advertising.screen.myadvertising.activity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.wifi.WifiManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.telephony.TelephonyManager;
//import android.util.DisplayMetrics;
//import android.view.Display;
//import android.widget.TextView;
//import com.advertising.screen.myadvertising.R;
//import com.luofx.base.BaseAppCompatActivity;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//
//public class SystemActivity extends Activity {
//
//    private TelephonyManager phone;
//    private WifiManager wifi;
//    private Display display;
//
//    @SuppressLint("WifiManagerLeak")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_system);
//        phone = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//
//        init();
//    }
//
//    /**
//     * 初始化数据
//     */
//    @SuppressLint("HardwareIds")
//    private void init() {
//        DisplayMetrics book = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(book);
//
//        try {
//            Class localClass = Class.forName("android.os.SystemProperties");
//            Object localObject1 = localClass.newInstance();
//            Object localObject2 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"gsm.version.baseband", "no message"});
//            Object localObject3 = localClass.getMethod("get", new Class[]{String.class, String.class}).invoke(localObject1, new Object[]{"ro.build.display.id", ""});
//
//            setEditText(R.id.get, localObject2 + "");
//            setEditText(R.id.osVersion, localObject3 + "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //获取网络连接管理者
//        ConnectivityManager connectionManager = (ConnectivityManager)
//                getSystemService(CONNECTIVITY_SERVICE);
//        //获取网络的状态信息，有下面三种方式
//        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
//
//        setEditText(R.id.lianwang, networkInfo.getType() + "");
//        setEditText(R.id.lianwangname, networkInfo.getTypeName());
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
////        setEditText(R.id.imei, phone.getDeviceId());
////        setEditText(R.id.deviceversion, phone.getDeviceSoftwareVersion());// 系统版本 ,
////        setEditText(R.id.imsi, phone.getSubscriberId());
////        setEditText(R.id.number, phone.getLine1Number());
////        setEditText(R.id.simserial, phone.getSimSerialNumber());
////        setEditText(R.id.simoperator, phone.getSimOperator());
////        setEditText(R.id.simoperatorname, phone.getSimOperatorName());
////        setEditText(R.id.simcountryiso, phone.getSimCountryIso());
////        setEditText(R.id.workType, phone.getNetworkType() + "");   // 网络类型
////        setEditText(R.id.netcountryiso, phone.getNetworkCountryIso());
////        setEditText(R.id.netoperator, phone.getNetworkOperator());
////        setEditText(R.id.netoperatorname, phone.getNetworkOperatorName());   // 网络类型名
//
//        setEditText(R.id.radiovis, Build.getRadioVersion());
//        setEditText(R.id.wifimac, wifi.getConnectionInfo().getMacAddress());   // mac 地址
//        setEditText(R.id.getssid, wifi.getConnectionInfo().getSSID());
//        setEditText(R.id.getbssid, wifi.getConnectionInfo().getBSSID());
//        setEditText(R.id.ip, wifi.getConnectionInfo().getIpAddress() + "");
//        setEditText(R.id.bluemac, BluetoothAdapter.getDefaultAdapter().getAddress());
//        setEditText(R.id.bluname, BluetoothAdapter.getDefaultAdapter().getName()
//        );
//
//        setEditText(R.id.cpu, getCpuName());
//        setEditText(R.id.andrlid_id, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
//        setEditText(R.id.serial, Build.SERIAL);
//        setEditText(R.id.brand, Build.BRAND); // ****品牌
//        setEditText(R.id.tags, Build.TAGS);
//        setEditText(R.id.device, Build.DEVICE);
//        setEditText(R.id.fingerprint, Build.FINGERPRINT);
//        setEditText(R.id.bootloader, Build.BOOTLOADER);
//        setEditText(R.id.release, Build.VERSION.RELEASE);
//        setEditText(R.id.sdk, Build.VERSION.SDK); // 系统版本值
//        setEditText(R.id.sdk_INT, Build.VERSION.SDK_INT + "");
//        setEditText(R.id.codename, Build.VERSION.CODENAME);
//        setEditText(R.id.incremental, Build.VERSION.INCREMENTAL);
//        setEditText(R.id.cpuabi, Build.CPU_ABI);
//        setEditText(R.id.cpuabi2, Build.CPU_ABI2);
//        setEditText(R.id.board, Build.BOARD);
//        setEditText(R.id.model, Build.MODEL); // ****  手机型号
//        setEditText(R.id.product, Build.PRODUCT);
//        setEditText(R.id.type, Build.TYPE);
//        setEditText(R.id.user, Build.USER);
//        setEditText(R.id.disply, Build.DISPLAY);
//        setEditText(R.id.hardware, Build.HARDWARE);
//        setEditText(R.id.host, Build.HOST);
//        setEditText(R.id.changshang, Build.MANUFACTURER);
//        setEditText(R.id.phonetype, phone.getPhoneType() + "");  //  手机类型
//        setEditText(R.id.simstate, phone.getSimState() + "");
//        setEditText(R.id.b_id, Build.ID);
//        setEditText(R.id.gjtime, Build.TIME + "");
//        setEditText(R.id.width, display.getWidth() + "");
//        setEditText(R.id.height, display.getHeight() + "");
//        setEditText(R.id.dpi, book.densityDpi + "");
//        setEditText(R.id.density, book.density + "");
//        setEditText(R.id.xdpi, book.xdpi + "");
//        setEditText(R.id.ydpi, book.ydpi + "");
//        setEditText(R.id.scaledDensity, book.scaledDensity + "");
//
//        // 方法2
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        setEditText(R.id.xwidth, width + "");
//        setEditText(R.id.xheight, height + "");
//    }
//
//
////    setEditText(R.id.wifimac, wifi.getConnectionInfo().getMacAddress());   // mac 地址
////    setEditText(R.id.phonetype, phone.getPhoneType() + "");  //  手机类型
////    setEditText(R.id.model, android.os.Build.MODEL); // ****  手机型号
////    setEditText(R.id.sdk, Build.VERSION.SDK); // 系统版本值
////
////    setEditText(R.id.deviceversion, phone.getDeviceSoftwareVersion());// 系统版本 ,
////    setEditText(R.id.workType, phone.getNetworkType() + "");   // 网络类型
////    setEditText(R.id.netoperatorname, phone.getNetworkOperatorName());   // 网络类型名
////    setEditText(R.id.wifimac, wifi.getConnectionInfo().getMacAddress());   // mac 地址
//
//
//
//
//
////    release:""  String  //系统版本	RELEASE	获取系统版本字符串。如4.1.2 或2.2 或2.3等	4.4.4
////    sdk：""  String 系统版本值 SDK 系统的API级别 一般使用下面大的SDK_INT 来查看 19
////    brand:""  String 品牌 BRAND 获取设备品牌 Huawei
////    model:""  String 型号 MODEL
////
////    networkoperatorname:"" String   网络类型名 getNetworkOperatorName 返回移动网络运营商的名字(SPN)中国联通
////    networktype："" String  网络类型 getNetworkType 3
////    phonetype："" 手机类型 getPhoneType 手机类型 1
////    mac：""mac地址 getMacAddress
//
//
//    private void setEditText(int id, String s) {
//        ((TextView) this.findViewById(id)).setText(s);
//    }
//
//    /**
//     * 获取CPU型号
//     *
//     * @return
//     */
//    public static String getCpuName() {
//
//        String str1 = "/proc/cpuinfo";
//        String str2 = "";
//
//        try {
//            FileReader fr = new FileReader(str1);
//            BufferedReader localBufferedReader = new BufferedReader(fr);
//            while ((str2 = localBufferedReader.readLine()) != null) {
//                if (str2.contains("Hardware")) {
//                    return str2.split(":")[1];
//                }
//            }
//            localBufferedReader.close();
//        } catch (IOException e) {
//        }
//        return null;
//
//    }
//
//}
//
//
