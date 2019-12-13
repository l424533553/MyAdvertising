package com.advertising.screen.myadvertising.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.advertising.screen.myadvertising.R
import com.advertising.screen.myadvertising.common.iinterface.IConstants
import com.advertising.screen.myadvertising.common.iinterface.IConstants.*
import com.advertising.screen.myadvertising.mvvm.main.ui.MainActivity
import com.xuanyuan.library.MyToast
import com.xuanyuan.library.base.activity.MyAppCompatActivity
import com.xuanyuan.library.base.activity.MyBaseACActivity
import com.xuanyuan.library.utils.net.MyNetWorkUtils
import com.xuanyuan.library.utils.storage.MyPreferenceUtils

/**
 * 作者：罗发新
 * 时间：2019/9/26 0026    星期四
 * 邮件：424533553@qq.com
 * 说明：
 */
class HomeActivity : MyBaseACActivity(),
    IConstants {
    override fun onLiveEvent(type: String?) {

    }

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
        val sellerId: String? = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID)
        if (DEFAULT_ID != sellerId) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                val isFirstlogin: Boolean =
                    MyPreferenceUtils.getSp().getBoolean(IS_FIRST_LOGIN, false)
                if (!isFirstlogin) {
                    jumpActivity(DataFlushActivity::class.java, true)
                } else {
                    jumpActivity(MainActivity::class.java, true)
                }
            } else {
                jumpActivity(MainActivity::class.java, true)
            }
        } else {
            showSellerIdDialog()
        }
    }

    var dialog: AlertDialog? = null
    private fun showSellerIdDialog() {
        dialog?.show()
        if (dialog == null) {
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
}

@SuppressLint("Registered")
open class MyCommonKtActivity : MyAppCompatActivity() {
    //    TYPE_APPLICATION_OVERLAY
    override fun getPermissionsArray(): Array<String> {
        return arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,

            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.DISABLE_KEYGUARD,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
        )
    }

    lateinit var mcontext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mcontext = context
    }

    @Suppress("DEPRECATION")
    @SuppressLint("ObsoleteSdkInt")
    private fun checkFloatPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appOpsMgr = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOpsMgr.unsafeCheckOpNoThrow(
                    "android:system_alert_window",
                    android.os.Process.myUid(),
                    packageName
                )
            } else {
                appOpsMgr.checkOpNoThrow(
                    "android:system_alert_window",
                    android.os.Process.myUid(),
                    packageName
                )
            }
//            val mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), packageName)
            return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return Settings.canDrawOverlays(this)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            var cls = Class.forName("android.content.Context")
            val declaredField = cls.getDeclaredField("APP_OPS_SERVICE")
            declaredField.isAccessible = true
            var obj = declaredField.get(cls) as? String ?: return false
            val str2 = obj
            obj = cls.getMethod("getSystemService", String::class.java).invoke(this, str2) as String
            cls = Class.forName("android.app.AppOpsManager")
            val declaredField2 = cls.getDeclaredField("MODE_ALLOWED")
            declaredField2.isAccessible = true
            val checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String::class.java)
            val result = checkOp.invoke(obj, 24, Binder.getCallingUid(), packageName) as Int
            return result == declaredField2.getInt(cls)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true
        }
        return false
    }


    /**
     * 跳转 Activity使用
     * @param cls      跳转的类, 时间功能。
     * @param isFinish 是否结束当前Activity，  true:是
     */

    fun jumpActivity(cls: Class<*>, isFinish: Boolean) {
        val intent = Intent(mcontext, cls)
        startActivity(intent)
        if (isFinish) {
            this.finish()
        }
    }
}

