package com.example.alarm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void onStart(){
        super.onStart();
        setContentView(R.layout.activity_main);
        Log.i("creation", "Main Activity created");
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
    }

    public void gotoLocAlarm(View view) {
        Intent goToLoc = new Intent(this, LocAlarm.class);
        startActivity(goToLoc);
    }

    public void gotoOneTimeAlarm(View view) {
        Intent gotoOneTimeAlarm = new Intent(this, OneTimeAlarm.class);
        startActivity(gotoOneTimeAlarm);
    }

    public void gotoRecursiveAlarm(View view) {
        Intent gotoRecursiveAlarm = new Intent(this, RecursiveAlarm.class);
        startActivity(gotoRecursiveAlarm);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }


}
