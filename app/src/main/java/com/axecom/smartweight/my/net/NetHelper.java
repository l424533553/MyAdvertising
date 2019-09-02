package com.axecom.smartweight.my.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import com.advertising.SysApplication;
import com.axecom.smartweight.my.IConstants;
import com.luofx.listener.VolleyListener;

/**
 * author: luofaxin
 * date： 2018/9/26 0026.
 * email:424533553@qq.com
 * describe:
 */
public class NetHelper implements IConstants {


    private SysApplication application;
    private VolleyListener volleyListener;

    public NetHelper(SysApplication application, VolleyListener volleyListener) {
        this.application = application;
        this.volleyListener = volleyListener;
    }


    /**
     * 获取  设备的唯一标识
     *
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

    public void initGoods(int tid,int flag) {
        String url = "http://119.23.43.64/api/smartsz/getquick?terid=" + tid;
        application.volleyGet(url, volleyListener, flag);
    }

    public void initGoodsType() {
        String url = "http://119.23.43.64/api/smartsz/getproducttype";
        application.volleyGet(url, volleyListener, 3);
    }

    public void initAllGoods() {
        String url = BASE_IP_ST+"/api/smartsz/getproducts";
        application.volleyGet(url, volleyListener, 4);
    }

    /**
     * 通过mac 获得用户信息
     *
     * @param mac  机器的mac地址
     * @param flag 请求浮标
     */
    public void getUserInfo(String mac, int flag) {
        String url = BASE_IP_ST + "/api/smart/getinfobymac?mac=" + mac;
        application.volleyGet(url, volleyListener, flag);
    }

}



//"http://119.23.43.64/api/smart/getinfobymac?mac=10:d0:7a:6e:ad:1b" + mac;

// 数据量 + 数据功能 选项