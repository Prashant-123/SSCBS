package com.cbs.sscbs.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.regex.Pattern;

import am.appwise.components.ni.NoInternetDialog;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    static String TAG = "TAG";
    NoInternetDialog noInternetDialog;
    Map<String, Object> default_map = new HashMap<>();

    CollectionReference getCls = FirebaseFirestore.getInstance().collection("Attendance");

    public Attendance_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
        RelativeLayout faculty = myView.findViewById(R.id.faculty);
        RelativeLayout stu = myView.findViewById(R.id.students);

        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();

//                 getTeachers
//                         .get()
//                         .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                             @Override
//                             public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                 if (task.isSuccessful()) {
//                                     for (DocumentSnapshot document : task.getResult()) {
//                                         Log.wtf(TAG, document.getId() + " => " + document.getData());
//                                     }
//                                 } else {
//                                     Log.d(TAG, "Error getting documents: ", task.getException());
//                                 }
//                             }
//                         });
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
                View alertLayout = inflater.inflate(R.layout.studentverify, null);
                final EditText cls = alertLayout.findViewById(R.id.student_class);
                final EditText roll = alertLayout.findViewById(R.id.roll);
                final EditText month = alertLayout.findViewById(R.id.month);

//                cls.setOnEditorActionListener(
//                        new EditText.OnEditorActionListener() {
//                            @Override
//                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//                                        actionId == EditorInfo.IME_ACTION_DONE ||
//                                        event != null &&
//                                                event.getAction() == KeyEvent.ACTION_DOWN &&
//                                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                                    if (event == null || !event.isShiftPressed()) {
//                                        // the user is done typing.
//                                        Log.wtf(TAG , "ok");
//
//                                        return true; // consume.
//                                    }
//                                }
//                                return false; // pass on to other listeners.
//                            }
//                        });

//
////                cls.addTextChangedListener(filterTextWatcher);
//                roll.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                cls.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//                    public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
//                        Log.wtf(TAG , "ok");
//                        return false;
//                    }
//                });
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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

                        Log.wtf(TAG, cls.getText().toString());
                        Log.wtf(TAG, roll.getText().toString());
                        Log.wtf(TAG, month.getText().toString());

                        getCls.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot document : task.getResult()) {
//                                                Log.wtf(TAG, document.getId() + " => " + document.getData());
                                                if(document.getId().compareToIgnoreCase(cls.getText().toString()) ==0 ){
                                                    Log.i(TAG ,document.getId());
                                                    break;
                                                }
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        return myView;
    }


//    private TextWatcher filterTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
////            Log.i(TAG,s.toString());
//        }
//    };
}