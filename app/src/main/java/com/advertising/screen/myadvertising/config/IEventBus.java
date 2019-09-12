package com.advertising.screen.myadvertising.config;

/**
 * 作者：罗发新
 * 时间：2019/4/22 0022    星期一
 * 邮件：424533553@qq.com
 * 说明：事件总线常量
 */
public interface IEventBus {
    String MARKET_NOTICE = "market_notice";//市场通知
    String TYPE_GET_K_VALUE = "getKValue";//获取K值
    //    String ACTION_UNLOCK_SOFT = "unlock_soft";// 如果锁定状态，则解锁软件，可以正常使用
    String BACKGROUND_CHANGE = "background_change";// 如果锁定状态，则解锁软件，可以正常使用
    String SHOPPING_BAG_PRICE_CHANGE = "ShoppingBagPrice_change";//购物袋 价格变化

    String NOTIFY_HOT_GOOD_CHANGE = "hotGoods";//商品菜单改变了 ，可能是数量，可能是追溯变化了
    String NOTIFY_USERINFO = "userInfo";//用户信息更新过了
    String NOTIFY_LIVEBUS_KEY = "notifyLiveBusKey";//更新基础信息

    String NOTIFY_BASE_INFO = "notifyBaseInfo";//更新基础信息
    String NOTIFY_BASE_PRICE = "notifyBasePrice";//更新价格信息
    String NOTIFY_BASE_INSPECT = "notifyBaseInspect";//更新检测信息
    String NOTIFY_NET_CHANGE = "notifyNetChange";//网络变化了

    String NOTIFY_SMALLL_ROUTINE = "smallRoutine";//更新小程序二维码
    String NOTIFY_AD_SECOND = "adSecond";//更新副屏广告

    String EVENT_GET_USERINFO_ERR_BY_NET = "EVENT_GET_USERINFO_ERR_BY_NET";//通过网络获取用户信息失败
    String EVENT_GET_USERINFO_OK_BY_NET = "EVENT_GET_USERINFO_OK_BY_NET";//通过网络获取用户信息成功
    String EVENT_GET_USERINFO_VALIDATE_FINISH = "EVENT_GET_USERINFO_VALIDATE_FINISH";//通过网络获取用户信息成功
    String EVENT_GET_USERINFO_NOT_GET = "EVENT_GET_USERINFO_NOT_GET";//未获取到用户信息参数
}
