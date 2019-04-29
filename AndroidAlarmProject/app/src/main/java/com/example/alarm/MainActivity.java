package com.example.alarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
