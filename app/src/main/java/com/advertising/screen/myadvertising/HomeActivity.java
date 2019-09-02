package com.advertising.screen.myadvertising;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import com.advertising.screen.myadvertising.R;
import com.advertising.screen.myadvertising.ScreenActivity;
import com.advertising.screen.myadvertising.activity.DataFlushActivity;
import com.advertising.screen.myadvertising.download.ApkUpdateActivity;
import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.advertising.SysApplication;
import com.axecom.smartweight.manager.AccountManager;
import com.axecom.smartweight.my.IConstants;
import com.axecom.smartweight.my.entity.*;
import com.axecom.smartweight.my.net.NetHelper;
import com.axecom.smartweight.utils.SPUtils;
import com.luofx.listener.VolleyListener;
import com.luofx.other.tid.UserInfo;
import com.luofx.utils.MyPreferenceUtils;
import com.luofx.utils.common.MyToast;
import com.luofx.utils.log.MyLog;
import com.luofx.utils.net.NetWorkJudge;
import org.json.JSONObject;

/**
 * 作者：罗发新
 * 时间：2018/12/21 0021    10:18
 * 邮件：424533553@qq.com
 * 说明：使用于5.1.1 版本不需要使用 权限检查
 */
public class HomeActivity extends Activity implements VolleyListener, IConstants {

    private static final String AUTO_LOGIN = "auto_login";
    private CheckedTextView savePwdCtv;
    private CheckedTextView autoLogin;
    private SysApplication sysApplication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sysApplication = (SysApplication) getApplication();
        context = this;
//        String sellerId = MyPreferenceUtils.getSp(context).getString(SELLER_ID, DEFAULT_ID);
//        if (DEFAULT_ID.equals(sellerId)) {  //弹框
//
//        }
        findViewById(R.id.ivLog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sellerId = MyPreferenceUtils.getSp(context).getString(SELLER_ID, DEFAULT_ID);
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
        String sellerId = MyPreferenceUtils.getSp(context).getString(SELLER_ID, DEFAULT_ID);
        if (!DEFAULT_ID.equals(sellerId)) {
            if (NetWorkJudge.isNetworkAvailable(context)) {
                // 进行数据更新
                boolean isFirstLogin = MyPreferenceUtils.getSp(context).getBoolean(IS_FIRST_LOGIN, false);
                if(!isFirstLogin){
                    jumpActivity(DataFlushActivity.class, true);
                }else {
                    jumpActivity(ScreenActivity.class, true);
                }
            } else {
                jumpActivity(ScreenActivity.class, true);
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
                    MyPreferenceUtils.getSp(context).edit().putString(SELLER_ID, sellerId).apply();
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

    private Handler handler;
    private int requestCount = 3;

    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {

                    case NOTIFY_SUCCESS:
                        if (successFlag == requestCount)
//                            jumpActivity();
                            break;
                    case NOTIFY_JUMP:
//                        jumpActivity();
                        break;
                }
                return false;
            }
        });
    }

    /****************************************************************************************/

    public void setInitView() {

    }

    /**
     * 上下文对象
     */
    private Context context;
    //网络帮助类
    private NetHelper netHelper;

    private void jumpActivity(Class clazz, boolean isFinish) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
        if (isFinish) {
            this.finish();
        }

    }


    @Override
    public void onBackPressed() {
    }

    @Override
    public void onResponse(VolleyError volleyError, int flag) {
        MyToast.toastShort(context, "网络请求失败");

        switch (flag) {
            case 1:
                break;
            case 2:
//                jumpActivity();
                MyToast.toastShort(context, "初始化数据不完全");
                break;
            case 7:
                handler.sendEmptyMessage(NOTIFY_JUMP);
                break;

            default:
                break;
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject, int flag) {
        try {
            final ResultInfo resultInfo = JSON.parseObject(jsonObject.toString(), ResultInfo.class);
            switch (flag) {
                case 1:
                    if (resultInfo != null) {
                        if (resultInfo.getStatus() == 0) {
                            UserInfo userInfo = JSON.parseObject(resultInfo.getData(), UserInfo.class);
                            if (userInfo != null) {
                                userInfo.setId(1);
//                                boolean isSuccess = userInfoDao.updateOrInsert(userInfo);
                                sysApplication.setUserInfo(userInfo);//保存信息
                                Message message = handler.obtainMessage();
                                message.arg1 = userInfo.getTid();
                                message.what = NOTIFY_JUMP;
                                handler.sendMessage(message);
                            }
                        } else {
                            MyToast.toastLong(context, "未获取到秤的配置信息");
                        }
                    } else {
                        MyToast.toastLong(context, "未获取到秤的配置信息");
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int successFlag = 0;

}
