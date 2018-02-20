package com.cbs.sscbs.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cbs.sscbs.Attendance.AttendanceDataClass;
import com.cbs.sscbs.Attendance.StudentsDataClass;
import com.cbs.sscbs.Attendance.TeacherCourseDetails;
import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.R;
import com.cbs.sscbs.ShowToStudents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import am.appwise.components.ni.NoInternetDialog;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    static String TAG = "TAG";
    String classs;
    private static final String URL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    public static ArrayList<StudentsDataClass> allSub = new ArrayList<>();
    CollectionReference getCls = FirebaseFirestore.getInstance().collection("Attendance");

    public Attendance_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
        RelativeLayout faculty = myView.findViewById(R.id.faculty);
        RelativeLayout stu = myView.findViewById(R.id.students);
        final Spinner month = myView.findViewById(R.id.month);

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();

                View alertLayout = inflater.inflate(R.layout.verify, null);
                final EditText faculty_name = alertLayout.findViewById(R.id.faculty_verify);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Verify Credentials");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Verification Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), TeacherCourseDetails.class);
                        intent.putExtra("teacherName", faculty_name.getText().toString());
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                final View alertLayout = inflater.inflate(R.layout.studentverify, null);
                final EditText roll = alertLayout.findViewById(R.id.roll);

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

                                ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.simple_spinner_item, classes);
                                classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cls.setAdapter(classAdapter);
                                cls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {
//                                        showAttendance(adapterView.getSelectedItem().toString(), "16527", "2018", "Feb");
//                                        Intent intent = new Intent(getContext(), ShowToStudents.class);
//                                        startActivity(intent);

                                        Spinner month = alertLayout.findViewById(R.id.month);

                                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(getContext(),
                                                android.R.layout.simple_spinner_item, theMonth());
                                        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        month.setAdapter(subjectAdapter);
                                        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView1, View view, int i, long l) {
                                                classs = adapterView.getSelectedItem().toString();
                                                showAttendance(adapterView.getSelectedItem().toString(), "16527", "2018", adapterView1.getSelectedItem().toString());
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
                    }
                });

                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Enter Credentials");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Verification Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), ShowToStudents.class);
                        intent.putExtra("class", classs);
                        intent.putExtra("roll", roll.getText().toString());
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        return myView;
    }

    public void showAttendance(final String clas, final String roll_no, final String year, final String month) {
        getCls.document(clas).collection("Students").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final DocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.getId().compareToIgnoreCase(roll_no) == 0) {
//                                                        Log.wtf(TAG , documentSnapshot.getId());

                                    getCls.document(clas).collection("Students")
                                            .document(documentSnapshot.getId()).collection("Subjects")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (final DocumentSnapshot ds : task.getResult()) {

                                                            DocumentReference reference = getCls
                                                                    .document(clas).collection("Students")
                                                                    .document(documentSnapshot.getId()).collection("Subjects")
                                                                    .document(ds.getId()).collection("Year").document(year)
                                                                    .collection("Months").document(month);
                                                            reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    if (documentSnapshot.exists()) {
                                                                        StudentsDataClass dataClass = new StudentsDataClass(ds.getId(), documentSnapshot.getDouble("attendance"));
                                                                        allSub.add(dataClass);

                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
//                                                    Intent intent = new Intent(getContext(), ShowToStudents.class);
//                                                    startActivity(intent);
                                                }
                                            });
                                    break;
                                }
                            }
                        }

                    }

                });
        Log.wtf(TAG, allSub.toString()+"jkghff");

    }

    public String[] theMonth() {
        return new String[]{"Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }

}