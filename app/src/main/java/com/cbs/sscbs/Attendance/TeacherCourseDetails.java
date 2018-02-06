package com.cbs.sscbs.Attendance;

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
    private static final String Classes = "Classes";
    String getClass;
    String cls;
    DataClas dataClas = new DataClas();
    String getSub;

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

//        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {if (task.isSuccessful()) {
//            for (DocumentSnapshot document : task.getResult()) {
//                Log.wtf(TAG, document.getId() + " -> " + document.getData());
//                            }
//                        }
//                    }
//                });

//        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
//                                android.R.layout.simple_spinner_item, );
//                        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        classSpinner.setAdapter(areasAdapter);
//
//        class_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                ArrayList<String> classesList = new ArrayList<>();
//                if (task.isSuccessful()) {
//                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                        Log.wtf(TAG,documentSnapshot.getId());
//                        classesList.add(documentSnapshot.getId());
//                        String[] classes = new String[classesList.size()+1];
//                        int k = 0 ;
//                        classes[0] = "Select Class";
//                        for(int  i = 1 ; i <= classesList.size();i++){
//                            classes[i] = classesList.get(k);
//                            k++;
//                        }
//                        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
//                                android.R.layout.simple_spinner_item, classes);
//                        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        classSpinner.setAdapter(areasAdapter);
//                        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(final AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                                Log.wtf(TAG,"Selected");
//                                if(position==0)
//                                    Toast.makeText(TeacherCourseDetails.this, "Please select your class", Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(TeacherCourseDetails.this,
//                                            "Selected " + parentView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
//
//                                CollectionReference sub_list = FirebaseFirestore.getInstance().
//                                        collection("Teachers").document(getName).collection(Classes).
//                                        document(parentView.getItemAtPosition(position).toString()).collection("Subjects");
//
//                                sub_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                        ArrayList<String> subList = new ArrayList<>();
//                                        if(task.isSuccessful()){
//                                            for(DocumentSnapshot documentSnapshot1 : task.getResult()){
//                                                Log.wtf(TAG,documentSnapshot1.getId());
//                                                subList.add(documentSnapshot1.getId());
//
//                                                Spinner subSpinner = (Spinner) findViewById(R.id.getSubSpinner);
//                                                String[] sub = new String[subList.size()+1];
//                                                int k = 0 ;
//                                                sub[0] = "Select Subject";
//                                                for(int  i = 1 ; i <= subList.size();i++){
//                                                    sub[i] = subList.get(k);
//                                                    k++;
//                                                }
//                                                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
//                                                        android.R.layout.simple_spinner_item, sub);
//                                                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                                subSpinner.setAdapter(areasAdapter);
//                                                subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                                    @Override
//                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                                        CollectionReference type_list = FirebaseFirestore.getInstance().
//                                                                collection("Teachers").document(getName).collection(Classes).
//                                                                document(parentView.getItemAtPosition(position).toString()).
//                                                                collection("Subjects").document(parent.getItemAtPosition(position).toString())
//                                                                .collection("ClassType");
//
//                                                        type_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                                ArrayList<String> typeList = new ArrayList<>();
//                                                                if(task.isSuccessful()){
//                                                                    for(DocumentSnapshot documentSnapshot2 :task.getResult()){
//                                                                        Log.wtf(TAG,documentSnapshot2.getId());
//                                                                        typeList.add(documentSnapshot2.getId());
//
//                                                                        Spinner typeSpinner = (Spinner) findViewById(R.id.getTypeSpinner);
//                                                                        if(typeList.size()!=0) {
//                                                                            typeSpinner.setVisibility(View.VISIBLE);
//                                                                        }
//                                                                        String[] type = new String[typeList.size()+1];
//                                                                        int k = 0 ;
//                                                                        type[0] = "Select Type ";
//                                                                        for(int  i = 1 ; i <= typeList.size();i++){
//                                                                            type[i] = typeList.get(k);
//                                                                            k++;
//                                                                        }
//                                                                        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(TeacherCourseDetails.this,
//                                                                                android.R.layout.simple_spinner_item, type);
//                                                                        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                                                        typeSpinner.setAdapter(areasAdapter);
//                                                                        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                                                            @Override
//                                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                                                                Intent intent = new Intent(getApplicationContext(),AttendanceMain.class);
//                                                                                startActivity(intent);
//                                                                            }
//
//                                                                            @Override
//                                                                            public void onNothingSelected(AdapterView<?> parent) {
//
//                                                                            }
//                                                                        });
//                                                                    }
//                                                                }
//                                                            }
//                                                        });
//                                                    }
//
//                                                    @Override
//                                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                                    }
//                                                });
//                                            }
//                                        }
//                                    }
//                                });
//
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parentView) {
//                                // your code here
//                            }
//                        });
//                    }
//                } else {
//                    Log.wtf(TAG, "Error getting teachers list");
//                }
//            }
//        });

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
                        if (task.isSuccessful()) {
                        }
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

                                    DocumentReference documentReference = subType.document(getSub);
                                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document != null) {
                                                    Log.d(TAG, "DocumentSnapshot data: " +
                                                            task.getResult().getData().toString().substring(7, 29));

                                                    String[] types = task.getResult().getData().toString().substring(7, 29).split(",");

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
                                                            }
                                                        }

                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                                        }
                                                    });
//

                                                } else {
                                                    Log.d(TAG, "No such document");
                                                }
                                            } else {
                                                Log.d(TAG, "get failed with ", task.getException());
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
