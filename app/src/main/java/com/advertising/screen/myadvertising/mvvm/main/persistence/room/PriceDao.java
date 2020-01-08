package com.advertising.screen.myadvertising.mvvm.main.persistence.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.PriceEntity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * dao  在编译期就会自动报错，强大的一匹
 */
@Dao
public interface PriceDao {
    String TABLE_NAME = "Price";

    /**
     * onConflict：表示当插入有冲突的时候的处理策略。OnConflictStrategy封装了Room解决冲突的相关策略：
     * OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务。
     * OnConflictStrategy.ROLLBACK：冲突策略是回滚事务。
     * OnConflictStrategy.ABORT：冲突策略是终止事务。默认策略
     * OnConflictStrategy.FAIL：冲突策略是事务失败。
     * OnConflictStrategy.IGNORE：冲突策略是忽略冲突。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(PriceEntity... beans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<PriceEntity> beans);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PriceEntity bean);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertListRt(PriceEntity... beans);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateList(PriceEntity... beans);

    @Delete
    int deleteList(PriceEntity... beans);


    @Query("DELETE  FROM " + TABLE_NAME)
    int deleteAll();

    @Query("SELECT * FROM " + TABLE_NAME)
    List<PriceEntity> findAll();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE id == :value")
    List<PriceEntity> findByColumnName(int value);

    /**
     * Rxjava2 中的 对象
     */
    @Query("SELECT * from " + TABLE_NAME)
    Flowable<List<PriceEntity>> loadList();

}