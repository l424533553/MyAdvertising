package com.advertising.screen.myadvertising.entity;

/**
 * 作者：罗发新
 * 时间：2019/8/30 0030    星期五
 * 邮件：424533553@qq.com
 * 说明：
 */
public class KK {


    /**
     * status : 0
     * msg : ok
     * data : {"licence":"","ad":"ups/uploads/file/20190830/tim.jpg;ups/uploads/file/20190830/a315bb19e17f686b679ee773a1df5157.jpg;ups/uploads/file/20190830/1F32Q05306-6.jpg","photo":"assets/files/20190520094255772.png","marketname":"黄田市场","companyno":"B102","introduce":"猪肉档","adcontent":"","companyname":"内测03","linkphone":"13056489536","companyid":1152,"status":"","baseurl":"https://data.axebao.com/smartsz/"}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * licence :
         * ad : ups/uploads/file/20190830/tim.jpg;ups/uploads/file/20190830/a315bb19e17f686b679ee773a1df5157.jpg;ups/uploads/file/20190830/1F32Q05306-6.jpg
         * photo : assets/files/20190520094255772.png
         * marketname : 黄田市场
         * companyno : B102
         * introduce : 猪肉档
         * adcontent :
         * companyname : 内测03
         * linkphone : 13056489536
         * companyid : 1152
         * status :
         * baseurl : https://data.axebao.com/smartsz/
         */

        private String licence;
        private String ad;
        private String photo;
        private String marketname;
        private String companyno;
        private String introduce;
        private String adcontent;
        private String companyname;
        private String linkphone;
        private int companyid;
        private String status;
        private String baseurl;

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

        public String getBaseurl() {
            return baseurl;
        }

        public void setBaseurl(String baseurl) {
            this.baseurl = baseurl;
        }
    }
}
