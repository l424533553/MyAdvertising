//package com.advertising.screen.myadvertising;
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.*;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.net.ConnectivityManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.text.InputFilter;
//import android.text.Selection;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.*;
//import com.advertising.screen.myadvertising.entity.UserInfoDao;
//import com.alibaba.fastjson.JSON;
//import com.android.volley.VolleyError;
//
//import com.axecom.smartweight.my.entity.AdResultBean;
//import com.axecom.smartweight.my.entity.ResultInfo;
//import com.axecom.smartweight.my.entity.dao.OrderBeanDao;
//import com.axecom.smartweight.my.entity.dao.OrderInfoDao;
//import com.axecom.smartweight.my.entity.netresult.OrderResultBean;
//import com.axecom.smartweight.my.entity.netresult.TraceNoBean;
//import com.axecom.smartweight.my.helper.DateTimeUtil;
//import com.axecom.smartweight.my.helper.HeartBeatServcice;
//import com.axecom.smartweight.my.helper.HttpHelper;
//import com.axecom.smartweight.my.rzl.utils.ApkUtils;
//import com.axecom.smartweight.my.rzl.utils.DownloadDialog;
//import com.axecom.smartweight.my.rzl.utils.Version;
//import com.axecom.smartweight.ui.activity.MainBaseActivity;
//import com.luofx.listener.VolleyListener;
//import com.luofx.listener.VolleyStringListener;
//import com.luofx.newclass.ActivityController;
//import com.luofx.newclass.weighter.MyBaseWeighter;
//import com.luofx.newclass.weighter.Weighter;
//import com.luofx.newclass.weighter.XSWeighter15;
//import com.luofx.other.tid.UserInfo;
//import com.luofx.utils.MyPreferenceUtils;
//import com.luofx.utils.ToastUtils;
//import com.luofx.utils.common.MyToast;
//import com.luofx.utils.log.MyLog;
//import com.luofx.utils.net.NetWorkJudge;
//import org.json.JSONObject;
//
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import static com.axecom.smartweight.my.IConstants.LOCK_STATE;
//import static com.axecom.smartweight.my.IConstants.NOTIFY_MESSAGE_CHANGE;
//import static com.axecom.smartweight.utils.CommonUtils.parseFloat;
//
//public class MainActivity extends MainBaseActivity implements VolleyListener, VolleyStringListener,
//        View.OnClickListener, View.OnLongClickListener {
//
//
//
//
//    private EditText etPrice;
//    private TextView tvGoodsName;
//    private TextView tvgrandTotal;
//    private TextView tvTotalWeight;
//    private TextView weightTv;
//    private TextView tvTotalPrice;
//
//    //摊位号
//    private TextView tvNumber;
//    // 公斤   ，市斤
//    private TextView weightTopTv, tvCatty;
//
//    boolean switchSimpleOrComplex;
//
//    /************************************************************************************/
//    private DownloadDialog downloadDialog;
//
//    // 市场名    售卖者   秤号  kg
//    private TextView tvmarketName, tvSeller, tvTid, tvKg;
//    private LinearLayout llKey, llorder;
//
//
//
//
//    /**
//     * 初始化 版本
//     */
//    private void initVersion() {
//        TextView tvVersion = findViewById(R.id.tvVersion);
//        PackageInfo packageInfo;
//        try {
//            packageInfo = getApplicationContext().getPackageManager()
//                    .getPackageInfo(this.getPackageName(), 0);
//            String localVersion = packageInfo.versionName;
//            tvVersion.setText(localVersion);//版本號
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //检查版本更新
//    private void checkVersion(int marketId) {
//        downloadDialog = new DownloadDialog(context);
//        ApkUtils.checkRemoteVersion(marketId, sysApplication, handler);
//    }
//
//    private UserInfoDao userInfoDao;
//    private UserInfo userInfo;
//
//
//    /**
//     * 上下文
//     */
//    private Context context;
//    DecimalFormat decimalFormat = new DecimalFormat("0.000");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//
//    private SharedPreferences sharedPreferences;
//
//    private TextView date, time, week;
//    private DateTimeUtil dateTimeUtil;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
////        String model = android.os.Build.MODEL; // ****  手机型号
////        String sdk = Build.VERSION.SDK; // 系统版本值
////        int sdk12 = Build.VERSION.SDK_INT; // 系统版本值
////        MyLog.blue(" MainActivity  开启 onCreate");
//
//        ActivityController.addActivity(this);
//        helper = new HttpHelper(this, sysApplication);
//        context = this;
//        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
//
//        initVersion();
//        initHandler();
//        initNetReceiver();
//
//
//        checkVersion(userInfo.getMarketid());//检查版本更新
//
//        Intent intent = new Intent();
//        intent.setAction("com.axecom.iweight.carouselservice.datachange");
//        sendBroadcast(intent);
//
//    }
//
//
//
//
//    //广播
//    private NetBroadcastReceiver recevier;
//
//    private void initNetReceiver() {
//        recevier = new NetBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        intentFilter.addAction("com.axecom.smartweight.ui.activity.setting.background.change");
//        intentFilter.addAction(NOTIFY_MESSAGE_CHANGE);
//        //当网络发生变化的时候，系统广播会发出值为android.net.conn.CONNECTIVITY_CHANGE这样的一条广播
//        registerReceiver(recevier, intentFilter);
//    }
//
//    private OrderInfoDao orderInfoDao;
//    private OrderBeanDao orderBeanDao;
//    boolean isAskOrdering = true;
//
//    /**
//     * 获取称重重量信息
//     */
//    private void initHandler() {
//        handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(final Message msg) {
//                int what = msg.what;
//                switch (what) {
//
//                    case 10012:
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (msg.arg2 > 0) {
//                                    downloadDialog.setProgress(msg.arg1, msg.arg2);//arg1:已下载字节数,arg2:总字节数
//                                }
//                            }
//                        });
//                        break;
//                    case 10013:
//                        final Version v = (Version) msg.obj;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                downloadDialog.setVersion(v.version);//版本
//                                downloadDialog.setDate(v.date);//更新日期
//                                downloadDialog.setMarketId(String.valueOf(v.getMarketId()));//市场编号
//                                downloadDialog.setDescription(v.description);//更新描述
//                                downloadDialog.setApkPath(v.apkPath);//apk路径
//                                downloadDialog.show();
//                            }
//                        });
//
//                        break;
//                    case 10014:
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                downloadDialog.canForceQuit();
//                                Toast.makeText(MainActivity.this, "更新App失败,请联系运营人员", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        isAskOrdering = false;
//
//        if (recevier != null) {
//            unregisterReceiver(recevier);
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 2222) {
//
//            } else if (requestCode == 1111) {
//                boolean isDataChange = data.getBooleanExtra("isDataChange", false); //将计算的值回传回去
//                boolean isLocalChange = data.getBooleanExtra("isLocalChange", false); //将计算的值回传回去
//                if (isDataChange) {// 设置改变了
//                    if (userInfoDao == null) {
//                        userInfoDao = new UserInfoDao(context);
//                    }
//                    userInfo = userInfoDao.queryById(1);
//                    sysApplication.setUserInfo(userInfo);
//
//                }
//
//            }
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.main_cash_btn:
//
//                break;
//        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    private class NetBroadcastReceiver extends BroadcastReceiver {
//        private OrderInfoDao orderInfoDao;
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //如果相等的话就说明网络状态发生了变化
//            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {//网络变化了
//
//            } else if (BACKGROUND_CHANGE.equals(intent.getAction())) {
//
//            } else if (NOTIFY_MESSAGE_CHANGE.equals(intent.getAction())) {
//
//            }
//        }
//    }
//
//
//
//    /**
//     * 连接错误
//     *
//     * @param volleyError 错误信息
//     * @param flag        请求表示索引
//     */
//    @Override
//    public void onResponse(VolleyError volleyError, int flag) {
//
//        switch (flag) {
//            case 3:
//                MyLog.myInfo("错误" + volleyError.getMessage());
//                break;
//            case 4:
//                MyLog.myInfo("错误" + volleyError.getMessage());
//                break;
//            case 5:
//                MyLog.myInfo("错误" + volleyError.getMessage());
//                break;
//        }
//    }
//
//    @Override
//    public void onResponse(final JSONObject jsonObject, final int flag) {
//        sysApplication.getThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                ResultInfo resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo.class);
//                switch (flag) {
//                    case 3:
//                        MyLog.myInfo("成功" + jsonObject.toString());
//                        break;
//                    case 4://
//                        break;
//                    case 5:
//                        break;
//                    case 7:
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onResponseError(VolleyError volleyError, int flag) {
//        MyLog.myInfo("错误" + volleyError);
//        switch (flag) {
//            case 3:
//                MyLog.myInfo("失败" + volleyError.toString());
//                break;
//            case 5:
//                //TODO   555666 计划修改状态
//                MyLog.myInfo("错误" + volleyError.getMessage());
//                break;
//        }
//    }
//
//    //  返回支付状态
//    @Override
//    public void onResponse(String response, int flag) {
//        try {
//            switch (flag) {
//                case 3:
//                    OrderResultBean resultInfo = JSON.parseObject(response, OrderResultBean.class);
//                    if (resultInfo != null && resultInfo.getStatus() == 0) {
//                        String billstatus = resultInfo.getData().getBillstatus();
//                        String billcode = resultInfo.getData().getBillcode();//交易号
//
//                        if (!TextUtils.isEmpty(billcode)) {
//                            orderInfoDao.update(billcode);
//                        }
//                    }
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    int pressCount = 0;
//
//    @Override
//    public void onBackPressed() {
//        if (pressCount == 0) {
//            ToastUtils.showToast(this, "再次点击退出！");
//            pressCount++;
//            Timer timer = new Timer();//实例化Timer类
//            timer.schedule(new TimerTask() {
//                public void run() {
//                    pressCount = 0;
//                    this.cancel();
//                }
//            }, 2000);//五百毫秒
//            return;
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    public boolean onLongClick(View v) {
//
//        return false;
//    }
//
//
//
//
//}
//
//
