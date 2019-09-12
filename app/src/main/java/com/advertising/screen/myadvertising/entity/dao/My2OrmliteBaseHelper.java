package com.advertising.screen.myadvertising.entity.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.advertising.screen.myadvertising.entity.AdImageInfo;
import com.advertising.screen.myadvertising.entity.AdUserBean;
import com.advertising.screen.myadvertising.entity.InspectBean;
import com.advertising.screen.myadvertising.entity.PriceBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xuanyuan.library.utils.log.MyLog;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明：  Ormlite 的基础工具类
 * 作者：User_luo on 2018/4/23 10:26
 * 邮箱：424533553@qq.com
 * 数据库操作管理工具类
 * <p>
 * 我们需要自定义一个类继承自ORMlite给我们提供的OrmLiteSqliteOpenHelper，创建一个构造方法，重写两个方法onCreate()和onUpgrade()
 * 在onCreate()方法中使用TableUtils类中的createTable()方法初始化数据表
 * 在onUpgrade()方法中我们可以先删除所有表，然后调用onCreate()方法中的代码重新创建表
 * <p>
 * 我们需要对这个类进行单例，保证整个APP中只有一个SQLite Connection对象
 * <p>
 * 这个类通过一个Map集合来管理APP中所有的DAO，只有当第一次调用这个DAO类时才会创建这个对象（并存入Map集合中）
 * 其他时候都是直接根据实体类的路径从Map集合中取出DAO对象直接调用
 */
public class My2OrmliteBaseHelper extends OrmLiteSqliteOpenHelper {
    // 数据库名称
    private static String DATABASE_NAME = "myproject.db";

    public final static int version = 1;
    // 本类的单例实例
    private static My2OrmliteBaseHelper instance;

    // 存储APP中所有的DAO对象的Map集合
    protected Map<String, Dao> daos = new HashMap<>();

    // 获取本类单例对象的方法
    public static synchronized My2OrmliteBaseHelper getInstance(Context context) {
        MyLog.mylog("数据库版本22================ getInstance");
        if (instance == null) {
            synchronized (My2OrmliteBaseHelper.class) {
                if (instance == null) {
                    instance = new My2OrmliteBaseHelper(context);
                }
            }
        }
        return instance;
    }

    // 私有的构造方法
    protected My2OrmliteBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
        MyLog.mylog("数据库版本22================" + version);
    }

    // 私有的构造方法
    protected My2OrmliteBaseHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
        MyLog.mylog("数据库版本22 =======  version  =========" + version);
    }

    // 根据传入的DAO的路径获取到这个DAO的单例对象（要么从daos这个Map中获取，要么新创建一个并存入daos）
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override // 创建数据库时调用的方法
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        MyLog.mylog("数据库版本22 ================  onCreate");
        try {
            TableUtils.createTable(connectionSource, AdImageInfo.class);
            TableUtils.createTable(connectionSource, AdUserBean.class);
            TableUtils.createTable(connectionSource, PriceBean.class);
            TableUtils.createTable(connectionSource, InspectBean.class);
//            TableUtils.createTable(connectionSource, Deviceinfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override // 数据库版本更新时调用的方法
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    // 释放资源
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            if (dao != null) {
                dao = null;
            }
        }
    }
}
