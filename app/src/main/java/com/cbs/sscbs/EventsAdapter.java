package com.cbs.sscbs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Prashant on 25-11-2017.
 */

    public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder>{

        private List<DataClass> objectList;
        private LayoutInflater inflater;

        public EventsAdapter(Context context, List<DataClass> objectList)
        {
            inflater = LayoutInflater.from(context);
            this.objectList = objectList;
        }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.list_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DataClass current = objectList.get(position);
//        holder.img.setImageResource(objectList.get(position).getImg());
        holder.setData(current, position);

    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title, date, organiser, venue;
        DataClass currentObject;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.title = (TextView)itemView.findViewById(R.id.title);
                this.date = (TextView)itemView.findViewById(R.id.date);
                this.organiser = (TextView)itemView.findViewById(R.id.organiser);
                this.venue = (TextView)itemView.findViewById(R.id.venue);
                this.img = (ImageView) itemView.findViewById(R.id.eventImage) ;

            }

        public void setData(DataClass currentObject, int position) {
            this.title.setText(currentObject.getTitle());
            this.date.setText(currentObject.getTime());
            this.organiser.setText(currentObject.getOrganiser());
            this.venue.setText(currentObject.getVenue());
            //this.img.setImageResource((currentObject.getImg()));
            this.currentObject = currentObject;
        }
    }
    }
