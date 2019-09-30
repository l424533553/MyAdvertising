package com.advertising.screen.myadvertising.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.advertising.screen.myadvertising.R
import com.advertising.screen.myadvertising.config.IConstants
import com.advertising.screen.myadvertising.config.IConstants.*
import com.advertising.screen.myadvertising.ui.screen.ScreenActivity
import com.xuanyuan.library.MyToast
import com.xuanyuan.library.utils.net.MyNetWorkUtils
import com.xuanyuan.library.utils.storage.MyPreferenceUtils

/**
 * 作者：罗发新
 * 时间：2019/9/26 0026    星期四
 * 邮件：424533553@qq.com
 * 说明：
 */
class HomeActivity1 : MyCommonKtActivity(), IConstants {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<ImageView>(R.id.ivLog).setOnClickListener {
            showSellerIdDialog()
        }
//        showSellerIdDialog()
    }

    override fun onStart() {
        super.onStart()
        nextActivity()
    }

    /**
     * 跳转到下一个Activity中去
     */
    private fun nextActivity() {
        val sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID)
        if (DEFAULT_ID != sellerId) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                val isFirstlogin = MyPreferenceUtils.getSp().getBoolean(IS_FIRST_LOGIN, false)
                if (!isFirstlogin) {
                    jumpActivity(DataFlushActivity::class.java, true)
                } else {
                    jumpActivity(ScreenActivity::class.java, true)
                }
            } else {
                jumpActivity(ScreenActivity::class.java, true)
            }
        } else {
            showSellerIdDialog()
        }
    }

    private fun showSellerIdDialog() {
        var sellerId = MyPreferenceUtils.getSp()
            .getString(SELLER_ID, DEFAULT_ID)

        val builder = AlertDialog.Builder(context)
        val view = View.inflate(context, R.layout.layout_dialog_sellerid, null)
        val etSellerId = view.findViewById<EditText>(R.id.etSellerId)
        etSellerId.setText(sellerId)
        etSellerId.setSelection(etSellerId.text.length)
        builder.setIcon(R.mipmap.log)
        builder.setTitle("设置  Buidlder")
        builder.setNegativeButton(
            "确定"
        ) { dialog, _ ->

            sellerId = etSellerId.text.toString()
            if (TextUtils.isEmpty(sellerId)) {
                MyToast.showError(context, "商户ID不可为空")
            } else {
                MyPreferenceUtils.getSp().edit().putString(SELLER_ID, sellerId).apply()
                MyToast.toastShort(context, "设置成功！")
                dialog.dismiss()
                nextActivity()
            }
        }

        val dialog = builder.create()
        dialog.setView(view)
        dialog.setTitle("SellerId设置")
        dialog.show()
    }
}

 @SuppressLint("Registered")
 open class MyCommonKtActivity: Activity() {
    lateinit var context :Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
    }
    /**
     * 跳转 Activity使用
     * @param cls      跳转的类, 时间功能。
     * @param isFinish 是否结束当前Activity，  true:是
     */

    fun jumpActivity(cls:Class<*>,isFinish:Boolean){
        val intent=Intent(context,cls)
        startActivity(intent)
        if(isFinish){
            this.finish()
        }
    }
}
