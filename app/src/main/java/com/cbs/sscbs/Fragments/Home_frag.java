package com.cbs.sscbs.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Home_frag extends Fragment {
    public Home_frag() {
        // Required empty public constructor
    }

    public static ArrayList<String> faculty_list = new ArrayList<>();
    public static ArrayList<String> bfia3List = new ArrayList<>();
    public static ArrayList<String> bms3List = new ArrayList<>();
    String user;
    public static ArrayList<String> classes_alloted = new ArrayList<>();
    public static ArrayList<String> myClasses = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_home_frag, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        faculty_list();                 //Get all teachers from Firebase to verify.
        faculty_subjects();             //Get subjects for Logged in User.
        Bfia_3_List();
        Class_List();
        Bms_3_List();
        return myView;
    }

    public void faculty_list(){
        CollectionReference reference = FirebaseFirestore.getInstance().collection("Teachers");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                faculty_list.clear();
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                        faculty_list.add(snapshot.getId().toString());
            }
        });
    }

    public void Bfia_3_List(){

        bfia3List.clear();

        CollectionReference reference = FirebaseFirestore.getInstance().collection("Attendance");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                        if (snapshot.getId().toString().contains("BFIA-3"))
                            bfia3List.add(snapshot.getId().toString());
            }
        });
    }
    public void Bms_3_List(){

        bms3List.clear();

        CollectionReference reference = FirebaseFirestore.getInstance().collection("Attendance");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                        if (snapshot.getId().toString().contains("BMS-3F"))
                            bms3List.add(snapshot.getId().toString());
            }
        });
    }

    public void Class_List(){

        myClasses.clear();
        CollectionReference reference = FirebaseFirestore.getInstance().collection("Attendance");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                            myClasses.add(snapshot.getId().toString());
            }
        });
    }

    public void faculty_subjects(){
        CollectionReference getSubjects = FirebaseFirestore.getInstance().collection("Teachers").document(user).collection("Classes");
        getSubjects.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                classes_alloted.clear();
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                        classes_alloted.add(snapshot.getId().toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "Faculty Subjects ERROR");
            }
        });
    }
}
