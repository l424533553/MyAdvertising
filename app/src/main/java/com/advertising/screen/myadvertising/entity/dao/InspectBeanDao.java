//package com.advertising.screen.myadvertising.entity.dao;
//
//
//import android.content.Context;
//
//import com.advertising.screen.myadvertising.entity.InspectBean;
//import com.j256.ormlite.dao.Dao;
//import com.xuanyuan.library.base.application.MyBaseApplication;
//
//import java.sql.SQLException;
//import java.util.List;
//
///**
// * 说明：操作article表的DAO类
// * 作者：User_luo on 2018/4/23 10:47
// * 邮箱：424533553@qq.com
// */
//@SuppressWarnings("ALL")
//public class InspectBeanDao {
//    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
//    private Dao<InspectBean, Integer> dao;
//
//    public InspectBeanDao(Context context) {
//        try {
//            this.dao = My2OrmliteBaseHelper.getInstance().getDao(InspectBean.class);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static InspectBeanDao baseDao;
//
//    public static InspectBeanDao getInstance() {
//        if (baseDao == null) {
//            baseDao = new InspectBeanDao(MyBaseApplication.getInstance());
//        }
//        return baseDao;
//    }
//
//    // 添加数据
//    public int insert(InspectBean data) {
//        try {
//            //noinspection unchecked
//            return dao.create(data);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//    // 添加数据
//    public int insert(List<InspectBean> datas) {
//        try {
//            //noinspection unchecked
//            return dao.create(datas);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//    // 删除数据
//    public int deleteAll() {
//        try {
//            return dao.deleteBuilder().delete();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//    // 删除数据
//    public void delete(InspectBean data) {
//        try {
//            dao.delete(data);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 修改数据
//    public int update(InspectBean data) {
//        int rows = -1;
//        try {
//            rows = dao.update(data);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rows;
//    }
//
//    // 修改或者插入数据
//    public boolean updateOrInsert(InspectBean data) {
//        try {
//            Dao.CreateOrUpdateStatus createOrUpdateStatus = dao.createOrUpdate(data);
//            return createOrUpdateStatus.isCreated() || createOrUpdateStatus.isUpdated();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // 数据表是否存在
//    public boolean isTableExists() {
//        try {
//            return dao.isTableExists();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    //    // 通过条件查询文章集合（通过用户ID查找）
//    public List<InspectBean> queryByName(String name) {
//        try {
//            return dao.queryBuilder().where().like("name", "%" + name + "%").query();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    //    // 通过条件查询文章集合（通过用户ID查找）
//    public List<InspectBean> queryByTypeId(int id) {
//        try {
//            if (id == -1) {
//                return dao.queryForAll();
//            }
//            return dao.queryBuilder().where().eq("typeid", id).query();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // 通过ID查询一条数据
//    public InspectBean queryById(int id) {
//        InspectBean article = null;
//        try {
//            article = dao.queryForId(id);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return article;
//    }
//
//    // 通过条件查询文章集合（通过用户ID查找）
//    public List<InspectBean> queryByUserName(String COLUMNNAME_NAME, String userName) {
//        try {
//            return dao.queryBuilder().where().eq(COLUMNNAME_NAME, userName).query();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // 查找所有bean
//    public List<InspectBean> queryAll() {
//        try {
////            return dao.queryBuilder().where().eq("Status", 0).query();
//            return dao.queryForAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}