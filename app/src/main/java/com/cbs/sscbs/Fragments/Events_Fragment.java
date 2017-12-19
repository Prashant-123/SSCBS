package com.cbs.sscbs.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cbs.sscbs.CreateEvent;
import com.cbs.sscbs.DataClass;
import com.cbs.sscbs.EventsAdapter;
import com.cbs.sscbs.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Created by Prashant on 18-12-2017.
 */

public class Events_Fragment extends Fragment{

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    ImageView imageView ;
    int count, i=1;

    public ArrayList<DataClass> data = new ArrayList<>();

    public Events_Fragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.activity_events, container, false);

        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final EventsAdapter adapter = new EventsAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);


        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();
        databaseRef.child("EventThings").orderByChild("sot").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                i++;
                count = (int) dataSnapshot.getChildrenCount();
                DataClass newData = dataSnapshot.getValue(DataClass.class);
                //newData.setDelId(i);
                if(newData.getOrganiser().toString().compareTo("Blitz")==0)
                {newData.setImg(R.drawable.about);}
                else
                { newData.setImg(R.drawable.contact_logo); }
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
//                Toast.makeText(getContext(), "Event-Deleted", Toast.LENGTH_SHORT).show();
//                Intent intent = getIntent();
                Intent intent = getActivity().getIntent();
                int recyclerCtr = intent.getIntExtra("ctr", 0);
                data.remove(recyclerCtr);
                adapter.notifyItemRemoved(recyclerCtr);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        FloatingActionButton fb = (FloatingActionButton) myView.findViewById(R.id.addEventButton);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
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

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Sign-In");
                alert.setView(alertLayout);
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Log-In Cancelled", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(getContext(), "Authentication Successfull", Toast.LENGTH_SHORT).show();
                                        createEvent();
                                    }
                                    else
                                        Toast.makeText(getContext(), "You Lost it :)", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        return myView;

    }


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

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Sign-In");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Log-In Cancelled", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getContext(), "Authentication Successfull", Toast.LENGTH_SHORT).show();
                                createEvent();
                            }
                            else
                                Toast.makeText(getContext(), "You Lost it :)", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(getContext(), CreateEvent.class);
        intent.putExtra("COUNT", i);
        startActivity(intent);
    }

}
