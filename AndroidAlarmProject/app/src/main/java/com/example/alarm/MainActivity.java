package com.example.alarm;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_main);
        Log.i("creation", "Main Activity created");

        //Start location service
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Log.i("permission", "Permission denied");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 22);
            Log.i("permission", "Requesting permission");
        }
        else{
            Log.i("permission", "Permission granted, attempt to start LocationService");
            Intent i = new Intent(this, LocationService.class);
            startService(i);
        }
        //Start Location Alarm
        LocAlarmObj.getInstance().cancelAlarm(this);
        LocAlarmObj.getInstance().setInterval(120000);
        LocAlarmObj.getInstance().setAlarm(this);

    }

    public void gotoLocAlarm(View view) {
        Intent goToLoc = new Intent(this, LocAlarm.class);
        startActivity(goToLoc);
    }

    public void gotoAlarmByDate(View view) {
        Intent gotoAlarmByDate = new Intent(this, DateAlarm.class);
        startActivity(gotoAlarmByDate);
    }

    public void gotoAlarmByTimer(View view) {
        Intent gotoAlarmByTimer = new Intent(this, TimerAlarm.class);
        startActivity(gotoAlarmByTimer);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

}
