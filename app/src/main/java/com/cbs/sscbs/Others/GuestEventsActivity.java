package com.cbs.sscbs.Others;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.cbs.sscbs.Adapters.EventsAdapter;
import com.cbs.sscbs.DataClass.DataClass;
import com.cbs.sscbs.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Tanya on 1/26/2018.
 */

public class GuestEventsActivity extends AppCompatActivity {

    public ArrayList<DataClass> data = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    DataClass newData;
    int count, i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fb = (FloatingActionButton) findViewById(R.id.addEventButton);
        fb.setVisibility(View.INVISIBLE);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rView) ;

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        final EventsAdapter adapter = new EventsAdapter(this, data);
        rv.setAdapter(adapter);
        final ProgressBar bar = (ProgressBar) findViewById(R.id.event_progress_bar);

        bar.setVisibility(View.VISIBLE);


        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();


        databaseRef.child("EventThings").orderByChild("sot").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                i++;
                count = (int) dataSnapshot.getChildrenCount();
                newData = dataSnapshot.getValue(DataClass.class);

                if (newData.getOrganiser().toString().compareToIgnoreCase("Blitz") == 0) {
                    newData.setImg(R.drawable.blitzlogo);
                } else if(newData.getOrganiser().toString().compareToIgnoreCase("Anthropos") == 0){
                    newData.setImg(R.drawable.anthroposlogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("Cbsmun") == 0){
                    newData.setImg(R.drawable.cbsmunlogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("cdc") == 0){
                    newData.setImg(R.drawable.cdclogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("Communique") == 0){
                    newData.setImg(R.drawable.communiquelogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("Dhwani") == 0){
                    newData.setImg(R.drawable.dhwanilogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("ecovision") == 0){
                    newData.setImg(R.drawable.ecovisionlogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("enactus") == 0){
                    newData.setImg(R.drawable.enactuslogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("grandeur") == 0){
                    newData.setImg(R.drawable.grandeurlogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("kartavya") == 0){
                    newData.setImg(R.drawable.kartavyalogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("kriti") == 0){
                    newData.setImg(R.drawable.kritilogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("markit") == 0){
                    newData.setImg(R.drawable.markitlogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("nucleus") == 0){
                    newData.setImg(R.drawable.nucleuslogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("synergy") == 0){
                    newData.setImg(R.drawable.synergylogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("verve") == 0){
                    newData.setImg(R.drawable.vervelogo);
                }
                else if(newData.getOrganiser().toString().compareToIgnoreCase("yuva") == 0){
                    newData.setImg(R.drawable.yuvalogo);
                }else {
                    newData.setImg(R.drawable.otherslogo);
                }
                data.add(newData);
                adapter.notifyDataSetChanged();
                bar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                DataClass p0 = dataSnapshot.getValue(DataClass.class);
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getDelId() == p0.getDelId()) {
                        data.remove(i);
                        adapter.notifyItemRemoved(i);
                        adapter.notifyItemRangeChanged(i, data.size());
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
