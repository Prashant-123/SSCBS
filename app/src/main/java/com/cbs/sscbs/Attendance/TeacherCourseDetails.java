package com.cbs.sscbs.Attendance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cbs.sscbs.Fragments.*;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TeacherCourseDetails extends AppCompatActivity {

    private static final String TAG = "TAG";
    String getClass, getSub;
    public int isLab;

    String[] types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course_details);
        final Spinner classSpinner = (Spinner) findViewById(R.id.getClassSpinner);
        final Spinner subSpinner = (Spinner) findViewById(R.id.getSubSpinner);
        final Spinner typeSpinner = (Spinner) findViewById(R.id.getTypeSpinner);


        final String getName;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                getName = null;
            } else {
                // getName= extras.getString("teacherName");
                getName = "Sonika Thakral";
            }
        } else {
            //getName= (String) savedInstanceState.getSerializable("teacherName");
            getName = "Sonika Thakral";
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_course_details);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CollectionReference getSubjects = FirebaseFirestore.getInstance().collection("Teachers/" + getName + "/Classes");

        getSubjects
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<String> classesList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                classesList.add(document.getId());
                                String[] classes = new String[classesList.size() + 1];
                                int k = 0;
                                classes[0] = "Select Class";
                                for (int i = 1; i <= classesList.size(); i++) {
                                    classes[i] = classesList.get(k);
                                    k++;
                                }
                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
                                        android.R.layout.simple_spinner_item, classes);
                                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                classSpinner.setAdapter(areasAdapter);
                                classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        if (i == 0)
                                            Toast.makeText(TeacherCourseDetails.this, "Please select your class", Toast.LENGTH_SHORT).show();
                                        else {
                                            Toast.makeText(TeacherCourseDetails.this,
                                                    "Selected " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                                            getClass = adapterView.getItemAtPosition(i).toString();
                                            showSub(getName, subSpinner, typeSpinner);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private void showSub(String getName, final Spinner subSpinner, final Spinner typeSpinner) {
        Log.wtf(TAG, getClass);

        final CollectionReference getSubjects = FirebaseFirestore.getInstance().collection("Teachers").document(getName)
                .collection("Classes").document(getClass).collection("Subjects");

        getSubjects.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<String> subjectList = new ArrayList<>();
                for (DocumentSnapshot documentSnapshot : task.getResult()) {

                    subjectList.add(documentSnapshot.getId());
                    String[] subjects = new String[subjectList.size() + 1];
                    int k = 0;
                    subjects[0] = "Select Subject";
                    for (int i = 1; i <= subjectList.size(); i++) {
                        subjects[i] = subjectList.get(k);
                        k++;
//                        if (task.isSuccessful()) {
//                        }
                        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
                                android.R.layout.simple_spinner_item, subjects);
                        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subSpinner.setAdapter(areasAdapter);
                        subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                if (i == 0)
                                    Toast.makeText(TeacherCourseDetails.this, "Please select your Subject",
                                            Toast.LENGTH_SHORT).show();
                                else {
                                    Toast.makeText(TeacherCourseDetails.this,
                                            "Selected " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                                    getSub = adapterView.getItemAtPosition(i).toString();

                                    CollectionReference subType = FirebaseFirestore.getInstance().collection("AllSubjects");
                                    subType.whereEqualTo("type", true)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful())
                                                        for (DocumentSnapshot document : task.getResult()) {
                                                            if (getSub.equals(document.getId())) {
                                                                types = new String[]{"Lab-G1", "Lab-G2", "Theory"};
                                                            }
                                                            else types = new String[]{"Theory"};
                                                        }

                                                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
                                                            android.R.layout.simple_spinner_item, types);
                                                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    typeSpinner.setAdapter(areasAdapter);
                                                    typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                            if (i == 0)
                                                                Toast.makeText(TeacherCourseDetails.this, "Please select your Type",
                                                                        Toast.LENGTH_SHORT).show();
                                                            else {
                                                                Toast.makeText(TeacherCourseDetails.this,
                                                                        "Selected " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();

                                                                //-----------INTENT-----------------

                                                                Intent intent = new Intent(getApplicationContext(), AttendanceMain.class);
                                                                startActivity(intent);

                                                                //----------------------------------

                                                            }
                                                        }

                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                                        }
                                                    });
                                                }
                                            });
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                }
            }
        });
    }


}
