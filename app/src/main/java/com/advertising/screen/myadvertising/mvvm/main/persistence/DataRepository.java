package com.advertising.screen.myadvertising.mvvm.main.persistence;


import android.app.Application;

import com.advertising.screen.myadvertising.common.CustomException;
import com.advertising.screen.myadvertising.common.iinterface.DataRequestBack;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.ApkBean;
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

    private final LocalDataSource localDataSource;
    private final NetDataSource netDataSource;

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

    public void getNewVersion(String marketId, @NotNull DataRequestBack<ApkBean> callBack) {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            netDataSource.getNewVersion(marketId, callBack);
        }
    }

    public void getUserInfo(String shellerId, int dataType, @NotNull DataRequestBack<UserInfoEntity> callBack) {
        if (dataType == DataRequestBack.DATA_TYPE_AUTO) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                netDataSource.getUserInfo(shellerId, callBack);
            } else {
                localDataSource.getUserInfo(callBack);
            }
        } else if (dataType == DataRequestBack.DATA_TYPE_NET) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                netDataSource.getUserInfo(shellerId, callBack);
            } else {
                CustomException e = new CustomException("无网络");
                callBack.onFailure(e, DATA_TYPE_NET);
            }
        } else if (dataType == DataRequestBack.DATA_TYPE_DATABASE) {
            localDataSource.getUserInfo(callBack);
        }
    }


    public void todayTrade(int dataType, String shllerId, @NotNull DataRequestBack<TodayTradeInfo> callBack) {
        if (dataType == DataRequestBack.DATA_TYPE_AUTO) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                netDataSource.todayTrade(shllerId, callBack);
            } else {
                localDataSource.todayTrade(callBack);
            }
        } else if (dataType == DataRequestBack.DATA_TYPE_NET) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                netDataSource.todayTrade(shllerId, callBack);
            } else {
                CustomException e = new CustomException("无网络");
                callBack.onFailure(e, DATA_TYPE_NET);
            }
        } else if (dataType == DataRequestBack.DATA_TYPE_DATABASE) {
            localDataSource.todayTrade(callBack);
        }
    }

    public void getTodayPrice(String shllerId, int dataType, @NotNull DataRequestBack<List<PriceEntity>> callBack) {
        if (dataType == DataRequestBack.DATA_TYPE_AUTO) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                netDataSource.getTodayPrice(shllerId, callBack);
            } else {
                localDataSource.getTodayPrice(callBack);
            }
        } else if (dataType == DataRequestBack.DATA_TYPE_NET) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                netDataSource.getTodayPrice(shllerId, callBack);
            } else {
                CustomException e = new CustomException("无网络");
                callBack.onFailure(e, DATA_TYPE_NET);
            }
        } else if (dataType == DataRequestBack.DATA_TYPE_DATABASE) {
            localDataSource.getTodayPrice(callBack);
        }
    }
}
