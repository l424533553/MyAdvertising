package com.advertising.screen.myadvertising.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.advertising.SysApplication;
import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.databinding.ActivityDataFlushBinding;
import com.advertising.screen.myadvertising.entity.*;
import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.axecom.smartweight.my.entity.*;
import com.axecom.smartweight.my.helper.HttpHelper;
import com.axecom.smartweight.my.helper.MyNetWorkUtils;
import com.luofx.listener.VolleyListener;
import com.luofx.newclass.ActivityController;
import com.luofx.utils.MyPreferenceUtils;
import com.luofx.utils.common.MyToast;
import com.luofx.utils.log.MyLog;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.axecom.smartweight.my.IConstants.*;


public class DataFlushActivity extends MyCommonActivity implements VolleyListener, View.OnClickListener {

    /**
     * 底部标题 、热键商品更新  ,商品类型更新  ，所有商品更新,  小程序 图片  ,强制更新热键
     */
    private TextView tvTitle, tvSmallRoutine;

    private TextView tvSecondImage;

    private void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        tvSmallRoutine = findViewById(R.id.tvSmallRoutine);
        tvSecondImage = findViewById(R.id.tvSecondImage);


        Button btnSmallRoutine = findViewById(R.id.btnSmallRoutine);
        btnSmallRoutine.setOnClickListener(this);
    }

    //
    private Handler handler;

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
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
                        tvSmallRoutine.setText("更新成功");
                        handler.sendEmptyMessage(HANDLER_SECOND_IMAGE);
                        break;
                    case HANDLER_SECOND_IMAGE:
                        upSecondImage();
                        break;
                    case HANDLER_UPDATE_FINISH:
                        MyPreferenceUtils.getSp(context).edit().putBoolean(IS_FIRST_LOGIN, true).apply();
                        jumpActivity(ScreenActivity.class, true);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private SysApplication sysApplication;


    private AdUserDao adUserDao;
    private ImageDao imageDao;

    private ActivityDataFlushBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data_flush);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_flush);
        ActivityController.addActivity(this);
        sysApplication = (SysApplication) getApplication();

        initView();
        initHandler();
        adUserDao = new AdUserDao(context);
        imageDao = new ImageDao(context);
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
                tvSmallRoutine.setText("更新失败");
                handler.sendEmptyMessage(HANDLER_SECOND_IMAGE);
                break;
            case VOLLEY_UPDATE_IMAGE:
                tvSecondImage.setText("更新失败");
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
                        PriceBeanDao.getInstance(getApplicationContext()).deleteAll();
                        PriceBeanDao.getInstance(getApplicationContext()).insert(list);
                    } else {
                        binding.tvPrice.setText("更新失败");
                    }
                    handler.sendEmptyMessage(HANDLER_FINISH_PRICE);
                    break;
                case VOLLEY_UPDATE_INSPECT:
                    resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo.class);
                    if (resultInfo.getStatus() == 0) {
                        List<InspectBean> list = JSON.parseArray(resultInfo.getData(), InspectBean.class);
                        InspectBeanDao.getInstance(getApplicationContext()).deleteAll();
                        InspectBeanDao.getInstance(getApplicationContext()).insert(list);
                    } else {
                        binding.tvPrice.setText("更新失败");
                    }
                    handler.sendEmptyMessage(HANDLER_FINISH_INSPECT);
                    break;
                case VOLLEY_UPDATE_SMALL_QR:
                    ResultInfoSmall result = JSON.parseObject(jsonObject.toString(), ResultInfoSmall.class);
                    if (result != null && result.getCode() == 0) {
                        String url = result.getUrl();
                        MyPreferenceUtils.getSp(context).edit().putString(SMALLROUTINE_URL, url).apply();
//                        LiveEventBus.get().with(EVENT_BUS_COMMON, String.class).post(NOTIFY_SMALLL_ROUTINE);
                        tvSmallRoutine.setText("更新成功");
                    } else {
                        tvSmallRoutine.setText("更新失败");
                    }

                    handler.sendEmptyMessage(HANDLER_SECOND_IMAGE);
                    break;
                case VOLLEY_UPDATE_IMAGE:
                    AdUserInfo resultAd = JSON.parseObject(jsonObject.toString(), AdUserInfo.class);
                    if (resultAd != null && resultAd.getStatus() == 0) {
                        AdUserBean adUserBean = resultAd.getData();
                        saveSecondImageUrl(adUserBean);
                        tvSecondImage.setText("更新成功");
                    } else {
                        tvSecondImage.setText("更新失败");
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



/*    private void saveSecondImageUrl222(final String response) {

        sysApplication.getThreadPool().execute(new Runnable() {
            @Override
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
            private void downImage(AdUserBean adUserBean, Context context) {

                adUserBean.setId(1);
                AdUserDao.getInstance(context).updateOrInsert(adUserBean);


                imageDao.deleteAll();
//                 图片修改   ******************
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
                                AdImageInfo.setType(2);
                                imageInfos.add(AdImageInfo);
                            }
                        }
                    }
                }
                imageDao.inserts(imageInfos);
                handler.sendEmptyMessage(NOTIFY_SUCCESS);
            }
        });
    }*/


    /**
     * 保存 副屏图片地址
     */
    private void saveSecondImageUrl(final AdUserBean adUserBean) {
        sysApplication.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if (adUserBean != null) {

                    MyPreferenceUtils.getSp(context).edit()
                            .putInt(DATA_MARK_ID, adUserBean.getMarketid())
                            .putString(DATA_MARK_NAME, adUserBean.getMarketname())
                            .putString(DATA_BOOTH_NUMBER, adUserBean.getCompanyno())
                            .apply();
                    // 更新时间
                    adUserBean.setId(1);
                    adUserDao.updateOrInsert(adUserBean);
                    imageDao.deleteAll();

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

                    imageDao.inserts(imageInfos);
                }
                handler.sendEmptyMessage(HANDLER_UPDATE_FINISH);
            }
        });
    }

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
     * 更新所有商品
     */
    private void upPrice() {
        if (MyNetWorkUtils.isNetworkAvailable(context)) {
            String sellerId = MyPreferenceUtils.getSp(context).getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).questPrice(DataFlushActivity.this, sellerId, VOLLEY_UPDATE_PRICE);
            binding.tvPrice.setText("正在更新。。。");
        } else {
            tvTitle.setText("无网络，请配置网络");
        }
    }

    /**
     * 获取检测结果
     */
    private void upInspect() {
        if (MyNetWorkUtils.isNetworkAvailable(context)) {
            String sellerId = MyPreferenceUtils.getSp(context).getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).questInspect(DataFlushActivity.this, sellerId, VOLLEY_UPDATE_INSPECT);
            binding.tvInspect.setText("正在更新。。。");
        } else {
            tvTitle.setText("无网络，请配置网络");
        }
    }

    /**
     * 更新小程序图片，该请求尽可能少的申请，有次数限制
     */
    private void upSmallRoutine() {
        if (MyNetWorkUtils.isNetworkAvailable(context)) {
            String sellerId = MyPreferenceUtils.getSp(context).getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).getSmallRoutine(DataFlushActivity.this, sellerId, VOLLEY_UPDATE_SMALL_QR);
            tvSmallRoutine.setText("正在更新。。。");
        } else {
            tvTitle.setText("无网络，请配置网络");
        }
    }

    /**
     * 更新副屏证件照及广告图
     */
    private void upSecondImage() {
        if (MyNetWorkUtils.isNetworkAvailable(context)) {
            String sellerId = MyPreferenceUtils.getSp(context).getString(SELLER_ID, DEFAULT_ID);
            HttpHelper.getmInstants(sysApplication).httpQuestImageEx22(DataFlushActivity.this, sellerId, VOLLEY_UPDATE_IMAGE);
            tvSecondImage.setText("正在更新。。。");
        } else {
            tvTitle.setText("无网络，请配置网络");
        }
    }


}

//ups/uploads/file/20190830/tim.jpg;
//ups/uploads/file/20190830/a315bb19e17f686b679ee773a1df5157.jpg;
//ups/uploads/file/20190830/1F32Q05306-6.jpg
//
//        https://data.axebao.com/smartsz/assets/files/20190520094255772.png