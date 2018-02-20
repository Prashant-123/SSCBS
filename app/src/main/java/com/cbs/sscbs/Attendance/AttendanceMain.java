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
import android.widget.Button;

import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.Others.MainActivity;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AttendanceMain extends AppCompatActivity {

    private static final String TAG = "TAG";
    Map<String, Object> default_map = new HashMap<>();
    Map<String, Object> default_map2 = new HashMap<>();
    private static final String URL2 = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1ztpTfrOZ-Ntehx01ab5jRNqQa96cvqbDcDS0nPekVDI";
    private static final String URL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    RecyclerView recyclerView;
    String path;
    public static ArrayList<String> getAllToUpdateTotal = new ArrayList<>();
    Integer Labtype;
    String clas;
    String sub;
    double newAttendence = 0 ;
    double newTotal = 0 ;
    Button button1;
//    CollectionReference toUpdateTotal = FirebaseFirestore.getInstance().collection("Attendance");

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());


    String getYear = formattedDate.substring(7,11);
    String getMonth = formattedDate.substring(3, 6);

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ArrayList<AttendanceDataClass> showdata = new ArrayList<>();
    AttendanceAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1 =  findViewById(R.id.save_at);
        setContentView(R.layout.common_rv);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AttendanceAdapter(this, showdata);
        recyclerView.setAdapter(adapter);

        Intent getPath = getIntent();
        path = String.valueOf(getPath.getStringExtra("path"));
        Intent getType = getIntent();
        Labtype = Integer.valueOf(getType.getStringExtra("Labtype"));
        Intent getClass = getIntent();
        clas = String.valueOf(getClass.getStringExtra("class"));
        Intent getSub = getIntent();
        sub = String.valueOf(getSub.getStringExtra("subject"));

        new getListFromExcel().execute();
        adapter.notifyDataSetChanged();
    }

    public void Save(View view) {
        int i = 0;

        if (Labtype == 1 || Labtype == 2){
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        AttendanceAdapter.saveRoll.get(i) + "/Subjects/" + sub + " [L]" + "/Year").document(getYear).collection("/Months");
                save(getStu);

                i++;

            }
        }else if(Labtype ==3)
        {
//            Log.wtf(TAG, String.valueOf(AttendanceAdapter.saveRoll.size()));
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        AttendanceAdapter.saveRoll.get(i) + "/Subjects/" + sub + "/Year").document(getYear).collection("/Months");
                save(getStu);
                i++;
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

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
                            final DocumentReference sfDocRef = getStu.document("Feb");
                            DocumentSnapshot snapshot = transaction.get(sfDocRef);
                             newAttendence = (snapshot.getDouble("attendance")) + 1;
                            transaction.update(sfDocRef, "attendance", newAttendence);
                          //  default_map2.put("attendance" ,newAttendence);
                            Log.i(TAG, "Attendence updated");
                            return null;
                        }
                    });
                }
            }
        });
    }

    public class getListFromExcel extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(URL);
                String jsonStr2 = sh.makeServiceCall(URL2);
//              Log.i(TAG, "Response from url: " + jsonStr2);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray contacts = object.getJSONArray(clas);
                JSONObject object2 = new JSONObject(jsonStr2);
                JSONArray contacts2 = object2.getJSONArray(clas);
//                Log.wtf(TAG, String.valueOf(contacts.length()));
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String grp = c.getString("Lab_Group");

//                    db.collection("Attendance").document(clas).collection("Students")
//                            .document(roll_no).set(default_map);

                    for(int j = 0 ; j<contacts2.length();j++){
                        JSONObject c2 = contacts2.getJSONObject(j);
                        String sub = c2.getString("Subjects");

//                        default_map2.put("attendance",newAttendence);
//                        db.collection("Attendance").document(clas).collection("Students")
//                                .document(roll_no).collection("Subjects").document(sub).collection("Year").document(getYear)
//                        .collection("Months").document(getMonth).set(default_map2);
                    }

                    if(grp.equals("1") && Labtype == 1)
                    {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                        getAllToUpdateTotal.add(roll_no);
                    }

                    if(grp.equals("2") && Labtype == 2)
                    {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                        getAllToUpdateTotal.add(roll_no);
                    }

                    if(Labtype == 3)
                    {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                        getAllToUpdateTotal.add(roll_no);
                    }

                }

            }
            catch(Exception ex)
            {
                Log.e("TAG", "getListFromExcel", ex);
            }

            return null;


        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();

            updateTotal();
//            int i = 0;
//            while (i < getAllToUpdateTotal.size()) {
                final CollectionReference toUpdateTotal = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        16501 + "/Subjects/" + sub + "/Year").document("2018").collection("/Months");

                toUpdateTotal.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            db.runTransaction(new Transaction.Function<Void>() {
                                @Override
                                public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                    final DocumentReference sfDocRef = toUpdateTotal.document("Feb");
                                    DocumentSnapshot snapshot = transaction.get(sfDocRef);
                                    newTotal = (snapshot.getDouble("total")) + 1;
                                    transaction.update(sfDocRef, "total", newTotal);
                                    //  default_map2.put("attendance" ,newAttendence);
                                    Log.i(TAG, "Total updated");
                                    return null;
                                }
                            });
                        }
                    }
                });

        }


    }

    public void updateTotal() {

        Log.i(TAG, getAllToUpdateTotal.toString());
//        int i = 0;
//        while (i < getAllToUpdateTotal.size()) {
//            final CollectionReference toUpdateTotal = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
//                    getAllToUpdateTotal.get(i) + "/Subjects/" + sub + "/Year").document("2018").collection("/Months");
//
//            toUpdateTotal.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        db.runTransaction(new Transaction.Function<Void>() {
//                            @Override
//                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
//                                final DocumentReference sfDocRef = toUpdateTotal.document("Feb");
//                                DocumentSnapshot snapshot = transaction.get(sfDocRef);
//                                newTotal = (snapshot.getDouble("total")) + 1;
//                                transaction.update(sfDocRef, "total", newTotal);
//                                //  default_map2.put("attendance" ,newAttendence);
//                                Log.i(TAG, "Total updated");
//                                return null;
//                            }
//                        });
//                    }
//                }
//            });
//        }
    }

}