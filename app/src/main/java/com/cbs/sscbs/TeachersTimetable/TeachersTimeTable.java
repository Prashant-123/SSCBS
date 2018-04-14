package com.cbs.sscbs.TeachersTimetable;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cbs.sscbs.Fragments.Home_frag;
import com.cbs.sscbs.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class TeachersTimeTable extends AppCompatActivity {
    private FirebaseDatabase database;
    TeacherDataClass teacherDataClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_time_table);
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

        final SearchView sv = findViewById(R.id.mSearch);
        RecyclerView rv = findViewById(R.id.myRecycler);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        final TeacherAdapter adapter = new TeacherAdapter(this, Home_frag.data);
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

}