package com.advertising.screen.myadvertising.mvvm.main.persistence.entity;

/**
 * 作者：罗发新
 * 时间：2019/12/9 0009    星期一
 * 邮件：424533553@qq.com
 * 说明：
 */
public class NetResultInfo<T> {
    /**
     * status : 0
     * msg : ok
     * data : {"licence":"ups/uploads/file/20191120/B046.jpg;ups/uploads/file/20191115/mmexport1573777983906.jpg","ad":"ups/uploads/file/20191206/u=677301244,183697913&fm=26&gp=0.jpg;ups/uploads/file/20191206/下载 (3).jpg","photo":"assets/files/20190725142200809.jpg","marketname":"黄田智慧农贸市场","commcontent":"欢迎顾客扫描溯源码，可以知道菜品检疫检测信息及来源，食用更放心；扫码点评，为商户点赞！","marketid":1,"companyno":"B046","introduce":"豆腐","adcontent":"","companyname":"陈冬慧","linkphone":"13925273118","companyid":1112,"status":"0","wx":"","wxnote":"","zfb":"","zfbnote":"","baseurl":"https://data.axebao.com/smartsz/"}
     */

    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
