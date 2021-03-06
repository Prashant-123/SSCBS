package com.cbs.sscbs.Events;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbs.sscbs.R;
import com.cbs.sscbs.utils.ItemClickListener;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.hoang8f.widget.FButton;

/**
 * Created by Prashant on 25-11-2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    View alertLayout1;
    private List<DataClass> objectList;
    private LayoutInflater inflater;
    ImageView imageView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public EventsAdapter(Context context, List<DataClass> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.events_sample_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final DataClass current = objectList.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener itemClickListener;
        ImageView img;
        TextView title, date, organiser, venue;
        DataClass currentObject;
        FButton readmore;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.date = itemView.findViewById(R.id.date);
            this.organiser = itemView.findViewById(R.id.organiser);
            this.venue = itemView.findViewById(R.id.venue);
            this.img = itemView.findViewById(R.id.eventImage);
            this.readmore = itemView.findViewById(R.id.read);
        }

        public void setData(final DataClass currentObject, int position) {
            this.title.setText(currentObject.getTitle());
            this.date.setText(currentObject.getTime());
            this.organiser.setText(currentObject.getOrganiser());
            this.venue.setText(currentObject.getVenue());
            this.img.setImageResource((currentObject.getImg()));
            this.currentObject = currentObject;
            this.readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inflateDescription(itemView.getContext(), String.valueOf(currentObject.getDelId()),
                            String.valueOf(currentObject.getImageUrl()));
                }
            });
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }
    }

    public void inflateDescription(final Context c, String pos, final String url) {
        LayoutInflater inflater = LayoutInflater.from(c);
        alertLayout1 = inflater.inflate(R.layout.event_click_frag, null);
        final ImagePopup imagePopup = new ImagePopup(alertLayout1.getContext());
        imagePopup.setWindowHeight(800);
        imagePopup.setWindowWidth(800);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setFullScreen(true);
        imagePopup.initiatePopupWithGlide(url);


        final TextView desc = alertLayout1.findViewById(R.id.tvDesc);
        final TextView link = alertLayout1.findViewById(R.id.tvLink);
        final TextView mobNo = alertLayout1.findViewById(R.id.tvMobno);

        DatabaseReference descRef = database.getReference("EventThings").child(pos).child("desc");
        DatabaseReference linkRef = database.getReference("EventThings").child(pos).child("link");
        DatabaseReference mobNoRef = database.getReference("EventThings").child(pos).child("mobNo");

        descRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String d = dataSnapshot.getValue(String.class);
                desc.setText(d);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        linkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String l = dataSnapshot.getValue(String.class);
                link.setText(l);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        mobNoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String m = dataSnapshot.getValue(String.class);
                mobNo.setText(m);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        imageView = alertLayout1.findViewById(R.id.imageEvent);
        final View thumb1View = alertLayout1.findViewById(R.id.imageEvent);
        Picasso.with(c).load(url).into((ImageView) thumb1View);

        AlertDialog.Builder alert1 = new AlertDialog.Builder(c);
        alert1.setTitle("Event-Description");
        alert1.setView(alertLayout1);
        alert1.setCancelable(true);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(view.getContext(), Uri.parse(link.getText().toString()));
            }
        });

        final AlertDialog dialog = alert1.create();
        dialog.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
                dialog.dismiss();
            }
        });
    }
}