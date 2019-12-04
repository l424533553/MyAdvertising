package com.advertising.screen.myadvertising.data;


import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import android.graphics.Bitmap;

import com.advertising.screen.myadvertising.entity.InspectBean;
import com.advertising.screen.myadvertising.entity.PriceBean;

/**
 * 作者：罗发新
 * 时间：2019/9/17 0017    星期二
 * 邮件：424533553@qq.com
 * 说明：
 */
public class ScreenState extends BaseObservable {

    private Bitmap bitmapDP;
    private Bitmap bitmapZS;

    public ObservableBoolean getIsWifi() {
        return isWifi;
    }

    public void setIsWifi(ObservableBoolean isWifi) {
        this.isWifi = isWifi;
    }

    private ObservableArrayList<PriceBean> priceBeans = new ObservableArrayList<>();
    private ObservableBoolean isWifi = new ObservableBoolean();
    private ObservableArrayList<InspectBean> inspectBeans = new ObservableArrayList<>();

    public ObservableArrayList<PriceBean> getPriceBeans() {
        return priceBeans;
    }

    public void setPriceBeans(ObservableArrayList<PriceBean> priceBeans) {
        this.priceBeans = priceBeans;
    }

    public ObservableArrayList<InspectBean> getInspectBeans() {
        return inspectBeans;
    }

    public void setInspectBeans(ObservableArrayList<InspectBean> inspectBeans) {
        this.inspectBeans = inspectBeans;
    }



    public Bitmap getBitmapDP() {
        return bitmapDP;
    }

    public void setBitmapDP(Bitmap bitmapDP) {
        this.bitmapDP = bitmapDP;
    }

    public Bitmap getBitmapZS() {
        return bitmapZS;
    }

    public void setBitmapZS(Bitmap bitmapZS) {
        this.bitmapZS = bitmapZS;
    }
}
