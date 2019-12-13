package com.advertising.screen.myadvertising.mvvm.main.persistence;

import android.app.Application;
import android.content.Context;

import com.advertising.screen.myadvertising.common.CustomException;
import com.advertising.screen.myadvertising.common.iinterface.DataRequestBack;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.UserInfoEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;
import com.advertising.screen.myadvertising.mvvm.main.persistence.room.RoomHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * 作者：罗发新
 * 时间：2019/12/6 0006    星期五
 * 邮件：424533553@qq.com
 * 说明：
 */
public class LocalDataSource implements IDataRepository {

    private Context context;

    public LocalDataSource(Application application) {
        context = application.getApplicationContext();
    }

    public void getWeather(@NotNull DataRequestBack<Weather> callBack) {
        List<Weather> list = RoomHelper.getDataBase(context).weatherDao().findByColumnName( 1);
        if (list != null) {
            if (list.size() >= 1) {
                Weather weather = list.get(0);
                callBack.onResponse(weather, DataRequestBack.DATA_TYPE_DATABASE);
                return;
            }
        }
        CustomException e = new CustomException("未获取到有效数据");
        callBack.onFailure(e, DataRequestBack.DATA_TYPE_DATABASE);
    }

    @Override
    public void getUserInfo(String shellerId, boolean isLocalData, @NotNull DataRequestBack<UserInfoEntity> callBack) {

    }


}
