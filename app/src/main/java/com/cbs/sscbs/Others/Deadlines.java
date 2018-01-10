package com.cbs.sscbs.Others;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

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
    Button button ;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StudentsRecord studentsRecord = new StudentsRecord() ;
//    DocumentSnapshot document =
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadlines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_deadlines);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());


        db.collection("Teachers/KR/Class/Bsc-1/Subjects/C++/Day").document(formattedDate);

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

                Log.i(TAG,line);
                String[] tokens = line.split(",");

                StudentsRecord studentsRecord = new StudentsRecord();
                studentsRecord.setName(tokens[0]);
                studentsRecord.setRollno(tokens[1]);
                studentsRecord.setAttendence(tokens[3]);
                recordList.add(studentsRecord);

                Log.i(TAG , "Creatd " + studentsRecord);

                Map<String, Object> data = new HashMap<>();
                data.put("name",tokens[0]);
                data.put("RollNo",tokens[1]);
                data.put("attendence",tokens[3]);
                db.collection("Teachers/KR/Class/Bsc-1/Subjects/C++/Day/10-Jan-2018/Students").document(tokens[0]).set(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

