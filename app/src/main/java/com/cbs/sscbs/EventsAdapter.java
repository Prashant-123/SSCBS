package com.cbs.sscbs;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Prashant on 25-11-2017.
 */

    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<DataClass> objectList;
    private LayoutInflater inflater;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public EventsAdapter(Context context, List<DataClass> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.events_sample_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Context c;

        final DataClass current = objectList.get(position);
//        holder.img.setImageResource(objectList.get(position).getImg());
        holder.setData(current, position);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                inflateDescription(v.getContext(), String.valueOf(current.getDelId()), String.valueOf(current.getImageUrl()));

//                FirebaseDatabase del = FirebaseDatabase.getInstance();
//                Log.i("TAG", "Dlete-ID: "+ current.getDelId());
//                Intent intent = new Intent().putExtra("ctr", current.getDelId());
//                del.getReference("EventThings").child(String.valueOf(current.getDelId())).removeValue();


            }
        });
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

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.organiser = (TextView) itemView.findViewById(R.id.organiser);
            this.venue = (TextView) itemView.findViewById(R.id.venue);
            this.img = (ImageView) itemView.findViewById(R.id.eventImage);
            itemView.setOnClickListener(this);

        }

        public void setData(DataClass currentObject, int position) {
            this.title.setText(currentObject.getTitle());
            this.date.setText(currentObject.getTime());
            this.organiser.setText(currentObject.getOrganiser());
            this.venue.setText(currentObject.getVenue());
            this.img.setImageResource((currentObject.getImg()));
            this.currentObject = currentObject;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }

    public void inflateDescription(final Context c, String pos, String url)
    {
        LayoutInflater inflater = LayoutInflater.from(c);
        View alertLayout1 = inflater.inflate(R.layout.event_click_frag, null);

        final TextView desc = alertLayout1.findViewById(R.id.tvDesc);
        final TextView link = alertLayout1.findViewById(R.id.tvLink);
        final TextView mobNo = alertLayout1.findViewById(R.id.tvMobno);
        final ImageView imageView = (ImageView)alertLayout1.findViewById(R.id.imageEvent);

        DatabaseReference descRef = database.getReference("EventThings").child(pos).child("desc");
        DatabaseReference linkRef = database.getReference("EventThings").child(pos).child("link");
        DatabaseReference mobNoRef = database.getReference("EventThings").child(pos).child("mobNo");

        Picasso.with(c).load(url).into(imageView);

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






        AlertDialog.Builder alert1 = new AlertDialog.Builder(c);
        alert1.setTitle("Event-Description");
        alert1.setView(alertLayout1);
        alert1.setCancelable(true);

        AlertDialog dialog = alert1.create();
        dialog.show();
    }

}
