package com.cbs.sscbs.Others;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.cbs.sscbs.Attendance.StudentsRecord;
import com.cbs.sscbs.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deadlines extends AppCompatActivity {

    private static final String TAG = "TAG";
    private String name;
    private String email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StudentsRecord studentsRecord = new StudentsRecord() ;


    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());


    String getMonth = formattedDate.substring(3, 6);

    String months = "Months" ;
    String day = "Day";
    String classes = "Class";
    String teachers = "Teachers" ;
    String subjects = "Subjects" ;
    String students = "Students" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadlines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_deadlines);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = df.format(c.getTime());

        Log.wtf(TAG , formattedDate);

        String getMonth = formattedDate.substring(3);
//
//        String months = "Months" ;
//        String day = "Day";
//        String classes = "Class";
//        String teachers = "Teachers" ;
//        String subjects = "Subjects" ;
//        String students = "Students" ;
//


//
////        String test = "hi";
//        Map<String, Object> city = new HashMap<>();
//        city.put("name", "Los Angeles");
//        city.put("state", "CA");
//        city.put("country", "USA");
//        db.collection("Years").document("2018-19").collection(months).document(getMonth)
//                .collection(day).document(formattedDate).collection(classes).document("Bsc-1")
//                .collection(teachers).document("Tanvi Goyal").collection(subjects).document("C++")
//                .collection(students).document();
//        db.collection("Teachers/KR/Class/Bsc-1/Subjects/C++/Day").document("12-Jan").collection(test).document(formattedDate).set(city);
//
//        //db.collection("cities").document(formattedDate).collection(test);
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (currentUser != null) {
//            name = currentUser.getDisplayName();
//            email = currentUser.getEmail();
//
//            Log.wtf(TAG, name + "   " +email);
//        }
////        Map<String, Object> city = new HashMap<>();
////        city.put("name", "Los Angeles");
////        city.put("state", "CA");
////        city.put("country", "USA");
////
////        db.collection("cities").document("LA")
////                .set(city)
////                .addOnSuccessListener(new OnSuccessListener<Void>() {
////                    @Override
////                    public void onSuccess(Void aVoid) {
////                        Log.d(TAG, "DocumentSnapshot successfully written!");
////                    }
////                })
////                .addOnFailureListener(new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        Log.w(TAG, "Error writing document", e);
////                    }
////                });
////

               readData();
    }

    private List<StudentsRecord> recordList = new ArrayList<>();
    private void readData() {

        StudentsRecord record;

        InputStream is = getResources().openRawResource(R.raw.cl);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;
        try {
            bufferedReader.readLine();
            while ((line= bufferedReader.readLine())!=null){

                Log.i(TAG,"Line: " + line);
                String[] tokens = line.split(",");

                int roll = Integer.parseInt(tokens[1]);

                StudentsRecord studentsRecord = new StudentsRecord();
                studentsRecord.setName(tokens[0]);
                studentsRecord.setRollno(roll);
                studentsRecord.setAttendence(tokens[3]);
                Log.wtf(TAG,tokens[1]);

                recordList.add(studentsRecord);

                Map<String, Object> data = new HashMap<>();
                data.put("name",tokens[0]);
                data.put("RollNo",roll);
                data.put("attendence",tokens[3]);
                db.collection("Years/2017-18/Months/Jan/Day/11-Jan-2018/Class/Bsc-1/Teachers/Tanvi Goyal/Subjects/C++/Ok").document(tokens[0]).set(data);

//                dbSt.orderBy("RollNo").get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (DocumentSnapshot document : task.getResult()) {
//                                        AttendanceDataClass attendanceDataClass = new AttendanceDataClass(document.getData().get("name").toString() , (long) document.getData().get("RollNo"),0  );
//                                        showdata.add(attendanceDataClass);
//                                        adapter.notifyDataSetChanged();
//                                    }
//                                } else {
//                                    Log.i(TAG, "Error getting documents: ", task.getException());
//                                }}
//                        });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

