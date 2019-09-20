package com.advertising.screen.myadvertising.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.config.IConstants;
import com.advertising.screen.myadvertising.ui.screen.ScreenActivity;
import com.xuanyuan.library.MyToast;
import com.xuanyuan.library.utils.log.MyLog;
import com.xuanyuan.library.utils.net.MyNetWorkUtils;
import com.xuanyuan.library.utils.storage.MyPreferenceUtils;

/**
 * 作者：罗发新
 * 时间：2018/12/21 0021    10:18
 * 邮件：424533553@qq.com
 * 说明：使用于5.1.1 版本不需要使用 权限检查
 */
public class HomeActivity extends MyCommonActivity implements IConstants {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.ivLog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
                showSellerIdDialog(sellerId);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.logTest("测试   onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyLog.logTest("测试   onstart()");
        nextActivity();
    }

    private void nextActivity() {
        String sellerId = MyPreferenceUtils.getSp().getString(SELLER_ID, DEFAULT_ID);
        if (!DEFAULT_ID.equals(sellerId)) {
            if (MyNetWorkUtils.isNetworkAvailable()) {
                // 进行数据更新
                boolean isFirstLogin = MyPreferenceUtils.getSp().getBoolean(IS_FIRST_LOGIN, false);
                if (!isFirstLogin) {
                    jumpActivity(DataFlushActivity.class,true);
                } else {
                    jumpActivity(ScreenActivity.class,true);
                }
            } else {
                jumpActivity(ScreenActivity.class,true);
            }
        } else {
            showSellerIdDialog(null);
        }
    }

    /**
     * 显示设置商户id
     */
    private void showSellerIdDialog(String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.layout_dialog_sellerid, null);   // 账号、密码的布局文件，自定义
        builder.setIcon(R.mipmap.log);//设置对话框icon
        builder.setTitle("SellerId设置");
        final EditText etSellerId = view.findViewById(R.id.etSellerId);
        if (hint != null) {
            etSellerId.setHint(hint);
        }
        AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sellerId = etSellerId.getText().toString();
                if (TextUtils.isEmpty(sellerId)) {
                    MyToast.showError(context, "商户id不可为空");
                } else {
                    MyPreferenceUtils.getSp().edit().putString(SELLER_ID, sellerId).apply();
                    MyToast.toastShort(context, "设置成功！");
                    dialog.dismiss();//关闭对话框
                    nextActivity();
                }
            }
        });
//        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "点我试试", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });


        dialog.show();

//        Window dialogWindow = dialog.getWindow();//获取window对象
//        if (dialogWindow != null) {
//            dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
//            dialogWindow.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
//            dialogWindow.setWindowAnimations(R.style.share_animation);//设置动画   见
//        }
    }



}
