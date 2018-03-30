package com.cbs.sscbs.Attendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cbs.sscbs.R;
import com.cbs.sscbs.utils.ItemClickListener;

import java.util.ArrayList;

class ClassListsAdapter extends RecyclerView.Adapter<ClassListsAdapter.ViewHolder> {
    ArrayList<String> mClasses;

    public ClassListsAdapter(ArrayList<String> myClasses) {
        mClasses = myClasses;
    }

    @Override
    public ClassListsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_upload_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClassListsAdapter.ViewHolder holder, int position) {
        holder.className.setText(mClasses.get(position));
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Toast.makeText(holder.itemView.getContext(),"Selected : " + holder.className.getText().toString(), Toast.LENGTH_SHORT).show();
//                if( holder.className.getText().toString().contains("BSC")){
//                    new AdminActivity.uploadBscList().execute();
//                }
//
//                if( holder.className.getText().toString().contains("BFIA-1")||holder.className.getText().toString().contains("BFIA-2")){
//                    new AdminActivity.uploadBfiaList().execute();
//                }
//
//                if( holder.className.getText().toString().contains("BFIA-3")){
//                    new AdminActivity.uploadBfiaMixList().execute();
//                }
//
//                if( holder.className.getText().toString().contains("BMS-1")||holder.className.getText().toString().contains("BMS-2")){
//                    new AdminActivity.uploadBmsList().execute();
//                }
//
//                if( holder.className.getText().toString().contains("BMS-3")){
//                    //new AdminActivity.uploadBmsList().execute();
//                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return mClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Button className;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
//            Toast.makeText(view.getContext(), "ok", Toast.LENGTH_SHORT).show();
        }
    }
}
