package com.advertising.screen.myadvertising.download

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.widget.Toast

import com.luofx.utils.common.MyToast

/**
 * APK 更新测试
 *
 */

@Suppress("DEPRECATION")
class ApkUpdateHelper {

    var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun startUpdown(DOWNLOADAPK_ID: Int, downloadUrl: String) {

        if (isServiceRunning(DownloadIntentService::class.java.name)) {// 服务已经打开了
            MyToast.toastShort(context, "正在下载")
            Toast.makeText(context, "正在下载", Toast.LENGTH_SHORT).show()
        } else {
//            val downloadUrl =
//                "/wapdl/android/apk/SogouInput_android_v8.25_sweb.apk?frm=new_pcjs_index/SogouInput_android_v8.25_sweb.apk"

            val intent = Intent(context, DownloadIntentService::class.java)
            intent.putExtra("download_url", downloadUrl)
            intent.putExtra("download_id", DOWNLOADAPK_ID)
            intent.putExtra("download_file", downloadUrl.substring(downloadUrl.lastIndexOf('/') + 1))
            context.startService(intent)
        }
    }

    /**
     * 用来判断服务是否运行,这一步很重要
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    fun isServiceRunning(className: String): Boolean {
        var isRunning = false
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val serviceList = activityManager.getRunningServices(Int.MAX_VALUE)
        if (serviceList.size <= 0)// 无服务则肯定返回0
            return false

        for (item in serviceList) {
            if (item.service.className == className) {
                isRunning = true
                break
            }
        }
        return isRunning
    }
}
