package example.android.ait.hu.practiceroomassistant.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import example.android.ait.hu.practiceroomassistant.broadcastreciever.NotificationBroadcastReciever;

/**
 * Created by Sarah Read on 12/14/2014.
 *
 * Service runs so that if the program is shut the broadcast reciever for the
 * reminders will still work and notifications will still appear
 */
public class NotificationService extends Service {

    /**
     * onCreate register the NotificationBroadcastReciever for Action_Time_Tick
     */
    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(new NotificationBroadcastReciever(), new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    /**
     * set the service to Start_Sticky so it does not stop
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
