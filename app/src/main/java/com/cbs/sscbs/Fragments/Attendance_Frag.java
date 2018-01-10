package com.cbs.sscbs.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cbs.sscbs.Attendance.AttendanceMain;
import com.cbs.sscbs.Others.About_Activity;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> classList = new ArrayList<>();

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
                View alertLayout = inflater.inflate(R.layout.fragment_login, null);
                final EditText username = alertLayout.findViewById(R.id.User);
                final EditText password = alertLayout.findViewById(R.id.Pass);
                final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);

                cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked)
                            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        else
                            password.setInputType(129);
                    }
                });

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Sign-In");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getContext(), "Log-In Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Log In", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DocumentReference mDocRef = FirebaseFirestore.getInstance().document("username/society");
                        Log.i("tag", "Log-1");
                        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                Log.i("tag", "Log-2");
                                if (documentSnapshot.exists()) {

                                    Log.i("tag", "Log-3");
                                    String u = documentSnapshot.getString(USERNAME);
                                    String p = documentSnapshot.getString(PASSWORD);
                                    if (username.getText().toString().equals(u) && password.getText().toString().equals(p)) {
                                        Toast.makeText(getContext(), "Authentication Successfull", Toast.LENGTH_SHORT).show();
                                        //createEvent();

                                        showClass();

                                    } else
                                        Toast.makeText(getContext(), "You Lost it :)", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });
        
        db.collection("Teachers").document("KR").collection("Class")
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
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
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
        db.collection("Teachers").document("KR").collection("Class").document(classList.get(which)).collection("Subjects")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.i(TAG,classList.get(which));
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                subList.add(document.getId());
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

