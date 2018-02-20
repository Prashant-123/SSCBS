package com.cbs.sscbs.Attendance;

/**
 * Created by Prashant on 31-12-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cbs.sscbs.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {


    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
   String formattedDate = df.format(c.getTime());
   String getMonth = formattedDate.substring(3, 6);

    public static ArrayList<String> saveRoll = new ArrayList<>();
    private List<AttendanceDataClass> objectList;
    private LayoutInflater inflater;
    private String checkRoll;

    private static final String TAG = "TAG";
    FirebaseFirestore db1 = FirebaseFirestore.getInstance();
//    CollectionReference db = FirebaseFirestore.getInstance().collection("/Attendance/Bsc-2/Students/" +
//            "Abhishek Dev/Subjects/Algorithms/Year/2017-18/Month");

    public AttendanceAdapter(Context context, List<AttendanceDataClass> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.attendance_model, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AttendanceDataClass current = objectList.get(position);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                objectList.get(holder.getAdapterPosition()).setChecked(b);

//                getAllToUpdateTotal.add(objectList.get(holder.getAdapterPosition()).getRoll().toString());

                checkRoll = objectList.get(holder.getAdapterPosition()).getRoll().toString();

                if (objectList.get(holder.getAdapterPosition()).isChecked())
                {
                    saveRoll.add(checkRoll);
                }
                else saveRoll.remove(checkRoll);
            }
        });

        holder.setData(current);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, roll;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.tvName);
            this.roll = itemView.findViewById(R.id.tvRollno);
            this.checkBox = itemView.findViewById(R.id.checkbox);
        }

        public void setData(AttendanceDataClass currentObject) {
            this.name.setText(currentObject.getName());
            this.roll.setText(String.valueOf(currentObject.getRoll()));
        }
    }
}