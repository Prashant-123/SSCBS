package com.cbs.sscbs.Attendance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
    String _waiverText;
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
    public void onBindViewHolder(final WaiversAdapter.MyViewHolder holder, final int position) {
    final WaiverDataClass waiverDataClass = objectList.get(position);
//    waiverList.clear();

        holder.stuWaiver.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //WaiverListClass waiverListClassAt0 = new WaiverListClass("0","0");
                WaiverListClass waiverListClass = new WaiverListClass(objectList.get(holder.getAdapterPosition()).getRoll(),holder.stuWaiver.getText().toString());
                //waiverList.add(0,waiverListClassAt0);
                if (waiverList.contains(waiverListClass)) {
                    waiverList.remove(waiverListClass);
                    //Log.wtf("OK" , "if "+String.valueOf(position));
                    waiverList.add(position, waiverListClass);
                    waiverList.get(position).setWaivers(s.toString());
                } else {
                    //Log.wtf("OK" ,"else "+ String.valueOf(position));
                    waiverList.add(position, waiverListClass);
                    waiverList.get(waiverList.indexOf(waiverListClass)).setWaivers(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        holder.setData(waiverDataClass);
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
