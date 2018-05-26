package com.cbs.sscbs.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cbs.sscbs.Attendance.ShowToStudents;
import com.cbs.sscbs.Attendance.StudentsDataClass;
import com.cbs.sscbs.Attendance.TeacherCourseDetails;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    static String TAG = "TAG";
    String classs, monthIntent, yearIntent;
    Button faculty, stu;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");

    ProgressBar bar;
    public static ArrayList<StudentsDataClass> allSub = new ArrayList<>();
    CollectionReference getCls = FirebaseFirestore.getInstance().collection("Attendance");
    CollectionReference getYears = FirebaseFirestore.getInstance().collection("Academic Year");
    String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
    ArrayList<String> getYearss = new ArrayList<>();
    LinearLayout stuLayout, facultyLayout;

    public Attendance_Frag() {}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
        initView(myView);
        getYears.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot document : task.getResult()) {
                        getYearss.add(document.getId());
                    }
                }
            }
        });

        faculty = myView.findViewById(R.id.faculty);
        stu = myView.findViewById(R.id.students);

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Home_frag.faculty_list.contains(user)){
                    Intent intent = new Intent(getContext(), TeacherCourseDetails.class);
                    intent.putExtra("getUser", user);
                    startActivity(intent);
                } else Toast.makeText(getContext(), "You are not authorised to proceed in this section", Toast.LENGTH_SHORT).show();
            }
        });

        stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.studentverify, null);
                final EditText roll = alertLayout.findViewById(R.id.roll);
                bar = alertLayout.findViewById(R.id.wait_bar);

                getCls.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<String> classesList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                classesList.add(document.getId());
                            }

                            String[] classes = new String[classesList.size() + 1];
                            int k = 0;
                            classes[0] = "Select Class";
                            for (int i = 1; i <= classesList.size(); i++) {
                                classes[i] = classesList.get(k);
                                k++;
                                Spinner cls = alertLayout.findViewById(R.id.student_class);

                                ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getContext(),
                                        android.R.layout.simple_spinner_item, classes);
                                classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cls.setAdapter(classAdapter);
                                cls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(final AdapterView<?> adapterView0, View view, int i, long l) {

                                        String[] yrs = new String[getYearss.size() + 1];
                                        int li = 0;
                                        yrs[0] = "Select Year";
                                        for (int p = 1; p <= getYearss.size(); p++) {
                                            yrs[p] = getYearss.get(li);
                                            li++;
                                            Spinner yr = alertLayout.findViewById(R.id.year);
                                            ArrayAdapter<String> yrAdapter = new ArrayAdapter<>(getContext(),
                                                    android.R.layout.simple_spinner_item, yrs);
                                            yrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            yr.setAdapter(yrAdapter);
                                            yr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(final AdapterView<?> adapterView1, View view, int i, long l) {
                                                    Spinner month = alertLayout.findViewById(R.id.month);

                                                    ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getContext(),
                                                            android.R.layout.simple_spinner_item, theMonth());
                                                    subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    month.setAdapter(subjectAdapter);
                                                    month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> adapterView2, View view, int i, long l) {
                                                            classs = adapterView0.getSelectedItem().toString();
                                                            monthIntent = adapterView2.getSelectedItem().toString();
                                                            yearIntent = adapterView1.getSelectedItem().toString();
                                                            if (i != 0)
                                                                showAttendance(adapterView0.getSelectedItem().toString(), roll.getText().toString(), adapterView1.getSelectedItem().toString(), adapterView2.getSelectedItem().toString());
                                                        }

                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> adapterView) {
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

                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Enter Credentials");
                alert.setView(alertLayout);

                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final Intent intent = new Intent(getContext(), ShowToStudents.class);
                        intent.putExtra("class", classs);
                        intent.putExtra("roll", roll.getText().toString());
                        intent.putExtra("month", monthIntent);
                        intent.putExtra("year", yearIntent);

                        if(monthIntent.contains("Select")||yearIntent.contains("Select")||classs.contains("Select")||roll.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Please fill all the required details correctly ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            bar.setVisibility(View.VISIBLE);

                            if (Attendance_Frag.allSub.isEmpty())
                            {
                                AlertDialog noData = new AlertDialog.Builder(alertLayout.getContext()).create();
                                noData.setTitle("No Data Found \uD83D\uDE41");
                                noData.setMessage("Please make sure that following details are correct: \n 1.Class \n 2.Roll No. \n 3.Year \n 4.Month");
                                noData.show();
                                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getView()).getWindowToken(), 0);
                                }
                            }
                            else startActivity(intent);
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        return myView;
    }

    public void showAttendance(final String clas, final String roll_no, final String year, final String month) {
        reference.child(clas).child(roll_no).child(year).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, String.valueOf(dataSnapshot.getKey()));
                Log.wtf(TAG, String.valueOf(dataSnapshot.child(month).child("attendance").getValue()));
                Log.wtf(TAG, String.valueOf(dataSnapshot.child(month).child("total").getValue()));

                String attendance = "0";

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        attendance = Objects.requireNonNull(dataSnapshot.child(month).child("attendance").getValue()).toString();
                    }
                } catch (Exception ignored){
                }

                StudentsDataClass data = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    data = new StudentsDataClass(dataSnapshot.getKey(), Integer.valueOf(attendance),
                            Integer.valueOf(Objects.requireNonNull(dataSnapshot.child(month).child("total").getValue()).toString()));
                }
                allSub.add(data);
                Log.i(TAG, allSub.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public String[] theMonth() {
        return new String[]{"Select Month", "Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }

    public void initView(View myView) {
        stuLayout = myView.findViewById(R.id.stuLayout);
        facultyLayout = myView.findViewById(R.id.facultyLayout);
        faculty = myView.findViewById(R.id.faculty);
        stu = myView.findViewById(R.id.students);
//        admin = myView.findViewById(R.id.admin);
    }
}