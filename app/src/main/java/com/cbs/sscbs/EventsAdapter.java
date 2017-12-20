package com.cbs.sscbs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Prashant on 25-11-2017.
 */

    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<DataClass> objectList;
    private LayoutInflater inflater;


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
        Context c;

        final DataClass current = objectList.get(position);
//        holder.img.setImageResource(objectList.get(position).getImg());
        holder.setData(current, position);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                FirebaseDatabase del = FirebaseDatabase.getInstance();

                Log.i("TAG", "Dlete-ID: "+ current.getDelId());
                Intent intent = new Intent().putExtra("ctr", current.getDelId());
                del.getReference("EventThings").child(String.valueOf(current.getDelId())).removeValue();


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

//    public void inflateDescription()
//    {
//        LayoutInflater inflater = getLayoutInflater();
//        View alertLayout1 = inflater.inflate(R.layout.event_click_frag, null);
//
//        final TextView desc = alertLayout1.findViewById(R.id.tvDesc);
//        final TextView link = alertLayout1.findViewById(R.id.tvLink);
//        final TextView mobNo = alertLayout1.findViewById(R.id.tvMobno);
//
//        AlertDialog.Builder alert1 = new AlertDialog.Builder(getContext());
//        alert1.setTitle("Event-Description");
//        alert1.setView(alertLayout1);
//        alert1.setCancelable(true);
//
//        alert1.setNegativeButton("Hello!!!", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getContext(), "Negative-Button", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        alert1.setPositiveButton("Goto-Link", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getContext(), "Wuhoo...", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
