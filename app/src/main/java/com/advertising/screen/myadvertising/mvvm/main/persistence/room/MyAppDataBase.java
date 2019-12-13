package com.advertising.screen.myadvertising.mvvm.main.persistence.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.MainUIBean;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;
import com.xuanyuan.arrlibrary.room.entity.Converters;

@Database(entities = {
        Weather.class,
}, version = 3)
@TypeConverters({Converters.class})
public abstract class MyAppDataBase extends RoomDatabase {

    public abstract WeatherDao weatherDao();

//    public abstract BookDao bookDao();

}