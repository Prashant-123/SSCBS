package com.cbs.sscbs.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cbs.sscbs.Attendance.AttendanceMain;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    private String name;
    private String email;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Years/2017-18/Months/Jan/Day/11-Jan-2018/Class/Bsc-1/Teachers");
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<String> classList = new ArrayList<>();
    ArrayList<String> teachersList = new ArrayList<>();

    static String TAG = "TAG";
    public Attendance_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
        RelativeLayout relativeLayout = (RelativeLayout)myView.findViewById(R.id.faculty);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Faculty");
                LayoutInflater inflater = getLayoutInflater();
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
                        if (currentUser != null) {
                            name = currentUser.getDisplayName();
                            email = currentUser.getEmail();
                            Log.wtf(TAG, name + "   " +email);
                        }
//                        Log.wtf(TAG, faculty_name.getText().toString());

                        collectionReference
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot documentSnapshot: task.getResult()) {
                                                Log.wtf(TAG, documentSnapshot.getId());
                                                teachersList.add(documentSnapshot.getId());

                                            }
                                        }
                                    }
                                });


                        for(int i = 0 ; i < teachersList.size();i++){
                            Log.wtf(TAG,teachersList.get(i)+ "hfhjh");
                            Log.wtf(TAG,faculty_name.getText().toString()+ "vddf");
                            if(teachersList.get(i).equals(faculty_name.getText().toString())){
                                Log.wtf(TAG,"Done");
                            }
                            else{
                                Log.wtf(TAG,"REtry");
                            }
                        }
//                            final ArrayList<String> subList = new ArrayList<>();
//                            db.collection("Teachers").document("KR").collection("Class").document(classList.get(which)).collection("Subjects")
//                                    .get()
//                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            if (task.isSuccessful()) {
//                                                for (DocumentSnapshot document : task.getResult()) {
//                                                    //Log.d(TAG, document.getId() + " => " + document.getData());
//                                                    subList.add(document.getId());
//                                                }
//
//                                            } else {
//                                                Log.d(TAG, "Error getting documents: ", task.getException());
//                                            }
//                                        }
//                                    });
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        db.collection("TeacherTest/Kavita Rastogi/Class")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                classList.add(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return myView;
    }

    public void showClass() {

        new MaterialDialog.Builder(getContext())
                .title("Select Your Class")
                .items(classList)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Log.i(TAG, String.valueOf(which));
                        showSub(which);
                        return true;
                    }
                })
                .show();
    }

    public void showSub(final int which)
    {
        Log.i(TAG, String.valueOf(which)+ "inside");
       final ArrayList<String> subList = new ArrayList<>();
        db.collection("TeacherTest/Kavita Rastogi/Class/" + (classList.get(which)) + "/Subjects")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.i(TAG,classList.get(which));
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                subList.add(document.getId());
                                Log.wtf(TAG, subList.toString());
                            }
                            new MaterialDialog.Builder(getContext())
                                    .title("Select Subject")
                                    .items(subList)
                                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                        @Override
                                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                            Intent intent = new Intent(getContext(), AttendanceMain.class);
                                            startActivity(intent);
                                            return true;
                                        }
                                    })
                                    .show();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    }
