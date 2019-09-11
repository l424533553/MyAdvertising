package com.axecom.smartweight.my.helper;


import com.advertising.SysApplication;
import com.axecom.smartweight.my.IConstants;
import com.luofx.listener.VolleyListener;
import com.luofx.listener.VolleyStringListener;

import java.util.Map;

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

    public HttpHelper(SysApplication application) {
        this.application = application;
    }

    public void setApplication(SysApplication application) {
        this.application = application;
    }

    /**
     * @param marketid     市场编号
     * @param terid        秤id
     * @param flag         设备状态   0正常/1异常
     * @param requestIndex 请求索引    返回数据  data =0 正常  。data=1 禁用
     */
    public void upState(int marketid, VolleyListener listener, int terid, int flag, int requestIndex) {
        if (marketid < 0 || terid < 0) {
            return;
        }
        String url = BASE_IP_ST + "/api/smartsz/addatatus?marketid=" + marketid + "&terid=" + terid + "&flag=" + flag;
        application.volleyGet(url, listener, requestIndex);
    }


    /**
     * 秤的标定,数据传给 安鑫宝
     */
    public void upTicBD(Map<String, String> map, VolleyStringListener listener, int requestIndex) {
        String url = BASE_IP_ST + "/api/smartsz/addbd";
        application.volleyPostString(url, map, listener, requestIndex);
    }

    /**
     * 加密3Dex
     */
//    public void upStateEx(int marketid, VolleyListener listener, int terid, String note, int flag, int requestIndex) {
//        if (marketid < 0 || terid < 0) {
//            return;
//        }
//
//        String data = "{\"marketid\":\"" + marketid + "\",\"terid\":\"" + terid + "\",\"flag\":\"" + flag + "\",\"note\":\"" + note + "\"}";
//        String desdata = application.getDesBCBHelper().encode(data);
//        if (desdata == null) {
//            return;
//        }
//        String url = BASE_IP_ST + "/api/smartsz/addatatusex?desdata=" + desdata;
//        application.volleyGet(url, listener, requestIndex);
//    }


//
//    {"marketid":"11","terid":"103","flag":"0"}

//    void upAdMessage(int marketid, VolleyListener listener, int requestIndex) {
//        if (marketid < 0) {
//            return;
//        }
////       String url = BASE_IP_ST + "/api/smartsz/addatatus?marketid=" + marketid + "&terid=" + terid + "&flag=" + flag;
//        String url = BASE_IP_ST + "/api/smartsz/getvbroadcasbymarketid?marketid=" + marketid;
//        application.volleyGet(url, listener, requestIndex);
//    }


//    //通过
//    public void upAdMessageEx(int marketid, VolleyListener listener, int requestIndex) {
//        if (marketid < 0) {
//            return;
//        }
//        String data = "{\"marketid\":\"" + marketid + "\"}";
//        String desdata = application.getDesBCBHelper().encode(data);
////        String url = BASE_IP_ST + "/api/smartsz/addatatus?marketid=" + marketid + "&terid=" + terid + "&flag=" + flag;
//        String url = BASE_IP_ST + "/api/smartsz/getvbroadcasbymarketid?desdata=" + desdata;
//        application.volleyGet(url, listener, requestIndex);
//    }


//    {"billcode":"AX14504012019180451","billstatus":"0","marketid":11,"" +
//            "orderItem":[{"itemno":"3541","money":"28.84","name":"椰子","price":"56",
//            "time1":0,"time2":0,"weight":"0.515","x0":"0","x1":"0","x2":"0"}],
//
//        "seller":"内测03","sellerid":1152,"settlemethod":0,"terid":145,"time":"2019-04-01 18:04:51"}


//    {"billcode":"AN14504012019180321","billstatus":"0","marketid":11,"orderItem":[{"itemno":"3556","k":"0.0383055","money":"26.78","name":"黄豆","price":"52","time1":1554112999853,"time2":0,"weight":"0.515","weight0":"0250000","x0":"2106979","x1":"2107365","x2":"0","xcur":"2120471"}],"seller":"内测03","sellerid":1152,"settlemethod":0,"terid":145,"time":"2019-04-01 18:03:21"}

