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

import com.cbs.sscbs.Fragments.Home_frag;
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

public class AttendanceMain extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final String bscSheet = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    private static final String SUBURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1ztpTfrOZ-Ntehx01ab5jRNqQa96cvqbDcDS0nPekVDI";
    private static final String bscLIST = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    private static final String bmsSheet = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=18_YyZhOv3me5QWWPn_ByF_IPiSgvDYcq-W3RfQxkHvQ";
    private static final String bfiaSheet = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1iZWNSlHipbkLyYhtdUPqZdXaq9enLrzUTPxOipxCiDc";
    public ArrayList<AttendanceDataClass> showdata = new ArrayList<>();
    String link;
    String clas, sub, type;
    ProgressBar bar;
    TextView tv;
    double newAttendence = 0, newTotal = 0;
    Button button1;
    CollectionReference getLink = FirebaseFirestore.getInstance().collection("Attendance");
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());
    String getYear = formattedDate.substring(7, 11);
    String getMonth = formattedDate.substring(3, 6);
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AttendanceAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1 = findViewById(R.id.save_at);
        setContentView(R.layout.common_rv);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AttendanceAdapter(this, showdata);
        recyclerView.setAdapter(adapter);
        //new bfiaExcelSheet().execute();
        getDataFromIntent();
        new bfiaMixExcelSheet().execute();
//        if(clas.contains("bsc")){
//            new bscExcelSheet().execute();
//        }
//        else  if(clas.contains("bfia")){
//
//        }
        adapter.notifyDataSetChanged();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.stu_toolbar);
        toolbar.setTitle(clas + " / " + sub);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bar = (ProgressBar) findViewById(R.id.list_progress_bar);
        tv = (TextView) findViewById(R.id.loading_lists);
        adapter.notifyDataSetChanged();

