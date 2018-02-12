package com.cbs.sscbs.Attendance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class AttendanceMain extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final String URL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    RecyclerView recyclerView;
    String path;
    Integer type;
    String clas;
    String sub;
    FloatingActionButton button1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<AttendanceDataClass> showdata = new ArrayList<>();
    AttendanceAdapter adapter = null;
    //

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

        Intent getPath = getIntent();
        path = String.valueOf(getPath.getStringExtra("path"));
        Intent getType = getIntent();
        type = Integer.valueOf(getType.getStringExtra("type"));
        Intent getClass = getIntent();
        clas = String.valueOf(getClass.getStringExtra("class"));
        Intent getSub = getIntent();
        sub = String.valueOf(getSub.getStringExtra("subject"));

        Log.wtf(TAG , type.toString());
        new yourDataTask().execute();

//        if(type == 3){
//
//            db.collection("ClassList/" + clas + "/Type/" + "Lab-G1" + "/StudentList")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (DocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
//                                    AttendanceDataClass attendanceDataClass =
//                                            new AttendanceDataClass(document.getData().get("name").toString(),
//                                                    document.getData().get("rollno").toString());
//                                    showdata.add(attendanceDataClass);
//                                }
//                            } else {
//                                Log.d(TAG, "Error getting documents: ", task.getException());
//                            }
//                        }
//                    });
//
//            db.collection("ClassList/" + clas + "/Type/" + "Lab-G2" + "/StudentList")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (DocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
//                                    AttendanceDataClass attendanceDataClass =
//                                            new AttendanceDataClass(document.getData().get("name").toString(),
//                                                    document.getData().get("rollno").toString());
//                                    showdata.add(attendanceDataClass);
//                                   adapter.notifyDataSetChanged();
//                                }
//                            } else {
//                                Log.d(TAG, "Error getting documents: ", task.getException());
//                            }
//                        }
//                    });
//        }
//
//        else {
//            db.collection(path)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (DocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, document.getId() + " => " + document.getData());
//                                    AttendanceDataClass attendanceDataClass =
//                                            new AttendanceDataClass(document.getData().get("name").toString(),
//                                                    document.getData().get("rollno").toString());
//                                    showdata.add(attendanceDataClass);
//                                    adapter.notifyDataSetChanged();
//                                }
//                            } else {
//                                Log.d(TAG, "Error getting documents: ", task.getException());
//                            }
//                        }
//                    });
//        }
        adapter.notifyDataSetChanged();
    }

    public void Save(View view) {
        int i = 0;

        if (type == 1 || type == 2){
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        AttendanceAdapter.saveRoll.get(i) + "/Subjects/" + sub + " [L]" + "/Year/2017-18/Months");
                save(getStu);
                i++;

            }
        }else if(type ==3)
        {
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        AttendanceAdapter.saveRoll.get(i) + "/Subjects/" + sub + "/Year/2017-18/Months");
                save(getStu);
                i++;
            }
        }

    }

    public void save(final CollectionReference getStu) {
        getStu.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    db.runTransaction(new Transaction.Function<Void>() {
                        @Override
                        public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                            final DocumentReference sfDocRef = getStu.document("/Feb");
                            DocumentSnapshot snapshot = transaction.get(sfDocRef);
                            double newAttendence = (snapshot.getDouble("attendance")) + 1;
                            transaction.update(sfDocRef, "attendance", newAttendence);
                            Log.i(TAG, "Attendence updated");
                            return null;
                        }
                    });
                }
            }
        });
    }

    public class yourDataTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(URL);

                Log.i(TAG, "Response from url: " + jsonStr);

                JSONObject object = new JSONObject(jsonStr);
                JSONArray contacts = object.getJSONArray("Sheet4");

                Log.wtf(TAG, String.valueOf(contacts.length()));

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String grp = c.getString("Group");
                    if(grp.equals("1") && type == 1)
                    {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                    }

                    if(grp.equals("2") && type == 2)
                    {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                    }

                    if(type == 3)
                    {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                    }



                }
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
        }
    }
}