package com.example.alarm;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class LocationService extends Service {
    Context mContext;
    LocationManager locationManager;

    @Override
    public void onCreate(){

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("creation", "LocationService handling intent");
        mContext = this;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.i("permission", "Permission granted, receiving location updates");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locListener);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    LocationListener locListener = new LocationListener(){
        @Override
        public void onLocationChanged(android.location.Location loc){
            Log.i("location","Location changed");
            LocAlarmObj.getInstance().cancelAlarm(mContext);
            LocAlarmObj.getInstance().setAlarm(mContext);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(1);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){

        }
        @Override
        public void onProviderDisabled(String provider){

        }
        @Override
        public void onProviderEnabled(String provider){

        }
    };
}

