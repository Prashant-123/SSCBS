package com.cbs.sscbs.Attendance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;

public class AttendanceMain extends AppCompatActivity {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    FloatingActionButton button1 ;
    FirebaseFirestore db1 = FirebaseFirestore.getInstance();
    CollectionReference db = FirebaseFirestore.getInstance().collection("2018-19/Month/Course/Day/Subject/Student/Student Details");

    public ArrayList<AttendanceDataClass> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1 = (FloatingActionButton) findViewById(R.id.save_at);
        setContentView(R.layout.common_rv);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final AttendanceAdapter adapter = new AttendanceAdapter(this, data);
        recyclerView.setAdapter(adapter);
//
//        AttendanceDataClass newData1 = new AttendanceDataClass("name-1", 16501, 0);
//        data.add(newData1);
//        data.add(newData1);
//        data.add(newData1);
//        data.add(newData1);
//        data.add(newData1);
        adapter.notifyDataSetChanged();

            db.orderBy("roll").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            AttendanceDataClass attendanceDataClass = new AttendanceDataClass(document.getData().get("name").toString() , (long) document.getData().get("roll"),0  );
                            data.add(attendanceDataClass);
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.i(TAG, "Error getting documents: ", task.getException());
                    }}
                });
    }

    public void Save(View v){

        int i= 0 ;
        while(i< AttendanceAdapter.saveRoll.size()) {
        final long roll = AttendanceAdapter.saveRoll.get(i);
            db.whereEqualTo("roll", roll).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (final DocumentSnapshot document : task.getResult()) {
                        db1.runTransaction(new Transaction.Function<Void>() {
                            @Override
                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                final DocumentReference sfDocRef = db1.collection("2018-19/Month/Course/Day/Subject/Student/Student Details").document(document.getId());
                                DocumentSnapshot snapshot = transaction.get(sfDocRef);
                                double newAttendence = (snapshot.getDouble("attendence")) + 1;
                                transaction.update(sfDocRef, "attendence", newAttendence);
                                Log.i(TAG, "Attendence updated");
                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Transaction success!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Transaction failure.", e);
                            }
                        });
                    }
                } else {
                    Log.i(TAG, "Error getting documents: ", task.getException());
                }}
                            });
                    i++;
                }

    }
}