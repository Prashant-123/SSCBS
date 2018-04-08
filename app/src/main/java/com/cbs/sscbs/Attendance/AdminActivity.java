package com.cbs.sscbs.Attendance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cbs.sscbs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Attendance/Bsc-2/16501/2018/Algorithms/Apr");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button uploadClassList = findViewById(R.id.uploadClassList);
        Button uploadBscClassList = findViewById(R.id.uploadBscClassList);
        Button uploadBfiaClassList = findViewById(R.id.uploadBfiaClassList);
        Button uploadBmsClassList = findViewById(R.id.uploadBmsClassList);

        final Map<String, Object> map = new HashMap<>();
        map.put("attendance", 25);


        uploadClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                database.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Log.wtf("TAG", String.valueOf(dataSnapshot.child("name").getValue()));
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//                database.child("Attendance").child("Bsc-2").child("16501").child("2018").child("Algorithms").child("Apr").updateChildren(map);
                database.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        if (mutableData.child("attendance").getValue() == null){
                            Log.i("TAG", "no");
                            mutableData.child("attendance").setValue("1");
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
//                database.child("Attendance").child("Bsc-2").child("16501").child("2018").child("Algorithms").child("Apr").child("total")


            }
        });

//        uploadBscClassList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new uploadBscList().execute();
//                Log.i(TAG, "ok");
////                db.collection("Attendance").document("Bsc")
////                        .delete()
////                        .addOnSuccessListener(new OnSuccessListener<Void>() {
////                            @Override
////                            public void onSuccess(Void aVoid) {
////                                Log.i("TAG", "DocumentSnapshot successfully deleted!");
////                            }
////                        })
////                        .addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                Log.i("TAG", "Error deleting document", e);
////                            }
////                        });
//            }
////                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
////                alertDialog.setTitle(" Confirm Upload ");
////                alertDialog.setMessage("Are you sure you want upload new lists ?");
////                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog,int which) {
//////                        new uploadBscList().execute();
////
////                        d
////
//////                        default_map2.put("attendance", 0);
//////                        default_map2.put("total", 0);
//////
//////                        db.collection("Attendance").document("Bsc-1").collection("Students")
//////                                .document("16527").collection("Year").document(getYear).collection("Subjects").document("Subject")
//////                                .collection("Months").document(getMonth).set(default_map2);
////
////                        Toast.makeText(AdminActivity.this, "List Uploaded", Toast.LENGTH_LONG).show();
////                    }
////                });
////                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int which) {
////                        dialog.cancel();
////                    }
////                });
////                alertDialog.show();
////            }
//        });
//
//        uploadBfiaClassList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
//                alertDialog.setTitle(" Confirm Upload ");
//                alertDialog.setMessage("Are you sure you want upload new lists ?");
//                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int which) {
//                        new uploadBfiaList().execute();
//                        new uploadBfiaMixList().execute();
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
//        });
//
//        uploadBmsClassList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminActivity.this);
//                alertDialog.setTitle(" Confirm Upload ");
//                alertDialog.setMessage("Are you sure you want upload new lists ?");
//                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int which) {
//                        // new uploadBmsList().execute();
//                        new uploadBmsMixList().execute();
//                        //new uploadBms3MList().execute();
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
//        });
//    }
//
//    public static class uploadBscList extends AsyncTask<Void , Void , Void>{
//
//
//        @Override
//        protected void onPreExecute(){
//            Log.wtf("TAG", "onPreExecute");
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try{
//
//                Log.i(TAG, "bsc-try");
//                HttpHandler httpHandler = new HttpHandler();
//                String bscStudentsList = httpHandler.makeServiceCall(bscSheet);
//                String bscSubjectsList = httpHandler.makeServiceCall(SUBURL);
//
//                JSONObject studentsListObject = new JSONObject(bscStudentsList);
//                JSONObject subjectsListObject = new JSONObject(bscSubjectsList);
//
//                JSONArray studentsListArray = studentsListObject.getJSONArray("Bsc-1");
//                JSONArray subjectsListArray = subjectsListObject.getJSONArray("Bsc-1");
//
//                for(int i = 0 ; i < studentsListArray.length() ; i++){
//                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
//                    String stu_name = jsonObject.getString("Name");
//                    String stu_roll = jsonObject.getString("Roll_No");
//
//                    default_map1.put("Name" , stu_name);
//                    db.collection("Attendance").document("Bsc-1").collection("Students").document(stu_roll).set(default_map1);
//
//                    for(int j = 0 ; j < subjectsListArray.length(); j++){
//
//                        JSONObject  jsonObject1 = subjectsListArray.getJSONObject(j);
//                        String subject = jsonObject1.getString("Semester_A");
//
//                        db.collection("Attendance").document("Bsc-1").collection("Students")
//                                .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject).set(default_map3);
//
//                        default_map2.put("attendance", 0);
//                        default_map2.put("total", 0);
//                        String [] months = theMonth();
//
//                        for(int k = 1 ; k< months.length;k++) {
//
//                            db.collection("Attendance").document("Bsc-1").collection("Students")
//                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
//                                    .collection("Months").document(getMonth).set(default_map2);
//                        }
//                    }
//                }
//
//
//            } catch (Exception ex) {
//                Log.wtf("TAG", "Nope");
//            }
//            return null;
//        }
//    }
//
//    public static class uploadBfiaList extends AsyncTask<Void , Void , Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            Log.wtf("TAG", "ok  ");
//
//            try{
//                HttpHandler httpHandler = new HttpHandler();
//                String bfiaStudentsList = httpHandler.makeServiceCall(bfiaURL);
//                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);
//
//                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
//                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);
//
//                JSONArray studentsListArray = studentsListObject.getJSONArray("BFIA-2B");
//                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BFIA-2B");
//
//                Log.wtf("TAG", "ok  "+studentsListArray.length());
//
//                for(int i = 0 ; i < studentsListArray.length() ; i++){
//                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
//                    String stu_name = jsonObject.getString("Name");
//                    String stu_roll = jsonObject.getString("Roll_No");
//
//                    default_map1.put("Name" , stu_name);
//                    db.collection("Attendance").document("BFIA-2B").collection("Students").document(stu_roll).set(default_map1);
//
//                    for(int j = 0 ; j < subjectsListArray.length(); j++){
//                        JSONObject  jsonObject1 = subjectsListArray.getJSONObject(j);
//                        String subject = jsonObject1.getString("Semester_A");
//
//                        db.collection("Attendance").document("BFIA-2B").collection("Students")
//                                .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
//                                .document(subject).set(default_map3);
//
//                        default_map2.put("attendance", 0);
//                        default_map2.put("total", 0);
//                        String [] months = theMonth();
//                        Log.i("TAG", getYear + stu_name + stu_roll + subject);
//
//                        for(int k = 1 ; k< months.length;k++) {
//
//                            db.collection("Attendance").document("BFIA-2B").collection("Students")
//                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
//                                    .collection("Months").document(months[k]).set(default_map2);
//                            Log.i("TAG", getYear + stu_name + stu_roll + subject);
//                        }
//                    }
//                }
//
//            } catch (Exception ex) {
//
//                Log.i("TAG", "no");
////                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
//    }
//
//    public static class uploadBfiaMixList extends AsyncTask<Void , Void , Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try{
//                HttpHandler httpHandler = new HttpHandler();
//                String bfiaStudentsList = httpHandler.makeServiceCall(bfiaURL);
//                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);
//
//                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
//                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);
//
//                JSONArray studentsListArray = studentsListObject.getJSONArray("BFIA-3B");
//                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BFIA-3B");
//
//                Log.wtf("TAG", "ok  "+studentsListArray.length());
//
//                for(int i = 0 ; i < studentsListArray.length() ; i++){
//                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
//                    String stu_name = jsonObject.getString("Name");
//                    String stu_roll = jsonObject.getString("Roll_No");
//                    String sub1 = jsonObject.getString("Sub_Type_1");
//                    String sub2 = jsonObject.getString("Sub_Type_2");
//
//                    default_map1.put("Name" , stu_name);
//                    db.collection("Attendance").document("BFIA-3B").collection("Students").document(stu_roll).set(default_map1);
//
//                    for(int j = 0 ; j < subjectsListArray.length(); j++) {
//                        JSONObject jsonObject1 = subjectsListArray.getJSONObject(j);
//                        String subject = jsonObject1.getString("Semester_A");
//                        String type = jsonObject1.getString("Sub");
//
//                        if (type == sub1 || type == sub2 || type.toString().equals(String.valueOf(("0")))) {
//                            db.collection("Attendance").document("BFIA-3B").collection("Students")
//                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
//                                    .document(subject).set(default_map3);
//
//                            default_map2.put("attendance", 0);
//                            default_map2.put("total", 0);
//
//                            String [] months = theMonth();
//
//                            for(int k = 1 ; k< months.length;k++) {
//
//                                db.collection("Attendance").document("BFIA-3B").collection("Students")
//                                        .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
//                                        .collection("Months").document(months[k]).set(default_map2);
//                            }
//                        }
//                    }
//                }
//
//            } catch (Exception ex) {
////                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
//    }
//
//    public static class uploadBmsList extends AsyncTask<Void , Void , Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            Log.wtf("TAG", "ok");
//
//            try{
//                HttpHandler httpHandler = new HttpHandler();
//                String bfiaStudentsList = httpHandler.makeServiceCall(bmsLIST);
//                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);
//
//                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
//                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);
//
//                JSONArray studentsListArray = studentsListObject.getJSONArray("BMS-2B");
//                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BMS-2B");
//
//                Log.wtf("TAG", "ok  "+studentsListArray.length());
//
//                for(int i = 0 ; i < studentsListArray.length() ; i++){
//                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
//                    String stu_name = jsonObject.getString("Name");
//                    String stu_roll = jsonObject.getString("Roll_No");
//
//                    default_map1.put("Name" , stu_name);
//                    db.collection("Attendance").document("BMS-2B").collection("Students").document(stu_roll).set(default_map1);
//
//                    for(int j = 0 ; j < subjectsListArray.length(); j++){
//                        JSONObject  jsonObject1 = subjectsListArray.getJSONObject(j);
//                        String subject = jsonObject1.getString("Semester_A");
//
//                        db.collection("Attendance").document("BMS-2B").collection("Students")
//                                .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
//                                .document(subject).set(default_map3);
//
//                        default_map2.put("attendance", 0);
//                        default_map2.put("total", 0);
//                        String [] months = theMonth();
//
//                        for(int k = 1 ; k< months.length;k++) {
//
//                            db.collection("Attendance").document("BMS-2B").collection("Students")
//                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
//                                    .collection("Months").document(getMonth).set(default_map2);
//                        }
//                    }
//                }
//
//            } catch (Exception ex) {
////                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
//    }
//
//    public static class uploadBmsMixList extends AsyncTask<Void , Void , Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try{
//                HttpHandler httpHandler = new HttpHandler();
//                String bfiaStudentsList = httpHandler.makeServiceCall(bmsLIST);
//                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);
//
//                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
//                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);
//
//                JSONArray studentsListArray = studentsListObject.getJSONArray("BMS-3FC");
//                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BMS-3FC");
//
//                Log.wtf("TAG", "ok  "+studentsListArray.length());
//
//                for(int i = 0 ; i < studentsListArray.length() ; i++){
//                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
//                    String stu_name = jsonObject.getString("Name");
//                    String stu_roll = jsonObject.getString("Roll_No");
//                    String sub1 = jsonObject.getString("Sub_Type_1");
//                    String sub2 = jsonObject.getString("Sub_Type_2");
//
//                    default_map1.put("Name" , stu_name);
//                    db.collection("Attendance").document("BMS-3FC").collection("Students").document(stu_roll).set(default_map1);
//
//                    for(int j = 0 ; j < subjectsListArray.length(); j++) {
//                        JSONObject jsonObject1 = subjectsListArray.getJSONObject(j);
//                        String subject = jsonObject1.getString("Semester_A");
//                        String type = jsonObject1.getString("Sub");
//
//                        if (type == sub1 || type == sub2 || type.toString().equals(String.valueOf(("0")))) {
//                            db.collection("Attendance").document("BMS-3FC").collection("Students")
//                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
//                                    .document(subject).set(default_map3);
//
//                            default_map2.put("attendance", 0);
//                            default_map2.put("total", 0);
//
//                            String [] months = theMonth();
//
//                            for(int k = 1 ; k< months.length;k++) {
//
//                                db.collection("Attendance").document("BMS-3FC").collection("Students")
//                                        .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
//                                        .collection("Months").document(months[k]).set(default_map2);
//                            }
//                        }
//                    }
//                }
//
//            } catch (Exception ex) {
////                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
//    }
//
//    public static class uploadBms3MList extends AsyncTask<Void , Void , Void>{
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try{
//                HttpHandler httpHandler = new HttpHandler();
//                String bfiaStudentsList = httpHandler.makeServiceCall(bmsLIST);
//                String bfiaSubjectsList = httpHandler.makeServiceCall(SUBURL);
//
//                JSONObject studentsListObject = new JSONObject(bfiaStudentsList);
//                JSONObject subjectsListObject = new JSONObject(bfiaSubjectsList);
//
//                JSONArray studentsListArray = studentsListObject.getJSONArray("BMS-3M");
//                JSONArray subjectsListArray = subjectsListObject.getJSONArray("BMS-3M");
//
//                for(int i = 0 ; i < studentsListArray.length() ; i++){
//                    JSONObject jsonObject = studentsListArray.getJSONObject(i);
//                    String stu_name = jsonObject.getString("Name");
//                    String stu_roll = jsonObject.getString("Roll_No");
//                    String sub1 = jsonObject.getString("Sub_Type_1");
//
//                    default_map1.put("Name" , stu_name);
//                    db.collection("Attendance").document("BMS-3M").collection("Students").document(stu_roll).set(default_map1);
//
//                    for(int j = 0 ; j < subjectsListArray.length(); j++) {
//                        JSONObject jsonObject1 = subjectsListArray.getJSONObject(j);
//                        String subject = jsonObject1.getString("Semester_A");
//                        String type = jsonObject1.getString("Sub");
//
//                        if (type == sub1 || type.toString().equals(String.valueOf(("0")))) {
//                            db.collection("Attendance").document("BMS-3M").collection("Students")
//                                    .document(stu_roll).collection("Year").document(getYear).collection("Subjects")
//                                    .document(subject).set(default_map3);
//
//                            default_map2.put("attendance", 0);
//                            default_map2.put("total", 0);
//
//                            String [] months = theMonth();
//
//                            for(int k = 1 ; k< months.length;k++) {
//
//                                db.collection("Attendance").document("BMS-3M").collection("Students")
//                                        .document(stu_roll).collection("Year").document(getYear).collection("Subjects").document(subject)
//                                        .collection("Months").document(months[k]).set(default_map2);
//                            }
//                        }
//                    }
//                }
//
//            } catch (Exception ex) {
////                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
    }

    public static String[] theMonth() {
        return new String[]{"Select Month", "Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }
}
