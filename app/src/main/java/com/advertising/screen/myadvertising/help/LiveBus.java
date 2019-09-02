package com.advertising.screen.myadvertising.help;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * 作者：罗发新
 * 时间：2019/6/11 0011    星期二
 * 邮件：424533553@qq.com
 * 说明：
 */
public class LiveBus {

//    public static LiveEventBus.Observable observe(String key, Class type) {
//        return LiveEventBus.get().with(key, type);
//    }

    public static <T> void post(String key, Class<T> type, T msg) {
        LiveEventBus.get().with(key, type).post(msg);
    }

    public static <T> void post(String key, Class<T> type, T msg, long delay) {
        LiveEventBus.get().with(key, type).postDelay(msg, delay);
    }

    public static <T> void observe(String key, Class<T> type, @NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        LiveEventBus.get().with(key, type).observe(owner, observer);
    }

    public static <T> void observeForever(String key, Class<T> type, @NonNull Observer<T> observer) {
        LiveEventBus.get().with(key, type).observeForever(observer);
    }

    public static <T> void removeObserver(String key, Class<T> type, @NonNull Observer<T> observer) {
        LiveEventBus.get().with(key, type).removeObserver(observer);

    }
}
