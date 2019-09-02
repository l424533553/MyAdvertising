package com.advertising.screen.myadvertising.download;

import android.os.Environment;
import com.advertising.SysApplication;


/**
 * 创建时间：2018/3/7
 * 编写人：czw
 * 功能描述 ：
 */

public interface Constant_APK {
    String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + SysApplication.getInstance().getPackageName();
    String DOWNLOAD_DIR = "/downlaod/";
}