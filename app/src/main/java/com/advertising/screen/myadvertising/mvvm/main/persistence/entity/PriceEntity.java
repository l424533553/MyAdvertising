package com.advertising.screen.myadvertising.mvvm.main.persistence.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.advertising.screen.myadvertising.mvvm.main.persistence.room.PriceDao;

/**
 * 作者：罗发新
 * 时间：2019/12/9 0009    星期一
 * 邮件：424533553@qq.com
 * 说明：
 */
@Entity(tableName = PriceDao.TABLE_NAME)
public class PriceEntity {
    /**
     * avgprice : 13.11
     * name : 油豆腐
     */

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String avgprice;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvgprice() {
        return avgprice;
    }

    public void setAvgprice(String avgprice) {
        this.avgprice = avgprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
