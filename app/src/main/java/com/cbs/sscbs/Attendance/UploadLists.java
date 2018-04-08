package com.cbs.sscbs.Attendance;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cbs.sscbs.Fragments.Home_frag;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UploadLists extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_lists);
        mRecyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
//
//            CollectionReference reference = FirebaseFirestore.getInstance().collection("Attendance");
//            reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful())
//                        for (DocumentSnapshot snapshot : task.getResult())
//                                myClasses.add(snapshot.getId());
//                }
//            });

            Log.wtf("TAG" , Home_frag.myClasses.toString());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mAdapter = new ClassListsAdapter(Home_frag.myClasses);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
