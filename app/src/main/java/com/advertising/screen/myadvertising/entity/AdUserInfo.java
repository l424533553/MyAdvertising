package com.advertising.screen.myadvertising.entity;


/**
 * author: luofaxin
 * date： 2018/11/12 0012.
 * email:424533553@qq.com
 * describe:
 */
public class AdUserInfo {

    /**
     * status : 0
     * msg : ok
     * data : {"companyno":1126,"img1":"ups/uploads/file/20181204/licence0.png","img2":"ups/uploads/file/20181220/operation.jpg;ups/uploads/file/20181220/93ee2dbdced77137a1a1fa2830296f36.jpg;ups/uploads/file/20181220/0b4fdc1af36f90aa862b3673eb1d8c75.jpg","boothno":"A066","boothname":"蔬菜档","headimg":"20181220141423574.jpg","companyname":"胡启城","linkphone":"15818546414","marketname":"罗湖市场","marketid":9}
     */

    private int status;
    private String msg;
    private AdUserBean data;

    public AdUserInfo() {
    }

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


    public AdUserBean getData() {
        return data;
    }

    public void setData(AdUserBean data) {
        this.data = data;
    }
}

