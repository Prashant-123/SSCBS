package com.cbs.sscbs.TeachersTimetable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cbs.sscbs.Fragments.Home_frag;
import com.cbs.sscbs.R;

import java.util.ArrayList;

public class DayWiseTT extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private ArrayList<DayWiseTTDataClass> mDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_wise_tt);
//        mOrientation = (Orientation) getIntent().getSerializableExtra(MainActivity.EXTRA_ORIENTATION);
//        mWithLinePadding = getIntent().getBooleanExtra(MainActivity.EXTRA_WITH_LINE_PADDING, false);

//        setTitle(mOrientation == Orientation.HORIZONTAL ? getResources().getString(R.string.horizontal_timeline) : getResources().getString(R.string.vertical_timeline));

        mRecyclerView = (RecyclerView) findViewById(R.id.dayWiseRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        mTimeLineAdapter = new TimeLineAdapter(this, Home_frag.dayData);
        mRecyclerView.setAdapter(mTimeLineAdapter);
        mTimeLineAdapter.notifyDataSetChanged();
//        initView();

    }

//    private void initView() {
//        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
//        mRecyclerView.setAdapter(mTimeLineAdapter);
//    }

}
