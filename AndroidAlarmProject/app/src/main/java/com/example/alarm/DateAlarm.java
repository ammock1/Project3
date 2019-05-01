package com.example.alarm;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class DateAlarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private int LOCATION_PERMISSION_CODE = 1;
    int year, month, day, hour, minute = 0;
    TimeZone timeZone = TimeZone.getDefault();
    String frequency = "Day";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == LOCATION_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT);
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_by_date);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner repeat = findViewById(R.id.repeatSpinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.repeatEvery, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeat.setAdapter(adapter1);
        repeat.setOnItemSelectedListener(this);

        Spinner timeZoneSpinner = findViewById(R.id.timeZoneSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.timeZone, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeZoneSpinner.setAdapter(adapter2);
        timeZoneSpinner.setOnItemSelectedListener(this);

        Button timePickButton = findViewById(R.id.setTimeButton);
        timePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button datePickButton = findViewById(R.id.setDateButton);
        datePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Button createDateAlarmButton = findViewById(R.id.createDateAlarmButton);
        createDateAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(DateAlarm.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    createDateAlarm(v);
                }
                else{
                    requestLocationPermission();
                }

            }
        });

    }

    private void requestLocationPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Location is needed to create a new alarm")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(DateAlarm.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idInt = parent.getId();
        switch (idInt){
            case R.id.timeZoneSpinner:
                if(parent.getItemAtPosition(position).toString().equals("Eastern"))
                    timeZone = TimeZone.getTimeZone("US/Eastern");
                if(parent.getItemAtPosition(position).toString().equals("Central"))
                    timeZone = TimeZone.getTimeZone("US/Central");
                if(parent.getItemAtPosition(position).toString().equals("Mountain"))
                    timeZone = TimeZone.getTimeZone("US/Mountain");
                if(parent.getItemAtPosition(position).toString().equals("Pacific"))
                    timeZone = TimeZone.getTimeZone("US/Pacific");
                break;
            case R.id.repeatSpinner:
                if(parent.getItemAtPosition(position).toString().equals("Day"))
                    frequency = "Day";
                if(parent.getItemAtPosition(position).toString().equals("15 Minutes"))
                    frequency = "15 Minutes";
                if(parent.getItemAtPosition(position).toString().equals("Half Hour"))
                    frequency = "Half Hour";
                if(parent.getItemAtPosition(position).toString().equals("Hour"))
                    frequency = "Hour";
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onRadioButtonClicked(View view) {
        TextView recursiveTextView = findViewById(R.id.recursiveTextView);
        Spinner repeat = findViewById(R.id.repeatSpinner);

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonOneTime:
                if (checked)
                    recursiveTextView.setVisibility(View.INVISIBLE);
                    repeat.setVisibility(View.INVISIBLE);
                    break;
            case R.id.radioButtonRecursive:
                if (checked)
                    recursiveTextView.setVisibility(View.VISIBLE);
                    repeat.setVisibility(View.VISIBLE);
                    break;
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createDateAlarm(View view){
        Calendar c = Calendar.getInstance(timeZone);
        RadioButton oneTimeRadio = findViewById(R.id.radioButtonOneTime);
        RadioButton recursiveRadio = findViewById(R.id.radioButtonRecursive);
        if(year < c.get(Calendar.YEAR)){
            if(month < c.get(Calendar.MONTH)){
                if(day < c.get(Calendar.DAY_OF_MONTH)){
                    if(hour < c.get(Calendar.HOUR_OF_DAY)){
                        if(minute <= c.get(Calendar.MINUTE)){
                            Toast.makeText(DateAlarm.this, "Either Alarm Date was not set or Alarm Date is set in the Past", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
        else if(oneTimeRadio.isChecked()){
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

            Toast.makeText(DateAlarm.this, "New One Time Alarm Created", Toast.LENGTH_SHORT).show();
        }
        else if(recursiveRadio.isChecked()){
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
            if(frequency.equals("Day"))
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            else if(frequency.equals("15 Minutes"))
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
            else if(frequency.equals("Half Hour"))
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
            else if(frequency.equals("Hour"))
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);

            Toast.makeText(DateAlarm.this, "New Repeating Alarm Created", Toast.LENGTH_SHORT).show();
        }
    }
}
