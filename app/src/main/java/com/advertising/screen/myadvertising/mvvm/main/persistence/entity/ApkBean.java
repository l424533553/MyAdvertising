package com.advertising.screen.myadvertising.mvvm.main.persistence.entity;

/**
 * 作者：罗发新
 * 时间：2019/11/14 0014    星期四
 * 邮件：424533553@qq.com
 * 说明：
 */
public class ApkBean {


    /**
     * status : 0
     * msg : ok
     * data : {"id":8,"name":"APP升级","version":"1.4","vtype":"0","filepath":"http://data.axebao.com/smartsz/assets/files/20181112125615467.apk","description":"","date":"2018-11-17","marketid":1}
     */

        /**
         * id : 8
         * name : APP升级
         * version : 1.4
         * vtype : 0
         * filepath : http://data.axebao.com/smartsz/assets/files/20181112125615467.apk
         * description :
         * date : 2018-11-17
         * marketid : 1
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
