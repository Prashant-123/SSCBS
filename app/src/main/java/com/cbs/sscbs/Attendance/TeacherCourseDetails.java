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

import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TeacherCourseDetails extends AppCompatActivity {

    private static final String TAG = "TAG";
String Classes = "Classes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course_details);


        final String getName ;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                getName= null;
            } else {
                getName= extras.getString("teacherName");
            }
        } else {
            getName= (String) savedInstanceState.getSerializable("teacherName");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_course_details);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.wtf(TAG,getName);

        CollectionReference class_list = FirebaseFirestore.getInstance().collection("Teachers").document(getName).collection(Classes);

        class_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                ArrayList<String> classesList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Log.wtf(TAG,documentSnapshot.getId());
                        classesList.add(documentSnapshot.getId());

                        Spinner classSpinner = (Spinner) findViewById(R.id.getClassSpinner);
                        String[] classes = new String[classesList.size()+1];
                        int k = 0 ;
                        classes[0] = "Select Class";
                        for(int  i = 1 ; i <= classesList.size();i++){
                            classes[i] = classesList.get(k);
                            k++;
                        }
                        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
                                android.R.layout.simple_spinner_item, classes);
                        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        classSpinner.setAdapter(areasAdapter);
                        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(final AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                Log.wtf(TAG,"Selected");
                                if(position==0)
                                    Toast.makeText(TeacherCourseDetails.this, "Please select your class", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(TeacherCourseDetails.this,
                                            "Selected " + parentView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                                CollectionReference sub_list = FirebaseFirestore.getInstance().
                                        collection("Teachers").document(getName).collection(Classes).
                                        document(parentView.getItemAtPosition(position).toString()).collection("Subjects");

                                sub_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        ArrayList<String> subList = new ArrayList<>();
                                        if(task.isSuccessful()){
                                            for(DocumentSnapshot documentSnapshot1 : task.getResult()){
                                                Log.wtf(TAG,documentSnapshot1.getId());
                                                subList.add(documentSnapshot1.getId());

                                                Spinner subSpinner = (Spinner) findViewById(R.id.getSubSpinner);
                                                String[] sub = new String[subList.size()+1];
                                                int k = 0 ;
                                                sub[0] = "Select Subject";
                                                for(int  i = 1 ; i <= subList.size();i++){
                                                    sub[i] = subList.get(k);
                                                    k++;
                                                }
                                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
                                                        android.R.layout.simple_spinner_item, sub);
                                                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                subSpinner.setAdapter(areasAdapter);
                                                subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        CollectionReference type_list = FirebaseFirestore.getInstance().
                                                                collection("Teachers").document(getName).collection(Classes).
                                                                document(parentView.getItemAtPosition(position).toString()).
                                                                collection("Subjects").document(parent.getItemAtPosition(position).toString())
                                                                .collection("ClassType");

                                                        type_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                ArrayList<String> typeList = new ArrayList<>();
                                                                if(task.isSuccessful()){
                                                                    for(DocumentSnapshot documentSnapshot2 :task.getResult()){
                                                                        Log.wtf(TAG,documentSnapshot2.getId());
                                                                        typeList.add(documentSnapshot2.getId());

                                                                        Spinner typeSpinner = (Spinner) findViewById(R.id.getTypeSpinner);
                                                                        if(typeList.size()!=0) {
                                                                            typeSpinner.setVisibility(View.VISIBLE);
                                                                        }
                                                                        String[] type = new String[typeList.size()+1];
                                                                        int k = 0 ;
                                                                        type[0] = "Select Type ";
                                                                        for(int  i = 1 ; i <= typeList.size();i++){
                                                                            type[i] = typeList.get(k);
                                                                            k++;
                                                                        }
                                                                        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
                                                                                android.R.layout.simple_spinner_item, type);
                                                                        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                        typeSpinner.setAdapter(areasAdapter);
                                                                        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                            @Override
                                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                                Intent intent = new Intent(getApplicationContext(),AttendanceMain.class);
                                                                                startActivity(intent);
                                                                            }

                                                                            @Override
                                                                            public void onNothingSelected(AdapterView<?> parent) {

                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });
                                            }
                                        }
                                    }
                                });

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }
                        });
                    }
                } else {
                    Log.wtf(TAG, "Error getting teachers list");
                }
            }
        });

    }


}
