package com.advertising.screen.myadvertising.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：罗发新
 * 时间：2019/9/18 0018    星期三
 * 邮件：424533553@qq.com
 * 说明：广告信息中的内容
 */
public class AdInfoLiveBean {
    private AdUserBean adUserBean;
    private String reallyAdString;
    // 头像路径
    private String photoString;
    private List<Object> imagePaths = new ArrayList<>();

    public List<Object> getImagePaths() {
        return imagePaths;
    }

    public String getReallyAdString() {
        return reallyAdString;
    }

    public void setReallyAdString(String reallyAdString) {
        this.reallyAdString = reallyAdString;
    }

    public AdUserBean getAdUserBean() {
        return adUserBean;
    }

    public void setAdUserBean(AdUserBean adUserBean) {
        this.adUserBean = adUserBean;
    }

    public String getPhotoString() {
        return photoString;
    }

    public void setPhotoString(String photoString) {
        this.photoString = photoString;
    }
}
