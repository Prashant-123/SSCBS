package com.cbs.sscbs.TeachersTimetable;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cbs.sscbs.R;
import com.cbs.sscbs.utils.ItemClickListener;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    private ArrayList<DayWiseTTDataClass> mFeedList;
//    private Context mContext;
//    private Orientation mOrientation;
//    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(Context context , ArrayList<DayWiseTTDataClass> feedList) {
        mLayoutInflater = LayoutInflater.from(context);
        mFeedList = feedList;
//        mOrientation = orientation;
//        mWithLinePadding = withLinePadding;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model, null);
        return new TimeLineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        DayWiseTTDataClass timeLineModel = mFeedList.get(position);
//        holder.title.setText(timeLineModel.nine);
        holder.setData(timeLineModel);
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//
//
//            }
//        });
        holder.timelineView_eleven.setMarker(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        holder.timelineView_twelve.setMarker(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        holder.timelineView_one.setMarker(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        holder.timelineView_two.setMarker(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        holder.timelineView_three.setMarker(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        holder.timelineView_four.setMarker(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        holder.timelineView_ten.setMarker(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
        holder.timelineView_nine.setMarker(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent));
//
////
//        if(!timeLineModel.getDate().isEmpty()) {
//            holder.mDate.setVisibility(View.VISIBLE);
//            holder.mDate.setText(DateTimeUtils.parseDateTime(timeLineModel.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
//        }
//        else
//            holder.mDate.setVisibility(View.GONE);
//
//        holder.mMessage.setText(timeLineModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    class TimeLineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TimelineView timelineView_nine,timelineView_ten , timelineView_eleven , timelineView_twelve , timelineView_one , timelineView_two
                ,timelineView_three ,timelineView_four;
        ItemClickListener itemClickListener;
        TextView title_nine , title_ten , title_eleven , title_twelve , title_one , title_two,title_three , title_four;
        TextView subj_nine , subj_ten , subj_eleven , subj_twelve ,subj_one , subj_two , subj_three , subj_four ;
        DayWiseTTDataClass currentObject;
        public TimeLineViewHolder(View itemView , int viewType) {
            super(itemView);
            this.title_nine = itemView.findViewById(R.id.text_timeline_title_nine);
            this.title_ten = itemView.findViewById(R.id.text_timeline_title_ten);
            this.title_eleven = itemView.findViewById(R.id.text_timeline_title_eleven);
            this.title_twelve = itemView.findViewById(R.id.text_timeline_title_twelve);
            this.title_one = itemView.findViewById(R.id.text_timeline_title_one);
            this.title_two = itemView.findViewById(R.id.text_timeline_title_two);
            this.title_three = itemView.findViewById(R.id.text_timeline_title_three);
            this.title_four = itemView.findViewById(R.id.text_timeline_title_four);

            this.subj_nine = itemView.findViewById(R.id.text_timeline_subj_nine) ;
            this.subj_ten = itemView.findViewById(R.id.text_timeline_subj_ten) ;
            this.subj_eleven = itemView.findViewById(R.id.text_timeline_subj_eleven) ;
            this.subj_twelve = itemView.findViewById(R.id.text_timeline_subj_twelve) ;
            this.subj_one = itemView.findViewById(R.id.text_timeline_subj_one) ;
            this.subj_two = itemView.findViewById(R.id.text_timeline_subj_two) ;
            this.subj_three = itemView.findViewById(R.id.text_timeline_subj_three) ;
            this.subj_four = itemView.findViewById(R.id.text_timeline_subj_four) ;


            timelineView_nine = (TimelineView) itemView.findViewById(R.id.time_marker_nine);
            timelineView_ten = (TimelineView) itemView.findViewById(R.id.time_marker_ten);
            timelineView_eleven = (TimelineView) itemView.findViewById(R.id.time_marker_eleven);
            timelineView_twelve = (TimelineView) itemView.findViewById(R.id.time_marker_twelve);
            timelineView_one = (TimelineView) itemView.findViewById(R.id.time_marker_one);
            timelineView_two = (TimelineView) itemView.findViewById(R.id.time_marker_two);
            timelineView_three = (TimelineView) itemView.findViewById(R.id.time_marker_three);
            timelineView_four = (TimelineView) itemView.findViewById(R.id.time_marker_four);

            timelineView_nine.initLine(viewType);
            timelineView_ten.initLine(viewType);
            timelineView_eleven.initLine(viewType);
            timelineView_twelve.initLine(viewType);
            timelineView_one.initLine(viewType);
            timelineView_two.initLine(viewType);
            timelineView_three.initLine(viewType);
            timelineView_four.initLine(viewType);

        }

        public void setData(DayWiseTTDataClass currentObject ){
            this.title_nine.setText(currentObject.nine_title);
            this.title_ten.setText(currentObject.ten_title);
            this.title_eleven.setText(currentObject.eleven_title);
            this.title_twelve.setText(currentObject.twelve_title);
            this.title_one.setText(currentObject.one_title);
            this.title_two.setText(currentObject.two_title);
            this.title_three.setText(currentObject.three_title);
            this.title_four.setText(currentObject.four_title);

            this.subj_nine.setText(currentObject.nine_subj);
            this.subj_ten.setText(currentObject.ten_subj);
            this.subj_eleven.setText(currentObject.eleven_subj);
            this.subj_twelve.setText(currentObject.twelve_subj);
            this.subj_one.setText(currentObject.one_subj);
            this.subj_two.setText(currentObject.two_subj);
            this.subj_three.setText(currentObject.three_subj);
            this.subj_four.setText(currentObject.four_subj);

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
//    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.text_timeline_date)
//        TextView mDate;
//        @BindView(R.id.text_timeline_title)
//        TextView mMessage;
//        @BindView(R.id.time_marker)
//        TimelineView mTimelineView;
//
//        public TimeLineViewHolder(View itemView, int viewType) {
//            super(itemView);
//
//            ButterKnife.bind(this, itemView);
//            mTimelineView.initLine(viewType);
//        }
//    }
}
