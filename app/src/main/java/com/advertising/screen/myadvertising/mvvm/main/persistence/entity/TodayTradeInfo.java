package com.advertising.screen.myadvertising.mvvm.main.persistence.entity;

import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.advertising.screen.myadvertising.mvvm.main.persistence.room.TodayTradeDao;

/**
 * 作者：罗发新
 * 时间：2019/12/9 0009    星期一
 * 邮件：424533553@qq.com
 * 说明：
 */
@Entity(tableName = TodayTradeDao.TABLE_NAME)
public class TodayTradeInfo {

    /**
     * c : 180  今日交易笔数
     * w : 196.830
     * m : 1980.68
     */
    @PrimaryKey
    public int id;
    private int c;
    private String w;
    private String m;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getW() {
        if(TextUtils.isEmpty(w)){
            return "0";
        }
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getM() {
        if(TextUtils.isEmpty(m)){
            return "0";
        }
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
