package com.advertising.screen.myadvertising.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.func.application.SysApplication;
import com.advertising.screen.myadvertising.databinding.ActivityDataFlushBinding;
import com.advertising.screen.myadvertising.entity.AdImageInfo;
import com.advertising.screen.myadvertising.entity.AdUserBean;
import com.advertising.screen.myadvertising.entity.AdUserInfo;
import com.advertising.screen.myadvertising.entity.InspectBean;
import com.advertising.screen.myadvertising.entity.PriceBean;
import com.advertising.screen.myadvertising.entity.ResultInfo;
import com.advertising.screen.myadvertising.entity.ResultInfoSmall;
import com.advertising.screen.myadvertising.entity.dao.AdUserDao;
import com.advertising.screen.myadvertising.entity.dao.ImageDao;
import com.advertising.screen.myadvertising.entity.dao.InspectBeanDao;
import com.advertising.screen.myadvertising.entity.dao.PriceBeanDao;
import com.advertising.screen.myadvertising.func.help.HttpHelper;
import com.advertising.screen.myadvertising.mvvm.main.ui.MainActivity;
import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.xuanyuan.library.MyToast;
import com.xuanyuan.library.help.ActivityController;
import com.xuanyuan.library.listener.VolleyListener;
import com.xuanyuan.library.utils.log.MyLog;
import com.xuanyuan.library.utils.net.MyNetWorkUtils;
import com.xuanyuan.library.utils.storage.MyPreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.advertising.screen.myadvertising.common.iinterface.IConstants.DATA_BOOTH_NUMBER;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.DATA_MARK_ID;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.DATA_MARK_NAME;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.DEFAULT_ID;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.HANDLER_FINISH_INSPECT;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.HANDLER_FINISH_PRICE;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.HANDLER_SECOND_IMAGE;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.HANDLER_SMALL_ROUTINE;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.HANDLER_UPDATE_ALLGOOD;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.HANDLER_UPDATE_FINISH;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.IS_FIRST_LOGIN;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.SELLER_ID;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.SMALLROUTINE_URL;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.VOLLEY_UPDATE_IMAGE;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.VOLLEY_UPDATE_INSPECT;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.VOLLEY_UPDATE_PRICE;
import static com.advertising.screen.myadvertising.common.iinterface.IConstants.VOLLEY_UPDATE_SMALL_QR;


public class DataFlushActivity extends MyCommonKtActivity implements VolleyListener, View.OnClickListener {

    /**
     * 消息转发机制  handler,使用情况 时间功能
     */
    private Handler handler;

    /**
     * 初始化handler
     */
    private void initHandler() {
        handler = new Handler(msg -> {
            switch (msg.what) {
                case HANDLER_UPDATE_ALLGOOD:
                    upPrice();
                    break;
                case HANDLER_FINISH_PRICE:
                    upInspect();
                    break;
                case HANDLER_FINISH_INSPECT:
                    upSecondImage();
                    break;
                case HANDLER_SMALL_ROUTINE:
                    binding.tvSmallRoutine.setText("更新成功");
                    handler.sendEmptyMessage(HANDLER_SECOND_IMAGE);
                    break;
                case HANDLER_SECOND_IMAGE:
                    upSecondImage();
                    break;
                case HANDLER_UPDATE_FINISH:
                    MyPreferenceUtils.getSp().edit().putBoolean(IS_FIRST_LOGIN, true).apply();
                    jumpActivity(MainActivity.class, true);
                    break;
            }
            return false;
        });
    }

