package com.cbs.sscbs.TeachersTimetable;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cbs.sscbs.Events.DataClass;
import com.cbs.sscbs.Events.EventsAdapter;
import com.cbs.sscbs.R;
import com.cbs.sscbs.utils.ItemClickListener;
import com.ceylonlabs.imageviewpopup.ImagePopup;

import java.util.ArrayList;
import java.util.List;

class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder> implements Filterable {
    public ArrayList<TeacherDataClass> teachers;
    private LayoutInflater inflater;

    ArrayList<TeacherDataClass> filterList;
    CustomFilter filter;

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
        final ImagePopup imagePopup = new ImagePopup(holder.itemView.getContext());

        holder.name.setText(current.getName());
        holder.setData(current, imagePopup);
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

        public void setData(TeacherDataClass currentObject, final ImagePopup imagePopup) {
            this.name.setText(currentObject.getName());

            Glide.with(itemView).load(currentObject.getImageUrl()).into(img);

            imagePopup.setWindowHeight(800);
            imagePopup.setWindowWidth(800);
            imagePopup.setBackgroundColor(Color.BLACK);
            imagePopup.setFullScreen(true);
            imagePopup.initiatePopupWithGlide(currentObject.getImageUrl());

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imagePopup.viewPopup();
                }
            });

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
