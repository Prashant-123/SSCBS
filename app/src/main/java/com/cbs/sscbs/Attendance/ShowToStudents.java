package com.cbs.sscbs.Attendance;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.cbs.sscbs.Fragments.Attendance_Frag;
import com.cbs.sscbs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ShowToStudents extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentsAdapter studentsAdapter;
    TextView textView , setRollText, monthText, yearText;
    Button showMonthTotal;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_rv);
        recyclerView = findViewById(R.id.st_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        studentsAdapter = new StudentsAdapter(this  ,Attendance_Frag.allSub);


        recyclerView.setAdapter(studentsAdapter);

        textView = findViewById(R.id.r_name);
        monthText = findViewById(R.id.monthText);
        yearText = findViewById(R.id.yearText);
        setRollText = findViewById(R.id.roll_text);
        showMonthTotal = findViewById(R.id.monthlyTotalBtn);

        if (Attendance_Frag.allSub.size() == 0)
            Log.i("TAG", "myList");

        yearText.setText("Year: " + getIntent().getStringExtra("year"));
        monthText.setText("Month: " + getIntent().getStringExtra("month"));
        setRollText.setText(getIntent().getStringExtra("roll"));

        reference.child(getIntent().getStringExtra("class"))
                .child(getIntent().getStringExtra("roll")).child("Name")
                .addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        textView.setText(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        studentsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Attendance_Frag.allSub.clear();
    }
}