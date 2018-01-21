package com.cbs.sscbs.Others;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.cbs.sscbs.Adapters.BookingsAdapter;
import com.cbs.sscbs.DataClass.BookingsDataClass;
import com.cbs.sscbs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Bookings extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    ImageView imageView ;
    int count, i=1;

    RecyclerView recyclerView;

    public ArrayList<BookingsDataClass> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_bookings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.bookingRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final BookingsAdapter adapter = new BookingsAdapter(getApplicationContext(), data);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();


        Intent intent = getIntent();
//        int child = intent.getIntExtra("childCount", 0);
        for (int i=1; i<=2; i++) {
            DatabaseReference venue = database.getReference("EventThings").child(String.valueOf(i)).child("venue");
            final DatabaseReference time = database.getReference("EventThings").child(String.valueOf(i)).child("time");

            venue.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String v = dataSnapshot.getValue(String.class);
                    Log.wtf(TAG,v);

                    time.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String t = dataSnapshot.getValue(String.class);
//                             String a = t.substring(18, 26);
//                             String b = t.substring(26, 30);
//                             String time = a + " to " + b;

                            Toast.makeText(Bookings.this, t, Toast.LENGTH_LONG).show();
//                            BookingsDataClass newData = new BookingsDataClass(v, time);
//                            data.add(newData);
//                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


    }

}