//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(clas.contains("bsc")){
//                    bscSave(v);
//                }
//                else  if(clas.contains("bfia")){
//                    bfiaSave(v);
//                }
//            }
//        });
    }

    private void getDataFromIntent() {
        Intent data = getIntent();
        clas = data.getStringExtra("class");
        sub = data.getStringExtra("subject");
        type = data.getStringExtra("type");
    }



    public void bscSave(View view) {
        int i = 0;
        Log.wtf(TAG, AttendanceAdapter.saveRoll.toString());
        if (type.equals("1") || type.equals("2")) { //If there's LAB.
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                        + clas + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                        .collection("/Subjects/").document(sub + " [L]")
                        .collection("/Months");
                update(getStu);
                i++;
            }
        } else {
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                        + clas + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                        .collection("/Subjects/").document(sub)
                        .collection("/Months");
                update(getStu);
                i++;
            }
        }
        switch_to_main();
    }

    private void switch_to_main() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        AttendanceAdapter.saveRoll.clear();
        startActivity(intent);
        finish();
    }

    public void update(final CollectionReference getStu) {
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
                            return null;
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "Attendence updated");
                        }
                    });
                }
            }
        });
    }

    public void bfiaSave(View view) {
        int i = 0;
        Log.wtf(TAG, AttendanceAdapter.saveRoll.toString());
        if (type.equals("Lab-G1") || type.equals("Lab-G2")) { //If there's LAB.
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                        + clas + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                        .collection("/Subjects/").document(sub + " [L]")
                        .collection("/Months");
                update(getStu);
                i++;
            }
        } else if (type.equals(" Tute-G1") || type.equals(" Tute-G2") || type.equals(" Tute-G3")) { //If there's TUTE.
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                        + clas + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                        .collection("/Subjects/").document(sub + " [T]")
                        .collection("/Months");
                update(getStu);
                i++;
            }
        } else {
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                        + clas + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                        .collection("/Subjects/").document(sub)
                        .collection("/Months");
                update(getStu);
                i++;
            }
        }
        switch_to_main();
    }

    public void bmsSave(View view) {
        int i = 0;
        Log.wtf(TAG, AttendanceAdapter.saveRoll.toString());
        if (type.equals("Lab-G1") || type.equals("Lab-G2")) { //If there's LAB.
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                        + clas + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                        .collection("/Subjects/").document(sub + " [L]")
                        .collection("/Months");
                update(getStu);
                i++;
            }
        } else if (type.equals(" Tute-G1") || type.equals(" Tute-G2") || type.equals(" Tute-G3")) { //If there's TUTE.
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                        + clas + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                        .collection("/Subjects/").document(sub + " [T]")
                        .collection("/Months");
                update(getStu);
                i++;
            }
        } else {
            while (i < AttendanceAdapter.saveRoll.size()) {
                final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                        + clas + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                        .collection("/Subjects/").document(sub)
                        .collection("/Months");
                update(getStu);
                i++;
            }
        }
        switch_to_main();
    }

    public void bfiaMixSave(View view) {
        int i = 0;
        Log.wtf(TAG, AttendanceAdapter.saveRoll.toString());
        if (type.equals("1") || type.equals("2")) { //If there's LAB.
                while (i < AttendanceAdapter.saveRoll.size()) {
                    for (int g = 0; g < Home_frag.bfia3List.size(); g++) {
                        final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                                + Home_frag.bfia3List.get(g) + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                                .collection("/Subjects/").document(sub + " [L]")
                                .collection("/Months");
                        update(getStu);
                    }
                    i++;
            }
        } else if (type.equals("3") || type.equals("4") || type.equals("5")) { //If there's TUTE.
            while (i < AttendanceAdapter.saveRoll.size()) {
                for (int g = 0; g < Home_frag.bfia3List.size(); g++) {
                    final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                            + Home_frag.bfia3List.get(g) + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                            .collection("/Subjects/").document(sub + " [T]")
                            .collection("/Months");
                    update(getStu);
                }
                i++;
            }
        } else {
            while (i < AttendanceAdapter.saveRoll.size()) {
                for (int g = 0; g < Home_frag.bfia3List.size(); g++) {
                    final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                            + Home_frag.bfia3List.get(g) + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                            .collection("/Subjects/").document(sub)
                            .collection("/Months");
                    update(getStu);
                }
                i++;
            }
        }
        switch_to_main();
    }

    public class bscExcelSheet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(bscSheet);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray sheet = object.getJSONArray(clas);
                Log.wtf(TAG, type);

                for (int i = 0; i < sheet.length(); i++) {
                    JSONObject c = sheet.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String grp = c.getString("Lab_Group");

                    if (type.equals("1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.equals("2")) {
                        if (grp.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                    }
                }
            } catch (Exception ex) {
                Log.e("TAG", "getListFromExcel", ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
            bar.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);

//            final CollectionReference toUpdateTotal = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
//                    16501 + "/Subjects/" + sub + "/Year").document("2018").collection("/Months");
//
//            toUpdateTotal.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        db.runTransaction(new Transaction.Function<Void>() {
//                            @Override
//                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
//                                final DocumentReference sfDocRef = toUpdateTotal.document(getMonth);
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
//
        }
    }

    public class bfiaExcelSheet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(bfiaSheet);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray sheet = object.getJSONArray(clas);

                for (int i = 0; i < sheet.length(); i++) {
                    JSONObject c = sheet.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String grp = c.getString("Lab_Group");
                    String tute = c.getString("Tute");
                    Log.wtf(TAG, type);

                    if (type.equals("1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.equals("2")) {
                        if (grp.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.equals("3")) {
                        if (tute.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }

                    } else if (type.equals("4")) {
                        if (tute.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.equals("5")) {
                        if (tute.equals("3")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                    }
                }
            } catch (Exception ex) {
                Log.e("TAG", "getListFromExcel", ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
            bar.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public class bmsExcelSheet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(bmsSheet);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray sheet = object.getJSONArray(clas);

                for (int i = 0; i < sheet.length(); i++) {
                    JSONObject c = sheet.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String grp = c.getString("Lab_Group");
                    String tute = c.getString("Tute");
                    Log.wtf(TAG, type);

                    if (type.equals("1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.equals("2")) {
                        if (grp.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.equals("3")) {
                        if (tute.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }

                    } else if (type.equals("4")) {
                        if (tute.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.equals("5")) {
                        if (tute.equals("3")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                    }
                }
            } catch (Exception ex) {
                Log.e("TAG", "getListFromExcel", ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
            bar.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
        }
    }

    public class bfiaMixExcelSheet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(bfiaSheet);
                JSONObject object = new JSONObject(jsonStr);

               //5 String bscSubjectsList = httpHandler.makeServiceCall(SUBURL);

                for(int h= 0 ; h < Home_frag.bfia3List.size();h++) {
                    JSONArray sheet = object.getJSONArray(Home_frag.bfia3List.get(h));

                    for (int i = 0; i < sheet.length(); i++) {
                        JSONObject c = sheet.getJSONObject(i);
                        String name = c.getString("Name");
                        String roll_no = c.getString("Roll_No");
                        String grp = c.getString("Lab_Group");
                        String tute = c.getString("Tute");
                        String sub1 = c.getString("Sub_Type_1");
                        String sub2 = c.getString("Sub_Type_2");

                        Log.wtf(TAG, type);

                        if (type.equals("1")) {
                            Log.i(TAG, "Yes-1");
                            if (grp.equals("1")) {
                                if (sub1.equals("2") || sub2.equals("2")) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }
                        } else if (type.equals("2")) {
                            Log.i(TAG, "Yes-2");
                            if (grp.equals("2")) {
                                if (sub1.equals("2") || sub2.equals("2")) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }
                        } else if (type.equals(" Tute-G1")) {
                            Log.i(TAG, "Yes");
                            if (tute.equals("1")) {
                                if (sub1.equals("2") || sub2.equals("2")) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }

                        } else if (type.equals("Tute-G2")) {
                            Log.i(TAG, "Yes-3");
                            if (tute.equals("2")) {
                                if (sub1.equals("2") || sub2.equals("2")) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }
                        } else if (type.equals("Tute-G3")) {
                            Log.i(TAG, "Yes-4");
                            if (tute.equals("3")) {
                                if (sub1.equals("2") || sub2.equals("2")) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }
                        } else {
                            Log.i(TAG, "no");
                            if (sub1.equals("2") || sub2.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("TAG", "getListFromExcel", ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
            bar.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
        }
    }

//    public class getMixListFromExcel extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            try {
//                HttpHandler sh = new HttpHandler();
//                String jsonStr = sh.makeServiceCall(link);
//                String jsonStr2 = sh.makeServiceCall(SUBURL);
//                JSONObject object = new JSONObject(jsonStr);
////                ArrayList<String> cl = new ArrayList<>();
////                cl.add("BFIA 3A");
////                cl.add("BFIA 3B");
//                for (int h = 0; h < Home_frag.list.size(); h++) {
//                    JSONArray contacts = object.getJSONArray(Home_frag.list.get(h));
////                    JSONObject object2 = new JSONObject(jsonStr2);
////                    JSONArray contacts2 = object2.getJSONArray(clas);
//                    String tute, sub1, sub2;
//
//                    for (int i = 0; i < contacts.length(); i++) {
//                        JSONObject c = contacts.getJSONObject(i);
//                        String name = c.getString("Name");
//                        String roll_no = c.getString("Roll_No");
//                        String grp = c.getString("Lab_Group");
//                        if (clas.contains("BFIA 3")) {
//                            sub1 = c.getString("Sub_Type_1");
//                            sub2 = c.getString("Sub_Type_2");
//
//                        } else {
//                            sub1 = "";
//                            sub2 = "";
//
//                        }
//                        if (clas.contains("BMS") || clas.contains("BFIA")) {
//                            tute = c.getString("Tute");
//                        } else tute = "";
//
////                    for (int k = 0; k < contacts2.length(); k++) {
////                        JSONObject c2 = contacts2.getJSONObject(k);
////                        String subj = c2.getString("Semester_A");
////                        String typesub = c2.getString("Sub");
////                        if (sub == subj) {
//                        if (type == 0) {
//                            if (sub1.equals("2") || sub2.equals("2")) {
//                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
//                                showdata.add(dataClass);
//                            }
//                        }
////                        }
////                    }
//
//                        if (type == 1) {
//                            if (grp.equals("1") && Labtype == 1) {
//                                if (sub1.equals("2") || sub2.equals("2")) {
//                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
//                                    showdata.add(dataClass);
//                                }
//                            }
//
//                            if (grp.equals("2") && Labtype == 2) {
//                                if (sub1.equals("2") || sub2.equals("2")) {
//                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
//                                    showdata.add(dataClass);
//                                }
//                            }
//
//                            if (Labtype == 3) {
//                                if (sub1.equals("2") || sub2.equals("2")) {
//                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
//                                    showdata.add(dataClass);
//                                }
//                            }
//                        } else if (type == 2) {
//
//                            if (tute.equals("1") && TutType == 1) {
//                                if (sub1.equals("2") || sub2.equals("2")) {
//                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
//                                    showdata.add(dataClass);
//                                }
//                            }
//
//                            if (tute.equals("2") && TutType == 2) {
//                                if (sub1.equals("2") || sub2.equals("2")) {
//                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
//                                    showdata.add(dataClass);
//                                }
//                            }
//                            if (tute.equals("3") && TutType == 3) {
//                                if (sub1.equals("2") || sub2.equals("2")) {
//                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
//                                    showdata.add(dataClass);
//                                }
//                            }
//
//                            if (TutType == 4) {
//                                if (sub1.equals("2") || sub2.equals("2")) {
//                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
//                                    showdata.add(dataClass);
//                                }
//                            }
//                        }
//
//                    }
//                }
//            } catch (Exception ex) {
//                Log.e("TAG", "getListFromExcel", ex);
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            adapter.notifyDataSetChanged();
//            bar.setVisibility(View.INVISIBLE);
//            tv.setVisibility(View.INVISIBLE);
//
//            final CollectionReference toUpdateTotal = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
//                    16501 + "/Subjects/" + sub + "/Year").document("2018").collection("/Months");
//
//            toUpdateTotal.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        db.runTransaction(new Transaction.Function<Void>() {
//                            @Override
//                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
//                                final DocumentReference sfDocRef = toUpdateTotal.document(getMonth);
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
//
//        }
//    }

}