package com.advertising.screen.myadvertising.common.iinterface;

/**
 * author: luofaxin
 * date： 2018/8/31 0031.
 * email:424533553@qq.com
 * describe:
 */
public interface IConstants {


    int NOTIFY_SUCCESS = 9555;
    int NOTIFY_DATA_MOVE = 9515;
    int NOTIFY_PATCH_UPDATE = 9525;
    int NOTIFY_JUMP = 9888;

    int FAILED = 9113;
    int UPDATE_QR = 9114;

    /***************************************/
//    int VOLLEY_UPDATE_GOOD = 4401;
    int VOLLEY_UPDATE_SMALL_QR = 4402;
    //  更新 广告图片
    int VOLLEY_UPDATE_IMAGE = 4403;

    int VOLLEY_UPDATE_PRICE = 4404;
    int VOLLEY_UPDATE_INSPECT = 4405;

    /***************************************/

    // 销售id
    String SELLER_ID = "seller_id";
    String IS_FIRST_LOGIN = "isFirstLogin";
    String DEFAULT_AD_CONTENT = "智慧农贸市场欢迎您！";


    String DATA_MARK_ID = "mark_id";
    String DATA_MARK_NAME = "mark_name";
    String DATA_BOOTH_NUMBER = "booth_number";
    String DEFAULT_ID = "0000";

    String BASE_IP_ST = "https://data.axebao.com";
    //    String BASE_IP_ST = "http://119.23.43.64";
    String BASE_IP_WEB = "https://data.axebao.com/smartsz";

    //请求更新产品类型表
    int HANDLER_UPDATE_GOOD_TYPE = 8201;
    int HANDLER_UPDATE_ALLGOOD = 8203;
    // 获取小程序
    int HANDLER_SMALL_ROUTINE = 8204;

    int HANDLER_SECOND_IMAGE = 8206;
    //更新完成
    int HANDLER_UPDATE_FINISH = 8205;
    int HANDLER_IMAGE_FINISH = 8207;
    int HANDLER_FINISH_PRICE = 8208;
    int HANDLER_FINISH_INSPECT = 8209;



    /**
     * 8寸按键常量  end
     *************************************************************************/
    String ACTIVITY_JUMP_TYPE = "jumpType";  // 跳转类型

    /**
     * 参数 配置区    心跳时间
     **********************************************************************/
    int UPDATE_STATE_TIME = 150 * 1000;
    String SMALLROUTINE_URL = "SmallRoutineURL";

}