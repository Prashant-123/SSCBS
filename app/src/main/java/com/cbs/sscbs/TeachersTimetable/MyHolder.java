package com.cbs.sscbs.TeachersTimetable;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbs.sscbs.R;
import com.cbs.sscbs.utils.ItemClickListener;

/**
 * Created by Tanya on 11/15/2017.
 */

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView img;
    TextView nameTxt , posTxt ;
    ItemClickListener itemClickListener;
    public MyHolder(View itemView) {
        super(itemView);

        this.img = (ImageView) itemView.findViewById(R.id.teacherImage) ;
        this.nameTxt = (TextView) itemView.findViewById(R.id.nameTxt) ;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view, getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener  = ic ;
    }
}
