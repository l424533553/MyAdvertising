package com.advertising.screen.myadvertising.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 作者：罗发新
 * 时间：2018/12/21 0021    11:10
 * 邮件：424533553@qq.com
 * 说明：基础公共Activity,有一些共有的性能方法
 */
@SuppressLint("Registered")
public class MyCommonActivity extends Activity {
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    /**
     * 跳转 Activity
     *
     * @param cls      跳转的类
     * @param isFinish 是否结束当前Activity，  true:是
     */
    public void jumpActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }

}
