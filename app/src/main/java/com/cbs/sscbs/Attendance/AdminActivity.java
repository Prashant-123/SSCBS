package com.cbs.sscbs.Attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private static final String bscSheet = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    private static final String bfiaURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1iZWNSlHipbkLyYhtdUPqZdXaq9enLrzUTPxOipxCiDc";
    private static final String bmsLIST = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=18_YyZhOv3me5QWWPn_ByF_IPiSgvDYcq-W3RfQxkHvQ";
    private static final String CLASSURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=16WP-U687v4q2MtsJbHM-yCkqQK856tJ5IkiYvgowe90";
    private static final String bms3 = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1x61Klq4PLhwXpZwzeMbax3tlgANRJ8b0q2g_J-1D1DA";
    private static final String SUBURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1ztpTfrOZ-Ntehx01ab5jRNqQa96cvqbDcDS0nPekVDI";
    static Calendar c = Calendar.getInstance();

    private static final String TAG = "TAG";

    static SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    static String formattedDate = df.format(c.getTime());
    static String getYear = formattedDate.substring(7, 11);
    static String getMonth = formattedDate.substring(3, 6);
    static Map<String, Object> default_map1 = new HashMap<>();
    static Map<String, Object> default_map2 = new HashMap<>();
    static Map<String, Object> default_map3 = new HashMap<>();
    static Map<String, Object> default_map4 = new HashMap<>();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        default_map3.put("field" ,"");

        Button uploadClassList = findViewById(R.id.uploadClassList);
        Button uploadBscClassList = findViewById(R.id.uploadBscClassList);
        Button uploadBfiaClassList = findViewById(R.id.uploadBfiaClassList);
        Button uploadBmsClassList = findViewById(R.id.uploadBmsClassList);

        uploadClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UploadLists.class);
                startActivity(intent);
            }
        });


        uploadBscClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new uploadBscList().execute();
                Log.i(TAG, "ok");
