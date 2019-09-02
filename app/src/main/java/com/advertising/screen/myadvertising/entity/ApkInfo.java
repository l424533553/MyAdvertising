package com.advertising.screen.myadvertising.entity;

/**
 * 作者：罗发新
 * 时间：2018/12/27 0027    10:53
 * 邮件：424533553@qq.com
 * 说明：
 */
public class ApkInfo {


    /**
     * status : 0
     * msg : ok
     * data : {"id":42,"name":"APP升级","version":"3.967","vtype":"",
     * "filepath":"http://data.axebao.com/smartsz/assets/files/20181220142238357.apk",
     *
     * "description":"1.修复图片更新问题","date":"2018-12-20","marketid":11}
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
         * id : 42
         * name : APP升级
         * version : 3.967
         * vtype :
         * filepath : http://data.axebao.com/smartsz/assets/files/20181220142238357.apk
         * description : 1.修复图片更新问题
         * date : 2018-12-20
         * marketid : 11
         */

        private int id;
        private String name;
        private String version;
        private String vtype;
        private String filepath;
        private String description;
        private String date;
        private int marketid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getVtype() {
            return vtype;
        }

        public void setVtype(String vtype) {
            this.vtype = vtype;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getMarketid() {
            return marketid;
        }

        public void setMarketid(int marketid) {
            this.marketid = marketid;
        }
    }
}
