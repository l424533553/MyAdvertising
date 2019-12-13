package com.advertising.screen.myadvertising.mvvm.main.persistence.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.advertising.screen.myadvertising.mvvm.main.persistence.room.WeatherDao;


/**
 * 作者：罗发新
 * 时间：2019/12/6 0006    星期五
 * 邮件：424533553@qq.com
 * 说明：
 */
@Entity(tableName = WeatherDao.TABLE_NAME)
public class Weather {
    /**
     * message : success感谢又拍云(upyun.com)提供CDN赞助
     * status : 200
     * date : 20191206
     * time : 2019-12-06 09:40:13
     * cityInfo : {"city":"深圳市","citykey":"101280601","parent":"广东","updateTime":"09:03"}
     * data : {"shidu":"29%","pm25":32,"pm10":55,"quality":"良","wendu":"14","ganmao":"极少数敏感人群应减少户外活动",
     * "forecast":[{"date":"06","high":"高温 16℃","low":"低温 10℃","ymd":"2019-12-06","week":"星期五","sunrise":"06:49","sunset":"17:39","aqi":51,"fx":"北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},
     * {"date":"07","high":"高温 18℃","low":"低温 11℃","ymd":"2019-12-07","week":"星期六","sunrise":"06:50","sunset":"17:39","aqi":59,"fx":"无持续风向","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"08","high":"高温 19℃","low":"低温 11℃","ymd":"2019-12-08","week":"星期日","sunrise":"06:51","sunset":"17:39","aqi":45,"fx":"无持续风向","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"09","high":"高温 20℃","low":"低温 13℃","ymd":"2019-12-09","week":"星期一","sunrise":"06:51","sunset":"17:39","aqi":42,"fx":"东北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"10","high":"高温 21℃","low":"低温 14℃","ymd":"2019-12-10","week":"星期二","sunrise":"06:52","sunset":"17:40","aqi":46,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"11","high":"高温 22℃","low":"低温 15℃","ymd":"2019-12-11","week":"星期三","sunrise":"06:53","sunset":"17:40","aqi":42,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"12","high":"高温 21℃","low":"低温 15℃","ymd":"2019-12-12","week":"星期四","sunrise":"06:53","sunset":"17:40","fx":"东风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"13","high":"高温 24℃","low":"低温 16℃","ymd":"2019-12-13","week":"星期五","sunrise":"06:54","sunset":"17:41","fx":"东北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"14","high":"高温 24℃","low":"低温 18℃","ymd":"2019-12-14","week":"星期六","sunrise":"06:54","sunset":"17:41","fx":"东风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"15","high":"高温 26℃","low":"低温 16℃","ymd":"2019-12-15","week":"星期日","sunrise":"06:55","sunset":"17:41","fx":"东风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"16","high":"高温 23℃","low":"低温 17℃","ymd":"2019-12-16","week":"星期一","sunrise":"06:56","sunset":"17:42","fx":"东北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"17","high":"高温 23℃","low":"低温 19℃","ymd":"2019-12-17","week":"星期二","sunrise":"06:56","sunset":"17:42","fx":"东北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"18","high":"高温 24℃","low":"低温 18℃","ymd":"2019-12-18","week":"星期三","sunrise":"06:57","sunset":"17:43","fx":"东北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"19","high":"高温 23℃","low":"低温 17℃","ymd":"2019-12-19","week":"星期四","sunrise":"06:57","sunset":"17:43","fx":"东北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"20","high":"高温 22℃","low":"低温 15℃","ymd":"2019-12-20","week":"星期五","sunrise":"06:58","sunset":"17:43","fx":"东北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"}],"yesterday":{"date":"05","high":"高温 15℃","low":"低温 11℃","ymd":"2019-12-05","week":"星期四","sunrise":"06:49","sunset":"17:39","aqi":47,"fx":"北风","fl":"4-5级","type":"阴","notice":"不要被阴云遮挡住好心情"}}
     */

    //设置主键自增 ，每个类至少需要有一个 主键
    @PrimaryKey
    public int id;
    private String city;
    private String parent;
    private String ymd;
    private String week;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        if (city == null) {
            return "";
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getParent() {
        if (parent == null) {
            return "";
        }
        return parent;
    }


    public String getType() {
        if (type == null) {
            return "";
        }
        return type;
    }


    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }


    public void setType(String type) {
        this.type = type;
    }

    /**
     * 非数据库字段
     *********************************************************/
    @Ignore
    private String message;
    @Ignore
    private int status;
    @Ignore
    private String date;
    @Ignore
    private String time;
    @Ignore
    private CityInfoBean cityInfo;
    @Ignore
    private QualityInfo data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CityInfoBean getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoBean cityInfo) {
        this.cityInfo = cityInfo;
    }

    public QualityInfo getData() {
        return data;
    }

    public void setData(QualityInfo data) {
        this.data = data;
    }

}
