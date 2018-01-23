package com.cbs.sscbs.TeachersTimetable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.cbs.sscbs.Others.CustomFilter;
import com.cbs.sscbs.DataClass.Teacher;
import com.cbs.sscbs.R;
import com.cbs.sscbs.utils.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Tanya on 11/15/2017.
 */

public class TeachersAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    public ArrayList<Teacher> teachers;
    ArrayList<Teacher> filterList;
    CustomFilter filter;

    public TeachersAdapter(Context ctx , ArrayList<Teacher> teachers)
    {
        this.c = ctx ;
        this.teachers = teachers ;
        this.filterList = teachers ;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_model,null) ;
        MyHolder holder = new MyHolder(v) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.nameTxt.setText(teachers.get(position).getName());
        holder.img.setImageResource(teachers.get(position).getImg());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Intent intent = new Intent(v.getContext(), Sample.class);
                intent.putExtra("intentName", teachers.get(pos).getName());
                intent.putExtra("intentPos", pos);
                intent.putExtra("intentQualification",teachers.get(pos).getQualification());
                intent.putExtra("intentEmail" , teachers.get(pos).getEmail()) ;
                c.startActivity(intent);
                //Snackbar.make(v, teachers.get(pos).getName(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (teachers == null) ? 0 : teachers.size();
    }

    @Override
    public Filter getFilter() {

        if(filter == null)
        {
            filter = new CustomFilter(filterList , this) ;
        }
        return filter;
    }
}