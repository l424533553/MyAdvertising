package com.advertising.screen.myadvertising.mvvm.main.persistence;


import android.app.Application;

import com.advertising.screen.myadvertising.common.CustomException;
import com.advertising.screen.myadvertising.common.iinterface.DataRequestBack;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.PriceEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.TodayTradeInfo;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.UserInfoEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;
import com.xuanyuan.library.utils.net.MyNetWorkUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.advertising.screen.myadvertising.common.iinterface.DataRequestBack.DATA_TYPE_NET;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class DataRepository implements IDataRepository {

    private LocalDataSource localDataSource;
    private NetDataSource netDataSource;

    public DataRepository(Application application) {
        localDataSource = new LocalDataSource(application);
        netDataSource = new NetDataSource();
    }

    public void getWeather(int dataType, @NotNull DataRequestBack<Weather> callBack) {
        if (dataType == DataRequestBack.DATA_TYPE_AUTO) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                netDataSource.getWeather(callBack);
            } else {
                localDataSource.getWeather(callBack);
            }
        } else if (dataType == DataRequestBack.DATA_TYPE_NET) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                netDataSource.getWeather(callBack);
            } else {
                CustomException e = new CustomException("无网络");
                callBack.onFailure(e, DATA_TYPE_NET);
            }
        } else if (dataType == DataRequestBack.DATA_TYPE_DATABASE) {
            localDataSource.getWeather(callBack);
        }
    }

    @Override
    public void getUserInfo(String shellerId, boolean isLocalData, @NotNull DataRequestBack<UserInfoEntity> callBack) {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            netDataSource.getUserInfo(shellerId, isLocalData, callBack);
        } else {
            localDataSource.getUserInfo(shellerId, isLocalData, callBack);
        }
    }


    public void todayTrade(String shllerId, boolean isLocalData, @NotNull DataRequestBack<TodayTradeInfo> callBack) {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            netDataSource.todayTrade(shllerId, isLocalData, callBack);
        }
    }

    public void getTodayPrice(String shllerId, boolean isLocalData, @NotNull DataRequestBack<List<PriceEntity>> callBack) {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            netDataSource.getTodayPrice(shllerId, isLocalData, callBack);
        }
    }


    /**
     * 获取天气
     */
//    public void getWeather(boolean isLocalData, DataRequestBack<Weather> callBack) {
//        if (MyNetWorkUtils.isNetworkAvailable()) {
//            netDataSource.getWeather(callBack);
//        } else {
//            localDataSource.getWeather(callBack);
//        }
//    }


//    public void getWeather(MutableLiveData<Weather> weatherLiveData) {
////        if (MyNetWorkUtils.isNetworkAvailable()) {
////            netDataSource.getWeather();
////        } else {
////            localDataSource.getWeather();
////        }
////    }

}