//    {"billcode":"AX10303062019151936","billstatus":"0","marketid":11,
//            "items":[ {"itemno":"3610","money":"0.2","name":"中袋","price":"0.2","weight":"1","x0":"2141288","x1":"2141289","x2":"2541289"},
//        {"itemno":"3610","money":"0.1","name":"小袋","price":"0.1","weight":"1","x0":"2141288","x1":"2141289","x2":"2541289"},
//        {"itemno":"3610","money":"0.3","name":"大袋","price":"0.3","weight":"1","x0":"2141288","x1":"2141289","x2":"2541289"},
//        {"itemno":"2173","money":"2.08","name":"鸭肠","price":"26","weight":"0.080","x0":"2141288","x1":"2141289","x2":"2541289"}]
//        ,"seller":"胡启城","sellerid":1126,"settlemethod":0,"terid":103,"time":"2019-03-06 15:19:36"}


    /**
     * 采用了volley  的模式请求
     *
     * @param volleyListener 回调监听
     * @param flag           请求标识
     */
//    public void getUserInfoEx(VolleyListener volleyListener, int flag) {
//        String data = "{\"mac\":\"" + SystemInfoUtils.getMac(application.getApplicationContext()) + "\"}";
////        JSON.toJSONString()
//        String desdata = application.getDesBCBHelper().encode(data);
//        String url = BASE_IP_ST + "/api/smart/getinfobymac?desdata=" + desdata;
//        application.volleyGet(url, volleyListener, flag);
//    }


//    http://119.23.43.64/api/smart/getinfobymac?desdata=95036e23953a518755f2f49870622d39881ef75a96d65040c4c87a80887e5413


