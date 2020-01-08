package com.advertising.screen.myadvertising.func;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.advertising.screen.myadvertising.common.iinterface.IConstants;
import com.advertising.screen.myadvertising.common.iinterface.IEventBus;
import com.advertising.screen.myadvertising.mvvm.main.ui.MainActivity;
import com.xuanyuan.library.utils.LiveBus;


/**
 * 注册广播
 */
public class BootBroadcastReceiver extends BroadcastReceiver implements IConstants , IEventBus {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_BOOT:
                    Intent mBootIntent = new Intent(context, MainActivity.class);
                    mBootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mBootIntent);
                    break;
                case CONNECTIVITY_CHANGE:
                    LiveBus.post(NOTIFY_LIVEBUS_KEY, String.class, NOTIFY_NET_CHANGE);
                    break;
                default:
                    break;
            }
        }
    }
}