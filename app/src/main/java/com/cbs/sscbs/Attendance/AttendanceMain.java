package com.cbs.sscbs.Attendance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cbs.sscbs.Fragments.Home_frag;
import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.Others.MainActivity;
import com.cbs.sscbs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AttendanceMain extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final String bscSheet = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    private static final String SUBURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1ztpTfrOZ-Ntehx01ab5jRNqQa96cvqbDcDS0nPekVDI";
    private static final String bscLIST = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    private static final String bmsSheet = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=18_YyZhOv3me5QWWPn_ByF_IPiSgvDYcq-W3RfQxkHvQ";
    private static final String bfiaSheet = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1iZWNSlHipbkLyYhtdUPqZdXaq9enLrzUTPxOipxCiDc";
    public ArrayList<AttendanceDataClass> showdata = new ArrayList<>();
    public ArrayList<WaiverDataClass> shwdata = new ArrayList<>();
    String link;
    String clas, sub, type , getType , btnChoosen;
    ProgressBar bar;
    TextView tv;
    int size;
    double newAttendence = 0, newTotal = 0;
    ImageView button1;
    CollectionReference getLink = FirebaseFirestore.getInstance().collection("Attendance");
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());
    String getYear = formattedDate.substring(7, 11);
    String getMonth = formattedDate.substring(3, 6);
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AttendanceAdapter adapter = null;
    WaiversAdapter waiversAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button1 = findViewById(R.id.save_at);
        setContentView(R.layout.common_rv);
        AttendanceAdapter.saveRoll.clear();
        /* TextView title = (TextView) findViewById(R.id.titleTextView); */

        recyclerView = findViewById(R.id.rv);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        /* title.setText(clas); */
        size = AttendanceAdapter.to_update_Total.size();

        btnChoosen = getDataFromIntent();
        Log.wtf("TAG" , btnChoosen);
        if(btnChoosen.contains("1")) {
            adapter = new AttendanceAdapter(this, showdata);
            recyclerView.setAdapter(adapter);

            if (clas.contains("Bsc")) {
                new bscExcelSheet().execute();
            } else if ((clas.contains("BMS-1")) || (clas.contains("BMS-2"))) {
                new bmsExcelSheet().execute();
            } else if (clas.contains("BMS-3F")) {
                new bmsMixExcelSheet().execute();
            } else if (clas.contains("BMS-3M")) {
                new bms3MExcelSheet().execute();
            } else if ((clas.contains("BFIA-1")) || (clas.contains("BFIA-2"))) {
                new bfiaExcelSheet().execute();
            } else if (clas.contains("BFIA-3")) {
                new bfiaMixExcelSheet().execute();
            }

            adapter.notifyDataSetChanged();
            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.stu_toolbar);
            toolbar.setTitle(clas);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            bar = findViewById(R.id.list_progress_bar);
            tv = findViewById(R.id.loading_lists);
            adapter.notifyDataSetChanged();
        }
        else if(btnChoosen.contains("2")){
            waiversAdapter = new WaiversAdapter(this, shwdata);
            recyclerView.setAdapter(adapter);

            if (clas.contains("Bsc")) {
                new bscExcelSheet().execute();
            } else if ((clas.contains("BMS-1")) || (clas.contains("BMS-2"))) {
                new bmsExcelSheet().execute();
            } else if (clas.contains("BMS-3F")) {
                new bmsMixExcelSheet().execute();
            } else if (clas.contains("BMS-3M")) {
                new bms3MExcelSheet().execute();
            } else if ((clas.contains("BFIA-1")) || (clas.contains("BFIA-2"))) {
                new bfiaExcelSheet().execute();
            } else if (clas.contains("BFIA-3")) {
                new bfiaMixExcelSheet().execute();
            }

            waiversAdapter.notifyDataSetChanged();
            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.stu_toolbar);
            toolbar.setTitle(clas);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            bar = findViewById(R.id.list_progress_bar);
            tv = findViewById(R.id.loading_lists);
            waiversAdapter.notifyDataSetChanged();
        }
    }

    private String getDataFromIntent() {
        Intent data = getIntent();
        clas = data.getStringExtra("class");
        sub = data.getStringExtra("subject");
        type = data.getStringExtra("type");
        btnChoosen = data.getStringExtra("btnChoosen");
        Log.wtf(TAG, "ok"+type + btnChoosen);
        return btnChoosen;
    }

    private void switch_to_main() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        AttendanceAdapter.saveRoll.clear();
        startActivity(intent);
        finish();
    }

    public void save(View view){

        init();
        int i=0;
        if (type.contains("Lab")){
            while (i < AttendanceAdapter.saveRoll.size()){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance")
                        .child(clas).child(AttendanceAdapter.saveRoll.get(i)).child(getYear).child(sub + " - Lab").child(getMonth);
                updateAtt(reference);
                i++;
            }
        } else if (type.contains("Tute")){
            while (i < AttendanceAdapter.saveRoll.size()){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance")
                        .child(clas).child(AttendanceAdapter.saveRoll.get(i)).child(getYear).child(sub + " - Tute").child(getMonth);
                updateAtt(reference);
                i++;
            }
        } else if (type.contains("Theory")) {
            while (i < AttendanceAdapter.saveRoll.size()){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance")
                        .child(clas).child(AttendanceAdapter.saveRoll.get(i)).child(getYear).child(sub).child(getMonth);
                updateAtt(reference);
                i++;
            }
        }

//        switch_to_main();
    }

    public void updateAtt(DatabaseReference reference){
        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.child("attendance").getValue() == null){
                    mutableData.child("attendance").setValue(1);
                } else {
                    int current = Integer.valueOf((String.valueOf(mutableData.child("attendance").getValue())));
                    mutableData.child("attendance").setValue(++current);
                }
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    public void init(){
        int i=0;
        if (type.contains("Lab")){
            while (i < AttendanceAdapter.to_update_Total.size()){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance")
                        .child(clas).child(AttendanceAdapter.to_update_Total.get(i)).child(getYear).child(sub + " - Lab").child(getMonth);
                initAttendance(reference);
                i++;
            }
        } else if (type.contains("Tute")){
            while (i < AttendanceAdapter.to_update_Total.size()){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance")
                        .child(clas).child(AttendanceAdapter.to_update_Total.get(i)).child(getYear).child(sub + " - Tute").child(getMonth);
                initAttendance(reference);
                i++;
            }
        } else if (type.contains("Theory")) {
            while (i < AttendanceAdapter.to_update_Total.size()){
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance")
                        .child(clas).child(AttendanceAdapter.to_update_Total.get(i)).child(getYear).child(sub).child(getMonth);
                initAttendance(reference);
                i++;
            }
        }
    }

    public void initAttendance(DatabaseReference reference){
        Log.i(TAG, "Init");
        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.child("total").getValue() == null){
                    mutableData.child("total").setValue(1);
                } else {
                    int current = Integer.valueOf((String.valueOf(mutableData.child("total").getValue())));
                    mutableData.child("total").setValue(++current);
                }
                return Transaction.success(mutableData);
            }
            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
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

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");
                    reference.child(clas).child(roll_no).child("Name").setValue(name);

                    if (type.contains("Lab-G1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else if (type.contains("Lab-G2")) {
                        if (grp.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                        AttendanceAdapter.saveRoll.add(roll_no);
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

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");
                    reference.child(clas).child(roll_no).child("Name").setValue(name);

                    if (type.contains("Lab-G1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else if (type.contains("Lab-G2")) {
                        if (grp.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else if (type.contains("Tute-G1")) {
                        if (tute.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }

                    } else if (type.contains("Tute-G2")) {
                        if (tute.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else if (type.contains("Tute-G3")) {
                        if (tute.equals("3")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                        AttendanceAdapter.saveRoll.add(roll_no);
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
            if(btnChoosen.contains("1"))
            adapter.notifyDataSetChanged();
            else
                waiversAdapter.notifyDataSetChanged();
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

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");
                    reference.child(clas).child(roll_no).child("Name").setValue(name);

                    if (type.contains("Lab-G1")) {
                        if (grp.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else if (type.contains("Lab-G2")) {
                        if (grp.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else if (type.contains("Tute-G1")) {
                        if (tute.equals("1")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }

                    } else if (type.contains("Tute-G2")) {
                        if (tute.equals("2")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else if (type.contains("Tute-G3")) {
                        if (tute.equals("3")) {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
                        }
                    } else {
                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                        showdata.add(dataClass);
                        AttendanceAdapter.saveRoll.add(roll_no);
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

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");
                        reference.child(clas).child(roll_no).child("Name").setValue(name);

                        if (type.contains("Lab-G1")) {
                            if (grp.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else if (type.contains("Lab-G2")) {
                            if (grp.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else if (type.contains("Tute-G1")) {
                            if (tute.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }

                        } else if (type.contains("Tute-G2")) {
                            if (tute.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else if (type.contains("Tute-G3")) {
                            if (tute.equals("3")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
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

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");
                            reference.child(clas).child(roll_no).child("Name").setValue(name);


                            if (type.contains("Lab-G1")) {
                                Log.i(TAG, "Yes-1");
                                if (grp.equals("1")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }
                            } else if (type.contains(" Lab-G2")) {
                                Log.i(TAG, "Yes-2");
                                if (grp.equals("2")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }
                            } else if (type.contains(" Tute-G1")) {
                                Log.i(TAG, "Yes");
                                if (tute.equals("1")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }

                            } else if (type.contains(" Tute-G2")) {
                                Log.i(TAG, "Yes-3");
                                if (tute.equals("2")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }
                            } else if (type.contains(" Tute-G3")) {
                                Log.i(TAG, "Yes-4");
                                if (tute.equals("3")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }
                            } else {
                                Log.i(TAG, "no");
                                if (sub1.equals(getType) || sub2.equals(getType) || getType.equals("0")) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                    AttendanceAdapter.saveRoll.add(roll_no);
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

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");
                        reference.child(clas).child(roll_no).child("Name").setValue(name);

                        if (type.contains("Lab-G1")) {
                            if (grp.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else if (type.contains("Lab-G2")) {
                            if (grp.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else if (type.contains("Tute-G1")) {
                            if (tute.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }

                        } else if (type.contains("Tute-G2")) {
                            if (tute.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else if (type.contains("Tute-G3")) {
                            if (tute.equals("3")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
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

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");
                            reference.child(clas).child(roll_no).child("Name").setValue(name);


                            if (type.contains("Lab-G1")) {
                                Log.i(TAG, "Yes-1");
                                if (grp.equals("1")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }
                            } else if (type.contains(" Lab-G2")) {
                                Log.i(TAG, "Yes-2");
                                if (grp.equals("2")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }
                            } else if (type.contains(" Tute-G1")) {
                                Log.i(TAG, "Yes");
                                if (tute_mix.equals("1")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }

                            } else if (type.contains(" Tute-G2")) {
                                Log.i(TAG, "Yes-3");
                                if (tute_mix.equals("2")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }
                            } else if (type.contains(" Tute-G3")) {
                                Log.i(TAG, "Yes-4");
                                if (tute_mix.equals("3")) {
                                    if (sub1.equals(getType) || sub2.equals(getType) ) {
                                        AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                        showdata.add(dataClass);
                                        AttendanceAdapter.saveRoll.add(roll_no);
                                    }
                                }
                            } else {
                                Log.i(TAG, "no");
                                if (sub1.equals(getType) || sub2.equals(getType) || getType.equals("0")) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                    AttendanceAdapter.saveRoll.add(roll_no);
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

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");
                        reference.child(clas).child(roll_no).child("Name").setValue(name);

                        if (type.contains("Tute-G1")) {
                            if (tute.equals("1")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }

                        } else if (type.contains("Tute-G2")) {
                            if (tute.equals("2")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else if (type.contains("Tute-G3")) {
                            if (tute.equals("3")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
                            }
                        } else {
                            AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                            showdata.add(dataClass);
                            AttendanceAdapter.saveRoll.add(roll_no);
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
                                    AttendanceAdapter.saveRoll.add(roll_no);
                                }
                            }

                        } else if (type.contains(" Tute-G2")) {
                            Log.i(TAG, "Yes-3");
                            if (tute.equals("2")) {
                                if (sub1.equals(getType) ) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                    AttendanceAdapter.saveRoll.add(roll_no);
                                }
                            }
                        } else if (type.contains(" Tute-G3")) {
                            Log.i(TAG, "Yes-4");
                            if (tute.equals("3")) {
                                if (sub1.equals(getType) ) {
                                    AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                    showdata.add(dataClass);
                                    AttendanceAdapter.saveRoll.add(roll_no);
                                }
                            }
                        } else {
                            Log.i(TAG, "no");
                            if (sub1.equals(getType)  || getType.equals("0")) {
                                AttendanceDataClass dataClass = new AttendanceDataClass(name, roll_no);
                                showdata.add(dataClass);
                                AttendanceAdapter.saveRoll.add(roll_no);
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