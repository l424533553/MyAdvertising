package com.advertising.screen.myadvertising.mvvm.main.vm;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.advertising.screen.myadvertising.common.iinterface.DataRequestBack;
import com.advertising.screen.myadvertising.common.iinterface.IConstants;
import com.advertising.screen.myadvertising.func.adapter.PriceAdapter;
import com.advertising.screen.myadvertising.mvvm.main.persistence.DataRepository;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.ApkBean;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.MainUIBean;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.PriceEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.TodayTradeInfo;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.UserInfoEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;
import com.advertising.screen.myadvertising.mvvm.main.persistence.room.RoomHelper;
import com.xuanyuan.library.apk_update.MyApkUtils;
import com.xuanyuan.library.help.QRCodeUtil;
import com.xuanyuan.library.utils.log.MyLog;
import com.xuanyuan.library.utils.net.MyNetWorkUtils;
import com.xuanyuan.library.utils.storage.MyPreferenceUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.advertising.screen.myadvertising.common.iinterface.DataRequestBack.DATA_TYPE_AUTO;

/**
 * 作者：罗发新
 * 时间：2019/12/6 0006    星期五
 * 邮件：424533553@qq.com
 * 说明：
 */
public class UIViewModel extends AndroidViewModel implements IConstants {

    private final String TAG = getClass().getName();
    private final DataRepository dataRepository;
    public MutableLiveData<MainUIBean> mainUILiveData;
    public MutableLiveData<String> versionInfoLiveData;
    public MutableLiveData<Weather> weatherLiveData;
    public MutableLiveData<UserInfoEntity> userInfoLiveData;
    private ObservableArrayList<PriceEntity> priceData;
    public PriceAdapter adapter;
    public MainUIBean uiBean;
    private int priceIndex;
    private Application application;

    @NotNull
    public Application getApplication() {
        return application;
    }

    public UIViewModel(@NonNull Application application) {
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
        versionInfoLiveData = new MutableLiveData<>();

        priceData = new ObservableArrayList<>();
        adapter = new PriceAdapter(priceData);
        uiBean = new MainUIBean();
    }

    public void notifyData() {

        MyLog.sysLog("MyJobService", "开始了main 中的 notifyData");
        //设定网络
        if (MyNetWorkUtils.isNetworkAvailable()) {
            uiBean.getIsWifi().set(true);
        } else {
            uiBean.getIsWifi().set(false);
        }

        String shellerId = MyPreferenceUtils.getString(IConstants.SELLER_ID, "0");
        if ("0".equals(shellerId)) {
            return;
        }

        getWeather(DATA_TYPE_AUTO);
        getUserInfo(shellerId, DATA_TYPE_AUTO);
        todayTrade(DATA_TYPE_AUTO, shellerId);

        String stringZS = "https://data.axebao.com/smartsz/trace/trace2.php?companyid=" + shellerId;
        Bitmap bitmapZS = QRCodeUtil.createBitmap(stringZS, 30, 30);
        uiBean.setBitmapZS(bitmapZS);
        mainUILiveData.postValue(uiBean);
        getPriceData(shellerId, DATA_TYPE_AUTO);

    }

    /**
     * 检查版本
     */
    public void checkVersion() {
        int marketId = MyPreferenceUtils.getInt(IConstants.DATA_MARK_ID, 0);
        if (0 != (marketId)) {
            getNewVersion(String.valueOf(marketId));
        }
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
     * VM 获取价格信息
     */
    private void getPriceData(String shellerId, int DATA_TYPE_AUTO) {
        dataRepository.getTodayPrice(shellerId, DATA_TYPE_AUTO, new DataRequestBack<List<PriceEntity>>() {
            @Override
            public void onResponse(@NotNull List<PriceEntity> entitys, int dataType) {
                priceData.clear();
                priceData.addAll(entitys);
                adapter.notifyDataSetChanged();
                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    RoomHelper.getDataBase(getApplication().getApplicationContext()).priceDao().deleteAll();
                    RoomHelper.getDataBase(getApplication().getApplicationContext()).priceDao().insertList(entitys);
                }
            }

            @Override
            public void onFailure(@NotNull Throwable t, int dataType) {
                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    getPriceData(shellerId, DataRequestBack.DATA_TYPE_DATABASE);
                }
            }
        });
    }

    /**
     * VM
     *
     * @param dataType 数据请求类型
     */
    private void getWeather(int dataType) {
        dataRepository.getWeather(dataType, new DataRequestBack<Weather>() {
            @Override
            public void onResponse(@NotNull Weather weather, int dataType) {
                weatherLiveData.postValue(weather);
                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    weather.setId(1);
                    long flag = RoomHelper.getDataBase(application.getApplicationContext()).weatherDao().insert(weather);
                    MyLog.sysLog(TAG, "   ===  数据库插入 数据  ==" + flag);
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

    /**
     * VM
     */
    private void getNewVersion(String marketId) {
        dataRepository.getNewVersion(marketId, new DataRequestBack<ApkBean>() {
            @Override
            public void onResponse(@NotNull ApkBean entity, int dataType) {
                String version = entity.getVersion();
                String versionName = MyApkUtils.getVersionName(application.getApplicationContext());
                if (versionName.compareTo(version) < 0) {
                    //进行更新
                    versionInfoLiveData.postValue(entity.getFilepath());
                }

            }

            @Override
            public void onFailure(@NotNull Throwable t, int dataType) {

            }
        });
    }

    /**
     * VM  获取 用户信息
     */
    private void getUserInfo(String shellerId, int DATA_TYPE_AUTO) {
        dataRepository.getUserInfo(shellerId, DATA_TYPE_AUTO, new DataRequestBack<UserInfoEntity>() {
            @Override
            public void onResponse(@NotNull UserInfoEntity userInfoEntity, int dataType) {
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
                MyPreferenceUtils.getSp().edit().putInt(IConstants.DATA_MARK_ID, userInfoEntity.getMarketid()).apply();

                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    userInfoEntity.setId(1);
                    long flag = RoomHelper.getDataBase(application.getApplicationContext()).userInfoDao().insert(userInfoEntity);
                    MyLog.sysLog("   ===  数据库插入 数据  ==" + flag);
                }
            }

            @Override
            public void onFailure(@NotNull Throwable t, int dataType) {
                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    getUserInfo(shellerId, DataRequestBack.DATA_TYPE_DATABASE);
                }
            }
        });
    }

    /**
     * VM  解析图片 url
     */
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

    /**
     * VM  获取今日 交易数据
     */
    private void todayTrade(int DATA_TYPE_AUTO, String shellerId) {
        dataRepository.todayTrade(DATA_TYPE_AUTO, shellerId, new DataRequestBack<TodayTradeInfo>() {
            @Override
            public void onResponse(@NotNull TodayTradeInfo todayTradeInfo, int dataType) {
                uiBean.getTodayCount().set(todayTradeInfo.getC());
                uiBean.getTodayWeight().set(todayTradeInfo.getW());

                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    todayTradeInfo.setId(1);
                    long flag = RoomHelper.getDataBase(application.getApplicationContext()).todayTradeDao().insert(todayTradeInfo);
                    MyLog.sysLog("   ===  数据库插入 数据  ==" + flag);
                }
            }

            @Override
            public void onFailure(@NotNull Throwable t, int dataType) {
                if (dataType == DataRequestBack.DATA_TYPE_NET) {
                    todayTrade(DataRequestBack.DATA_TYPE_DATABASE, shellerId);
                }
            }
        });
    }

}
