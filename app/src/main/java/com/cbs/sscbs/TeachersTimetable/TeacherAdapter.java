package com.cbs.sscbs.TeachersTimetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbs.sscbs.Events.DataClass;
import com.cbs.sscbs.Events.EventsAdapter;
import com.cbs.sscbs.R;
import com.cbs.sscbs.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder> implements Filterable {
    public ArrayList<TeacherDataClass> teachers;
    private LayoutInflater inflater;

    ArrayList<TeacherDataClass> filterList;
    CustomFilter filter;
    //public ArrayList<TeacherDataClass> teachers;

    public TeacherAdapter(Context context, ArrayList<TeacherDataClass> data) {

        inflater = LayoutInflater.from(context);
        this.teachers = data;
        this.filterList = teachers;
    }

    @Override
    public TeacherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_model, null);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TeacherAdapter.MyViewHolder holder, int position) {

        final TeacherDataClass current = teachers.get(position);
        Log.wtf("TAG" , "as " + current.getTName());
        holder.name.setText(current.getTName());
        holder.setData(current, position);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    @Override
    public Filter getFilter() {

        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }
        return filter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ItemClickListener itemClickListener;
        ImageView img;
        TextView name;
        TeacherDataClass currentObject;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.teacherImage);
            this.name = (TextView) itemView.findViewById(R.id.nameTxt);
            itemView.setOnClickListener(this);
        }

        public void setData(TeacherDataClass currentObject, int position) {
            this.name.setText(currentObject.getTName());
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
}
