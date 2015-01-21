package example.android.ait.hu.practiceroomassistant.broadcastreciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

import example.android.ait.hu.practiceroomassistant.R;
import example.android.ait.hu.practiceroomassistant.SongListActivity;
import example.android.ait.hu.practiceroomassistant.data.Reminder;

/**
 * Created by Sarah Read on 12/14/2014.
 *
 * Called on time tick
 */
public class NotificationBroadcastReciever extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 102;

    /**
     * When time tick is received determine if any of the user
     * created reminders should go off
     * If so, create a notification
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        //get an instance of calendar
        Calendar calendar = Calendar.getInstance();

        //get all user reminders
        ArrayList<Reminder> reminders = (ArrayList<Reminder>) Reminder.listAll(Reminder.class);

        //get current day, hour, and minute
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        //loop through all reminders
        for(int i = 0; i<reminders.size(); i++){
            Reminder curReminder = reminders.get(i);

            //convert the reminder day integer to the
            //calendar day integer
            int day = ((curReminder.getDay()+1)%7)+1;

            if(day == today){
                if(hour == curReminder.getStartHour()){
                    if(min == curReminder.getStartMin()){

                        //if the day hour and minute are all the same create
                        //a notification
                        Intent resultIntent = new Intent(context, SongListActivity.class);
                        PendingIntent pi = PendingIntent.getActivity(context,
                                NOTIFICATION_ID,resultIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT);

                        //set the message to "Time to practice!" if no
                        //message was set by the user
                        String theMessage = "Time to practice!";
                        if(!curReminder.getMessage().equals("")){
                            theMessage = curReminder.getMessage();
                        }

                        Notification notification = new Notification.Builder(context)
                                .setContentTitle("Practice Reminder")
                                .setContentText(theMessage)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setSmallIcon(R.drawable.treble_clef_small)
                                .setAutoCancel(true)
                                .setContentIntent(pi)
                                .build();


                        NotificationManager mNotificationManager =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                        //set the notification
                        mNotificationManager.notify(NOTIFICATION_ID, notification);

                        //if the reminder should only go off once delete it
                        if(!curReminder.isRepeat()){
                            curReminder.delete();
                        }
                    }
                }
            }
        }
    }
}
