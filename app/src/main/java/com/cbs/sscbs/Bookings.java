package com.cbs.sscbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Bookings extends AppCompatActivity {

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

        BookingsDataClass newData1 = new BookingsDataClass("1", "2");
        BookingsDataClass newData2 = new BookingsDataClass("1", "2");
        BookingsDataClass newData3 = new BookingsDataClass("1", "2");
        data.add(newData1);
        data.add(newData2);
        data.add(newData3);
        adapter.notifyDataSetChanged();

//        database = FirebaseDatabase.getInstance();
//        databaseRef = database.getReference();
//
//
//        Intent intent = getIntent();
//        int child = intent.getIntExtra("childCount", 0);
//        for (int i=0; i<child; i++) {
//            DatabaseReference venue = database.getReference("EventThings").child(String.valueOf(i)).child("venue");
//            DatabaseReference time = database.getReference("EventThings").child(String.valueOf(i)).child("time");
//
//            venue.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    String v = dataSnapshot.getValue(String.class);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    System.out.println("The read failed: " + databaseError.getCode());
//                }
//            });
//
//            time.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    String t = dataSnapshot.getValue(String.class);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    System.out.println("The read failed: " + databaseError.getCode());
//                }
//            });
//
//
//        }
//

    }

}