//                db.collection("Attendance").document("Bsc")
//                        .delete()
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.i("TAG", "DocumentSnapshot successfully deleted!");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.i("TAG", "Error deleting document", e);
//                            }
//                        });
            }
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
//                alertDialog.setTitle(" Confirm Upload ");
//                alertDialog.setMessage("Are you sure you want upload new lists ?");
//                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int which) {
////                        new uploadBscList().execute();
//
//                        d
//
////                        default_map2.put("attendance", 0);
////                        default_map2.put("total", 0);
////
////                        db.collection("Attendance").document("Bsc-1").collection("Students")
////                                .document("16527").collection("Year").document(getYear).collection("Subjects").document("Subject")
////                                .collection("Months").document(getMonth).set(default_map2);
//
//                        Toast.makeText(AdminActivity.this, "List Uploaded", Toast.LENGTH_LONG).show();
//                    }
//                });
//                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                alertDialog.show();
//            }
        });

        uploadBfiaClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setTitle(" Confirm Upload ");
                alertDialog.setMessage("Are you sure you want upload new lists ?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        new uploadBfiaList().execute();
                       // new uploadBfiaMixList().execute();
                        Toast.makeText(AdminActivity.this, "List Uploaded", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        uploadBmsClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
                alertDialog.setTitle(" Confirm Upload ");
                alertDialog.setMessage("Are you sure you want upload new lists ?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        // new uploadBmsList().execute();
                        new uploadBmsMixList().execute();
                        Toast.makeText(AdminActivity.this, "List Uploaded", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    public static class uploadBscList extends AsyncTask<Void , Void , Void>{


        @Override
        protected void onPreExecute(){
            Log.wtf("TAG", "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                Log.i(TAG, "bsc-try");
                HttpHandler httpHandler = new HttpHandler();
                String bscStudentsList = httpHandler.makeServiceCall(bscSheet);
                String bscSubjectsList = httpHandler.makeServiceCall(SUBURL);

                JSONObject studentsListObject = new JSONObject(bscStudentsList);
                JSONObject subjectsListObject = new JSONObject(bscSubjectsList);

                JSONArray studentsListArray = studentsListObject.getJSONArray("Bsc-1");
                JSONArray subjectsListArray = subjectsListObject.getJSONArray("Bsc-1");

                for(int i = 0 ; i < studentsListArray.length() ; i++){
                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
                    String stu_name = jsonObject.getString("Name");
                    String stu_roll = jsonObject.getString("Roll_No");

                    default_map1.put("Name" , stu_name);
                    db.collection("Attendance").document("Bsc-1").collection("Students").document(stu_roll).set(default_map1);

                    for(int j = 0 ; j < subjectsListArray.length(); j++){

                        JSONObject  jsonObject1 = subjectsListArray.getJSONObject(j);
                        String subject = jsonObject1.getString("Semester_A");

                        db.collection("Attendance").document("Bsc-1").collection("Students")
                                .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject).set(default_map3);

                        default_map2.put("attendance", 0);
                        default_map2.put("total", 0);
                        String [] months = theMonth();

                        for(int k = 1 ; k< months.length;k++) {

                            db.collection("Attendance").document("Bsc-1").collection("Students")
                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
                                    .collection("Months").document(getMonth).set(default_map2);
                        }
                    }
                }


            } catch (Exception ex) {
                Log.wtf("TAG", "Nope");
            }
            return null;
        }
    }

    public static class uploadBfiaList extends AsyncTask<Void , Void , Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            Log.wtf("TAG", "ok  ");

            try{
                HttpHandler httpHandler = new HttpHandler();
                String bfiaStudentsList = httpHandler.makeServiceCall(bfiaURL);
                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);

                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);

                JSONArray studentsListArray = studentsListObject.getJSONArray("BFIA-2A");
                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BFIA-2A");

                Log.wtf("TAG", "ok  "+studentsListArray.length());

                for(int i = 0 ; i < studentsListArray.length() ; i++){
                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
                    String stu_name = jsonObject.getString("Name");
                    String stu_roll = jsonObject.getString("Roll_No");

                    default_map1.put("Name" , stu_name);
                    db.collection("Attendance").document("BFIA-2A").collection("Students").document(stu_roll).set(default_map1);

                    for(int j = 0 ; j < subjectsListArray.length(); j++){
                        JSONObject  jsonObject1 = subjectsListArray.getJSONObject(j);
                        String subject = jsonObject1.getString("Semester_A");

                        db.collection("Attendance").document("BFIA-2A").collection("Students")
                                .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
                                .document(subject).set(default_map3);

                        default_map2.put("attendance", 0);
                        default_map2.put("total", 0);
                        String [] months = theMonth();
                        Log.i("TAG", getYear + stu_name + stu_roll + subject);

                        for(int k = 1 ; k< months.length;k++) {

                            db.collection("Attendance").document("BFIA-2A").collection("Students")
                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
                                    .collection("Months").document(months[k]).set(default_map2);
                            Log.i("TAG", getYear + stu_name + stu_roll + subject);
                        }
                    }
                }

            } catch (Exception ex) {

                Log.i("TAG", "no");
//                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

    public static class uploadBfiaMixList extends AsyncTask<Void , Void , Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                HttpHandler httpHandler = new HttpHandler();
                String bfiaStudentsList = httpHandler.makeServiceCall(bfiaURL);
                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);

                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);

                JSONArray studentsListArray = studentsListObject.getJSONArray("BFIA-3B");
                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BFIA-3B");

                Log.wtf("TAG", "ok  "+studentsListArray.length());

                for(int i = 0 ; i < studentsListArray.length() ; i++){
                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
                    String stu_name = jsonObject.getString("Name");
                    String stu_roll = jsonObject.getString("Roll_No");
                    String sub1 = jsonObject.getString("Sub_Type_1");
                    String sub2 = jsonObject.getString("Sub_Type_2");

                    default_map1.put("Name" , stu_name);
                    db.collection("Attendance").document("BFIA-3B").collection("Students").document(stu_roll).set(default_map1);

                    for(int j = 0 ; j < subjectsListArray.length(); j++) {
                        JSONObject jsonObject1 = subjectsListArray.getJSONObject(j);
                        String subject = jsonObject1.getString("Semester_A");
                        String type = jsonObject1.getString("Sub");

                        if (type == sub1 || type == sub2 || type.toString().equals(String.valueOf(("0")))) {
                            db.collection("Attendance").document("BFIA-3B").collection("Students")
                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
                                    .document(subject).set(default_map3);

                            default_map2.put("attendance", 0);
                            default_map2.put("total", 0);

                            String [] months = theMonth();

                            for(int k = 1 ; k< months.length;k++) {

                                db.collection("Attendance").document("BFIA-3B").collection("Students")
                                        .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
                                        .collection("Months").document(months[k]).set(default_map2);
                            }
                        }
                    }
                }

            } catch (Exception ex) {
//                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

    public static class uploadBmsList extends AsyncTask<Void , Void , Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Log.wtf("TAG", "ok");

            try{
                HttpHandler httpHandler = new HttpHandler();
                String bfiaStudentsList = httpHandler.makeServiceCall(bmsLIST);
                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);

                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);

                JSONArray studentsListArray = studentsListObject.getJSONArray("BMS-2B");
                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BMS-2B");

                Log.wtf("TAG", "ok  "+studentsListArray.length());

                for(int i = 0 ; i < studentsListArray.length() ; i++){
                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
                    String stu_name = jsonObject.getString("Name");
                    String stu_roll = jsonObject.getString("Roll_No");

                    default_map1.put("Name" , stu_name);
                    db.collection("Attendance").document("BMS-2B").collection("Students").document(stu_roll).set(default_map1);

                    for(int j = 0 ; j < subjectsListArray.length(); j++){
                        JSONObject  jsonObject1 = subjectsListArray.getJSONObject(j);
                        String subject = jsonObject1.getString("Semester_A");

                        db.collection("Attendance").document("BMS-2B").collection("Students")
                                .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
                                .document(subject).set(default_map3);

                        default_map2.put("attendance", 0);
                        default_map2.put("total", 0);
                        String [] months = theMonth();

                        for(int k = 1 ; k< months.length;k++) {

                            db.collection("Attendance").document("BMS-2B").collection("Students")
                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
                                    .collection("Months").document(getMonth).set(default_map2);
                        }
                    }
                }

            } catch (Exception ex) {
//                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

    public static class uploadBmsMixList extends AsyncTask<Void , Void , Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                HttpHandler httpHandler = new HttpHandler();
                String bfiaStudentsList = httpHandler.makeServiceCall(bmsLIST);
                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);

                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);

                JSONArray studentsListArray = studentsListObject.getJSONArray("BMS-3FA");
                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BMS-3FA");

                Log.wtf("TAG", "ok  "+studentsListArray.length());

                for(int i = 0 ; i < studentsListArray.length() ; i++){
                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
                    String stu_name = jsonObject.getString("Name");
                    String stu_roll = jsonObject.getString("Roll_No");
                    String sub1 = jsonObject.getString("Sub_Type_1");
                    String sub2 = jsonObject.getString("Sub_Type_2");

                    default_map1.put("Name" , stu_name);
                    db.collection("Attendance").document("BMS-3FA").collection("Students").document(stu_roll).set(default_map1);

                    for(int j = 0 ; j < subjectsListArray.length(); j++) {
                        JSONObject jsonObject1 = subjectsListArray.getJSONObject(j);
                        String subject = jsonObject1.getString("Semester_A");
                        String type = jsonObject1.getString("Sub");

                        if (type == sub1 || type == sub2 || type.toString().equals(String.valueOf(("0")))) {
                            db.collection("Attendance").document("BMS-3FA").collection("Students")
                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
                                    .document(subject).set(default_map3);

                            default_map2.put("attendance", 0);
                            default_map2.put("total", 0);

                            String [] months = theMonth();

                            for(int k = 1 ; k< months.length;k++) {

                                db.collection("Attendance").document("BMS-3FA").collection("Students")
                                        .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
                                        .collection("Months").document(months[k]).set(default_map2);
                            }
                        }
                    }
                }

            } catch (Exception ex) {
//                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

    public class uploadListFromExcel extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
//                String jsonStr1 = sh.makeServiceCall(CLASSURL);
                String jsonStr = sh.makeServiceCall(bfiaURL);
                String jsonStr2 = sh.makeServiceCall(SUBURL);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray contacts = object.getJSONArray("BFIA 3A");
                JSONObject object2 = new JSONObject(jsonStr2);
                JSONArray contacts2 = object2.getJSONArray("BFIA 3A");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String sub1 = c.getString("Sub_Type_1");
                    String sub2 = c.getString("Sub_Type_2");
                   // String core = c.getString("Sub_Type_3");
                    default_map1.put("name", name);
                    db.collection("Attendance").document("BFIA 3A").collection("Students")
                            .document(roll_no).set(default_map1);

                    for (int j = 0; j < contacts2.length(); j++) {
                        JSONObject c2 = contacts2.getJSONObject(j);
                        String sub = c2.getString("Semester_A");
                        String type = c2.getString("Sub");

                        if(type == sub1 || type == sub2 || type.toString().equals(String.valueOf(( "0")))) {

                            db.collection("Attendance").document("BFIA 3A").collection("Students")
                                    .document(roll_no).collection("Year").document(getYear).collection("Subjects").document(sub).set(default_map3);

                            default_map2.put("attendance", 0);
                            default_map2.put("total", 0);

                            db.collection("Attendance").document("BFIA 3A").collection("Students")
                                    .document(roll_no).collection("Year").document(getYear).collection("Subjects").document(sub)
                                    .collection("Months").document(getMonth).set(default_map2);
                        }
                    }
                }

            } catch (Exception ex) {
                Log.e("TAG", "getListFromExcel", ex);
                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

    public static String[] theMonth() {
        return new String[]{"Select Month", "Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }
}
