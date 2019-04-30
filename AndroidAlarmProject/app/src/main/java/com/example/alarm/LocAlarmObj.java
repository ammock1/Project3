package com.example.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public class LocAlarmObj extends BroadcastReceiver {
    //using singleton design pattern
    private static LocAlarmObj locAl = new LocAlarmObj();
    private LocAlarmObj(){

    }
    public static LocAlarmObj getInstance(){
        return locAl;
    }
    public void setAlarm(Context context){
       /* AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 120000, pi);*/
    }
    public void cancelAlarm(Context context){
        /*Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);*/
    }
    @Override
    public void onReceive(Context context, Intent intent){
        /*PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock w1 = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "wakey:");
        w1.acquire();
        //Put code to send notification
        w1.release();*/
    }
}