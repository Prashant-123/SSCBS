package com.cbs.sscbs.Others;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cbs.sscbs.Attendance.StudentsRecord;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanya on 1/21/2018.
 */


public class StoreData extends AppCompatActivity {
    private static final String TAG = "TAG";
    Map<String, Object> default_map = new HashMap<>();
    FirebaseFirestore stu = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stu.collection("Students").document("Bsc-II").set(default_map);
//        readBscIIStudents();
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

    }

    private List<StudentsRecord> stuRecordList = new ArrayList<>();
//    private void readBscIIStudents() {
//       InputStream is = getResources().openRawResource(R.raw.bsc_ii_classlist);
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
//                stu.collection("Students").document("Bsc-II").collection("StudentsList").document(tokens[1]).set(city);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
