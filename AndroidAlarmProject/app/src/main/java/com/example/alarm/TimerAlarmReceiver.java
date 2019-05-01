package com.example.alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class TimerAlarmReceiver extends BroadcastReceiver {

	public static String NOTIFICATION_ID = "id";
	public static String NOTIFICATION = "notification";
	public static String CHANNEL_ID = "TimerAlarmChannel";
	NotificationChannel nChannel;

	@Override
	public void onReceive (Context context, Intent intent) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			nChannel = new NotificationChannel(CHANNEL_ID, "timerChannel", NotificationManager.IMPORTANCE_DEFAULT);
			nChannel.setDescription("Timer Alarm Notification Channel");

			NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
			notificationManager.createNotificationChannel(nChannel);
			Notification notification = intent.getParcelableExtra(NOTIFICATION);
			notificationManager.notify(intent.getIntExtra(NOTIFICATION_ID, 0), notification);
		}
	}
}