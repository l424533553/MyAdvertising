//package com.axecom.smartweight.base;
//
//import android.content.Context;
//import android.graphics.Color;
//import cn.pedant.SweetAlert.SweetAlertDialog;
//import com.advertising.SysApplication;
//import com.axecom.smartweight.ui.uiutils.UIUtils;
//
///**
// * Created by Longer on 2016/10/26.
// */
//public class BaseDialog {
//
//    private SweetAlertDialog mSweetAlertDialog;
//    protected SysApplication sysApplication; //
//    private Context context;  // 数据功能
//
//    public BaseDialog(Context context) {
//        this.context = context;
//    }
//
//    public void showLoading(String titleText, int type) {
//        if (mSweetAlertDialog != null) {
//            mSweetAlertDialog.dismiss();
//        }
//        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mSweetAlertDialog.setTitleText(titleText);
//        mSweetAlertDialog.setCancelable(true);
//        mSweetAlertDialog.show();
//    }
//
//    public void showLoading() {
//        if (mSweetAlertDialog != null) {
//            if (mSweetAlertDialog.isShowing()) {
//                mSweetAlertDialog.dismiss();
//            }
//            mSweetAlertDialog = null;
//        }
//        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mSweetAlertDialog.setCancelable(true);
//        mSweetAlertDialog.show();
//    }
//
//    public void showLoading(String titleText, String confirmText) {
//        if (mSweetAlertDialog != null) {
//            mSweetAlertDialog.dismiss();
//        }
//        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
//        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mSweetAlertDialog.setTitleText(titleText);
//        mSweetAlertDialog.setConfirmText(confirmText);
//        mSweetAlertDialog.setCancelable(true);
//        mSweetAlertDialog.show();
//    }
//
//
//    /**
//     * 功能测试
//     */
//    private void testFunction() {
//        // 数据功能选项 ，测试功能 功能
//
//    }
//
//    public void showLoading(String titleText, String confirmText, long times) {
//        showLoading(titleText, confirmText);
//        UIUtils.getMainThreadHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                closeLoading();
//            }
//        }, times);
//    }
//
//    public void showLoading(String titleText) {
//        SweetAlertDialog mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
//        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mSweetAlertDialog.setTitleText(titleText);
//
//        mSweetAlertDialog.setCancelable(true);
//        mSweetAlertDialog.show();
//    }
//
//    public void closeLoading() {
//        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing()) {
//            mSweetAlertDialog.dismissWithAnimation();
//        }
//    }
//}
//
//
