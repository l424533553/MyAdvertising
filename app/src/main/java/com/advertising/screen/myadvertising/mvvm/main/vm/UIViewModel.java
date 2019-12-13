package com.advertising.screen.myadvertising.mvvm.main.vm;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.advertising.screen.myadvertising.func.adapter.PriceAdapter;
import com.advertising.screen.myadvertising.common.iinterface.IConstants;
import com.advertising.screen.myadvertising.common.iinterface.DataRequestBack;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.MainUIBean;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.PriceEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.TodayTradeInfo;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.UserInfoEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;
import com.advertising.screen.myadvertising.mvvm.main.persistence.DataRepository;
import com.advertising.screen.myadvertising.mvvm.main.persistence.room.RoomHelper;
import com.xuanyuan.library.help.QRCodeUtil;
import com.xuanyuan.library.utils.log.MyLog;
import com.xuanyuan.library.utils.storage.MyPreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：罗发新
 * 时间：2019/12/6 0006    星期五
 * 邮件：424533553@qq.com
 * 说明：
 */
public class UIViewModel extends AndroidViewModel implements IConstants {

    private String TAG = getClass().getName();
    private DataRepository dataRepository;

    public MutableLiveData<MainUIBean> mainUILiveData;
    public MutableLiveData<Weather> weatherLiveData;
    public MutableLiveData<UserInfoEntity> userInfoLiveData;
    private ObservableArrayList<PriceEntity> priceData;
    public PriceAdapter adapter;
    public MainUIBean uiBean;
    private int priceIndex;
    private Application application;

    public UIViewModel(@NonNull Application application, @NonNull LifecycleOwner owner) {
        super(application);
        this.application = application;
        dataRepository = new DataRepository(application);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        weatherLiveData = new MutableLiveData<>();
        mainUILiveData = new MutableLiveData<>();
        userInfoLiveData = new MutableLiveData<>();

        priceData = new ObservableArrayList<>();
        adapter = new PriceAdapter(priceData);
        uiBean = new MainUIBean();

    }

    public void notifyData() {
        getWeather(DataRequestBack.DATA_TYPE_AUTO);
        String shellerId = MyPreferenceUtils.getString(SELLER_ID, "1112");
        getUserInfo(shellerId);
        todayTrade(shellerId);

        String stringZS = "https://data.axebao.com/smartsz/trace/trace2.php?companyid=" + shellerId;
        Bitmap bitmapZS = QRCodeUtil.createBitmap(stringZS, 30, 30);
        uiBean.setBitmapZS(bitmapZS);
        mainUILiveData.postValue(uiBean);

        getPriceData(shellerId);

    }

    /**
     * 获取要滑动的索引
     */
    public int getSmollPriceIndex() {
        int priceCount = adapter.getItemCount();
        if (priceCount > 0) {
            if (priceIndex == priceCount - 1) {
                priceIndex = 0;
            } else {
                priceIndex += 6;
            }

            if (priceIndex >= priceCount - 1) {
                priceIndex = priceCount - 1;
            }
            return priceIndex;
        } else {
            return -1;
        }
    }


    /**
     * 获取价格信息
     */
    private void getPriceData(String shellerId) {
        dataRepository.getTodayPrice(shellerId, false, new DataRequestBack<List<PriceEntity>>() {
            @Override
            public void onResponse(@NotNull List<PriceEntity> entitys, int dataType) {
                //TODO  待测试
                priceData.clear();
                priceData.addAll(entitys);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(@NotNull Throwable t, int dataType) {

            }
        });
    }

    /**
     * @param dataType 数据请求类型
     */
    public void getWeather(int dataType) {
        dataRepository.getWeather(dataType, new DataRequestBack<Weather>() {
            @Override
            public void onResponse(@NotNull Weather weather, int dataType) {
                weatherLiveData.postValue(weather);
                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    weather.setId(1);
                    long flag = RoomHelper.getDataBase(application.getApplicationContext()).weatherDao().insert(weather);
                    MyLog.sysLog("   ===  数据库插入 数据  ==" + flag);
                }
            }

            @Override
            public void onFailure(@NotNull Throwable t, int dataType) {
                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    getWeather(DataRequestBack.DATA_TYPE_DATABASE);
                }
            }
        });
    }

    public void getUserInfo(String shellerId) {
        dataRepository.getUserInfo(shellerId, false, new DataRequestBack<UserInfoEntity>() {
            @Override
            public void onResponse(@NotNull UserInfoEntity userInfoEntity, int dataType) {
                MyLog.bluelog(TAG, "ffaf" + userInfoEntity.toString());

                if (!TextUtils.isEmpty(userInfoEntity.getPhoto()) && userInfoEntity.getPhoto().length() > 0) {
                    String headUrl = userInfoEntity.getBaseurl() + userInfoEntity.getPhoto();
                    userInfoEntity.setHeadUrl(headUrl);
                }
                if (!TextUtils.isEmpty(userInfoEntity.getWxnote())) {
                    Bitmap wxbitma = QRCodeUtil.createBitmap(userInfoEntity.getWx(), 30, 30);
                    userInfoEntity.setWxQR(wxbitma);
                }
                if (!TextUtils.isEmpty(userInfoEntity.getZfbnote())) {
                    Bitmap zfbbitma = QRCodeUtil.createBitmap(userInfoEntity.getZfb(), 30, 30);
                    userInfoEntity.setZfbQR(zfbbitma);
                }

                List<String> ads = paserUrls(userInfoEntity.getAd(), userInfoEntity.getBaseurl());
                List<String> licences = paserUrls(userInfoEntity.getLicence(), userInfoEntity.getBaseurl());
                userInfoEntity.getUrls().clear();
                userInfoEntity.getUrls().addAll(licences);
                userInfoEntity.getUrls().addAll(ads);
                userInfoLiveData.postValue(userInfoEntity);
            }

            @Override
            public void onFailure(@NotNull Throwable t, int dataType) {

            }
        });
    }

    private List<String> paserUrls(String content, String baseUrl) {
        List<String> urls = new ArrayList<>();
        if (TextUtils.isEmpty(content)) {
            return urls;
        }
        String[] adArray = content.split(";");
        if (adArray.length <= 0) {
            return urls;
        }

        // 限制长度最长 为8
        int piclength;
        if (adArray.length > 8) {
            piclength = 8;
        } else {
            piclength = adArray.length;
        }

        for (int i = 0; i < piclength; i++) {
            String comUrl = adArray[i].trim();
            if (!TextUtils.isEmpty(comUrl)) {
                String netUrl = baseUrl + comUrl;
                urls.add(netUrl);
            }
        }
        return urls;
    }

    private void todayTrade(String shellerId) {
        dataRepository.todayTrade(shellerId, false, new DataRequestBack<TodayTradeInfo>() {
            @Override
            public void onResponse(@NotNull TodayTradeInfo todayTradeInfo, int dataType) {
                uiBean.getTodayCount().set(todayTradeInfo.getC());
                uiBean.getTodayWeight().set(todayTradeInfo.getW());
            }

            @Override
            public void onFailure(@NotNull Throwable t, int dataType) {

            }
        });
    }


}
