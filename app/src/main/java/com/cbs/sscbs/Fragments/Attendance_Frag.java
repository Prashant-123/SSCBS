package com.cbs.sscbs.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cbs.sscbs.Attendance.TeacherCourseDetails;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import am.appwise.components.ni.NoInternetDialog;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    FirebaseFirestore stu = FirebaseFirestore.getInstance();
    static String TAG = "TAG";
    NoInternetDialog noInternetDialog;
    Map<String, Object> default_map = new HashMap<>();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String name ;
//    ArrayList<String> teachersList = new ArrayList<>();
    int f = 0 ;
    CollectionReference getTeachers = FirebaseFirestore.getInstance().collection("Teacher/");

    ArrayList<String> teachersList = new ArrayList<>();
    DataClas dataClas = new DataClas();

    public Attendance_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
        RelativeLayout relativeLayout = myView.findViewById(R.id.faculty);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final LayoutInflater inflater = getLayoutInflater();

                 //----------------------------------Setting up Firebase---------------------------------

//                 getTeachers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                     @Override
//                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                         if (task.isSuccessful()) {
//                             for (QuerySnapshot snapshots : ) {
//                                 teachersList.add(documentSnapshot.getId());
//                                 Log.wtf(TAG, teachersList.toString());
//                             }
//                         } else
//                             Log.wtf(TAG, "Error getting teachers list");
//                     }
//                 });

//                 DocumentReference docRef = getTeachers.document("Vipin Rathi");
//                 docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                     @Override
//                     public void onSuccess(DocumentSnapshot documentSnapshot) {
//                         dataClas = documentSnapshot.toObject(DataClas.class);
//                         Log.i(TAG, dataClas.getName() + " ->  " + dataClas.getId());
//                     }
//                 });

                 getTeachers
                         .get()
                         .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                             @Override
                             public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                 if (task.isSuccessful()) {
                                     for (DocumentSnapshot document : task.getResult()) {
                                         Log.wtf(TAG, document.getId() + " => " + document.getData());
                                     }
                                 } else {
                                     Log.d(TAG, "Error getting documents: ", task.getException());
                                 }
                             }
                         });







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
        return myView;
    }
}