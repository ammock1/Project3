package com.example.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class LocAlarmObj extends BroadcastReceiver {
    //was going to use singleton design pattern, but android insisted on it having a default constructor

    private static LocAlarmObj locAl = new LocAlarmObj();
    Context mContext;
    private long interval = 5000;
    NotificationChannel channel;

    public static LocAlarmObj getInstance(){
        return locAl;
    }

    public LocAlarmObj(){
    }

    public void setAlarm(Context context){
        mContext = context;
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, LocAlarmObj.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pi);
    }

    public void setInterval(long inter){
        interval = inter;
    }

    public void cancelAlarm(Context context){
        mContext = context;
        Intent intent = new Intent(context, LocAlarmObj.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
    @Override
    public void onReceive(Context context, Intent intent){
       /* PowerManager pm = (PowerManager)context.getSystemService(context.POWER_SERVICE);
        PowerManager.WakeLock w1 = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "wakey:");
        w1.acquire();*/
        mContext = context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("Channel1", "noteChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("it's a channel");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder locNoteBuilder = new NotificationCompat.Builder(context, "Channel1")
                .setContentText("Get up and walk!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                ;
        notificationManager.notify(1, locNoteBuilder.build());

       // w1.release();
    }
}