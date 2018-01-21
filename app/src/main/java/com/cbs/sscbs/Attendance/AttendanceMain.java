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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceMain extends AppCompatActivity {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    FloatingActionButton button1 ;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    Calendar c = Calendar.getInstance();
//    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//    String formattedDate = df.format(c.getTime());
//    String getMonth = formattedDate.substring(3, 6);

   // CollectionReference dbSt = FirebaseFirestore.getInstance().collection("Years/2017-18/Months/Jan/Day/11-Jan-2018/Class/Bsc-1/Teachers/Tanvi Goyal/Subjects/C++/Ok");


//    FirebaseFirestore db1 = FirebaseFirestore.getInstance();
//    CollectionReference db32 = FirebaseFirestore.getInstance().collection("TeacherTest/Kavita Rastogi/Class/Bsc-1/Subjects/C++/Day/10-Jan-2018/Students");

    public ArrayList<AttendanceDataClass> showdata = new ArrayList<>();

     AttendanceAdapter adapter= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1 = (FloatingActionButton) findViewById(R.id.save_at);
        setContentView(R.layout.common_rv);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AttendanceAdapter(this, showdata);
        recyclerView.setAdapter(adapter);

        db.collection("Students")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                AttendanceDataClass attendanceDataClass =
//                                    new AttendanceDataClass(document.getData().get("name").toString() ,
//                                            document.getData().get("roll").toString(),"ok"  );
                                        adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        adapter.notifyDataSetChanged();

//            dbSt.orderBy("roll").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        for (DocumentSnapshot document : task.getResult()) {
//                            AttendanceDataClass attendanceDataClass =
//                                    new AttendanceDataClass(document.getData().get("name").toString() , document.getData().get("RollNo").toString(),"ok"  );
//                            showdata.add(attendanceDataClass);
//                            adapter.notifyDataSetChanged();
//                        }
//                    } else {
//                        Log.i(TAG, "Error getting documents: ", task.getException());
//                    }}
//                });

    }

//    public void Save(View v){
//        int i= 0 ;
//        while(i< AttendanceAdapter.saveRoll.size()) {
//        final String roll = AttendanceAdapter.saveRoll.get(i).toString();
//            dbSt.whereEqualTo("RollNo", roll).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (final DocumentSnapshot document : task.getResult()) {
//                        db.runTransaction(new Transaction.Function<Void>() {
//                            @Override
//                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
//                                final DocumentReference sfDocRef = db
//                                        .collection("Years/2017-18/Months/Jan/Day/11-Jan-2018/Class/Bsc-1/Teachers/Tanvi Goyal/Subjects/C++/Ok")
//                                        .document(document.getId());
//                                DocumentSnapshot snapshot = transaction.get(sfDocRef);
//                                String newAttendence = snapshot.get("attendence").toString();
//                                transaction.update(sfDocRef, "attendence", newAttendence);
//                                Log.i(TAG, "Attendence updated");
//                                return null;
//                            }
//                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d(TAG, "Transaction success!");
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w(TAG, "Transaction failure.", e);
//                            }
//                        });
//                    }
//                } else {
//                    Log.i(TAG, "Error getting documents: ", task.getException());
//                }}
//                            });
//                    i++;
//                }
//    }
}