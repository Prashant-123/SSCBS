package com.cbs.sscbs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateEvent extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    String dateStr,timeStr, et4;
    final Calendar cal = Calendar.getInstance();
    final Calendar time = Calendar.getInstance();
    int year_x, month_x, date_x;

    String sot;
    int img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        date_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialoguOnButtonClick();
    }
    public void showDialoguOnButtonClick()
    {
        findViewById(R.id.newTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id)
    {
        if (id==0)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, date_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date_x = dayOfMonth; month_x = month; year_x = year;
             dateStr = " "+date_x+" "+ theMonth(month_x)+ " "+ year_x + ", ";
            int newMonth = month+1;

            sot = year +""+ newMonth + "-" + dayOfMonth;

            Log.i("tag", sot);
             updateTime();
        }
    };

    private void updateTime()
    { new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), false).show(); }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            time.set(Calendar.MINUTE, minute);
            int hour = hourOfDay % 12;
            timeStr = String.format("%02d:%02d%s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm");
            et4= dateStr + timeStr;
        }
    };

    public void save(View view)
    {
        EditText et1 = (EditText) findViewById(R.id.newTitle);
        EditText et2 = (EditText) findViewById(R.id.newOrganiser);
        EditText et3 = (EditText) findViewById(R.id.newVenue);


        DataClass data = new DataClass(et1.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4, sot , img);
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();

        Intent intent = getIntent();
        int count = intent.getIntExtra("COUNT", 0);
        String ctr = String.valueOf(count);
        Log.i("tag", "count:  "+count);

        databaseRef.child("EventThings").child(ctr).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CreateEvent.this, "Event Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Events.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateEvent.this, "Internal Error Occurred. \n Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

}
