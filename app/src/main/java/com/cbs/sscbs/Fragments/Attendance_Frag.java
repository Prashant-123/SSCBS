package com.cbs.sscbs.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbs.sscbs.R;
import com.google.firebase.firestore.FirebaseFirestore;

import am.appwise.components.ni.NoInternetDialog;

/**
 * Created by Prashant on 09-01-2018.
 */

public class Attendance_Frag extends android.support.v4.app.Fragment {

    FirebaseFirestore stu = FirebaseFirestore.getInstance();
    static String TAG = "TAG";
    NoInternetDialog noInternetDialog;
//    Map<String, Object> default_map = new HashMap<>();
//    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//    String name ;
//    ArrayList<String> teachersList = new ArrayList<>();
//    int f = 0 ;
//    CollectionReference teachers_list = FirebaseFirestore.getInstance().collection("Teachers");

    public Attendance_Frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.attendence_fragment, container, false);
//        default_map.put("default", "");
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "Faculty");
//                final LayoutInflater inflater = getLayoutInflater();
//
//
//                //----------------------------------Setting up Firebase---------------------------------
//
//                teachers_list.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                teachersList.add(documentSnapshot.getId());
//                            }
//                        } else
//                            Log.wtf(TAG, "Error getting teachers list");
//                    }
//                });
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
//                                f = 1;
//                                Intent intent = new Intent(getContext(),TeacherCourseDetails.class);
//                                intent.putExtra("teacherName",faculty_name.getText().toString());
//                                startActivity(intent);
//                                break;
//                            }
//                        }
//                    }
//                });
//                AlertDialog dialog = alert.create();
//                dialog.show();
//            }
//        });
//
//                stu.collection("Students").document("Bsc-II")
//                        .set(default_map);
//
//        readBscIIStudents();
//
//        CollectionReference courses = FirebaseFirestore.getInstance().collection("Students").document("Bsc-II").collection("StudentsList");
//        courses.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(DocumentSnapshot documentSnapshot : task.getResult()){
//                        Log.wtf(TAG,documentSnapshot.getId());
//                    }
//                }
//            }
//        });
        return myView;
    }

//    private List<StudentsRecord> stuRecordList = new ArrayList<>();
//    private void readBscIIStudents() {
//        InputStream is = getResources().openRawResource(R.raw.bsc_ii_classlist);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//
//        String line;
//        try {
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] tokens = line.split(",");
//
//                Log.wtf(TAG,tokens[1]);
//                StudentsRecord stuRecord = new StudentsRecord();
//                stuRecord.setName(tokens[1]);
//
//                stuRecordList.add(stuRecord);
//
//                Map<String, Object> city = new HashMap<>();
//                city.put("name", tokens[1]);
//                city.put("roll", tokens[0]);
//                city.put("group", "");
//                for(int i = 2 ; i <=6 ;i++) {
//                    stu.collection("Students").document("Bsc-II")
//                            .collection("StudentsList").document(tokens[1])
//                            .collection("Subjects").document(tokens[i]).set(default_map);
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
