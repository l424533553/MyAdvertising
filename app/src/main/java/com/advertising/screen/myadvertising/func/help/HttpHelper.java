package com.advertising.screen.myadvertising.func.help;

import com.advertising.screen.myadvertising.func.application.SysApplication;
import com.advertising.screen.myadvertising.common.iinterface.IConstants;
import com.xuanyuan.library.listener.VolleyListener;

/**
 * author: luofaxin
 * date： 2018/9/10 0010.
 * email:424533553@qq.com
 * describe:
 */
public class HttpHelper implements IConstants {
    //    private VolleyListener listener;
    private SysApplication application;
    private static HttpHelper mInstants;

    public static HttpHelper getmInstants(SysApplication application) {
        if (mInstants == null) {
            mInstants = new HttpHelper(application);
        }
        return mInstants;
    }

    private HttpHelper(SysApplication application) {
        this.application = application;
    }

    /**
     * 查询价格列表
     */
    public void questPrice(VolleyListener listener, String shellerid, int flag) {
        String data = "{\"companyid\":\"" + shellerid + "\"}";
        String desdata = application.getDesBCBHelper().encode(data);
        String url = BASE_IP_ST + "/api/smartsz/getpriceinfo?desdata=" + desdata;
        application.volleyGet(url, listener, flag);
    }

    /**
     * 查询检测结果
     */
    public void questInspect(VolleyListener listener, String shellerid, int flag) {
        String data = "{\"companyid\":\"" + shellerid + "\"}";
        String desdata = application.getDesBCBHelper().encode(data);
        String url = BASE_IP_ST + "/api/smartsz/getinspectinfo?desdata=" + desdata;
        application.volleyGet(url, listener, flag);
    }


    /**
     * @param listener  监听
     * @param shellerid 商户id
     * @param flag      请求旗标
     */
    public void httpQuestImageEx22(VolleyListener listener, String shellerid, int flag) {
        String data = "{\"companyid\":\"" + shellerid + "\"}";
        String desdata = application.getDesBCBHelper().encode(data);
        String url = BASE_IP_ST + "/api/smartsz/getadinfo?desdata=" + desdata;
        application.volleyGet(url, listener, flag);
    }


//    /**
//     * 在线检测
//     */
//    public void onLineTest(VolleyListener listener, int flag) {
//        String url = BASE_IP_ST + "/api/smartsz/onlinecheck";
//        application.volleyGet(url, listener, flag);
//    }

    /**
     * 获取小程序图片信息
     */
    public void getSmallRoutine(VolleyListener listener, String sellerId, int flag) {
        String url = BASE_IP_WEB + "/getimgcode.php?id=" + sellerId;
        application.volleyGet(url, listener, flag);
    }

    //
}
