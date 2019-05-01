package com.example.alarm;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class TimerAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_by_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onButtonClicked(View view) {
        EditText messageText = findViewById(R.id.messageText);
        String message = messageText.getText().toString();
        EditText timeText = findViewById(R.id.timerText);
        int mins = Integer.parseInt(timeText.getText().toString());
        EditText dayText = findViewById(R.id.dayText);
        int days = Integer.parseInt(dayText.getText().toString());

        scheduleTimedNotification(days, mins, message);
    }

    private void scheduleTimedNotification(int days, int minutes, String message) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String location="";
        double latitude=0;
        double longitude=0;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

                    latitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
                    longitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
                    location = "Latitude:" + latitude +", Longitude: " + longitude;
        }



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, TimerAlarmReceiver.CHANNEL_ID);
        builder.setContentTitle("Timer Notification");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(location+"\n"+message));
        builder.setContentText(location+"...");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        Notification notification = builder.build();

        Intent notificationIntent = new Intent(this, TimerAlarmReceiver.class);
        notificationIntent.putExtra(TimerAlarmReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(TimerAlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + (minutes * 60000);
        futureInMillis += days * 60000 * 24 * 60;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
