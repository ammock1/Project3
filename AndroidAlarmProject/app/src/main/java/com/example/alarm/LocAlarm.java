package com.example.alarm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class LocAlarm extends AppCompatActivity {
//Activity that gets updates to Location Alarm's interval
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_alarm);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText locMins = (EditText)findViewById(R.id.locMins);
        final Context mContext = this;

        locMins.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
                if(!locMins.getText().toString().equals("")){
                    int minutes = Integer.parseInt(locMins.getText().toString());
                    long interval = minutes * 60000;
                    LocAlarmObj.getInstance().cancelAlarm(mContext);
                    LocAlarmObj.getInstance().setInterval(interval);
                    LocAlarmObj.getInstance().setAlarm(mContext);
                }

            }
            public void beforeTextChanged(CharSequence s, int start,
            int count, int after){

            }
            public void onTextChanged(CharSequence s, int start,
            int before, int count){

            }
        });

    }


}
