package com.example.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TimerAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_by_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onButtonClicked(View view) {
        TextView messageTextView = findViewById(R.id.messageText);
        String message = messageTextView.getText().toString();
        TextView timeTextView = findViewById(R.id.timerText);
        int mins = Integer.parseInt((String) timeTextView.getText());

        scheduleTimedNotification(mins, message);
    }

    private void scheduleTimedNotification(int minutes, String message) {

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Timer set at location "/*ADD LOCATION*/);
        builder.setContentText(message);
        Notification notification = builder.build();

        Intent notificationIntent = new Intent(this, TimerAlarmReceiver.class);
        notificationIntent.putExtra(TimerAlarmReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(TimerAlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + (minutes * 60000);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