    /**
     * 返回 按键  操作返回结果
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private SysApplication sysApplication;
    //    private AdUserDao adUserDao;
//    private ImageDao imageDao;
    private ActivityDataFlushBinding binding;

    /**
     * 时间、功能、速度
     *
     * @param savedInstanceState 保存原始的状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_data_flush);
        binding.setOnClickListener(this);
        ActivityController.addActivity(this);
        sysApplication = (SysApplication) getApplication();

        initHandler();
        upPrice();
    }

    @Override
    public void onResponse(VolleyError volleyError, int flag) {
        MyToast.toastShort(context, "网络请求失败");
        switch (flag) {
            case VOLLEY_UPDATE_PRICE://查询价格
                binding.tvPrice.setText("更新失败");
                handler.sendEmptyMessage(HANDLER_FINISH_PRICE);
                break;
            case VOLLEY_UPDATE_INSPECT://查询检测结果
                binding.tvInspect.setText("更新失败");
                handler.sendEmptyMessage(HANDLER_FINISH_INSPECT);
                break;

            case VOLLEY_UPDATE_SMALL_QR:
                binding.tvSmallRoutine.setText("更新失败");
                handler.sendEmptyMessage(HANDLER_SECOND_IMAGE);
                break;
            case VOLLEY_UPDATE_IMAGE:
                binding.tvSecondImage.setText("更新失败");
                handler.sendEmptyMessageDelayed(HANDLER_UPDATE_FINISH, 500);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject, int flag) {
        try {
            ResultInfo resultInfo;
            switch (flag) {
                case VOLLEY_UPDATE_PRICE:
                    resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo.class);

                    MyLog.logTest("55555555555 ==" + resultInfo.toString());
                    if (resultInfo.getStatus() == 0) {
                        List<PriceBean> list = JSON.parseArray(resultInfo.getData(), PriceBean.class);
                        PriceBeanDao.getInstance().deleteAll();
                        PriceBeanDao.getInstance().insert(list);
                    } else {
                        binding.tvPrice.setText("更新失败");
                    }
                    handler.sendEmptyMessage(HANDLER_FINISH_PRICE);
                    break;
                case VOLLEY_UPDATE_INSPECT:
                    resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo.class);
                    if (resultInfo.getStatus() == 0) {
                        List<InspectBean> list = JSON.parseArray(resultInfo.getData(), InspectBean.class);
                        InspectBeanDao.getInstance().deleteAll();
                        InspectBeanDao.getInstance().insert(list);
                    } else {
                        binding.tvInspect.setText("更新失败");
                    }
                    handler.sendEmptyMessage(HANDLER_FINISH_INSPECT);
                    break;
                case VOLLEY_UPDATE_SMALL_QR:
                    ResultInfoSmall result = JSON.parseObject(jsonObject.toString(), ResultInfoSmall.class);
                    if (result != null && result.getCode() == 0) {
                        String url = result.getUrl();
                        MyPreferenceUtils.getSp().edit().putString(SMALLROUTINE_URL, url).apply();
                        binding.tvSmallRoutine.setText("更新成功");
                    } else {
                        binding.tvSmallRoutine.setText("更新失败");
                    }

                    handler.sendEmptyMessage(HANDLER_SECOND_IMAGE);
                    break;
                case VOLLEY_UPDATE_IMAGE:
                    AdUserInfo resultAd = JSON.parseObject(jsonObject.toString(), AdUserInfo.class);
                    if (resultAd != null && resultAd.getStatus() == 0) {
                        AdUserBean adUserBean = resultAd.getData();
                        saveSecondImageUrl(adUserBean);
                        binding.tvSecondImage.setText("更新成功");
                    } else {
                        binding.tvSecondImage.setText("更新失败");
                        handler.sendEmptyMessageDelayed(HANDLER_UPDATE_FINISH, 500);
                    }

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存 副屏图片地址
     */
    private void saveSecondImageUrl(final AdUserBean adUserBean) {
        sysApplication.getThreadPool().execute(() -> {
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
            handler.sendEmptyMessage(HANDLER_UPDATE_FINISH);
        });
    }

    /**
     * 点击监听事件
     *
     * @param v 点击的控件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpData:
                break;
            case R.id.btnSmallRoutine:
                upSmallRoutine();
                break;
            case R.id.btnSecondImage:
                upSecondImage();
                break;
            default:
                break;
        }
    }


    /**
     * 更新所有商品的价格
     */
    private void upPrice() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).questPrice(DataFlushActivity.this, sellerId, VOLLEY_UPDATE_PRICE);
            binding.tvPrice.setText("正在更新。。。");
        } else {
            binding.tvTitle.setText("无网络，请配置网络");
        }
    }

    /**
     * 获取检测结果，环境功能测试
     */
    private void upInspect() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).questInspect(DataFlushActivity.this, sellerId, VOLLEY_UPDATE_INSPECT);
            binding.tvInspect.setText("正在更新。。。");
        } else {
            binding.tvTitle.setText("无网络，请配置网络");
        }
    }

    /**
     * 更新小程序图片，该请求尽可能少的申请，有次数限制
     */
    private void upSmallRoutine() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).getSmallRoutine(DataFlushActivity.this, sellerId, VOLLEY_UPDATE_SMALL_QR);
            binding.tvSmallRoutine.setText("正在更新。。。");
        } else {
            binding.tvTitle.setText("无网络，请配置网络");
        }
    }

    /**
     * 更新副屏证件照及广告图
     */
    private void upSecondImage() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).httpQuestImageEx22(DataFlushActivity.this, sellerId, VOLLEY_UPDATE_IMAGE);
            binding.tvSecondImage.setText("正在更新。。。");
        } else {
            binding.tvTitle.setText("无网络，请配置网络");
        }
    }
}

//
//ups/uploads/file/20190830/tim.jpg;
//ups/uploads/file/20190830/a315bb19e17f686b679ee773a1df5157.jpg;
//ups/uploads/file/20190830/1F32Q05306-6.jpg
//https://data.axebao.com/smartsz/assets/files/20190520094255772.png