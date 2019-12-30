package com.advertising.screen.myadvertising.mvvm.main.persistence.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.PriceEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.TodayTradeInfo;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.UserInfoEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;
import com.xuanyuan.arrlibrary.room.entity.Converters;

@Database(entities = {
        Weather.class,
        TodayTradeInfo.class,
        UserInfoEntity.class,
        PriceEntity.class
}, version = 3)
@TypeConverters({Converters.class})
public abstract class MyAppDataBase extends RoomDatabase {

    public abstract WeatherDao weatherDao();

    public abstract TodayTradeDao todayTradeDao();

    public abstract UserInfoDao userInfoDao();

    public abstract PriceDao priceDao();

}