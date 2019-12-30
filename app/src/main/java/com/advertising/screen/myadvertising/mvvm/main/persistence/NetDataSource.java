package com.advertising.screen.myadvertising.mvvm.main.persistence;

import com.advertising.screen.myadvertising.common.CustomException;
import com.advertising.screen.myadvertising.common.iinterface.DataRequestBack;
import com.advertising.screen.myadvertising.func.retrofit.IRetrofitAPI;
import com.advertising.screen.myadvertising.func.retrofit.RetrofitHttpUtils;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.ApkBean;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.NetResultInfo;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.PriceEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.TodayTradeInfo;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.UserInfoEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.advertising.screen.myadvertising.common.iinterface.DataRequestBack.DATA_TYPE_NET;

/**
 * 作者：罗发新
 * 时间：2019/12/6 0006    星期五
 * 邮件：424533553@qq.com
 * 说明：
 */
public class NetDataSource implements IDataRepository {

    public void getWeather(@NotNull DataRequestBack<Weather> callBack) {
        Call<Weather> call = RetrofitHttpUtils.getRetrofitAPI(IRetrofitAPI.class).getWeather();
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(@NotNull Call<Weather> call, @NotNull Response<Weather> response) {
                Weather weather = response.body();
                if (weather != null) {
                    weather.setCity(weather.getCityInfo().getCity());
                    weather.setParent(weather.getCityInfo().getParent());
                    weather.setWeek(weather.getData().getForecast().get(0).getWeek());
                    weather.setYmd(weather.getData().getForecast().get(0).getYmd());
                    weather.setType(weather.getData().getForecast().get(0).getType());
                    callBack.onResponse(weather, DATA_TYPE_NET);
                } else {
                    CustomException e = new CustomException("获取天气请求返回空内容");
                    callBack.onFailure(e, DATA_TYPE_NET);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Weather> call, @NotNull Throwable t) {
                callBack.onFailure(t, DATA_TYPE_NET);
            }
        });
    }

    public void getNewVersion(String marketId, @NotNull DataRequestBack<ApkBean> callBack) {
        Observable<NetResultInfo<ApkBean>> call = RetrofitHttpUtils.getRetrofitAPI(IRetrofitAPI.class).getNewVersion(marketId, "2");
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NetResultInfo<ApkBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetResultInfo<ApkBean> netResultInfo) {
                        if (netResultInfo.getStatus() == 0) {
                            ApkBean entity = netResultInfo.getData();
                            callBack.onResponse(entity, DATA_TYPE_NET);
                        } else {
                            CustomException e1 = new CustomException(netResultInfo.getMsg());
                            callBack.onFailure(e1, DATA_TYPE_NET);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(e, DATA_TYPE_NET);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void getUserInfo(String shellerId, @NotNull DataRequestBack<UserInfoEntity> callBack) {
        Observable<NetResultInfo<UserInfoEntity>> call = RetrofitHttpUtils.getRetrofitAPI(IRetrofitAPI.class).getUserInfo(shellerId);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NetResultInfo<UserInfoEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetResultInfo<UserInfoEntity> netResultInfo) {
                        if (netResultInfo.getStatus() == 0) {
                            UserInfoEntity entity = netResultInfo.getData();
                            callBack.onResponse(entity, DATA_TYPE_NET);
                        } else {
                            CustomException e1 = new CustomException(netResultInfo.getMsg());
                            callBack.onFailure(e1, DATA_TYPE_NET);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(e, DATA_TYPE_NET);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getTodayPrice(String shellerId, @NotNull DataRequestBack<List<PriceEntity>> callBack) {
        Observable<NetResultInfo<List<PriceEntity>>> call = RetrofitHttpUtils.getRetrofitAPI(IRetrofitAPI.class).getTodayPrice(shellerId);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NetResultInfo<List<PriceEntity>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetResultInfo<List<PriceEntity>> netResultInfo) {
                        if (netResultInfo.getStatus() == 0) {
                            List<PriceEntity> entitys = netResultInfo.getData();
                            callBack.onResponse(entitys, DATA_TYPE_NET);
                        } else {
                            CustomException e1 = new CustomException(netResultInfo.getMsg());
                            callBack.onFailure(e1, DATA_TYPE_NET);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(e, DATA_TYPE_NET);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void todayTrade(String shllerId, @NotNull DataRequestBack<TodayTradeInfo> callBack) {
        Observable<TodayTradeInfo> call = RetrofitHttpUtils.getRetrofitAPI(IRetrofitAPI.class).todayTrade(shllerId);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TodayTradeInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TodayTradeInfo todayTradeInfo) {
                        callBack.onResponse(todayTradeInfo, DATA_TYPE_NET);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailure(e, DATA_TYPE_NET);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
