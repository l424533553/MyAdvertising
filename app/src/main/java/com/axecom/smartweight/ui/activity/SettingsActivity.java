//package com.axecom.smartweight.ui.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.wifi.WifiConfiguration;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.advertising.screen.myadvertising.entity.UserInfo;
//import com.advertising.screen.myadvertising.entity.UserInfoDao;
//import com.alibaba.fastjson.JSON;
//import com.android.volley.VolleyError;
//import com.axecom.smartweight.R;
//import com.axecom.smartweight.base.BaseDialog;
//import com.advertising.SysApplication;
//import com.axecom.smartweight.bean.SettingsBean;
//import com.axecom.smartweight.my.IConstants;
//import com.axecom.smartweight.my.adapter.SettingsAdapter;
//import com.axecom.smartweight.my.entity.*;
//import com.axecom.smartweight.my.entity.dao.*;
//import com.axecom.smartweight.my.net.NetHelper;
//import com.axecom.smartweight.my.rzl.utils.ApkUtils;
//import com.axecom.smartweight.ui.activity.datasummary.SummaryActivity;
//import com.axecom.smartweight.ui.activity.setting.GoodsSettingActivity;
//import com.luofx.HelpActivity;
//import com.axecom.smartweight.ui.activity.setting.LocalSettingActivity;
//import com.luofx.help.QRHelper;
//import com.luofx.listener.VolleyListener;
//import com.luofx.newclass.ActivityController;
//import com.luofx.utils.common.MyToast;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Administrator on 2018-5-16.
// */
//
//public class SettingsActivity extends Activity implements VolleyListener, IConstants {
//
//    public final String IS_RE_BOOT = "is_re_boot";
//
//    private final int POSITION_PATCH = 1;
//    private final int POSITION_REPORTS = 2;
//    private final int POSITION_SERVER = 3;
//    private final int POSITION_INVALID = 4;
//    private final int POSITION_ABNORMAL = 5;
//
//    private final int POSITION_COMMODITY = 6;
//    private final int POSITION_UPDATE = 7;
//    private final int POSITION_HOT = 15;
//    private final int POSITION_RE_CONNECTING = 8;
//    private final int POSITION_WIFI = 9;
//    private final int POSITION_LOCAL = 10;
//
//    private final int POSITION_WEIGHT = 11;
//    private final int POSITION_RE_BOOT = 12;
//    private final int POSITION_HELP = 17;
//    private final int POSITION_DATA_DELETE = 18;
//    private final int POSITION_BACK = 16;
//    private final int POSITION_BD = 13;
//    private final int POSITION_SYSTEM = 14;
//
//    private GridView settingsGV;
//    private WifiManager wifiManager;
//    private Context context;
//    private SysApplication sysApplication;
//    private HotGoodsDao hotGoodsDao;
//    private GoodsTypeDao goodsTypeDao;
//    private AllGoodsDao allGoodsDao;
//    private UserInfoDao userInfoDao;
//    private NetHelper netHelper;
//    private BaseDialog baseDialog;
//    private int type;
//    private boolean isDataChange;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.settings_activity);
//        ActivityController.addActivity(this);
//        sysApplication = (SysApplication) getApplication();
//        context = this;
//
//
//        type = getIntent().getIntExtra(STRING_TYPE, 0);
//
//        userInfoDao = new UserInfoDao(context);
//        hotGoodsDao = new HotGoodsDao(context);
//        goodsTypeDao = new GoodsTypeDao(context);
//        allGoodsDao = new AllGoodsDao(context);
//
//
//        baseDialog = new BaseDialog(context);
//        netHelper = new NetHelper(sysApplication, SettingsActivity.this);
//
//        initHandler();
//        setInitView();
//        initView();
//
//        ImageView ivImageView = findViewById(R.id.ivImageView);
//        if (type == 0) {
//            String url = "https://data.axebao.com/smartsz/home.php?shid=" + sysApplication.getUserInfo().getSellerid();
//            Bitmap bitmap = QRHelper.createQRImage(url);
//            ivImageView.setImageBitmap(bitmap);
//        } else {
//            ivImageView.setVisibility(View.GONE);
//        }
//
//    }
//
//    //
//    private Handler handler;
//    private int requestCount = 3;
//
//    private void initHandler() {
//        handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                switch (msg.what) {
//                    case NOTIFY_INITDAT:
//                        final int tid = msg.arg1;
//                        sysApplication.getThreadPool().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                netHelper.initGoodsType();
//                                netHelper.initAllGoods();
//                                List<Goods> goodsList = hotGoodsDao.queryAll();
//                                if (goodsList == null || goodsList.size() == 0) {// 本地没数据则从网上拉取下来
//                                    netHelper.initGoods(tid, 2);
//                                    requestCount = 3;
//                                } else {
//                                    requestCount = 2;
//                                }
//                            }
//                        });
//                        break;
//                    case NOTIFY_SUCCESS:// 更新数据成功
//                        if (successFlag == requestCount)
//                            baseDialog.closeLoading();
//                        break;
//                    case NOTIFY_CLOSE_DIALOG:// 数据更新完 关闭弹框
//                        baseDialog.closeLoading();
//                        break;
//                }
//                return false;
//            }
//        });
//    }
//
//    public void setInitView() {
//        settingsGV = findViewById(R.id.settings_grid_view);
//        //当前版本
//        TextView tvSystemVersion = findViewById(R.id.tvDataUpdate_SystemVersion);
//        tvSystemVersion.setText(ApkUtils.getVersionName(this));
//    }
//
//    private SettingsAdapter settingsAdapter;
//
//    public void initView() {
//        List<SettingsBean> settngsList = new ArrayList<>();
//        SettingsBean settingsBean1 = new SettingsBean(R.drawable.printer, "补打上笔", POSITION_PATCH, R.color.color_settings1);
//        settngsList.add(settingsBean1);
//        SettingsBean settingsBean2 = new SettingsBean(R.drawable.settings2, "数据汇总", POSITION_REPORTS, R.color.color_settings2);
//        settngsList.add(settingsBean2);
//        SettingsBean settingsBean6 = new SettingsBean(R.drawable.settings3, "商品设置", POSITION_COMMODITY, R.color.color_settings3);
//        settngsList.add(settingsBean6);
//        SettingsBean settingsBean7 = new SettingsBean(R.drawable.settings4, "数据更新", POSITION_UPDATE, R.color.color_settings4);
//        settngsList.add(settingsBean7);
//        SettingsBean settingsBean16 = new SettingsBean(R.drawable.settings5, "热键更新", POSITION_HOT, R.color.color_settings1);
//        settngsList.add(settingsBean16);
//        SettingsBean settingsBean3 = new SettingsBean(R.drawable.settings6, "服务器测试", POSITION_SERVER, R.color.color_settings5);
//        settngsList.add(settingsBean3);
//
////      SettingsBean settingsBean8 = new SettingsBean(R.drawable.re_connecting, "一键重连", POSITION_RE_CONNECTING);
////      settngsList.add(settingsBean8);
//
//        SettingsBean settingsBean9 = new SettingsBean(R.drawable.settings7, "WIFI设置", POSITION_WIFI, R.color.color_settings7);
//        settngsList.add(settingsBean9);
////      SettingsBean settingsBean10 = new SettingsBean(R.drawable.local_setting, "本机设置", POSITION_LOCAL);
////      settngsList.add(settingsBean10);
//
//        SettingsBean settingsBean15 = new SettingsBean(R.drawable.settings8, "本机设置", POSITION_LOCAL, R.color.color_settings8);
//        settngsList.add(settingsBean15);
//        if (type == 1) {
//            SettingsBean settingsBean4 = new SettingsBean(R.drawable.settings13, "异常订单", POSITION_INVALID, R.color.color_settings13);
//            settngsList.add(settingsBean4);
//            SettingsBean settingsBean5 = new SettingsBean(R.drawable.settings14, "订单作废", POSITION_BD, R.color.color_settings14);
//            settngsList.add(settingsBean5);
//            SettingsBean settingsBean11 = new SettingsBean(R.drawable.settings12, "标定管理", POSITION_WEIGHT, R.color.color_settings12);
//            settngsList.add(settingsBean11);
//            SettingsBean settingsBean13 = new SettingsBean(R.drawable.settings11, "系统设置", POSITION_SYSTEM, R.color.color_settings11);
//            settngsList.add(settingsBean13);
//        }
//
//        SettingsBean settingsBean18 = new SettingsBean(R.drawable.settings18, "清除数据", POSITION_DATA_DELETE, R.color.color_settings18);
//        settngsList.add(settingsBean18);
//
//        SettingsBean settingsBean12 = new SettingsBean(R.drawable.settings10, "重启", POSITION_RE_BOOT, R.color.color_settings10);
//        settngsList.add(settingsBean12);
//        SettingsBean settingsBean17 = new SettingsBean(R.drawable.settings17, "帮助", POSITION_HELP, R.color.color_settings17);
//        settngsList.add(settingsBean17);
//        SettingsBean settingsBean14 = new SettingsBean(R.drawable.settings9, "返回", POSITION_BACK, R.color.color_settings9);
//        settngsList.add(settingsBean14);
//
//
//        settingsAdapter = new SettingsAdapter(this, settngsList);
//        settingsGV.setAdapter(settingsAdapter);
//        settingsGV.setOnItemClickListener(settingsOnItemClickListener);
//    }
//
//    @Override
//    public void onResponse(VolleyError volleyError, int flag) {
//        MyToast.toastShort(context, "网络请求失败");
//        handler.sendEmptyMessage(NOTIFY_CLOSE_DIALOG);
//        switch (flag) {
//            case 1:
//                break;
//            case 2:
//                MyToast.toastShort(context, "初始化数据不完全");
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public void onResponse(JSONObject jsonObject, int flag) {
//        try {
//            final ResultInfo resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo.class);
//            switch (flag) {
//                case 1:
//                    if (resultInfo != null) {
//                        if (resultInfo.getStatus() == 0) {
//                            UserInfo userInfo = JSON.parseObject(resultInfo.getData(), UserInfo.class);
//                            if (userInfo != null) {
//                                userInfo.setId(1);
////                                boolean isSuccess =
//                                int tty = userInfoDao.update(userInfo);
//                                sysApplication.setUserInfo(userInfo);
//                                Message message = handler.obtainMessage();
//                                message.arg1 = userInfo.getTid();
//                                message.what = NOTIFY_INITDAT;
//                                handler.sendMessage(message);
//                            }
//                        } else {
//                            MyToast.toastLong(context, "未获取到秤的配置信息");
//                        }
//                    } else {
//                        MyToast.toastLong(context, "未获取到秤的配置信息");
//                    }
//                    break;
//                case 2:
//                    if (resultInfo != null) { //
//                        sysApplication.getThreadPool().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (resultInfo.getStatus() == 0) {
//                                    hotGoodsDao.deleteAll();
//                                    List<Goods> goodsList = JSON.parseArray(resultInfo.getData(), Goods.class);
//                                    if (goodsList != null && goodsList.size() > 0) {
//                                        hotGoodsDao.insert(goodsList);
//                                    }
//                                }
//                            }
//                        });
//                        successFlag++;
//                        handler.sendEmptyMessage(NOTIFY_SUCCESS);
//                    }
////                  jumpActivity();
//                    break;
//                case 8:// 热键更新 ， 先删除原始数据，在下载
//                    if (resultInfo != null) {
//                        sysApplication.getThreadPool().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (resultInfo.getStatus() == 0) {
//                                    hotGoodsDao.deleteAll();
//                                    List<Goods> goodsList = JSON.parseArray(resultInfo.getData(), Goods.class);
//                                    if (goodsList != null && goodsList.size() > 0) {
//                                        hotGoodsDao.insert(goodsList);
//                                    }
//                                }
//                            }
//                        });
//                    }
//                    handler.sendEmptyMessage(NOTIFY_CLOSE_DIALOG);
//                    break;
//                case 3:
//                    if (resultInfo != null) {
//                        sysApplication.getThreadPool().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (resultInfo.getStatus() == 0) {
//                                    goodsTypeDao.deleteAll();
//                                    List<GoodsType> goodsList = JSON.parseArray(resultInfo.getData(), GoodsType.class);
//                                    if (goodsList != null && goodsList.size() > 0) {
//                                        goodsTypeDao.insert(goodsList);
//                                    }
//                                }
//                            }
//                        });
//                        successFlag++;
//                        handler.sendEmptyMessage(NOTIFY_SUCCESS);
//                    }
////                  jumpActivity();
//                    break;
//
//                case 4:
//                    if (resultInfo != null) {
//
//                        sysApplication.getThreadPool().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (resultInfo.getStatus() == 0) {
//                                    allGoodsDao.deleteAll();
//                                    List<AllGoods> goodsList = JSON.parseArray(resultInfo.getData(), AllGoods.class);
//                                    if (goodsList != null && goodsList.size() > 0) {
//                                        allGoodsDao.insert(goodsList);
//                                    }
//                                }
//                            }
//                        });
//
//                        successFlag++;
//                        handler.sendEmptyMessage(NOTIFY_SUCCESS);
//                    }
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 是否更新成功
//     */
//    private int successFlag = 0;
//    private boolean isLocalChange;//本機設置 有变化
//
//    AdapterView.OnItemClickListener settingsOnItemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            int flag = settingsAdapter.getItem(position).getFlag();
//            switch (flag) {
//                case POSITION_PATCH://补打上一笔
//                    OrderInfoDao orderInfoDao = new OrderInfoDao(context);
//                    List<OrderInfo> infoList = orderInfoDao.queryByDay(false, 1);
//                    if (infoList.size() == 0) {
//                        MyToast.toastShort(context, "暂无交易数据");
//                    } else {
//                        OrderInfo orderInfo = infoList.get(0);
////                        MyPrinter myPrinter = new MyPrinter(sysApplication.getPrint());
//                        sysApplication.getPrint().printOrder(sysApplication.getSingleThread(), orderInfo);
//                    }
//
////                    EventBus.getDefault().post(new BusEvent(BusEvent.POSITION_PATCH, SPUtils.getString(SettingsActivity.this, "print_orderno", ""), SPUtils.getString(SettingsActivity.this, "print_payid", "")));
////                    finish();
//                    break;
//                case POSITION_REPORTS://数据汇总
//                    startDDMActivity(SummaryActivity.class, false);
//                    break;
//                case POSITION_INVALID:
//
//                    //TODO
////                    startDDMActivity(OrderInvalidActivity.class, false);
//                    break;
//                case POSITION_ABNORMAL:
//                    startDDMActivity(AbnormalOrderActivity.class, false);
//                    break;
//                case POSITION_SERVER:
//                    startDDMActivity(ServerTestActivity.class, false);
//                    break;
//                case POSITION_BD:
//                    startDDMActivity(CalibrationActivity.class, false);
//                    break;
//                case POSITION_HELP:
//                    startDDMActivity(HelpActivity.class, false);
//                    break;
//                case POSITION_WIFI:
//                    startDDMActivity(WifiSettingsActivity.class, false);
//                    break;
//                case POSITION_COMMODITY:// 商品设置
//                    isDataChange = true;
//                    startDDMActivity(GoodsSettingActivity.class, false);
//                    break;
//                case POSITION_LOCAL://本机操作 ，当地操作
//                    isLocalChange = true;
//                    Intent intent2 = new Intent(SettingsActivity.this, LocalSettingActivity.class);
//                    startActivity(intent2);
//                    break;
//                case POSITION_SYSTEM:// 系统设置
//                    isDataChange = true;
//                    startDDMActivity(SystemSettingsActivity2.class, true);
//                    break;
//                case POSITION_RE_BOOT:
////                    EventBus.getDefault().post(new BusEvent(BusEvent.GO_HOME_PAGE, true));
////                    Intent intent = new Intent();
////                    intent.setClass(SettingsActivity.this, HomeActivity.class);
////                    intent.putExtra(IS_RE_BOOT, true);
////                    startActivity(intent);
////                    System.exit(0);
//
//                    Intent intent = new Intent();
//                    intent.setClass(SettingsActivity.this.getApplicationContext(), HomeActivity.class);
//                    intent.putExtra(IS_RE_BOOT, true);
//                    ActivityController.finishAll();
//                    startActivity(intent);
//
//
//                    break;
//                case POSITION_WEIGHT:// 标定管理
//
////                    finish();
//                    break;
//                case POSITION_HOT:
//                    //TODO  热键  ，强制更新热键
//                    baseDialog.showLoading();
//                    isDataChange = true;
//                    UserInfo userInfo = sysApplication.getUserInfo();
//                    if (userInfo != null) {
//                        int tid = userInfo.getTid();
//                        netHelper.initGoods(tid, 8);//热键更新
//                    }
//
//
//                    break;
//                case POSITION_BACK:
//                    finishActivity();
//                    break;
//                case POSITION_DATA_DELETE://清除数据
//                    //TODO
//
//
//                    break;
////                case POSITION_SWITCH:
////                    showLoading("切换成功");
////                    boolean switchSimpleOrComplex = (boolean) SPUtils.get(SettingsActivity.this, KET_SWITCH_SIMPLE_OR_COMPLEX, false);
////                    SPUtils.put(SettingsActivity.this, KET_SWITCH_SIMPLE_OR_COMPLEX, !switchSimpleOrComplex);
////                    break;
//                case POSITION_RE_CONNECTING:
////                    baseDialog.showLoading();
////                    String wifiSSID = SPUtils.getString(SettingsActivity.this, WifiSettingsActivity.KEY_SSID_WIFI_SAVED, "");
////                    if (!TextUtils.isEmpty(wifiSSID)) {
////                        WifiConfiguration mWifiConfiguration;
////                        WifiConfiguration tempConfig = IsExsits(wifiSSID);
////                        if (tempConfig != null) {
////                            mWifiConfiguration = tempConfig;
////                            boolean b = wifiManager.enableNetwork(mWifiConfiguration.networkId, true);
////                            if (b) {
//////                                showLoading("连接成功");
////                                baseDialog.closeLoading();
////                            }
////                        }
////                    }
//                    break;
//                case POSITION_UPDATE:// 数据更新
//                    baseDialog.showLoading();
//                    successFlag = 0;
//                    isDataChange = true;
//
//                    //TODO 各种数据更新  ，
//                    netHelper.getUserInfo(netHelper.getIMEI(context), 1);
//
//
////                    SystemSettingManager.updateData(SettingsActivity.this);
////                    updateScalesId();
//
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            baseDialog.closeLoading();
////                        }
////                    }, 2000);
//
//                    break;
//            }
//        }
//    };
//
//    @Override
//    public void onBackPressed() {
//        finishActivity();
//    }
//
//    private void finishActivity() {
//        Intent intentData = new Intent();
//        intentData.putExtra("isDataChange", isDataChange); //将计算的值回传回去
//        intentData.putExtra("isLocalChange", isLocalChange); //将计算的值回传回去
//        //通过intent对象返回结果，必须要调用一个setResult方法，
//        //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
//        setResult(RESULT_OK, intentData);
//        finish();
//    }
//
//
//    public WifiConfiguration IsExsits(String SSID) {
//        List<WifiConfiguration> existingConfigs = wifiManager
//                .getConfiguredNetworks();
//        for (WifiConfiguration existingConfig : existingConfigs) {
//            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
//                return existingConfig;
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 跳转Activity的方法,传入我们需要的参数即可
//     * 是否需要 开启动画
//     */
//    public <T> void startDDMActivity(Class<T> activity, boolean isAinmain) {
//        Intent intent = new Intent(SettingsActivity.this, activity);
//        startActivity(intent);
//        //是否需要开启动画(目前只有这种x轴平移动画,后续可以添加):
//        if (isAinmain) {
//            this.overridePendingTransition(R.anim.activity_translate_x_in, R.anim.activity_translate_x_out);
//        }
//    }
//
//}
