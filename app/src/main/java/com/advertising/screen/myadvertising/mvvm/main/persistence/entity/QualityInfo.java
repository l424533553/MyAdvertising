package com.advertising.screen.myadvertising.mvvm.main.persistence.entity;

import java.util.List;

public  class QualityInfo {
        /**
         * shidu : 29%
         * pm25 : 32
         * pm10 : 55
         * quality : 良
         * wendu : 14
         * ganmao : 极少数敏感人群应减少户外活动
         * forecast : [{"date":"06","high":"高温 16℃","low":"低温 10℃","ymd":"2019-12-06","week":"星期五","sunrise":"06:49","sunset":"17:39","aqi":51,"fx":"北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"07","high":"高温 18℃","low":"低温 11℃","ymd":"2019-12-07","week":"星期六","sunrise":"06:50","sunset":"17:39","aqi":59,"fx":"无持续风向","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"08","high":"高温 19℃","low":"低温 11℃","ymd":"2019-12-08","week":"星期日","sunrise":"06:51","sunset":"17:39","aqi":45,"fx":"无持续风向","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"09","high":"高温 20℃","low":"低温 13℃","ymd":"2019-12-09","week":"星期一","sunrise":"06:51","sunset":"17:39","aqi":42,"fx":"东北风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"10","high":"高温 21℃","low":"低温 14℃","ymd":"2019-12-10","week":"星期二","sunrise":"06:52","sunset":"17:40","aqi":46,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"11","high":"高温 22℃","low":"低温 15℃","ymd":"2019-12-11","week":"星期三","sunrise":"06:53","sunset":"17:40","aqi":42,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"12","high":"高温 21℃","low":"低温 15℃","ymd":"2019-12-12","week":"星期四","sunrise":"06:53","sunset":"17:40","fx":"东风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"13","high":"高温 24℃","low":"低温 16℃","ymd":"2019-12-13","week":"星期五","sunrise":"06:54","sunset":"17:41","fx":"东北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"14","high":"高温 24℃","low":"低温 18℃","ymd":"2019-12-14","week":"星期六","sunrise":"06:54","sunset":"17:41","fx":"东风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"15","high":"高温 26℃","low":"低温 16℃","ymd":"2019-12-15","week":"星期日","sunrise":"06:55","sunset":"17:41","fx":"东风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"16","high":"高温 23℃","low":"低温 17℃","ymd":"2019-12-16","week":"星期一","sunrise":"06:56","sunset":"17:42","fx":"东北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"17","high":"高温 23℃","low":"低温 19℃","ymd":"2019-12-17","week":"星期二","sunrise":"06:56","sunset":"17:42","fx":"东北风","fl":"<3级","type":"晴","notice":"愿你拥有比阳光明媚的心情"},{"date":"18","high":"高温 24℃","low":"低温 18℃","ymd":"2019-12-18","week":"星期三","sunrise":"06:57","sunset":"17:43","fx":"东北风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"19","high":"高温 23℃","low":"低温 17℃","ymd":"2019-12-19","week":"星期四","sunrise":"06:57","sunset":"17:43","fx":"东北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"20","high":"高温 22℃","low":"低温 15℃","ymd":"2019-12-20","week":"星期五","sunrise":"06:58","sunset":"17:43","fx":"东北风","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"}]
         * yesterday : {"date":"05","high":"高温 15℃","low":"低温 11℃","ymd":"2019-12-05","week":"星期四","sunrise":"06:49","sunset":"17:39","aqi":47,"fx":"北风","fl":"4-5级","type":"阴","notice":"不要被阴云遮挡住好心情"}
         */
        private String shidu;
        private int pm25;
        private int pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private YesterdayBean yesterday;
        private List<ForecastBean> forecast;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean {
            /**
             * date : 05
             * high : 高温 15℃
             * low : 低温 11℃
             * ymd : 2019-12-05
             * week : 星期四
             * sunrise : 06:49
             * sunset : 17:39
             * aqi : 47
             * fx : 北风
             * fl : 4-5级
             * type : 阴
             * notice : 不要被阴云遮挡住好心情
             */

            private String date;
            private String high;
            private String low;
            private String ymd;
            private String week;
            private String sunrise;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
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

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }

        public static class ForecastBean {
            /**
             * date : 06
             * high : 高温 16℃
             * low : 低温 10℃
             * ymd : 2019-12-06
             * week : 星期五
             * sunrise : 06:49
             * sunset : 17:39
             * aqi : 51
             * fx : 北风
             * fl : 3-4级
             * type : 多云
             * notice : 阴晴之间，谨防紫外线侵扰
             */

            private String date;
            private String high;
            private String low;
            private String ymd;
            private String week;
            private String sunrise;
            private String sunset;
            private int aqi;
            private String fx;
            private String fl;
            private String type;
            private String notice;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
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

            public String getSunrise() {
                return sunrise;
            }

            public void setSunrise(String sunrise) {
                this.sunrise = sunrise;
            }

            public String getSunset() {
                return sunset;
            }

            public void setSunset(String sunset) {
                this.sunset = sunset;
            }

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }
        }
    }