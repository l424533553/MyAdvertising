package com.advertising.screen.myadvertising.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 作者：罗发新
 * 时间：2019/9/26 0026    星期四
 * 邮件：424533553@qq.com
 * 说明：走马灯 控件
 */
public class MarqueeTextView extends AppCompatTextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
