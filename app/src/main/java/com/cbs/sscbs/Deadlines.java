package com.cbs.sscbs;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class Deadlines extends AppCompatActivity {

    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadlines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_deadlines);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        button = (Button)findViewById(R.id.timePicker);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(),"timePicker");
//
//            }
//        });
    }

    private void setToolbar() {

    }

//
//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
//        calendar.set(Calendar.MINUTE,minute);
//        startAlarm(calendar);
//    }
//
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void startAlarm(Calendar time) {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(getApplicationContext(),AlertReciever.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext() , 1, intent ,0);
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP , time.getTimeInMillis(),pendingIntent);
//
//    }
}
