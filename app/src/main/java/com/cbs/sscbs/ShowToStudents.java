package com.cbs.sscbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cbs.sscbs.Adapters.StudentsAdapter;
import com.cbs.sscbs.Attendance.StudentsDataClass;
import com.cbs.sscbs.Fragments.Attendance_Frag;

import java.util.ArrayList;

public class ShowToStudents extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentsAdapter studentsAdapter;
    ArrayList<StudentsDataClass> filelist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_to_students);

//       ArrayList<StudentsDataClass> filelist = new ArrayList<>();
//       StudentsDataClass dataClass = new StudentsDataClass("1", (double) 1);
//       filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);
//        filelist.add(dataClass);



        recyclerView = findViewById(R.id.showsturview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        studentsAdapter = new StudentsAdapter(this  , Attendance_Frag.allSub);
        recyclerView.setAdapter(studentsAdapter);
        studentsAdapter.notifyDataSetChanged();
    }
}
