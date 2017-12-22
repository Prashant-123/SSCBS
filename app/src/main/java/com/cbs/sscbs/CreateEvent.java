package com.cbs.sscbs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cbs.sscbs.Fragments.Events_Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import com.cbs.sscbs.ScheduleClient;

public class CreateEvent extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    String dateStr,timeStr1,timeStr2, et4;
    final Calendar cal = Calendar.getInstance();
    final Calendar time = Calendar.getInstance();
    int year_x, month_x, date_x;


    String sot;
    Button notifyBtn ;
    int img;
    Integer REQUEST_CAMERA =  1 , SELECT_FILE = 0 ;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayout layout0 = (LinearLayout) findViewById(R.id.subLayout);
        layout0.setVisibility(View.GONE);

        final TextView text0 = (TextView) findViewById(R.id.desTitle);
        text0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LinearLayout layout0 = (LinearLayout) findViewById(R.id.subLayout);

                if(layout0.getVisibility()== View.GONE) {
                    layout0.setVisibility(View.VISIBLE);
                }
                else layout0.setVisibility(View.GONE);

            }
        });


        image = (ImageView) findViewById(R.id.newEventImage);
        image.setVisibility(View.GONE);

        final TextView text1 = (TextView) findViewById(R.id.imageTitle);
        text1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ImageView image = (ImageView) findViewById(R.id.newEventImage);

                if(image.getVisibility()== View.GONE) {
                    image.setVisibility(View.VISIBLE);
                }
                //else image.setVisibility(View.GONE);

            }
        });
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        date_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialoguOnButtonClick();
    }

    public void uploadEventImage(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage()
    {
        final CharSequence[] items = {"Camera" , "Gallery" , "Cancel"} ;
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateEvent.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Camera")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null)
                        startActivityForResult(intent,REQUEST_CAMERA);

                }else if(items[which].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*") ;
                    startActivityForResult(intent.createChooser(intent,"Select File") , SELECT_FILE);
                }else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    @Override
    public void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap)bundle.get("data");
                image.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectImageUri = data.getData();
                image.setImageURI(selectImageUri);
            }
        }
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
             updateTime(0);
            updateTime(1);
        }
    };
    TimePickerDialog.OnTimeSetListener t=null;
    private void updateTime(int i)
    { new TimePickerDialog(this, t, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), false).show();
    time(i);}

    private void time(final int i) {
         t = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time.set(Calendar.MINUTE, minute);
                int hour = hourOfDay % 12;
                if(i==0)
                timeStr1 = String.format("%02d:%02d%s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm");
                if(i==1)
                    timeStr2 = String.format("%02d:%02d%s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm");
                et4= dateStr + timeStr1 + timeStr2;

            }
        };
    }

    public void save(View view)
    {
        final EditText et1 = (EditText) findViewById(R.id.newTitle);
        EditText et2 = (EditText) findViewById(R.id.newOrganiser);
        EditText et3 = (EditText) findViewById(R.id.newVenue);
        EditText desc = (EditText) findViewById(R.id.eventDescription);
        EditText link = (EditText) findViewById(R.id.registrationLink);
        EditText mobNo = (EditText) findViewById(R.id.mobNo);

        Intent intent = getIntent();
        int count = intent.getIntExtra("COUNT", 0);
        Log.i("venue" ,et3.getText().toString() );
        //send(et4);
        DataClass data = new DataClass(et1.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4, sot , img, count, desc.getText().toString(), link.getText().toString(), mobNo.getText().toString());
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();

        String ctr = String.valueOf(count);
        Log.i("tag", "count:  " + count);
        databaseRef.child("EventThings").child(ctr).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CreateEvent.this, "Event Created", Toast.LENGTH_SHORT).show();
                onBackPressed();

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
