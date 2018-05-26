package com.cbs.sscbs.Attendance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cbs.sscbs.R;

import java.util.ArrayList;
import java.util.List;

public class WaiversAdapter extends RecyclerView.Adapter<WaiversAdapter.MyViewHolder>  {
    private List<WaiverDataClass> objectList;
    public static ArrayList<WaiverListClass> waiverList = new ArrayList<>();
    private LayoutInflater inflater;
    public WaiversAdapter(Context context, List<WaiverDataClass> objectList) {
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public WaiversAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WaiversAdapter.MyViewHolder(inflater.inflate(R.layout.waiver_model, parent, false));
    }

    @Override
    public void onBindViewHolder(WaiversAdapter.MyViewHolder holder, int position) {
    final WaiverDataClass waiverDataClass = objectList.get(position);
    holder.setData(waiverDataClass);
    holder.stuWaiver.getText().toString();
    Log.wtf("TAG" , holder.stuWaiver.getText().toString());
    if(!holder.stuWaiver.getText().toString().equals("0")){
        WaiverListClass waiverListClass = new WaiverListClass(holder.roll.toString(),holder.stuWaiver.getText().toString());
        waiverList.add(waiverListClass);
    }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }
     class MyViewHolder extends RecyclerView.ViewHolder {
         TextView name, roll;
         EditText stuWaiver ;
         public MyViewHolder(View itemView) {
             super(itemView);
             this.name = itemView.findViewById(R.id.tvWaiverName);
             this.roll = itemView.findViewById(R.id.tvWaiverRollno);
             stuWaiver = itemView.findViewById(R.id.waiverText);
         }
         public void setData(WaiverDataClass currentObject) {
             this.name.setText(currentObject.getName());
             this.roll.setText(String.valueOf(currentObject.getRoll()));
         }
     }
}
