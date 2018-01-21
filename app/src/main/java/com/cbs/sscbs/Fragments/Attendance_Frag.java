package com.cbs.sscbs.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cbs.sscbs.Attendance.Bsc2Subjects;
import com.cbs.sscbs.Attendance.ClassListRecord;
import com.cbs.sscbs.Attendance.ClassListTypeRecord;
import com.cbs.sscbs.Attendance.StudentsRecord;
import com.cbs.sscbs.Attendance.TeacherCourseDetails;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

//    static String TAG = "TAG";
//    Calendar c = Calendar.getInstance();
//    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//    String formattedDate = df.format(c.getTime());
//    String getMonth = formattedDate.substring(3, 6);
//
//    Map<String, Object> default_map = new HashMap<>();
//
//    String months = "Months";
//    String date = "Date";
//    String classes = "Class";
//    String classType = "Class-Type";
//    String groups = "Groups";
//    String subjects = "Subjects";
//    String students = "Students";
//
//    CollectionReference years_list = FirebaseFirestore.getInstance().collection("Year");
//    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//    ArrayList<String> classList = new ArrayList<>();
//    ArrayList<String> teachersList = new ArrayList<>();
//    ArrayList<String> yearsList = new ArrayList<>();
//    ArrayList<String> classesList = new ArrayList<>();
//
//    String name ;
//    FirebaseFirestore day = FirebaseFirestore.getInstance();
//
//    private List<ClassListRecord> classRecordList = new ArrayList<>();
//    private List<Bsc2Subjects> bsc2SubjectsList = new ArrayList<>();
//    private List<ClassListTypeRecord> classTypeRecordList = new ArrayList<>();

    FirebaseFirestore stu = FirebaseFirestore.getInstance();

    static String TAG = "TAG";
    Map<String, Object> default_map = new HashMap<>();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String name ;
    ArrayList<String> teachersList = new ArrayList<>();
    int f = 0 ;
    CollectionReference teachers_list = FirebaseFirestore.getInstance().collection("Teachers");

    public Attendance_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
        RelativeLayout relativeLayout = (RelativeLayout) myView.findViewById(R.id.faculty);
        default_map.put("default", "");
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Faculty");
                final LayoutInflater inflater = getLayoutInflater();


                //----------------------------------Setting up Firebase---------------------------------

                teachers_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                teachersList.add(documentSnapshot.getId());
                            }
                        } else
                            Log.wtf(TAG, "Error getting teachers list");
                    }
                });

                for(int i = 0 ; i < teachersList.size();i++)
                    Log.wtf(TAG,teachersList.get(i));

//                years_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                yearsList.add(documentSnapshot.getId());
//                            }
//                        } else
//                            Log.wtf(TAG, "Error getting academic session");
//                    }
//                });

                View alertLayout = inflater.inflate(R.layout.verify, null);
                final EditText faculty_name = alertLayout.findViewById(R.id.faculty_verify);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Verify Credentials");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Verification Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });



                alert.setPositiveButton("Verify", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (currentUser != null)
                            name = currentUser.getDisplayName();

                        for (int i = 0; i < teachersList.size(); i++) {
                            if (teachersList.get(i).equals(faculty_name.getText().toString())) {
                                Log.wtf(TAG, "Done");
                                f = 1;
                                Intent intent = new Intent(getContext(),TeacherCourseDetails.class);
                                intent.putExtra("teacherName",faculty_name.getText().toString());
                                startActivity(intent);
                                break;
                            }
                        }
//                        if(f==0)
//                            Toast.makeText(getContext(), "Please check your credentials again", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

//
//                View alertLayout = inflater.inflate(R.layout.verify, null);
//                final EditText faculty_name = alertLayout.findViewById(R.id.faculty_verify);
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                alert.setTitle("Verify Credentials");
//                alert.setView(alertLayout);
//                alert.setCancelable(false);
//                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getContext(), "Verification Cancelled", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
                //Get the list of all classes.
                stu.collection("Students").document("Bsc-II")
                        .set(default_map);

        readBscIIStudents();

        CollectionReference courses = FirebaseFirestore.getInstance().collection("Students").document("Bsc-II").collection("StudentsList");
        courses.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        Log.wtf(TAG,documentSnapshot.getId());
                    }
                }
            }
        });
