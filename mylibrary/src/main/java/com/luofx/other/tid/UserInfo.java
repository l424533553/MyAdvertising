package com.luofx.other.tid;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * author: luofaxin
 * date： 2018/9/26 0026.
 * email:424533553@qq.com
 * describe:
 */
@DatabaseTable(tableName = "userinfo")
public class UserInfo {
    /**
     * marketid : 1
     * marketname : 黄田市场
     * companyno : B070
     * tid : 101
     * seller : 郭金龙
     * sellerid : 127
     * key : null
     * mchid : null
     */
    //部门编号
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int marketid;
    @DatabaseField
    private String marketname;
    @DatabaseField
    private String companyno;
    @DatabaseField
    private int tid;
    @DatabaseField
    private String seller;
    @DatabaseField
    private int sellerid;
    @DatabaseField
    private String key;
    @DatabaseField
    private String mchid;

    public UserInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public int getMarketid() {
        return marketid;
    }

    public void setMarketid(int marketid) {
        this.marketid = marketid;
    }

    public String getMarketname() {
        return marketname;
    }

    public void setMarketname(String marketname) {
        this.marketname = marketname;
    }

    public String getCompanyno() {
        return companyno;
    }

    public void setCompanyno(String companyno) {
        this.companyno = companyno;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public String getKey() {
        return key;
    }

    public String getMchid() {
        return mchid;
    }
}


/*data: {
steelyardtype:0  int 0:商通称,1：香山7.0,2:香山15.6-1:其他型号秤待定
        userinfo：{
        marketid:11  int  市场编号
         marketname:"黄田市场"  String
          companyno:"B070"  String
           tid:101   int
           seller:"郭金龙"  String
           sellerid:127  int
        }

        errorinfo：{
            classpath:"com.axecom.smartweight.my.entity.MainActivity"
            errormessage:" fasf "  String
            errortime:" 2018-01-01"  String
        }

        deviceinfo:{
            release:""  String  //系统版本	RELEASE	获取系统版本字符串。如4.1.2 或2.2 或2.3等	4.4.4
            sdk：""  String 系统版本值 SDK 系统的API级别 一般使用下面大的SDK_INT 来查看 19
            brand:""  String 品牌 BRAND 获取设备品牌 Huawei
            model:""  String 型号 MODEL

            networkoperatorname:"" String   网络类型名 getNetworkOperatorName 返回移动网络运营商的名字(SPN)中国联通
            networktype："" String  网络类型 getNetworkType 3
            phonetype："" 手机类型 getPhoneType 手机类型 1
            mac：""mac地址 getMacAddress

        }
}*/