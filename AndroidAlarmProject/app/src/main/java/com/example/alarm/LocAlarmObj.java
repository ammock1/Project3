package com.example.alarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class LocAlarmObj extends BroadcastReceiver {
    //was going to use singleton design pattern, but android insisted on it having a default constructor

    private static LocAlarmObj locAl = new LocAlarmObj();
    Context mContext;
    private long interval = 120000;
    NotificationChannel channel;

    public static LocAlarmObj getInstance(){
        return locAl;
    }

    public LocAlarmObj(){
        interval = 120000;
    }

    public void setAlarm(Context context){
        mContext = context;
        Intent i = new Intent(context, LocAlarmObj.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 5, i, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(am!=null){
            am.cancel(pi);
        }

        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+locAl.interval, pi);

    }

    public void setInterval(long inter){
        interval = inter;
    }

    public void cancelAlarm(Context context){
        mContext = context;
        Intent intent = new Intent(context, LocAlarmObj.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 5, intent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if(am!=null){
            am.cancel(sender);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent){
       // if(intent.getAction())
        mContext = context;
        Log.i("alarm", "Location alarm received");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("Channel1", "noteChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("it's a channel");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder locNoteBuilder = new NotificationCompat.Builder(context, "Channel1")
                .setContentText("Get up and walk!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(1, locNoteBuilder.build());

    }

}