//    {"mac":"10:d0:7a:6e:f6:c3"}
//    95036e23953a5187e1ae2f29819781c11a54dd33ec40217a97d94f486831945d
//    http://119.23.43.64/api/smart/getinfobymac?desdata=95036e23953a5187e1ae2f29819781c11a54dd33ec40217a97d94f486831945d

    /**
     * 传输数据，传输离线订单内容
     */
   /* public void updateData(final OrderInfoDao orderInfoDao) {
        application.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                final List<OrderInfo> orderInfos1 = orderInfoDao.queryByState();
                if (orderInfos1 != null && orderInfos1.size() > 0) {
                    String StringOld = JSON.toJSONString(orderInfos1);
                    String stringNew = StringOld.replace("orderBeans", "items");
                    //1.创建OkHttpClient对象
                    final OkHttpClient okHttpClient = new OkHttpClient();
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, stringNew);
                    //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
                    okhttp3.Request request = new okhttp3.Request.Builder().url(BASE_IP_ST + "/api/smart/commitszexlist").method("POST", body).build();
                    //3.创建一个call对象,参数就是Request请求对象
                    Call call = okHttpClient.newCall(request);
                    //4.同步调用会阻塞主线程,这边在子线程进行
                    okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                            MyLog.blue(e.getMessage());
                        }

                        // response  返回结果
                        @Override
                        public void onStringResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                            // 注：该回调是子线程，非主线程
//                                Log.i("wxy", "callback thread id is " + Thread.currentThread().getId());
                            ResultInfo resultInfo = com.alibaba.fastjson.JSON.parseObject(response.body().string(), ResultInfo.class);
                            if (resultInfo.getStatus() == 0) {
                                List<String> data = com.alibaba.fastjson.JSON.parseArray(resultInfo.getData(), String.class);
                                if (data != null && data.size() > 0) {
                                    for (int i = 0; i < data.size(); i++) {
                                        for (int j = 0; j < orderInfos1.size(); j++) {
                                            if (data.get(i).equals(orderInfos1.get(j).getBillcode())) {
                                                orderInfos1.get(j).setState(1);
                                                orderInfoDao.updateOrInsert(orderInfos1.get(j));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }*/


    /**
     * 請求广告证照图片
     */
    //通过
    public void httpQuestImageEx(VolleyStringListener listener, int shellerid, int flag) {
//        http://119.23.43.64/api/smartsz/getadinfo?desdata=5186f38001c5e0a01837a08ddd9a74d2b77680a7fafed075
//        int shellerid=1135;
        String data = "{\"companyid\":\"" + shellerid + "\"}";
        String desdata = application.getDesBCBHelper().encode(data);
        String url = BASE_IP_ST + "/api/smartsz/getadinfo?desdata=" + desdata;
        application.volleyStringGet(url, listener, flag);
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
     * 复制上面的  方法  ，监听接口不一样  而已
     *
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


    /**
     * 在线检测
     */
    public void onLineTest(VolleyListener listener, int flag) {
        String url = BASE_IP_ST + "/api/smartsz/onlinecheck";
        application.volleyGet(url, listener, flag);
    }

//    /**
//     * 获取批次号
//     */
//    public void getTraceNo(VolleyListener listener, int sellerid, int flag) {
//        String url = BASE_IP_ST + "/api/smartsz/gettracenolist?shid=" + sellerid;
//        application.volleyGet(url, listener, flag);
//    }

    /**
     * 获取批次号
     */
    //通过
    public void getTraceNoEx(VolleyListener listener, int sellerid, int flag) {
        String data = "{\"shid\":\"" + sellerid + "\"}";
        String desdata = application.getDesBCBHelper().encode(data);
        String url = BASE_IP_ST + "/api/smartsz/gettracenolist?desdata=" + desdata;
        application.volleyGet(url, listener, flag);
    }

    /**
     * 获取所有的商品信息,无参数
     */
    public void initAllGoods(VolleyListener listener, int flag) {
        String url = BASE_IP_ST + "/api/smartsz/getproducts";
        application.volleyGet(url, listener, flag);
    }

    /**
     * 获取小程序图片信息
     */
    public void getSmallRoutine(VolleyListener listener, String sellerId, int flag) {
        String url = BASE_IP_WEB + "/getimgcode.php?id=" + sellerId;
        application.volleyGet(url, listener, flag);
    }

//    https://data.axebao.com/smartsz/getimgcode.php?id=1152

    /**
     * 计量院 接口接入  登陆
     */
    public void onFpmsLogin(VolleyListener listener, String data, int flag) {
        String url = "https://fpms.chinaap.com/admin/trade?executor=http&appCode=FPMSWS&data=" + data;
        application.volleyGet(url, listener, flag);
    }

    /**
     * 计量院 接口接入  登陆
     * vtype  0：8寸     1: 15寸      2: 21寸
     */
    public void onCheckVersion(VolleyListener listener, int marketId, int flag) {
        String url = "https://data.axebao.com/api/smartsz/getvbymarketid?marketid=" + marketId;
        application.volleyGet(url, listener, flag);
    }


    /**
     * @param userInfo 用户信息
     * @param tidType  秤类型  0:商通   1: 15.6寸秤   2 ：8寸秤
     * @return 验证用户信息的有效性   是否有效可行
     */
  /*  public boolean validateUserInfo(UserInfo userInfo, int tidType) {
        if (userInfo == null) {
            MyToast.showError(application, "未获取到用户配置信息");
            return false;
        }
        if (TextUtils.isEmpty(userInfo.getSeller())) {
            MyToast.showError(application, "用户信息未设置用户名");
            return false;
        }
        if (tidType == 1 || tidType == 2) {
            if (userInfo.getSno() == null) {
                MyToast.showError(application, "请配置序列号");
                return false;
            } else {
                if (userInfo.getSno().length() != 12) {
                    MyToast.showError(application, "序列号长度为12");
                    return false;
                }
            }

            if (userInfo.getModel() == null) {
                MyToast.showError(application, "请配置规格型号");
                return false;
            }

            if (TextUtils.isEmpty(userInfo.getProducer())) {
                MyToast.showError(application, "请配置出厂厂家");
                return false;
            }

            if (userInfo.getOuttime() == null) {
                MyToast.showError(application, "请配置出厂时间");
                return false;
            }
        }
        return true;
    }*/
}
