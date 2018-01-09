package com.cbs.sscbs.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbs.sscbs.DataClass.BookingsDataClass;
import com.cbs.sscbs.utils.ItemClickListener;
import com.cbs.sscbs.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Prashant on 25-11-2017.
 */

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyViewHolder> {

    private List<BookingsDataClass> objectList;
    private LayoutInflater inflater;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public BookingsAdapter(Context context, List<BookingsDataClass> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.bookings_sample_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Context c;

        final BookingsDataClass current = objectList.get(position);
        holder.setData(current, position);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener itemClickListener;
        TextView date, venue;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.date_bookings);
            this.venue = (TextView) itemView.findViewById(R.id.venue_bookings);

        }

        public void setData(BookingsDataClass currentObject, int position) {
            this.date.setText(currentObject.getTime());
            this.venue.setText(currentObject.getVenue());
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }

}
