package com.advertising.screen.myadvertising.common.iinterface;

/**
 * 作者：罗发新
 * 时间：2019/4/22 0022    星期一
 * 邮件：424533553@qq.com
 * 说明：事件总线常量
 */
public interface IEventBus {

    String NOTIFY_LIVEBUS_KEY = "notifyLiveBusKey";//更新基础信息
    String NOTIFY_BASE_INFO = "notifyBaseInfo";//更新基础信息
    String NOTIFY_BASE_PRICE = "notifyBasePrice";//更新价格信息
    String NOTIFY_BASE_INSPECT = "notifyBaseInspect";//更新检测信息
    String NOTIFY_NET_CHANGE = "notifyNetChange";//网络变化了

}
