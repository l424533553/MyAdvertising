package com.advertising.screen.myadvertising.func.retrofit;

import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.NetResultInfo;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.PriceEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.TodayTradeInfo;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.UserInfoEntity;
import com.advertising.screen.myadvertising.mvvm.main.persistence.entity.Weather;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * 作者：罗发新
 * 时间：2019/5/31 0031    星期五
 * 邮件：424533553@qq.com
 * 说明：Call 是普通的方法，Observable 是Rx的请求
 */
public interface IRetrofitAPI {

    //IP地址
//    String BASE_IP_HOST = "http://119.23.43.64/";
    String BASE_IP_WEB = "https://data.axebao.com";


//    //可以在 URL 中指定查询参数
//    @GET("api/smart/getinfobymac?")
//    Call<JSONObject> getResultInfo(@Query("desdata") String desdata);
//
//    @GET("/avatardata/mingrenmingyan/lookup")
//    Call<FamousInfo> getFamousResult(@Header("apiKey") String apiKey,
//                                     @Query("keyword") String keyword,
//                                     @Query("page") int page,
//                                     @Query("rows") int rows);

    /*************************************************************************/
    @GET("/api/smartex/getweather?")
    Call<Weather> getWeather();

    //    /api/smartsz/getadinfo?companyid=1112
    @GET("/api/smartsz/getadinfo?")
    Observable<NetResultInfo<UserInfoEntity>> getUserInfo(@Query("companyid") String shellerId);


    /**
     * 金融交易
     */
    @GET("/api/smartsz/gettjtoday?")
    Observable<TodayTradeInfo> todayTrade(@Query("companyid") String shellerId);

    /**
     * 金融交易
     */
    @GET("/api/smartsz/getpriceinfo?")
    Observable<NetResultInfo<List<PriceEntity>>> getTodayPrice(@Query("companyid") String shellerId);


    // 两种请求方式
//    //    @其他声明
////    //    @请求方式("api/smart/getinfobymac?") //需要baseurl后部分
////    Observable<JSONObject> getResultInfo();
////    //    @其他声明
////    //    @请求方式    //该方法中地址需要全地址  http://119.23.43.64/api/smart/getinfobymac?
////    Observable<JSONObject> getResultInfo(@Url String 请求地址，请求参数);


//    @GET("test.php")
//    Observable<String> test();
//    @POST("submitOrder")
//    Observable<BaseEntity<SubOrderBean>> submitOrder(@Body SubOrderReqBean subOrderReqBean);
//    @POST("submitOrders")
//    Observable<BaseEntity> submitOrders(@Body List<SubOrderReqBean> list);


//    @Multipart
//    @POST("http://api.stay4it.com/v1/public/core/?service=user.updateAvatar")
//    Observable<HttpResponse<String>> uploadFile(@Part("data") String des,
//                                                @PartMap Map<String, RequestBody> params);

}


//下面列举“客户端内置证书”时的配置方法，其他方式请参考
//        http://blog.csdn.net/dd864140130/article/details/52625666
//
//
//        （这里文件类型以png图片为例，所以MediaType为"image/png"，
//        不同文件类型对应不同的type，具体请参考http://tool.oschina.net/commons）
//
//
//        参考自https://zhuanlan.zhihu.com/p/21966621
//
