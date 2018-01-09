package com.cbs.sscbs.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
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
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
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
        return myView;
    }

    public void showClass() {

        new MaterialDialog.Builder(getContext())
                .title("Select Class")
                .items(new String[]{"Bsc", "Bms", "Bfia"})
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        showSub();
                        return true;
                    }
                })
                .show();
    }

    public void showSub()
    {
        new MaterialDialog.Builder(getContext())
                .title("Select Class")
                .items(new String[]{"asfe", "Bms", "Bfia"})
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        return true;
                    }
                })
                .show();
    }


    }

