package com.cbs.sscbs.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbs.sscbs.Attendance.StudentsDataClass;
import com.cbs.sscbs.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

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
//                holder.flipView.flipTheView();
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sub;
        TextView att;
        EasyFlipView flipView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.sub = itemView.findViewById(R.id.stuSub);
            this.att = itemView.findViewById(R.id.stuAtt);
//           this.flipView = itemView.findViewById(R.id.flipView1);
        }

        public void setData(StudentsDataClass currentObject) {
            this.sub.setText(currentObject.getSubject());
            this.att.setText(String.valueOf(currentObject.getAttendance()));
        }
    }
}
