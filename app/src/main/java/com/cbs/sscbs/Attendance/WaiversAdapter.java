package com.cbs.sscbs.Attendance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbs.sscbs.R;

import java.util.List;

public class WaiversAdapter extends RecyclerView.Adapter<WaiversAdapter.MyViewHolder>  {
    private List<WaiverDataClass> objectList;
    private LayoutInflater inflater;
    public WaiversAdapter(Context context, List<WaiverDataClass> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }
    @Override
    public WaiversAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WaiversAdapter.MyViewHolder(inflater.inflate(R.layout.waiver_model, parent, false));
    }

    @Override
    public void onBindViewHolder(WaiversAdapter.MyViewHolder holder, int position) {
    final WaiverDataClass waiverDataClass = objectList.get(position);
    holder.setData(waiverDataClass);
    }


    @Override
    public int getItemCount() {
        return objectList.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
         TextView name, roll;
         public MyViewHolder(View itemView) {
             super(itemView);
             this.name = itemView.findViewById(R.id.tvWaiverName);
             this.roll = itemView.findViewById(R.id.tvWaiverRollno);
         }
         public void setData(WaiverDataClass currentObject) {
             this.name.setText(currentObject.getName());
             this.roll.setText(String.valueOf(currentObject.getRoll()));
         }
     }
}
