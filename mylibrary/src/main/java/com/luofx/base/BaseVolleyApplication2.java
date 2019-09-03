package com.luofx.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.luofx.listener.OkHttpListener;
import com.luofx.listener.VolleyListener;
import com.luofx.listener.VolleyStringListener;
import com.luofx.utils.CharsetJsonRequest;
import com.luofx.utils.net.NetWorkJudge;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import static com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES;

/**
 * 说明：
 * 作者：User_luo on 2018/7/24 13:52
 * 邮箱：424533553@qq.com
 * 需要导入Volley.jar 或者  远程依赖
 */
@SuppressLint("Registered")
public class BaseVolleyApplication2 extends Application {
    /**
     * 先创建一个请求队列，因为这个队列是全局的，所以在Application中声明这个队列
     */
    protected RequestQueue queues;


    public RequestQueue getQueues() {
        return queues;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        queues = Volley.newRequestQueue(getApplicationContext());
    }

    /**
     * 获取硬件信息
     */
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
//        // 数据
//        Deviceinfo deviceinfo = new Deviceinfo(release, sdk, brand, model, networkoperatorname, networktype, phonetype, mac, radioVersion);
//        DeviceInfoDao deviceInfoDao = new DeviceInfoDao(context);
//        deviceInfoDao.insert(deviceinfo);
//        setDeviceinfo(deviceinfo);
//    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Volley Get 请求方式
     *
     * @param url      网址
     * @param listener 监听请求
     * @param flag     旗标
     */
    public void volleyGet(String url, final VolleyListener listener, final int flag) {
        if (NetWorkJudge.isNetworkAvailable(this)) {
            CharsetJsonRequest request = new CharsetJsonRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    listener.onResponse(jsonObject, flag);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    listener.onResponse(volleyError, flag);
                }
            });
            // 设置Volley超时重试策略
            request.setRetryPolicy(new DefaultRetryPolicy(5000, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getQueues().add(request);
        }
    }

    /**
     * Volley Get 请求方式
     *
     * @param url      网址
     * @param listener 监听请求
     * @param flag     旗标
     */
    public void volleyStringGet(String url, final VolleyStringListener listener, final int flag) {
        if (NetWorkJudge.isNetworkAvailable(this)) {
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onResponse(response, flag);

//                    MyLog.logTest(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onResponseError(error, flag);
                }
            });
            // 设置Volley超时重试策略
            request.setRetryPolicy(new DefaultRetryPolicy(5000, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getQueues().add(request);
        }
    }

    /**
     * Volley Post请求方式
     *
     * @param url            网络地址
     * @param map            post请求参数
     * @param volleyListener 监听接口
     */
    public void volleyPost(String url, final Map<String, String> map, final VolleyListener volleyListener, final int flag) {
        if (NetWorkJudge.isNetworkAvailable(this)) {
            CharsetJsonRequest request = new CharsetJsonRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    volleyListener.onResponse(jsonObject, flag);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyListener.onResponse(volleyError, flag);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    return map;
                }
            };
            // 设置Volley超时重试策略
            request.setRetryPolicy(new DefaultRetryPolicy(5000, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getQueues().add(request);
        }
    }

    public void volleyPost(String url, JSONObject jsonRequest, final VolleyListener volleyListener, final int flag) {
        if (NetWorkJudge.isNetworkAvailable(this)) {
            CharsetJsonRequest request = new CharsetJsonRequest(Request.Method.POST, url, jsonRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    volleyListener.onResponse(jsonObject, flag);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyListener.onResponse(volleyError, flag);
                }
            });
//            RequestBody body = RequestBody.create(MEDia_MEDIA_TYPE_JSON, json);
            // 设置Volley超时重试策略
            request.setRetryPolicy(new DefaultRetryPolicy(5000, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getQueues().add(request);
        }
    }

    public void volleyPostString(String url, final Map<String, String> map, final VolleyStringListener volleyListener, final int flag) {
        if (NetWorkJudge.isNetworkAvailable(this)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    volleyListener.onResponse(response, flag);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    volleyListener.onResponseError(error, flag);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    return map;
                }

                //设定 头部 文件
//                @Override
//                public Map<String, String> getHeaders() {
//                    Map<String, String> headers = new HashMap<>();
////                    headers.put("Charset", "UTF-8");
//                    headers.put("Content-Type", "application/json");
////                    headers.put("Accept-Encoding", "gzip,deflate");
//                    return headers;
//                }
            };

            // 设置Volley超时重试策略  ,  当次数 为零时就是不试 ，当为一时就是 可有两次
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            getQueues().add(stringRequest);
        }
    }

    /**
     * 使用okhttp的 方式请求  ，多数用 POST请求
     * BASE_IP_ST + "/api/smart/commitszexlist"
     *
     * @param url 请求地址
     */
    public void okHttpPost(String url, String stringBody, final OkHttpListener okHttpListener) {
        //1.创建OkHttpClient对象
        final OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, stringBody);
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.同步调用会阻塞主线程,这边在子线程进行
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                okHttpListener.onFailure(call, e);
            }

            /**
             * @param response  返回结果
             */
            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) {
                // 注：该回调是子线程，非主线程
//                ResultInfo resultInfo = com.alibaba.fastjson.JSON.parseObject(response.body().string(), ResultInfo.class);
                okHttpListener.onResponse(call, response);
            }
        });
    }


}
