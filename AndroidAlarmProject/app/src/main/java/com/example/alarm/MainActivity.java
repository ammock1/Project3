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

    public void gotoOneTimeAlarm(View view) {
        Intent gotoOneTimeAlarm = new Intent(this, OneTimeAlarm.class);
        startActivity(gotoOneTimeAlarm);
    }

    public void gotoRecursiveAlarm(View view) {
        Intent gotoRecursiveAlarm = new Intent(this, RecursiveAlarm.class);
        startActivity(gotoRecursiveAlarm);
    }
}
