package com.axecom.smartweight.my.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 作者：罗发新
 * 时间：2019/8/28 0028    星期三
 * 邮件：424533553@qq.com
 * 说明：
 */
@DatabaseTable(tableName = "priceBean")
public class PriceBean {

    /**
     * avgprice : 53.00
     * name : 云耳
     */
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String avgprice;
    @DatabaseField
    private String name;

    public PriceBean() {
    }

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

