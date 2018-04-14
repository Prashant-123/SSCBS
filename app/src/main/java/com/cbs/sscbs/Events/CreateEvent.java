package com.cbs.sscbs.Events;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class CreateEvent extends AppCompatActivity {

    public static final String FB_STORAGE_PATH = "image/", FB_DATABASE_PATH = "image", TAG = "TAG";
    public static final int REQUEST_CODE = 1234;

    ArrayList<String> VenueThings = new ArrayList<>();
    ArrayList<String> TimeThings = new ArrayList<>();

    final Calendar cal = Calendar.getInstance();

    private static int CalendarHour, CalendarMinute, child;
    Calendar calendar;
    String date, time, v;
    TimePickerDialog timepickerdialog;
    String dateStr, timeStr1, timeStr2, et4, sot;
    int year_x, month_x, date_x, img;
    ImageView image;

    TimePickerDialog.OnTimeSetListener t = null;
    private FirebaseDatabase database, db;
    private StorageReference storageReference;
    private DatabaseReference databaseRef;
    private Uri imgUri;

    DatePickerDialog.OnDateSetListener dpickerListener;

    public static String theMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("EventThings");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                child = (int) dataSnapshot.getChildrenCount();

                db = FirebaseDatabase.getInstance();
                for (int i=1; i<=child; i++) {
                    final DatabaseReference venue = db.getReference("EventThings").child(String.valueOf(i)).child("venue");
                    final DatabaseReference time = db.getReference("EventThings").child(String.valueOf(i)).child("time");

                    venue.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String v = dataSnapshot.getValue(String.class);
                            VenueThings.add(v);

                            time.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                            String time = dataSnapshot.getValue(String.class);
                            String t = Objects.requireNonNull(time).substring(18, 20) + time.substring(23, 25);
                            String d = time.substring(0, 16);
                            TimeThings.add(t);
                            TimeThings.add(d);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("The read failed: " + databaseError.getCode());
                                }
                            });
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        dpickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_x = dayOfMonth;
                month_x = month;
                year_x = year;
                dateStr = " " + date_x + " " + theMonth(month_x) + " " + year_x + ", ";
                int newMonth = month + 1;
                sot = year + "" + newMonth + "-" + dayOfMonth;
                et4 = dateStr;
                Toast.makeText(CreateEvent.this, dateStr, Toast.LENGTH_SHORT).show();
            }
        };

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);
        image = (ImageView) findViewById(R.id.newEventImage);
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        date_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialoguOnButtonClick();
        findViewById(R.id.startTime).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                timepickerdialog = new TimePickerDialog(v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int hour = hourOfDay%12;
                                timeStr1 = String.format("%02d:%02d%s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am-" : "pm-");
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }

        });

        findViewById(R.id.endTime).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                timepickerdialog = new TimePickerDialog(v.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                int hour = hourOfDay%12;
                                timeStr2 = String.format("%02d:%02d%s", hour == 0 ? 12 : hour, minute, hourOfDay < 12 ? "am" : "pm");
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });

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

    public void save(View view)
    {
        final EditText et3 = (EditText) findViewById(R.id.newVenue);
        final EditText et1 = (EditText) findViewById(R.id.newTitle);
        final EditText et2 = (EditText) findViewById(R.id.newOrganiser);
        final EditText desc = (EditText) findViewById(R.id.eventDescription);
        final EditText link = (EditText) findViewById(R.id.registrationLink);
        final EditText mobNo = (EditText) findViewById(R.id.mobNo);


        try{
            et4 = dateStr + timeStr1 + timeStr2;
            date = et4.substring(0, 16);
            time = et4.substring(18, 20) + et4.substring(23, 25);
            v = et3.getText().toString();
        } catch (Exception e){
            Toast.makeText(view.getContext(), "nope", Toast.LENGTH_LONG);
        }
        if (VenueThings.contains(v) && TimeThings.contains(date) && TimeThings.contains(time))
        {
            Snackbar.make(view, "This location is already booked for the time you selected.", Snackbar.LENGTH_SHORT);
        } else {
            if (!et1.getText().toString().isEmpty() && !et2.getText().toString().isEmpty() &&
                    !et3.getText().toString().isEmpty() && !et4.isEmpty() && sot.isEmpty() && !desc.getText().toString().isEmpty() &&
                    !link.getText().toString().isEmpty() && !mobNo.getText().toString().isEmpty() ) {
                final ProgressDialog dialogue = new ProgressDialog(this);
                dialogue.setTitle("Uploading...");
                dialogue.show();

                StorageReference ref = storageReference.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));
                ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        dialogue.dismiss();
                        Toast.makeText(CreateEvent.this, "Image-Uploaded", Toast.LENGTH_SHORT).show();

                        Intent intent = getIntent();
                        int count = intent.getIntExtra("COUNT", 0);
                        Log.i("venue", et3.getText().toString());

                        DataClass data = new DataClass(et1.getText().toString(), et2.getText().toString(),
                                et3.getText().toString(), et4, sot, img, count, desc.getText().toString(),
                                link.getText().toString(), mobNo.getText().toString(),
                                Objects.requireNonNull(taskSnapshot.getDownloadUrl()).toString());
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
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialogue.setMessage("Uploaded " + (int) progress + "%");

                    }
                });
            }
            else {
                Snackbar.make(view, "Fill all required details", Snackbar.LENGTH_SHORT).show();
                Log.wtf(TAG,et1.getText().toString() + "--" +  et2.getText().toString() + "--" +
                        et3.getText().toString() + "--" +  et4 + "--" +  sot + "--" +  desc.getText().toString() + "--" +
                        link.getText().toString() + "--" +  mobNo.getText().toString());
//                Log.wtf(TAG , String.valueOf(!et1.getText().toString().isEmpty()) +  String.valueOf(et2.getText().toString().isEmpty()) +
//                        String.valueOf(et3.getText().toString().isEmpty()) + String.valueOf(et4.isEmpty()) + String.valueOf(sot.isEmpty()) +
//                        String.valueOf(desc.getText().toString().isEmpty()) + String.valueOf(link.getText().toString().isEmpty()) +
//                        String.valueOf(mobNo.getText().toString().isEmpty()));
            }
        }
    }

    public void btnBrowse(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select-File"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!= null && data.getData()!=null)
                imgUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                image.setImageBitmap(bitmap);
                image.setBackgroundColor(000000);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            Toast.makeText(this, "Select any image to upload", Toast.LENGTH_SHORT).show();
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        return MimeTypeMap.getFileExtensionFromUrl(contentResolver.getType(uri));
    }
}