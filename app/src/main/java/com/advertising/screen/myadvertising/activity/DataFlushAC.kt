package com.advertising.screen.myadvertising.activity

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.advertising.screen.myadvertising.R
import com.advertising.screen.myadvertising.func.application.SysApplication
import com.advertising.screen.myadvertising.common.iinterface.IConstants
import com.advertising.screen.myadvertising.common.iinterface.IConstants.*
import com.advertising.screen.myadvertising.databinding.ActivityDataFlushBinding
import com.advertising.screen.myadvertising.entity.*
import com.advertising.screen.myadvertising.entity.dao.InspectBeanDao
import com.advertising.screen.myadvertising.entity.dao.PriceBeanDao
import com.advertising.screen.myadvertising.func.help.HttpHelper
import com.advertising.screen.myadvertising.mvvm.main.ui.MainActivity
import com.alibaba.fastjson.JSON
import com.android.volley.VolleyError
import com.xuanyuan.library.MyToast
import com.xuanyuan.library.help.ActivityController
import com.xuanyuan.library.listener.VolleyListener
import com.xuanyuan.library.utils.net.MyNetWorkUtils
import com.xuanyuan.library.utils.storage.MyPreferenceUtils
import org.json.JSONObject

/**
 * 作者：罗发新
 * 时间：2019/9/29 0029    星期日
 * 邮件：424533553@qq.com
 * 说明：
 */

class DataFlushKtAC : MyCommonKtActivity(),
    IConstants, VolleyListener, View.OnClickListener {
    override fun onClick(v: View?) {
        if (v == null) {
            return
        }
        when (v.id) {
            R.id.btnSecondImage -> upSecondImage()
            else -> {
            }
        }
    }

    override fun onResponse(volleyError: VolleyError?, flag: Int) {
        MyToast.toastNetError(context)
        when (flag) {
            VOLLEY_UPDATE_PRICE -> {
                binding.tvPrice.text = "更新失败"
                handler.sendEmptyMessage(HANDLER_FINISH_PRICE)
            }
            VOLLEY_UPDATE_INSPECT -> {
                binding.tvInspect.text = "更新失败"
                handler.sendEmptyMessage(HANDLER_FINISH_INSPECT)
            }
            VOLLEY_UPDATE_SMALL_QR -> {
                binding.tvSmallRoutine.text = "更新失败"
                handler.sendEmptyMessage(HANDLER_SECOND_IMAGE)
            }
            VOLLEY_UPDATE_IMAGE -> {
                binding.tvSecondImage.text = "更新失败"
                handler.sendEmptyMessageDelayed(HANDLER_UPDATE_FINISH, 500)
            }

        }
    }

    override fun onResponse(jsonObject: JSONObject?, flag: Int) {
        val resultInfo: ResultInfo
        when (flag) {
            VOLLEY_UPDATE_PRICE -> {
                resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo::class.java)
                if (resultInfo.status == 0) {
                    val list: List<PriceBean> =
                        JSON.parseArray(resultInfo.data, PriceBean::class.java)
                    PriceBeanDao.getInstance().deleteAll()
                    PriceBeanDao.getInstance().insert(list)
                } else {
                    binding.tvPrice.text = "更新失败"
                }
                handler.sendEmptyMessage(HANDLER_FINISH_PRICE)
            }

            VOLLEY_UPDATE_INSPECT -> {
                resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo::class.java)
                if (resultInfo.status == 0) {
                    val list: List<InspectBean> =
                        JSON.parseArray(resultInfo.data, InspectBean::class.java)
                    InspectBeanDao.getInstance().deleteAll()
                    InspectBeanDao.getInstance().insert(list)
                } else {
                    binding.tvInspect.text = "更新失败"
                }
                handler.sendEmptyMessage(HANDLER_FINISH_INSPECT)
            }

            VOLLEY_UPDATE_SMALL_QR -> {
                val result =
                    JSON.parseObject(jsonObject.toString(), ResultInfoSmall::class.java)
                if (result != null && result.code == 0) {
                    val url = result.url
                    MyPreferenceUtils.getSp().edit().putString(SMALLROUTINE_URL, url)
                        .apply()
                    binding.tvSmallRoutine.text = "更新成功"
                } else {
                    binding.tvSmallRoutine.text = "更新失败"
                }
                handler.sendEmptyMessage(HANDLER_SECOND_IMAGE)
            }

            VOLLEY_UPDATE_IMAGE -> {
                val resultAd: AdUserInfo =
                    JSON.parseObject(jsonObject.toString(), AdUserInfo::class.java)
                if (resultAd.status == 0) {
                    val adUserBean = resultAd.data
                    saveSecondImageUrl(adUserBean)
                    binding.tvSecondImage.text = "更新成功"

                } else {
                    binding.tvSecondImage.text = "更新失败"
                    handler.sendEmptyMessageDelayed(HANDLER_UPDATE_FINISH, 500)
                }
            }
        }
    }

    private fun saveSecondImageUrl(adUserBean: AdUserBean) {

    }


    private lateinit var binding: ActivityDataFlushBinding
    private lateinit var sysApplication: SysApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_flush)
        sysApplication = application as SysApplication
        ActivityController.addActivity(this)
        initHandler()

        upPrice()
    }



    private fun upPrice() {

    }

    private fun upInspect() {

    }

    private fun upSecondImage() {
        if (MyNetWorkUtils.isNetworkAvailable()) {
            val sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID)
            HttpHelper.getmInstants(sysApplication)
                .httpQuestImageEx22(this, sellerId, VOLLEY_UPDATE_IMAGE)
            binding.tvSecondImage.text = "正在更新。。。"
        } else {
            binding.tvTitle.text = "无网络，请配置网络"
        }

    }

    private lateinit var handler: Handler
    private fun initHandler() {
        handler = Handler(Handler.Callback {
            when (it.what) {
                HANDLER_UPDATE_ALLGOOD -> upPrice()
                HANDLER_FINISH_PRICE -> upInspect()
                HANDLER_FINISH_INSPECT -> upSecondImage()
                HANDLER_SMALL_ROUTINE -> {
                    binding.tvSmallRoutine.text = "更新成功"
                    handler.sendEmptyMessage(HANDLER_SECOND_IMAGE)
                }
                HANDLER_SECOND_IMAGE -> upSecondImage()
                HANDLER_UPDATE_FINISH -> {
                    MyPreferenceUtils.getSp().edit().putBoolean(IS_FIRST_LOGIN, true)
                        .apply()
                    jumpActivity(MainActivity::class.java, true)
                }
            }
            false
        })
    }

//        handler = Handler { msg ->
//            when (msg.what) {
//                HANDLER_UPDATE_ALLGOOD -> upPrice()
//                HANDLER_FINISH_PRICE -> upInspect()
//                HANDLER_FINISH_INSPECT -> upSecondImage()
//                HANDLER_SMALL_ROUTINE -> {
//                    binding.tvSmallRoutine.setText("更新成功")
//                    handler!!.sendEmptyMessage(HANDLER_SECOND_IMAGE)
//                }
//                HANDLER_SECOND_IMAGE -> upSecondImage()
//                HANDLER_UPDATE_FINISH -> {
//                    MyPreferenceUtils.getSp().edit().putBoolean(IS_FIRST_LOGIN, true).apply()
//                    jumpActivity(ScreenActivity::class.java, true)
//                }
//            }
//            false
//        }
//    }


    /**
     * 测试网络的  使用情况
     *
     */
    fun testVolley123() {

    }

    // 时间测试
    /**
     * 时间测试环境
     */
    fun testVolley() {

    }
}