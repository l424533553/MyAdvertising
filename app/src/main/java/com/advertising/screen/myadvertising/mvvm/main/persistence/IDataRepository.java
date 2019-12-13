package com.advertising.screen.myadvertising.mvvm.main.persistence;

import com.advertising.screen.myadvertising.common.iinterface.DataRequestBack;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.UserInfoEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;

import org.jetbrains.annotations.NotNull;

/**
 * 作者：罗发新
 * 时间：2019/12/6 0006    星期五
 * 邮件：424533553@qq.com
 * 说明：
 */
public interface IDataRepository {



     void getUserInfo(String shellerId, boolean isLocalData, @NotNull DataRequestBack<UserInfoEntity> callBack);
}
