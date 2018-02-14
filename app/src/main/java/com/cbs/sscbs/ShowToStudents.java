package com.cbs.sscbs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cbs.sscbs.Adapters.StudentsAdapter;
import com.cbs.sscbs.Attendance.StudentsDataClass;

import java.util.ArrayList;

public class ShowToStudents extends AppCompatActivity {

    ArrayList<StudentsDataClass> filelist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_to_students);

//        ArrayList<StudentsDataClass> filelist =(ArrayList<StudentsDataClass>)getIntent().getSerializableExtra("allSubjects");

        Log.wtf("TAG" , filelist.toString());
        RecyclerView recyclerView = findViewById(R.id.showsturview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        StudentsAdapter studentsAdapter = new StudentsAdapter(this  ,filelist);
        recyclerView.setAdapter(studentsAdapter);

        studentsAdapter.notifyDataSetChanged();
    }
}
