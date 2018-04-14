package com.cbs.sscbs.Attendance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbs.sscbs.R;
import com.libRG.CustomTextView;
import java.util.List;

/**
 * Created by Tanya on 2/14/2018.
 */


public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyViewHolder> {

    private List<StudentsDataClass> objectList;
    private LayoutInflater inflater;
    private static final String TAG = "TAG";
    TextView subject;
    public StudentsAdapter(Context applicationContext, List<StudentsDataClass> objectList) {
        inflater = LayoutInflater.from(applicationContext);
        this.objectList = objectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.stushow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        StudentsDataClass object = objectList.get(position);
        holder.setData(object);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sub, afterText;
        TextView att;
        TextView tot;
        CustomTextView perc;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.sub = itemView.findViewById(R.id.stuSub);
            this.att = itemView.findViewById(R.id.stuAtt);
            this.tot = itemView.findViewById(R.id.tt);
            this.perc = itemView.findViewById(R.id.c6);
            this.afterText = itemView.findViewById(R.id.afterText);
            }

        public void setData(StudentsDataClass currentObject) {
            this.sub.setText(currentObject.getSubject());
            this.att.setText(String.valueOf(currentObject.getAttendance()));
            this.tot.setText(String.valueOf(currentObject.getTotal()));

            if (((float) currentObject.getAttendance()/(float) currentObject.getTotal())*100 < 67.7)
                afterText.setText("Please be in class, Attendance matters :)");
            else afterText.setText("Cool , You're doing good");

            String perc = String.valueOf(((float) currentObject.getAttendance()/(float) currentObject.getTotal())*100);
            this.perc.setText(perc + "%");
        }
    }
}
