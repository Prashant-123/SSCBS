package com.cbs.sscbs.TeachersTimetable;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by Tanya on 11/15/2017.
 */

public class CustomFilter extends Filter {

    TeacherAdapter adapter ;
    ArrayList<TeacherDataClass> filterList ;
    public CustomFilter(ArrayList<TeacherDataClass> filterList, TeacherAdapter adapter)
    {

        this.adapter = adapter;
        this.filterList = filterList ;
    }
    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();

        if(charSequence != null && charSequence.length() > 0 )
        {
            charSequence= charSequence.toString().toUpperCase() ;
            ArrayList<TeacherDataClass> filteredTeacher = new ArrayList<>() ;

            for(int i = 0 ; i < filterList.size() ; i++)
            {
                if(filterList.get(i).getName().toUpperCase().contains(charSequence))
                {
                    filteredTeacher.add(filterList.get(i)) ;
                }
            }
            results.count = filteredTeacher.size() ;
            results.values = filteredTeacher ;
        }
        else
        {
            results.count = filterList.size() ;
            results.values = filterList ;
        }

        return results ;
    }
    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.teachers = (ArrayList<TeacherDataClass>) filterResults.values ;
        adapter.notifyDataSetChanged();
    }
}
