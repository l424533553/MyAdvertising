package com.luofx.mvp.base.view;

/**
 * 作者：罗发新
 * 时间：2018/12/21 0021    11:10
 * 邮件：424533553@qq.com
 * 说明：
 */
public interface IBaseView extends IBaseXView {

    /**
     * 显示正在加载 view
     */
    void showLoading();

    /**
     * 关闭正在加载 view
     */
    void hideLoading();

    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(String msg);
}
