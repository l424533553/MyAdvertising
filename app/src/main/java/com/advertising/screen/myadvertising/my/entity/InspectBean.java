package com.advertising.screen.myadvertising.my.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 作者：罗发新
 * 时间：2019/8/28 0028    星期三
 * 邮件：424533553@qq.com
 * 说明：检测说明，使用情况
 */
@DatabaseTable(tableName = "inspectBean")
public class InspectBean {

    /**
     * status : 0
     * msg : ok
     * data : [{"gsmc":"生菜","jcjg":"1.53","hgzt":"合格","sjsj":"2019-08-28 07:59:51.000"},{"gsmc":"茄子","jcjg":"2.18","hgzt":"合格","sjsj":"2019-08-28 07:59:47.000"},{"gsmc":"油菜","jcjg":"9.76","hgzt":"合格","sjsj":"2019-08-28 07:59:43.000"},{"gsmc":"菠菜","jcjg":"2.91","hgzt":"合格","sjsj":"2019-08-28 07:59:40.000"},{"gsmc":"上海青","jcjg":".43","hgzt":"合格","sjsj":"2019-08-28 07:59:36.000"},{"gsmc":"苦瓜","jcjg":".46","hgzt":"合格","sjsj":"2019-08-28 07:59:32.000"},{"gsmc":"根芹菜","jcjg":"1.22","hgzt":"合格","sjsj":"2019-08-28 07:59:28.000"},{"gsmc":"春菜","jcjg":"7.05","hgzt":"合格","sjsj":"2019-08-28 07:59:25.000"},{"gsmc":"奶白菜","jcjg":".12","hgzt":"合格","sjsj":"2019-08-28 07:59:21.000"},{"gsmc":"苦瓜","jcjg":"3.57","hgzt":"合格","sjsj":"2019-08-28 07:42:31.000"},{"gsmc":"扁豆","jcjg":"5.19","hgzt":"合格","sjsj":"2019-08-28 07:42:27.000"},{"gsmc":"番茄","jcjg":".54","hgzt":"合格","sjsj":"2019-08-28 07:42:24.000"},{"gsmc":"包菜","jcjg":"3.52","hgzt":"合格","sjsj":"2019-08-28 07:42:20.000"},{"gsmc":"空心菜","jcjg":"3.08","hgzt":"合格","sjsj":"2019-08-28 07:42:16.000"},{"gsmc":"菜心","jcjg":"7.54","hgzt":"合格","sjsj":"2019-08-28 07:42:12.000"},{"gsmc":"大白菜","jcjg":"9.94","hgzt":"合格","sjsj":"2019-08-28 07:42:09.000"},{"gsmc":"蒲瓜","jcjg":"5.94","hgzt":"合格","sjsj":"2019-08-28 07:42:05.000"},{"gsmc":"苦瓜","jcjg":"7.45","hgzt":"合格","sjsj":"2019-08-28 07:42:01.000"},{"gsmc":"猪肉\n","jcjg":".04","hgzt":"合格","sjsj":"2019-08-28 07:14:00.000"},{"gsmc":"鹅肉加工品","jcjg":".19","hgzt":"合格","sjsj":"2019-08-28 07:14:00.000"},{"gsmc":"鸡肉加工品","jcjg":".08","hgzt":"合格","sjsj":"2019-08-28 07:14:00.000"},{"gsmc":"花甲","jcjg":".76","hgzt":"合格","sjsj":"2019-08-28 07:13:00.000"},{"gsmc":"节瓜","jcjg":"9.89","hgzt":"合格","sjsj":"2019-08-27 07:57:31.000"},{"gsmc":"生菜","jcjg":"3.64","hgzt":"合格","sjsj":"2019-08-27 07:57:27.000"},{"gsmc":"油菜","jcjg":"6.04","hgzt":"合格","sjsj":"2019-08-27 07:57:23.000"}]
     * gsmc : 生菜
     * jcjg : 1.53
     * hgzt : 合格
     * sjsj : 2019-08-28 07:59:51.000
     */
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String gsmc;
    @DatabaseField
    private String jcjg;
    @DatabaseField
    private String hgzt;
    @DatabaseField
    private String sjsj;

    public InspectBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGsmc() {
        return gsmc;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    public String getJcjg() {
        return jcjg;
    }

    public void setJcjg(String jcjg) {
        this.jcjg = jcjg;
    }

    public String getHgzt() {
        return hgzt;
    }

    public void setHgzt(String hgzt) {
        this.hgzt = hgzt;
    }

    public String getSjsj() {
        return sjsj;
    }

    public void setSjsj(String sjsj) {
        this.sjsj = sjsj;
    }

}
