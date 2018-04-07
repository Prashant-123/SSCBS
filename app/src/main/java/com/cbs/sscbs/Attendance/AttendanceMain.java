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
    String clas, sub, type , getType;
    ProgressBar bar;
    TextView tv;
    int size;
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
    private View view;

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
        size = AttendanceAdapter.to_update_Total.size();

        getDataFromIntent();

        if(clas.contains("Bsc")){
            new bscExcelSheet().execute();
        }
        else if((clas.contains("BMS-1"))||(clas.contains("BMS-2"))){
            new bmsExcelSheet().execute();
        }
        else if(clas.contains("BMS-3F")){
            new bmsMixExcelSheet().execute();
        }
        else if(clas.contains("BMS-3M")){
            new bms3MExcelSheet().execute();
        }
        else if((clas.contains("BFIA-1"))||(clas.contains("BFIA-2"))){
            new bfiaExcelSheet().execute();
        }
        else if(clas.contains("BFIA-3")){
            new bfiaMixExcelSheet().execute();
        }

        adapter.notifyDataSetChanged();
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.stu_toolbar);
        toolbar.setTitle(clas + " / " + sub);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bar = (ProgressBar) findViewById(R.id.list_progress_bar);
        tv = (TextView) findViewById(R.id.loading_lists);
        adapter.notifyDataSetChanged();

    }

    private void getDataFromIntent() {
        Intent data = getIntent();
        clas = data.getStringExtra("class");
        sub = data.getStringExtra("subject");
        type = data.getStringExtra("type");
        Log.wtf(TAG, "ok"+type);
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

    private void updateTotal(final CollectionReference toUpdateTotal, final String s) {
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
                            Log.i(TAG, "Total updated-> " + s);
                            return null;
                        }
                    });
                }
            }
        });
    }

    public void bscSave(View view) {
        int i = 0;
        Log.wtf(TAG, AttendanceAdapter.saveRoll.toString());

//        /Attendance/Bsc-2/Students/16501/Year/2018/Subjects/Algorithms/Months/Mar

        if (type.contains("Lab-G1") || type.contains("Lab-G2")) { //If there's LAB.
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

        if (type.contains("Lab-G1") || type.contains("Lab-G2")){
            Log.i(TAG, String.valueOf(AttendanceAdapter.to_update_Total.size()));
            i = 0;
            while (i < AttendanceAdapter.to_update_Total.size()){
                final CollectionReference toUpdateTotal = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        AttendanceAdapter.to_update_Total.get(i) + "/Year/" + getYear + "/Subjects/").document(sub + " [L]").collection("/Months/");
                updateTotal(toUpdateTotal, AttendanceAdapter.to_update_Total.get(i));
                i++;
            }
        } else {
            Log.i(TAG, String.valueOf(AttendanceAdapter.to_update_Total.size()));
            i = 0;
            while (i < AttendanceAdapter.to_update_Total.size()){
                final CollectionReference toUpdateTotal = FirebaseFirestore.getInstance().collection("Attendance/" + clas + "/Students/" +
                        AttendanceAdapter.to_update_Total.get(i) + "/Year/" + getYear + "/Subjects/").document(sub).collection("/Months/");
                updateTotal(toUpdateTotal, AttendanceAdapter.to_update_Total.get(i));
                i++;
            }
        }

//        switch_to_main();
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
        } else if(type.contains("Theory")){
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
        if (type.contains("Lab-G1") || type.contains("Lab-G2")) { //If there's LAB.
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
        } else if (type.contains("Tute-G1") || type.contains("Tute-G2") || type.contains("Tute-G3")) { //If there's TUTE.
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
        } else if(type.contains("Theory")) {
            Log.wtf(TAG , "dsfs");
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

    public void bmsMixSave(View view) {
        this.view = view;
        int i = 0;
        Log.wtf(TAG, AttendanceAdapter.saveRoll.toString());
        if (type.contains("Lab-G1") || type.contains("Lab-G2")) { //If there's LAB.
            while (i < AttendanceAdapter.saveRoll.size()) {
                for (int g = 0; g < Home_frag.bms3List.size(); g++) {
                    final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                            + Home_frag.bms3List.get(g) + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                            .collection("/Subjects/").document(sub + " [L]")
                            .collection("/Months");
                    update(getStu);
                }
                i++;
            }
        } else if (type.contains("Tute-G1") || type.contains("Tute-G2") || type.contains("Tute-G3")) { //If there's TUTE.
            while (i < AttendanceAdapter.saveRoll.size()) {
                for (int g = 0; g < Home_frag.bms3List.size(); g++) {
                    final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                            + Home_frag.bms3List.get(g) + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                            .collection("/Subjects/").document(sub + " [T]")
                            .collection("/Months");
                    update(getStu);
                }
                i++;
            }
        } else if(type.contains("Theory")) {
            Log.wtf(TAG , "dsfs");
            while (i < AttendanceAdapter.saveRoll.size()) {
                for (int g = 0; g < Home_frag.bms3List.size(); g++) {
                    final CollectionReference getStu = FirebaseFirestore.getInstance().collection("Attendance/"
                            + Home_frag.bms3List.get(g) + "/Students/" + AttendanceAdapter.saveRoll.get(i) + "/Year/").document(getYear)
                            .collection("/Subjects/").document(sub)
                            .collection("/Months");
                    update(getStu);
                }
                i++;
            }
        }
        switch_to_main();
    }

    public void bms3MSave(View view) {
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

    public class bscExcelSheet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(bscSheet);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray sheet = object.getJSONArray(clas);

                for (int i = 0; i < sheet.length(); i++) {
                    JSONObject c = sheet.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String grp = c.getString("Lab_Group");

                    if (type.contains("Lab-G1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.contains("Lab-G2")) {
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

                    if (type.contains("Lab-G1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.contains("Lab-G2")) {
                        if (grp.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.contains("Tute-G1")) {
                        if (tute.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }

                    } else if (type.contains("Tute-G2")) {
                        if (tute.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.contains("Tute-G3")) {
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

                    if (type.contains("Lab-G1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.contains("Lab-G2")) {
                        if (grp.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.contains("Tute-G1")) {
                        if (tute.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }

                    } else if (type.contains("Tute-G2")) {
                        if (tute.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    } else if (type.contains("Tute-G3")) {
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

                HttpHandler sh1 = new HttpHandler();
                String jsonStr1 = sh1.makeServiceCall(SUBURL);
                JSONObject object1 = new JSONObject(jsonStr1);
                JSONArray sheet1 = object1.getJSONArray(clas);

                for(int i = 0 ; i<sheet1.length() ; i++){
                    JSONObject jsonObject1 = sheet1.getJSONObject(i);
                    String subject = jsonObject1.getString("Semester_A");
                    String type = jsonObject1.getString("Sub");
                    Log.wtf(TAG , subject + " ______________ " + sub);

                    if(subject.contains(sub)){
                        Log.wtf(TAG, "Inside if");
                        getType = type;
                        break;
                    }else {
                        Log.wtf(TAG, "Inside not if");
                    }


                }

                Log.wtf(TAG , "Sub type is : " + getType);
                if(getType.equals("0")){
                    Log.wtf(TAG , "ssdfdsfsfw");
                    JSONArray sheet = object.getJSONArray(clas);
                    for (int i = 0; i < sheet.length(); i++) {
                        JSONObject c = sheet.getJSONObject(i);
                        String name = c.getString("Name");
                        String roll_no = c.getString("Roll_No");
                        String grp = c.getString("Lab_Group");
                        String tute = c.getString("Tute");
                        Log.wtf(TAG, type);

                        if (type.contains("Lab-G1")) {
                            if (grp.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else if (type.contains("Lab-G2")) {
                            if (grp.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else if (type.contains("Tute-G1")) {
                            if (tute.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }

                        } else if (type.contains("Tute-G2")) {
                            if (tute.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else if (type.contains("Tute-G3")) {
                            if (tute.equals("3")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    }
                }else{

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


                        if (type.contains("Lab-G1")) {
                            Log.i(TAG, "Yes-1");
                            if (grp.equals("1")) {
                                if (sub1.equals(getType) || sub2.equals(getType) ) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }
                        } else if (type.contains(" Lab-G2")) {
                            Log.i(TAG, "Yes-2");
                            if (grp.equals("2")) {
                                if (sub1.equals(getType) || sub2.equals(getType) ) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }
                        } else if (type.contains(" Tute-G1")) {
                            Log.i(TAG, "Yes");
                            if (tute.equals("1")) {
                                if (sub1.equals(getType) || sub2.equals(getType) ) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }

                        } else if (type.contains(" Tute-G2")) {
                            Log.i(TAG, "Yes-3");
                            if (tute.equals("2")) {
                                if (sub1.equals(getType) || sub2.equals(getType) ) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }
                        } else if (type.contains(" Tute-G3")) {
                            Log.i(TAG, "Yes-4");
                            if (tute.equals("3")) {
                                if (sub1.equals(getType) || sub2.equals(getType) ) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
                            }
                        } else {
                            Log.i(TAG, "no");
                            if (sub1.equals(getType) || sub2.equals(getType) || getType.equals("0")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
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

    public class bmsMixExcelSheet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(bmsSheet);
                JSONObject object = new JSONObject(jsonStr);

                HttpHandler sh1 = new HttpHandler();
                String jsonStr1 = sh1.makeServiceCall(SUBURL);
                JSONObject object1 = new JSONObject(jsonStr1);
                JSONArray sheet1 = object1.getJSONArray(clas);

                for(int i = 0 ; i<sheet1.length() ; i++){
                    JSONObject jsonObject1 = sheet1.getJSONObject(i);
                    String subject = jsonObject1.getString("Semester_A");
                    String type = jsonObject1.getString("Sub");
                    Log.wtf(TAG , subject + " ______________ " + sub);

                    if(subject.contains(sub)){
                        Log.wtf(TAG, "Inside if");
                        getType = type;
                        break;
                    }else {
                        Log.wtf(TAG, "Inside not if");
                    }


                }

                Log.wtf(TAG , "Sub type is : " + getType);
                if(getType.equals("0")){
                    Log.wtf(TAG , "ssdfdsfsfw");
                    JSONArray sheet = object.getJSONArray(clas);
                    for (int i = 0; i < sheet.length(); i++) {
                        JSONObject c = sheet.getJSONObject(i);
                        String name = c.getString("Name");
                        String roll_no = c.getString("Roll_No");
                        String grp = c.getString("Lab_Group");
                        String tute = c.getString("Tute");
                        Log.wtf(TAG, type);

                        if (type.contains("Lab-G1")) {
                            if (grp.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else if (type.contains("Lab-G2")) {
                            if (grp.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else if (type.contains("Tute-G1")) {
                            if (tute.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }

                        } else if (type.contains("Tute-G2")) {
                            if (tute.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else if (type.contains("Tute-G3")) {
                            if (tute.equals("3")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    }
                }else{

                    for(int h= 0 ; h < Home_frag.bfia3List.size();h++) {
                        JSONArray sheet = object.getJSONArray(Home_frag.bfia3List.get(h));
                        for (int i = 0; i < sheet.length(); i++) {
                            JSONObject c = sheet.getJSONObject(i);
                            String name = c.getString("Name");
                            String roll_no = c.getString("Roll_No");
                            String grp = c.getString("Lab_Group");
                            String tute_mix = c.getString("Tute_Mix");
                            String sub1 = c.getString("Sub_Type_1");
                            String sub2 = c.getString("Sub_Type_2");


                            if (type.contains("Lab-G1")) {
                                Log.i(TAG, "Yes-1");
                                if (grp.equals("1")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                    }
                                }
                            } else if (type.contains(" Lab-G2")) {
                                Log.i(TAG, "Yes-2");
                                if (grp.equals("2")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                    }
                                }
                            } else if (type.contains(" Tute-G1")) {
                                Log.i(TAG, "Yes");
                                if (tute_mix.equals("1")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                    }
                                }

                            } else if (type.contains(" Tute-G2")) {
                                Log.i(TAG, "Yes-3");
                                if (tute_mix.equals("2")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                    }
                                }
                            } else if (type.contains(" Tute-G3")) {
                                Log.i(TAG, "Yes-4");
                                if (tute_mix.equals("3")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                    }
                                }
                            } else {
                                Log.i(TAG, "no");
                                if (sub1.equals(getType) || sub2.equals(getType) || getType.equals("0")) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                }
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

    public class bms3MExcelSheet extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(bmsSheet);
                JSONObject object = new JSONObject(jsonStr);

                HttpHandler sh1 = new HttpHandler();
                String jsonStr1 = sh1.makeServiceCall(SUBURL);
                JSONObject object1 = new JSONObject(jsonStr1);
                JSONArray sheet1 = object1.getJSONArray(clas);

                for(int i = 0 ; i<sheet1.length() ; i++){
                    JSONObject jsonObject1 = sheet1.getJSONObject(i);
                    String subject = jsonObject1.getString("Semester_A");
                    String type = jsonObject1.getString("Sub");
                    Log.wtf(TAG , subject + " ______________ " + sub);

                    if(subject.contains(sub)){
                        Log.wtf(TAG, "Inside if");
                        getType = type;
                        break;
                    }else {
                        Log.wtf(TAG, "Inside not if");
                    }
                }

                Log.wtf(TAG , "Sub type is : " + getType);
                if(getType.equals("0")){
                    Log.wtf(TAG , "ssdfdsfsfw");
                    JSONArray sheet = object.getJSONArray(clas);
                    for (int i = 0; i < sheet.length(); i++) {
                        JSONObject c = sheet.getJSONObject(i);
                        String name = c.getString("Name");
                        String roll_no = c.getString("Roll_No");
                        String tute = c.getString("Tute");
                        Log.wtf(TAG, type);

                        if (type.contains("Tute-G1")) {
                            if (tute.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }

                        } else if (type.contains("Tute-G2")) {
                            if (tute.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else if (type.contains("Tute-G3")) {
                            if (tute.equals("3")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                            }
                        } else {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                        }
                    }
                }else{

                        JSONArray sheet = object.getJSONArray(clas);
                        for (int i = 0; i < sheet.length(); i++) {
                            JSONObject c = sheet.getJSONObject(i);
                            String name = c.getString("Name");
                            String roll_no = c.getString("Roll_No");
                            String tute = c.getString("Tute_Mix");
                            String sub1 = c.getString("Sub_Type_1");

                            if (type.contains(" Tute-G1")) {
                                Log.i(TAG, "Yes");
                                if (tute.equals("1")) {
                                    if (sub1.equals(getType)  ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                    }
                                }

                            } else if (type.contains(" Tute-G2")) {
                                Log.i(TAG, "Yes-3");
                                if (tute.equals("2")) {
                                    if (sub1.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                    }
                                }
                            } else if (type.contains(" Tute-G3")) {
                                Log.i(TAG, "Yes-4");
                                if (tute.equals("3")) {
                                    if (sub1.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                    }
                                }
                            } else {
                                Log.i(TAG, "no");
                                if (sub1.equals(getType)  || getType.equals("0")) {
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

}