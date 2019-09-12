package com.advertising.screen.myadvertising.help;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.advertising.screen.myadvertising.activity.HomeActivity;
import com.advertising.screen.myadvertising.config.IConstants;
import com.xuanyuan.library.utils.LiveBus;

import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_LIVEBUS_KEY;
import static com.advertising.screen.myadvertising.config.IEventBus.NOTIFY_NET_CHANGE;


public class BootBroadcastReceiver extends BroadcastReceiver implements IConstants {

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    private static final String CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case ACTION_BOOT:
                    Intent mBootIntent = new Intent(context, HomeActivity.class);
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