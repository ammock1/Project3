package com.example.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.example.alarm.NotificationService.LocalBinder;

public class LocAlarmObj extends BroadcastReceiver {
    //was going to use singleton design pattern, but android insisted on it having a default constructor

    private static LocAlarmObj locAl = new LocAlarmObj();
    private long interval = 5000;
    private boolean bounded;
    //NotificationService noteServe = new NotificationService();

    public static LocAlarmObj getInstance(){
        return locAl;
    }

    public LocAlarmObj(){
    }

    public void setAlarm(Context context){
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, LocAlarmObj.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pi);
    }

    public void setInterval(long inter){
        interval = inter;
    }

    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, LocAlarmObj.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
    @Override
    public void onReceive(Context context, Intent intent){
        /*Intent mIntent = new Intent(context, NotificationService.class);
        context.bindService(mIntent, connection, context.BIND_AUTO_CREATE);*/
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock w1 = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "wakey:");
        w1.acquire();
        //noteServe.showLocNote();
        Log.i("alarm", "onReceive of LocAlarmObj called");
        w1.release();
        //context.unbindService(connection);
    }
 /*   ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            bounded = false;
            noteServe = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bounded = true;
            LocalBinder mLocalBinder = (LocalBinder)service;
            noteServe = mLocalBinder.getInstance();
        }
    };*/
}