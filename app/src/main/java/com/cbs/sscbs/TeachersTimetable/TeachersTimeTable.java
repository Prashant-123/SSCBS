package com.cbs.sscbs.TeachersTimetable;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cbs.sscbs.Fragments.Home_frag;
import com.cbs.sscbs.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TeachersTimeTable extends AppCompatActivity {

    public static ProgressBar bar;
    private FirebaseDatabase database;
    TeacherAdapter adapter;
    public static ArrayList<TeacherDataClass> data = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_time_table);

        getTT();

        Toolbar toolbar = findViewById(R.id.toolbar_tt);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        bar = findViewById(R.id.dayProgressBar);
        bar.setVisibility(View.VISIBLE);
        final SearchView sv = findViewById(R.id.mSearch);
        RecyclerView rv = findViewById(R.id.myRecycler);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
         adapter = new TeacherAdapter(this, data);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.setIconified(false);
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }

    public void getTT(){

        data.clear();
//        final String day = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
          final String day = "Wednesday";
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("TeacherTimeTable");
        databaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot p0, String s) {
                if (p0!=null && p0.hasChild("name") && p0.hasChild("timetable")) {
                        TeacherDataClass teacher_data = new TeacherDataClass(
                        p0.child("name").getValue().toString(),p0.child("timetable").getValue().toString(),
                                p0.child(day+"/09:00am-10:00am/title").getValue().toString(),p0.child(day+"/10:00am-11:00am/title").getValue().toString(),
                                p0.child(day+"/11:00am-12:00pm/title").getValue().toString(),p0.child(day+"/12:00pm-01:00pm/title").getValue().toString(),
                                p0.child(day+"/01:00pm-02:00pm/title").getValue().toString(),p0.child(day+"/02:00pm-03:00pm/title").getValue().toString(),
                                p0.child(day+"/03:00pm-04:00pm/title").getValue().toString(),p0.child(day+"/04:00pm-05:00pm/title").getValue().toString(),
                                p0.child(day+"/09:00am-10:00am/subj").getValue().toString(), p0.child(day+"/10:00am-11:00am/subj").getValue().toString(),
                                p0.child(day+"/11:00am-12:00pm/subj").getValue().toString(), p0.child(day+"/12:00pm-01:00pm/subj").getValue().toString(),
                                p0.child(day+"/01:00pm-02:00pm/subj").getValue().toString(), p0.child(day+"/02:00pm-03:00pm/subj").getValue().toString(),
                                p0.child(day+"/03:00pm-04:00pm/subj").getValue().toString(), p0.child(day+"/04:00pm-05:00pm/subj").getValue().toString());
                        TeachersTimeTable.data.add(teacher_data);
                        adapter.notifyDataSetChanged();
                        bar.setVisibility(View.GONE);
                } else
                    Log.wtf("TAG", "Nope");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


