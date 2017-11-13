package com.cbs.sscbs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*

/**
 * Created by Tanya on 11/10/2017.
 */
class CustomAdapter(var context:Context , var teacher:ArrayList<Teacher>) :BaseAdapter (){

    private class ViewHolder(row: View?) {

        var txtName : TextView
        var ivImage : ImageView

        init {
            this.txtName = row?.findViewById<TextView>(R.id.txtName) as TextView
            this.ivImage = row?.findViewById<ImageView>(R.id.ivTeachers) as ImageView

        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view : View?
        var viewHolder: ViewHolder

        if(convertView == null)
        {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.teachers_item_list , parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else
        {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var teacher : Teacher = getItem(position) as Teacher
        viewHolder.txtName.text = teacher.name
        viewHolder.ivImage.setImageResource(teacher.image)
        return view as View
    }

    override fun getItem(position: Int): Any {
        return teacher.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return teacher.count()
    }

}