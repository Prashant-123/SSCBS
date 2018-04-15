package com.cbs.sscbs.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cbs.sscbs.Events.EventsAdapter;
import com.cbs.sscbs.Events.DataClass;
import com.cbs.sscbs.Others.MainActivity;
import com.cbs.sscbs.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Tanya on 1/26/2018.
 */

public class GuestEventsActivity extends AppCompatActivity {

    public ArrayList<DataClass> data = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    DataClass newData;
    com.github.clans.fab.FloatingActionButton fab;

    int count, i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Toolbar toolbar = findViewById(R.id.toolbar_events);
        toolbar.setTitle("Upcoming Events");
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        fab = findViewById(R.id.addEventButton);
        fab.setVisibility(View.GONE);
        RecyclerView rv = findViewById(R.id.rView);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        final EventsAdapter adapter = new EventsAdapter(this, data);
        rv.setAdapter(adapter);
        final ProgressBar bar = findViewById(R.id.event_progress_bar);
        final TextView tv = findViewById(R.id.loading_events);

        bar.setVisibility(View.VISIBLE);


        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();


        databaseRef.child("EventThings").orderByChild("sot").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                i++;
                count = (int) dataSnapshot.getChildrenCount();
                newData = dataSnapshot.getValue(DataClass.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(newData).setImg(R.drawable.logo);
                }
                data.add(newData);
                adapter.notifyDataSetChanged();
                bar.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                DataClass p0 = dataSnapshot.getValue(DataClass.class);
                for (int i = 0; i < data.size(); i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (data.get(i).getDelId() == Objects.requireNonNull(p0).getDelId()) {
                            data.remove(i);
                            adapter.notifyItemRemoved(i);
                            adapter.notifyItemRangeChanged(i, data.size());
                        }
                    }
                }
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
