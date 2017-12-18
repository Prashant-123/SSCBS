package com.cbs.sscbs;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cbs.sscbs.Fragments.TimeTable_frag;
import com.cbs.sscbs.utils.BottomNavigationViewHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Events extends AppCompatActivity {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    ImageView imageView;

    int count, i = 1;
    public ArrayList<DataClass> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_events);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final EventsAdapter adapter = new EventsAdapter(this, data);
        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();
        databaseRef.child("EventThings").orderByChild("sot").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                i++;
                count = (int) dataSnapshot.getChildrenCount();
                DataClass newData = dataSnapshot.getValue(DataClass.class);
                if (newData.getOrganiser().toString().compareTo("Blitz") == 0) {
                    newData.setImg(R.drawable.icon);
                } else {
                    newData.setImg(R.drawable.contact_logo);
                }
//                String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
//                imageView = (ImageView) findViewById(R.id.eventImage);
//                Picasso.with(getApplicationContext()).load(imageUri).into(imageView);
                data.add(newData);
                adapter.notifyDataSetChanged();
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

//        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        navigationView.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);

    }

//    private BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener = new
//            BottomNavigationView.OnNavigationItemReselectedListener() {
//                @Override
//                public void onNavigationItemReselected(@NonNull MenuItem item) {
//                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    switch ((item.getItemId())) {
//                        case R.id.ic_timetable:
//                            fragmentTransaction.replace(R.id.main_Frame, new TimeTable_frag).commit();
//                            break;
//                        default:
//                            Toast.makeText(getApplicationContext() ,"Hi",Toast.LENGTH_SHORT).show(); ;
//                    }
//                }
//            }

    public void login(View view)
    {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.fragment_login, null);
        final EditText username = alertLayout.findViewById(R.id.User);
        final EditText password = alertLayout.findViewById(R.id.Pass);
        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);

        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                 else
                    password.setInputType(129);
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Sign-In");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Log-In Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Log In", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                    DocumentReference mDocRef = FirebaseFirestore.getInstance().document("username/society");
                Log.i("tag", "Log-1");
                    mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            Log.i("tag", "Log-2");
                            if (documentSnapshot.exists()) {

                                Log.i("tag", "Log-3");
                                String u = documentSnapshot.getString(USERNAME);
                                String p = documentSnapshot.getString(PASSWORD);
                                if (username.getText().toString().equals(u)&& password.getText().toString().equals(p))
                                {
                                    Toast.makeText(Events.this, "Authentication Successfull", Toast.LENGTH_SHORT).show();
                                    createEvent();
                                }
                                else
                                    Toast.makeText(Events.this, "You Lost it :)", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void createEvent()
    {
        Intent intent = new Intent(this, CreateEvent.class);
        intent.putExtra("COUNT", i);
        startActivity(intent);
    }




}
