package com.advertising.screen.myadvertising.entity;


import android.content.Context;
import android.database.Cursor;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.luofx.entity.dao.OrmliteBaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * 说明：操作article表的DAO类
 * 作者：User_luo on 2018/4/23 10:47
 * 邮箱：424533553@qq.com
 */
@SuppressWarnings("ALL")
public class ImageDao {
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<AdImageInfo, Integer> dao;
    private My2OrmliteBaseHelper myOrmliteBaseHelper;

    public ImageDao(Context context) {
        try {
            myOrmliteBaseHelper = My2OrmliteBaseHelper.getInstance(context.getApplicationContext());
            this.dao = myOrmliteBaseHelper.getDao(AdImageInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ImageDao baseDao;

    public  static ImageDao getInstance(Context context) {
        if (baseDao == null) {
            baseDao = new ImageDao(context);
        }
        return baseDao;
    }

    // 添加数据
    public int insert(AdImageInfo data) {
        try {
            //noinspection unchecked
            return dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 添加数据集
    public int inserts(List<AdImageInfo> datas) {
        try {
            //noinspection unchecked
            return dao.create(datas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    // 删除数据
    public int delete(AdImageInfo data) {
        try {
            return dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 删除表中的数据，非删除表
    public int deleteAll() {
        try {
            return dao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 删除数据
    public void delete2(AdImageInfo data) {
        try {

//            SELECT DATE_FORMAT(f.created_at,'%Y-%m-%d') days
//            FROM samplerules f   GROUP BY DATE_FORMAT(f.created_at,'%Y-%m-%d')

            dao.queryBuilder().groupBy("f").query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 有条件的更新字段值
     *
     * @param conditionColumnName 条件列名
     * @param conditionValue      条件值
     * @param updateColumnName    更新列名
     * @param updateValue         更新值
     * @return
     */
    public int update(String conditionColumnName, Object conditionValue, String updateColumnName, Object updateValue) {
        int rows = -1;
        try {
            UpdateBuilder updateBuilder = dao.updateBuilder();
            updateBuilder.where().eq(conditionColumnName, conditionValue);
            return updateBuilder.updateColumnValue(updateColumnName, updateValue).update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    // 修改数据
    public int update(AdImageInfo data) {
        int rows = -1;
        try {
            rows = dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    /**
     * 更新 数据集
     *
     * @param datas 数据集
     * @return
     */
    public int updates(List<AdImageInfo> datas) {
        int rows = -1;
        try {
            for (AdImageInfo bean : datas) {
                rows += dao.update(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    // 修改或者插入数据
    public boolean updateOrInsert(AdImageInfo data) {
        try {
            Dao.CreateOrUpdateStatus createOrUpdateStatus = dao.createOrUpdate(data);
            return createOrUpdateStatus.isCreated() || createOrUpdateStatus.isUpdated();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // 数据表是否存在
    public boolean isTableExists() {
        try {
            return dao.isTableExists();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //    // 通过列名对应值查找
    public List<AdImageInfo> queryByColumnName(String columnName, Object value) {
        try {
            return dao.queryBuilder().where().like(columnName, "%" + value + "%").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 通过ID查询一条数据
    public AdImageInfo queryById(int id) {
        AdImageInfo bean = null;
        try {
            bean = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    // 通过条件查询文章集合（通过用户ID查找）
    public List<AdImageInfo> queryByUserName(String COLUMNNAME_NAME, String userName) {
        try {
            return dao.queryBuilder().where().eq(COLUMNNAME_NAME, userName).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param day    2018-01-09格式
     * @param isAsce true 升序 ，false 降序
     * @return
     */
    public List<AdImageInfo> queryByDay(String day, boolean isAsce) {
        try {
            return dao.queryBuilder().orderBy("time", false).limit(3L).where().like("time", day + "%").query(); //参数false表示降序，true表示升序。
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查找所有bean
    public List<AdImageInfo> queryAll() {
        try {
//            return dao.queryForAll();
            return dao.queryBuilder().where().gt("type", 0).query();
//                    .orderBy("time", false)
//                    .limit(3L)

//                    .where().like("time", day + "%")
//                    .query(); //参数false表示降序，true表示升序。
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查找所有bean
    public List<AdImageInfo> queryPhoto() {
        try {
            return dao.queryBuilder().where().eq("type", 0).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void queryBySql(String name) {
        String[] str = new String[0];
//        String sql = "SELECT DATE_FORMAT(f.time,'%Y-%m-%d') time  FROM orderinfo f   GROUP BY DATE_FORMAT(f.time,'%Y-%m-%d')";
        String sql = "SELECT f.time  FROM orderinfo f   GROUP BY time";
        Cursor cursor = myOrmliteBaseHelper.getReadableDatabase().rawQuery(sql, str);
        cursor.close();
    }

    // 查找所有bean
    public List<AdImageInfo> queryAllTest() {
        try {
//            return dao.queryBuilder().where().eq("Status", 0).query();

//       GenericRawResults<T[]> afa= dao.query("","fasf");


            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}