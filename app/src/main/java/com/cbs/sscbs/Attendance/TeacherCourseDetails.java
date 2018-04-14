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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.cbs.sscbs.Fragments.Home_frag.classes_alloted;

public class TeacherCourseDetails extends AppCompatActivity {

    String getSub, getClass;
//    String[] type;
//    ArrayList listTypes;
    Spinner classSpinner, subSpinner,typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course_details);
         classSpinner = (Spinner) findViewById(R.id.getClassSpinner);
         subSpinner = (Spinner) findViewById(R.id.getSubSpinner);
         typeSpinner = (Spinner) findViewById(R.id.getTypeSpinner);
         Toolbar toolbar = findViewById(R.id.toolbar_course_details);
         toolbar.setTitle("Attendance");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final String getName = getIntent().getStringExtra("getUser");
        String[] classes = new String[classes_alloted.size() + 1];
        int k = 0;
        classes[0] = "Select Class";
        for (int i = 1; i <= classes_alloted.size(); i++) {
            classes[i] = classes_alloted.get(k);
            k++;
        }

        ArrayAdapter<String> areasAdapter = new ArrayAdapter<>(TeacherCourseDetails.this,
                android.R.layout.simple_spinner_item, classes);
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(areasAdapter);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    Toast.makeText(TeacherCourseDetails.this, "Select your class", Toast.LENGTH_SHORT).show();
                else {
                    getClass = adapterView.getItemAtPosition(i).toString();
                    showSub(getName, subSpinner, typeSpinner);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void showSub(final String getName, final Spinner subSpinner, final Spinner typeSpinner) {

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
                        final ArrayAdapter<String> areasAdapter = new ArrayAdapter<>(TeacherCourseDetails.this,
                                android.R.layout.simple_spinner_item, subjects);
                        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subSpinner.setAdapter(areasAdapter);
                        subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {

                                if (i == 0)
                                    Toast.makeText(TeacherCourseDetails.this, "Select Subject",
                                            Toast.LENGTH_SHORT).show();
                                else {
                                    getSub = adapterView.getItemAtPosition(i).toString();

                                    CollectionReference subType = FirebaseFirestore.getInstance().collection("AllSubjects");
                                    subType.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful())
                                                for (DocumentSnapshot snapshot : task.getResult()) {
                                                    if (snapshot.getId().equals(getSub)) {

                                                        String splitStr[] = snapshot.get("type").toString().substring(1, snapshot.get("type").toString().length()-1).split(",");
                                                        ArrayList listTypes = new ArrayList(Arrays.asList(splitStr));

                                                    String[] typ = new String[listTypes.size() + 1];
                                                    typ[0] = "Select Type";
                                                    int k = 0;
                                                    for (int z = 1; z <= listTypes.size() ; z++) {
                                                        typ[z] = (String) listTypes.get(k);
                                                        k++;
                                                    }

                                                    ArrayAdapter<String> areasAdapter = new ArrayAdapter<>(TeacherCourseDetails.this,
                                                            android.R.layout.simple_spinner_item, typ);
                                                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    typeSpinner.setAdapter(areasAdapter);
                                                    typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                                            if (i == 0)
                                                                Toast.makeText(TeacherCourseDetails.this, "Select type", Toast.LENGTH_SHORT).show();

                                                            else {

                                                                    Intent intent = new Intent(getApplicationContext(), AttendanceMain.class);
//                                                                    intent.putExtra("teacherName", "/ClassList/" + getClass + "/Type/" + adapterView.getItemAtPosition(i).toString() + "/StudentList");
                                                                    intent.putExtra("type", String.valueOf(adapterView.getSelectedItem()));
                                                                    intent.putExtra("class", getClass);
                                                                    intent.putExtra("subject", getSub);



//                                                                    Log.wtf(TAG, ("->" + adapterView.getSelectedItem()) + "->" );

                                                                    startActivity(intent);
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