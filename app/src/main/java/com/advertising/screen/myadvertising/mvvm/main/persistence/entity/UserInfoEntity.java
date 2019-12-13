package com.advertising.screen.myadvertising.mvvm.main.persistence.entity;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：罗发新
 * 时间：2019/12/9 0009    星期一
 * 邮件：424533553@qq.com
 * 说明：
 */
public class UserInfoEntity {

    /**
     * licence : ups/uploads/file/20191120/B046.jpg;ups/uploads/file/20191115/mmexport1573777983906.jpg
     * ad : ups/uploads/file/20191206/u=677301244,183697913&fm=26&gp=0.jpg;ups/uploads/file/20191206/下载 (3).jpg
     * photo : assets/files/20190725142200809.jpg
     * marketname : 黄田智慧农贸市场
     * commcontent : 欢迎顾客扫描溯源码，可以知道菜品检疫检测信息及来源，食用更放心；扫码点评，为商户点赞！
     * marketid : 1
     * companyno : B046
     * introduce : 豆腐
     * adcontent :
     * companyname : 陈冬慧
     * linkphone : 13925273118
     * companyid : 1112
     * status : 0
     * wx :
     * wxnote :
     * zfb :
     * zfbnote :
     * baseurl : https://data.axebao.com/smartsz/ups/uploads/file/20191206/下载 (3).jpg
     */

    private String licence;
    private String ad;
    private String photo;
    private String marketname;
    private String commcontent;
    private int marketid;
    private String companyno;
    private String introduce;
    private String adcontent;
    private String companyname;
    private String linkphone;
    private int companyid;
    private String status;
    private String wx;
    private String wxnote;
    private String zfb;
    private String zfbnote;
    private String baseurl;


    /* 非永久层数据**************************************************/
    private Bitmap wxQR;
    private Bitmap zfbQR;
    //TODO 数据库字段
    private String headUrl;

    private List<String> urls;


    public String getHeadUrl() {
        if (headUrl == null) {
            return null;
        } else if (headUrl.length() < 3) {
            return null;
        }
        return headUrl;
    }


    public List<String> getUrls() {
        if (urls == null) {
            urls = new ArrayList<>();
        }
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Bitmap getWxQR() {
        return wxQR;
    }

    public void setWxQR(Bitmap wxQR) {
        this.wxQR = wxQR;
    }

    public Bitmap getZfbQR() {
        return zfbQR;
    }

    public void setZfbQR(Bitmap zfbQR) {
        this.zfbQR = zfbQR;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMarketname() {
        return marketname;
    }

    public void setMarketname(String marketname) {
        this.marketname = marketname;
    }

    public String getCommcontent() {
        return commcontent;
    }

    public void setCommcontent(String commcontent) {
        this.commcontent = commcontent;
    }

    public int getMarketid() {
        return marketid;
    }

    public void setMarketid(int marketid) {
        this.marketid = marketid;
    }

    public String getCompanyno() {
        return companyno;
    }

    public void setCompanyno(String companyno) {
        this.companyno = companyno;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAdcontent() {
        return adcontent;
    }

    public void setAdcontent(String adcontent) {
        this.adcontent = adcontent;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getLinkphone() {
        return linkphone;
    }

    public void setLinkphone(String linkphone) {
        this.linkphone = linkphone;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getWxnote() {
        return wxnote;
    }

    public void setWxnote(String wxnote) {
        this.wxnote = wxnote;
    }

    public String getZfb() {
        return zfb;
    }

    public void setZfb(String zfb) {
        this.zfb = zfb;
    }

    public String getZfbnote() {
        return zfbnote;
    }

    public void setZfbnote(String zfbnote) {
        this.zfbnote = zfbnote;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

}
