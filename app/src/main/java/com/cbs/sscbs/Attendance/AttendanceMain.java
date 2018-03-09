package com.cbs.sscbs.Attendance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.Others.MainActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceMain extends AppCompatActivity {

    private static final String TAG = "TAG";
    String link;
    private static final String bscLIST = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    private static final String bmsLIST = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=18_YyZhOv3me5QWWPn_ByF_IPiSgvDYcq-W3RfQxkHvQ";
    private static final String bfiaLIST = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1iZWNSlHipbkLyYhtdUPqZdXaq9enLrzUTPxOipxCiDc";

    Integer Labtype,TutType;
    String clas,sub,path;
    ProgressBar bar;
    Integer type;
    TextView tv;
    double newAttendence = 0, newTotal = 0 ;
    Button button1;
    CollectionReference getLink = FirebaseFirestore.getInstance().collection("Excel Data");

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());

    String getYear = formattedDate.substring(7,11);
    String getMonth = formattedDate.substring(3, 6);

    RecyclerView recyclerView;
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
        type = Integer.valueOf(getType.getStringExtra("type"));
        Log.wtf(TAG,"Type : "+ type.toString());
        if(type == 1){
            Intent getLabType = getIntent();
            Labtype = Integer.valueOf(getLabType.getStringExtra("Labtype"));
        }
        else if(type == 2){
            Intent getTutType = getIntent();
            TutType = Integer.valueOf(getTutType.getStringExtra("TutType"));
        }
        Intent getClass = getIntent();
        clas = String.valueOf(getClass.getStringExtra("class"));
        Intent getSub = getIntent();
        sub = String.valueOf(getSub.getStringExtra("subject"));

        if (clas.contains("Bsc"))
        {
            link = bscLIST;
        }
        else if (clas.contains("BFIA"))
        {
            link = bfiaLIST;
        }
        else if (clas.contains("BMS"))
            link = bmsLIST;

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.stu_toolbar);
        toolbar.setTitle(clas + " / " + sub );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        bar = (ProgressBar) findViewById(R.id.list_progress_bar);
        tv = (TextView)findViewById(R.id.loading_lists);
        new getListFromExcel().execute();
        adapter.notifyDataSetChanged();
    }

    public void Save(View view) {
        int i = 0;

        if(type == 0){
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        AttendanceAdapter.saveRoll.get(i) + "/Subjects/" + sub + "/Year").document(getYear).collection("/Months");
                save(getStu);
                i++;
            }
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
        }
        if(type == 1) {
            if (Labtype == 1 || Labtype == 2) {
                while (i < AttendanceAdapter.saveRoll.size()) {
                    final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                            AttendanceAdapter.saveRoll.get(i) + "/Subjects/" + sub + " [L]" + "/Year").document(getYear).collection("/Months");
                    save(getStu);
                    i++;

                }
            } else if (Labtype == 3) {
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
            if(type == 2){
                if (TutType == 1 || TutType == 2 || TutType == 3) {
                    while (i < AttendanceAdapter.saveRoll.size()) {
                        final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                                AttendanceAdapter.saveRoll.get(i) + "/Subjects/" + sub + " [T]" + "/Year").document(getYear).collection("/Months");
                        save(getStu);
                        i++;

                    }
                } else if (TutType == 4) {
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

    }

    public void save(final CollectionReference getStu) {
        getStu.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    db.runTransaction(new Transaction.Function<Void>() {
                        @Override
                        public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                            final DocumentReference sfDocRef = getStu.document(getMonth);
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

    public class getListFromExcel extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(link);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray contacts = object.getJSONArray(clas);
                Log.wtf(TAG,clas);
                String tute;

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String grp = c.getString("Lab_Group");
                    if(clas.contains("BMS")|| clas.contains("BFIA")) {
                        tute = c.getString("Tute");
                    }else tute="";

                    if(type == 0){
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                    }

                    if(type ==1) {
                        if (grp.equals("1") && Labtype == 1) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }

                        if (grp.equals("2") && Labtype == 2) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }

                        if (Labtype == 3) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    }
                    else if(type==2){

                        if (tute.equals("1") && TutType == 1) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }

                        if (tute.equals("2") && TutType == 2) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                        if (tute.equals("3") && TutType == 3) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }

                        if (TutType == 4) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
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
            bar.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);

                final CollectionReference toUpdateTotal = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        16501 + "/Subjects/" + sub + "/Year").document("2018").collection("/Months");

                toUpdateTotal.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            db.runTransaction(new Transaction.Function<Void>() {
                                @Override
                                public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                    final DocumentReference sfDocRef = toUpdateTotal.document(getMonth);
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

}