//                class_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                classesList.add(documentSnapshot.getId());
//                                showClass();
//                            }
//                        } else
//                            Log.wtf(TAG, "Error getting classes record");
//                    }
//                });
//
//                //---------------------------------------------------------------------------------------
//
//                alert.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (currentUser != null)
//                            name = currentUser.getDisplayName();
//
//                        for (int i = 0; i < teachersList.size(); i++) {
//                            if (teachersList.get(i).equals(faculty_name.getText().toString())) {
//                                Log.wtf(TAG, "Done");
//                                showClass();
//                            } else {
//                            }
//                        }
//                    }
//                });
//                AlertDialog dialog = alert.create();
//                dialog.show();
//            }
//        });

        return myView;
    }

    private List<StudentsRecord> stuRecordList = new ArrayList<>();
    private void readBscIIStudents() {
        InputStream is = getResources().openRawResource(R.raw.bsc_ii_classlist);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        String line;
        try {
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");

                Log.wtf(TAG,tokens[1]);
                StudentsRecord stuRecord = new StudentsRecord();
                stuRecord.setName(tokens[1]);

                stuRecordList.add(stuRecord);

                Map<String, Object> city = new HashMap<>();
                city.put("name", tokens[1]);
                city.put("roll", tokens[0]);
                city.put("group", "");
                for(int i = 2 ; i <=6 ;i++) {
                    stu.collection("Students").document("Bsc-II")
                            .collection("StudentsList").document(tokens[1])
                            .collection("Subjects").document(tokens[i]).set(default_map);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
//    private void readClassList() {
//
//        InputStream is = getResources().openRawResource(R.raw.classlist);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//
//        String line;
//        try {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//
//                Log.i(TAG, "Line: " + line);
//                String[] tokens = line.split(",");
//
//                //int roll = Integer.parseInt(tokens[1]);
//
//                ClassListRecord classListRecord = new ClassListRecord();
//                classListRecord.setClasses(tokens[0]);
//
//                Log.wtf(TAG, tokens[0]);
//
//                classRecordList.add(classListRecord);
//
//                Map<String, Object> city = new HashMap<>();
//                city.put("name", "Los Angeles");
//                city.put("state", "CA");
//                city.put("country", "USA");
//                day.collection("Year").document("2015-16")
//                        .collection(months).document(getMonth).collection(date).document(formattedDate)
//                        .collection(classes).document(tokens[0]).set(city);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void showClass() {
//
//        final ArrayList<String> classesType = new ArrayList<>();
//        new MaterialDialog.Builder(getContext())
//                .title("Select Your Class")
//                .items(classesList)
//                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {
//
//                        //Get the type of class - lab,Theory.
//                        day.collection("Year").document("2015-16")
//                                .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                .collection(classes).document(classesList.get(which)).set(default_map);
//
//                        readClassListType(which);
//
//                        CollectionReference class_Type = FirebaseFirestore.getInstance().collection("Year").document("2015-16")
//                                .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                .collection(classes).document(classesList.get(which)).collection(classType);
//
//                        class_Type.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                        classesType.add(documentSnapshot.getId());
//
//                                    }
//                                } else
//                                    Log.wtf(TAG, "Error getting classes record");
//
//                                new MaterialDialog.Builder(getContext())
//                                        .title("Select Your Class Type")
//                                        .items(classesType)
//                                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
//                                            @Override
//                                            public boolean onSelection(MaterialDialog dialog, View view, final int whichClassType,
//                                                                       CharSequence text) {
//
//                                                if (whichClassType == 0) {
//
//                                                    //Get the group no.
//                                                    day.collection("Year").document("2015-16")
//                                                            .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                                            .collection(classes).document(classesList.get(which))
//                                                            .collection(classType).document(classesType.get(whichClassType))
//                                                            .collection(groups).document("Group-1").set(default_map);
//
//                                                    CollectionReference grp_Type = FirebaseFirestore.getInstance()
//                                                            .collection("Year").document("2015-16")
//                                                            .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                                            .collection(classes).document(classesList.get(which))
//                                                            .collection(classType).document(classesType.get(whichClassType))
//                                                            .collection(groups);
//
//                                                    final ArrayList<String> grpList = new ArrayList<>();
//                                                    grp_Type.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                            if (task.isSuccessful()) {
//                                                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                                                    Log.wtf(TAG, "asf" + documentSnapshot.getId().toString());
//                                                                    grpList.add(documentSnapshot.getId());
//                                                                    new MaterialDialog.Builder(getContext())
//                                                                            .title("Select Your Group ")
//                                                                            .items(grpList)
//                                                                            .itemsCallbackSingleChoice(-1,
//                                                                                    new MaterialDialog.ListCallbackSingleChoice() {
//                                                                                        @Override
//                                                                                        public boolean onSelection(MaterialDialog dialog, View view,
//                                                                                                                   final int whichClassType, CharSequence text) {
//
//                                                                                            //Get the respective subjects of the class .
//                                                                                            day.collection("Year").document("2015-16")
//                                                                                                    .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                                                                                    .collection(classes).document(classesList.get(which))
//                                                                                                    .collection(classType).document(classesType.get(whichClassType))
//                                                                                                    .collection(groups).document("Group-1")
//                                                                                                    .set(default_map);
//
//                                                                                            final ArrayList<String> bsc2Sub = new ArrayList<>();
//
//                                                                                            readBSc2Subjects(which,whichClassType);
//
//                                                                                            CollectionReference sub_type = FirebaseFirestore.getInstance()
//                                                                                                    .collection("Year").document("2015-16")
//                                                                                                    .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                                                                                    .collection(classes).document(classesList.get(which)).collection(classType)
//                                                                                                    .document(classesType.get(whichClassType))
//                                                                                                    .collection(groups).document("Group-1").collection(subjects);
//
//
//                                                                                            sub_type.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                                                                @Override
//                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                                                                    if (task.isSuccessful()) {
//                                                                                                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                                                                                            bsc2Sub.add(documentSnapshot.getId());
//                                                                                                            new MaterialDialog.Builder(getContext())
//                                                                                                                    .title("Select Your Subjects")
//                                                                                                                    .items(bsc2Sub)
//                                                                                                                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
//                                                                                                                        @Override
//                                                                                                                        public boolean onSelection(MaterialDialog dialog, View view, final int whichSubType,
//                                                                                                                                                   CharSequence text) {
//
//                                                                                                                            //Get the students of the class.
//                                                                                                                            final ArrayList<String> studentsRecords = new ArrayList<>();
//                                                                                                                            day.collection("Year").document("2015-16")
//                                                                                                                                    .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                                                                                                                    .collection(classes).document(classesList.get(which))
//                                                                                                                                    .collection(classType).document(classesType.get(whichClassType))
//                                                                                                                                    .collection(groups).document("Group-1")
//                                                                                                                                    .collection(subjects).document(bsc2Sub.get(whichSubType))
//                                                                                                                                    .set(default_map);
//
//                                                                                                                            getStudentsList(which,whichClassType,whichSubType,bsc2Sub);
//
//                                                                                                                            CollectionReference studentsList = FirebaseFirestore.getInstance()
//                                                                                                                                    .collection("Year").document("2015-16")
//                                                                                                                                    .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                                                                                                                    .collection(classes).document(classesList.get(which)).collection(classType)
//                                                                                                                                    .document(classesType.get(whichClassType))
//                                                                                                                                    .collection(groups).document("Group-1")
//                                                                                                                                    .collection(subjects).document(bsc2Sub.get(whichSubType)).collection(students);
//
//                                                                                                                            studentsList.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                                                                                                                @Override
//                                                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                                                                                                    if (task.isSuccessful()) {
//                                                                                                                                        for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                                                                                                                            studentsRecords.add(documentSnapshot.getId());
//                                                                                                                                        }
//                                                                                                                                    } else
//                                                                                                                                        Log.wtf(TAG, "Error getting classes record");
//
//                                                                                                                                    for(int i = 0 ; i<studentsRecords.size();i++){
//                                                                                                                                        Log.wtf(TAG,studentsRecords.get(i));
//                                                                                                                                    }
//                                                                                                                                }
//                                                                                                                            });
//
//                                                                                                                            return true;
//                                                                                                                        }
//                                                                                                                    }).show();
//
//                                                                                                        }
//                                                                                                    } else
//                                                                                                        Log.wtf(TAG, "Error getting classes record");
//                                                                                                }
//                                                                                            });
//                                                                                            return true;
//                                                                                        }
//                                                                                    }).show();
//                                                                }
//                                                            } else
//                                                                Log.wtf(TAG, "Error getting classes record");
//                                                        }
//                                                    });
//                                                } else {
//
//                                                    day.collection("Year").document("2015-16")
//                                                            .collection(months).document(getMonth).collection(date).document(formattedDate)
//                                                            .collection(classes).document(classesList.get(which))
//                                                            .collection(classType).document(classesType.get(whichClassType)).set(default_map);
//
//                                                }
//
//                                                return true;
//                                            }
//                                        }).show();
//
//                            }
//                        });
//                        return true;
//                    }
//                })
//                .show();
//    }
//
//    private List<StudentsRecord> studentsList = new ArrayList<>();
//    private void getStudentsList(int which, int whichClassType, int whichSubType,ArrayList<String> bs2Sub) {
//
//        InputStream is = getResources().openRawResource(R.raw.cl);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//
//        String line;
//        try {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//
//                Log.i(TAG, "Line: " + line);
//                String[] tokens = line.split(",");
//
//                int roll = Integer.parseInt(tokens[1]);
//
//                StudentsRecord studentsRecord = new StudentsRecord();
//                studentsRecord.setName(tokens[0]);
//                studentsRecord.setRollno(roll);
//
//                Log.wtf(TAG, tokens[0]);
//
//                studentsList.add(studentsRecord);
//
//                day.collection("Year").document("2015-16")
//                        .collection(months).document(getMonth).collection(date).document(formattedDate)
//                        .collection(classes).document(classesList.get(which))
//                        .collection(classType).document("Lab")
//                        .collection(groups).document("Group-1")
//                        .collection(subjects).document(bs2Sub.get(whichSubType))
//                        .collection(students).document(tokens[0]).set(default_map);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void readBSc2Subjects(int which, int whichClassType) {
//
//        InputStream is = getResources().openRawResource(R.raw.bsc2_subjects);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//
//        String line;
//        try {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//
//                Log.i(TAG, "Line: " + line);
//                String[] tokens = line.split(",");
//
//                //int roll = Integer.parseInt(tokens[1]);
//
//                Bsc2Subjects bsc2Subjects = new Bsc2Subjects();
//                bsc2Subjects.setbsc2Subjects(tokens[0]);
//
//                Log.wtf(TAG, tokens[0]);
//
//                bsc2SubjectsList.add(bsc2Subjects);
//
//                day.collection("Year").document("2015-16")
//                        .collection(months).document(getMonth).collection(date).document(formattedDate)
//                        .collection(classes).document(classesList.get(which))
//                        .collection(classType).document("Lab")
//                        .collection(groups).document("Group-1")
//                        .collection(subjects).document(tokens[0]).set(default_map);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void readClassListType(int which) {
//
//        InputStream is = getResources().openRawResource(R.raw.classlisttypes);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//
//        String line;
//        try {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//
//                Log.i(TAG, "Line: " + line);
//                String[] tokens = line.split(",");
//
//                //int roll = Integer.parseInt(tokens[1]);
//
//                ClassListTypeRecord classListTypeRecord = new ClassListTypeRecord();
//                classListTypeRecord.setClassType(tokens[0]);
//
//                Log.wtf(TAG, tokens[0]);
//
//                classTypeRecordList.add(classListTypeRecord);
//
//                day.collection("Year").document("2015-16")
//                        .collection(months).document(getMonth).collection(date).document(formattedDate)
//                        .collection(classes).document(classesList.get(which)).collection(classType).document(tokens[0]).set(default_map);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
