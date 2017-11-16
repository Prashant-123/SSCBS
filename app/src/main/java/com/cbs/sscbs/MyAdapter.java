package com.cbs.sscbs;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Created by Tanya on 11/15/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyHolder> implements Filterable {

    Context c;
    ArrayList<Teacher> teachers , filterList;
    CustomFilter filter ;

    public MyAdapter(Context ctx , ArrayList<Teacher> teachers)
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
    public void onBindViewHolder(final MyHolder holder, int position) {

        holder.posTxt.setText(teachers.get(position).getPos("ok"));
        holder.nameTxt.setText(teachers.get(position).getName("ag"));
        holder.img.setImageResource(teachers.get(position).getImg(R.drawable.abhihek_tandon));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
//                Intent intent = new Intent(this , Sample.class);
//                c.startActivity(intent);
               // Toast.makeText(Context,"Hi" , Toast.LENGTH_SHORT).show();
//                Intent intent2 = new Intent(this, Sample.class);
//                c.startActivity();
//

                Snackbar.make(v,teachers.get(pos).getName("ag") , Snackbar.LENGTH_SHORT).show();

//                Intent intent = new Intent(String.valueOf(Sample.class));
//                c.startActivity(intent);
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
