package com.cbs.sscbs.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cbs.sscbs.Attendance.AdminActivity;
import com.cbs.sscbs.Attendance.AttendanceDataClass;
import com.cbs.sscbs.Attendance.AttendanceMain;
import com.cbs.sscbs.Attendance.StudentsDataClass;
import com.cbs.sscbs.Attendance.TeacherCourseDetails;
import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.R;
import com.cbs.sscbs.ShowToStudents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Timer;
import java.util.TimerTask;

import am.appwise.components.ni.NoInternetDialog;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    static String TAG = "TAG";
    String classs, monthIntent;
    ProgressBar bar;
    public static ArrayList<StudentsDataClass> allSub = new ArrayList<>();
    CollectionReference getCls = FirebaseFirestore.getInstance().collection("Attendance");
    String user = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
    //String user = "Sonika Thakral";
    LinearLayout stuLayout, facultyLayout;
    String adminId = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

    public Attendance_Frag() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
        stuLayout = myView.findViewById(R.id.stuLayout);
        facultyLayout = myView.findViewById(R.id.facultyLayout);
        Button faculty = myView.findViewById(R.id.faculty);
        Button stu = myView.findViewById(R.id.students);
        Button admin = myView.findViewById(R.id.admin);

        CollectionReference subType = FirebaseFirestore.getInstance().collection("Teachers");

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adminId.compareTo("pk021998@gmail.com")==0){
                    Intent intent = new Intent(getContext(),AdminActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Sorry! You do not have the admin access ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int[] flag = {0};
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Verify Credentials");
                alert.setView(null);
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
                        CollectionReference subType = FirebaseFirestore.getInstance().collection("Teachers");
                        subType
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot document : task.getResult()) {
                                                if (document.getId().compareToIgnoreCase(user) == 0) {
                                                     flag[0] =1;
                                                    Intent intent = new Intent(getContext(), TeacherCourseDetails.class);
                                                    intent.putExtra("getUser", user);
                                                    startActivity(intent);
                                                    break;
                                                }
                                            }
                                        }
                                        if(flag[0] ==0){
                                            Toast.makeText(getContext(), "Please make sure you are registered as a faculty of SSCBS", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
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

                                ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.simple_spinner_item, classes);
                                classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cls.setAdapter(classAdapter);
                                cls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {

                                        Spinner month = alertLayout.findViewById(R.id.month);

                                        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(getContext(),
                                                android.R.layout.simple_spinner_item, theMonth());
                                        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        month.setAdapter(subjectAdapter);
                                        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView1, View view, int i, long l) {
                                                classs = adapterView.getSelectedItem().toString();
                                                monthIntent = adapterView1.getSelectedItem().toString();
                                                if (i!=0)
                                                showAttendance(adapterView.getSelectedItem().toString(), roll.getText().toString(), "2018", adapterView1.getSelectedItem().toString());
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {                                            }
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

                        if(classs.isEmpty()||roll.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Please fill all the required details correctly ! ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            bar.setVisibility(View.VISIBLE);
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    startActivity(intent);
                                }
                            }, 2000);
                            bar.setVisibility(View.INVISIBLE);
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
        getCls.document(clas).collection("Students").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final DocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.getId().compareToIgnoreCase(roll_no) == 0) {

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
                                                                        StudentsDataClass dataClass = new StudentsDataClass(ds.getId(), documentSnapshot.getDouble("attendance" ), documentSnapshot.getDouble("total"));
                                                                        allSub.add(dataClass);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            });
                                    break;
                                }
                            }
                        }
                    }

                });
    }

    public String[] theMonth() {
        return new String[]{"Select Month", "Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }

}