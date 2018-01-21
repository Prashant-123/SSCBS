package com.cbs.sscbs.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanya on 1/20/2018.
 */

public class Attendence_Fragment extends Fragment{
    static String TAG = "TAG";
    Map<String, Object> default_map = new HashMap<>();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String name ;
    ArrayList<String> teachersList = new ArrayList<>();
    CollectionReference teachers_list = FirebaseFirestore.getInstance().collection("Teachers");

    public Attendence_Fragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) myView.findViewById(R.id.faculty);
        default_map.put("default", "");

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Faculty");
                LayoutInflater inflater = getLayoutInflater();

                teachers_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                teachersList.add(documentSnapshot.getId());
                            }
                        } else
                            Log.wtf(TAG, "Error getting teachers list");
                    }
                });

                for(int i = 0 ; i < teachersList.size();i++)
                    Log.wtf(TAG,teachersList.get(i));

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
                        if (currentUser != null)
                            name = currentUser.getDisplayName();

                        for (int i = 0; i < teachersList.size(); i++) {
                            if (teachersList.get(i).equals(faculty_name.getText().toString())) {
                                Log.wtf(TAG, "Done");
                                //showClass();
                            } else {
                                Log.wtf(TAG, "Retry");
                            }
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });


        return myView;
    }
}
