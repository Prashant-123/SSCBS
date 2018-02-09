package com.cbs.sscbs.Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    String path , type , clas, sub;
    FloatingActionButton button1 ;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<AttendanceDataClass>  showdata = new ArrayList<>();
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

//        final String path;
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if (extras == null) {
//                path = null;
//            } else {
//                path= extras.getString("path");
//
//            }
//        } else {
//            path= (String) savedInstanceState.getSerializable("path");
//        }

        Intent getPath = getIntent();
        path = String.valueOf(getPath.getStringExtra("path"));

        Intent getType = getIntent();
        type = String.valueOf(getType.getStringExtra("type"));

        Intent getClass = getIntent();
        clas = String.valueOf(getClass.getStringExtra("class"));

        Intent getSub = getIntent();
        sub = String.valueOf(getSub.getStringExtra("subject"));


        Log.wtf(TAG,type + " " + clas +  " " + sub + path);

        db.collection(path)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                AttendanceDataClass attendanceDataClass =
                                    new AttendanceDataClass(document.getData().get("name").toString() ,
                                            document.getData().get("rollno").toString() );
                                        showdata.add(attendanceDataClass);
                                        adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        final CollectionReference getStudents = FirebaseFirestore.getInstance().collection("Attendance").document(clas).collection("Students");
        getStudents.whereEqualTo("Group",2)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                for(DocumentSnapshot documentSnapshot : task.getResult()){
                   Log.wtf(TAG , documentSnapshot.getId());

                   CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                           documentSnapshot.getId() + "Subjects" + sub + "/Year/2017-18/Month/Feb");

                   getStu.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                          if(task.isSuccessful()){
                              for(final DocumentSnapshot documentSnapshot1 : task.getResult()){
                                  db.runTransaction(new Transaction.Function<Void>() {
                                      @Override
                                      public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                          final DocumentReference sfDocRef = db.collection("").document(documentSnapshot1.getId());
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
                          }
                       }
                   });
                }}
            }
        });


        adapter.notifyDataSetChanged();
//
    }
}