package com.advertising.screen.myadvertising.entity;


import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * author: luofaxin
 * date： 2018/11/12 0012.
 * email:424533553@qq.com
 * describe:
 */
@DatabaseTable(tableName = "AdUserbean")
public class AdUserBean {
    /**
     * licence : ups/uploads/file/20190902/20170531084035264_0001.jpg
     * ad :
     * photo : assets/files/20190902105319436.png
     * marketname : 黄田智慧农贸市场
     * marketid : 1
     * companyno : B102
     * introduce : 蔬菜档
     * adcontent : 这是一个测试广告
     * companyname : 内测03
     * linkphone : 13056489536
     * companyid : 1152
     * status :
     * baseurl : https://data.axebao.com/smartsz/
     */

    /**
     * licence : ups/uploads/file/20190902/20170531084035264_0001.jpg
     * ad :
     * photo : assets/files/20190902105319436.png
     * marketname : 黄田智慧农贸市场
     * marketid : 1
     * companyno : B102
     * introduce : 蔬菜档
     * adcontent : 这是一个测试广告
     * companyname : 内测03
     * linkphone : 13056489536
     * companyid : 1152
     * status :
     * baseurl : https://data.axebao.com/smartsz/
     * commcontent : 欢迎顾客扫描追溯码，可以知道菜品检疫检测信息及来源，食用更放心；扫码点评，为经营户点赞！
     */
    @DatabaseField(id = true)
    private int id;
    private String licence;
    private String ad;
    private String photo;
    @DatabaseField
    private String companyno;
    @DatabaseField
    private String introduce;
    @DatabaseField
    private String companyname;
    @DatabaseField
    private String linkphone;
    @DatabaseField
    private String companyid;
    //自定义广告
    @DatabaseField
    private String adcontent;
    private String baseurl;
    public String status;
    @DatabaseField
    private String marketname;
    @DatabaseField
    private int marketid;
    //市场公共广告
    @DatabaseField
    private String commcontent;


    public AdUserBean() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdcontent() {
        return adcontent;
    }

    public void setAdcontent(String adcontent) {
        this.adcontent = adcontent;
    }

    public String getLicence() {
        return licence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getMarketname() {
        if (marketname == null) {
            return "智慧农贸市场";
        }
        return marketname;
    }

    public void setMarketname(String marketname) {
        this.marketname = marketname;
    }

    @NonNull
    @Override
    public String toString() {
        return "AdUserBean{" +
                "id=" + id +
                ", licence='" + licence + '\'' +
                ", ad='" + ad + '\'' +
                ", photo='" + photo + '\'' +
                ", companyno='" + companyno + '\'' +
                ", introduce='" + introduce + '\'' +
                ", companyname='" + companyname + '\'' +
                ", linkphone='" + linkphone + '\'' +
                ", companyid='" + companyid + '\'' +
                ", adcontent='" + adcontent + '\'' +
                ", baseurl='" + baseurl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}

//		"licence": "ups\/uploads\/file\/20190118\/tim.jpg;ups\/uploads\/file\/20190118\/tim.jpg;ups\/uploads\/file\/20190118\/tim.jpg",
//                "ad": "ups\/uploads\/file\/20190118\/u=257373811,4016047896&fm=26&gp=0.jpg",
//                "photo": "assets\/files\/20190118115808442.jpg",
//                "companyno": "",
//                "introduce": "\u5e72\u8d27\u6863",
//                "adcontent": "",
//                "companyname": "\u5185\u6d4b01",
//                "linkphone": "",
//                "companyid": "1150",
//                "status": "",
//                "baseurl": "https:\/\/data.axebao.com\/smartsz\/"

//https:\/\/data.axebao.com\/smartsz\/ups\/uploads\/file\/20190118\/u=257373811,4016047896&fm=26&gp=0.jpg
//https:\/\/data.axebao.com\/smartsz\/assets\/files\/20190118115808442.jpg
//
//https:\/\/data.axebao.com\/smartsz\/ups\/uploads\/file\/20190118\/tim.jpg;ups\/uploads\/file\/20190118\/tim.jpg;ups\/uploads\/file\/20190118\/tim.jpg