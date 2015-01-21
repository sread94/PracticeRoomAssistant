package example.android.ait.hu.practiceroomassistant.broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import example.android.ait.hu.practiceroomassistant.service.NotificationService;

/**
 * Created by Sarah Read on 11/30/2014.
 *
 * Called on Boot Complete
 */
public class ReceiveOnBoot extends BroadcastReceiver{

    /**
     * when boot is complete start the NotificationService
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, NotificationService.class));
        }
    }
}
