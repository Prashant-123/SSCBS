package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView

/**
 * Created by Tanya on 11/10/2017.
 */
class TeachersMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teachers)

        var listView = findViewById<ListView>(R.id.listView) as ListView
        var arrTeachers: ArrayList<Teacher> = ArrayList()

        arrTeachers.add(Teacher("ag", R.drawable.ag))
        arrTeachers.add(Teacher("aj", R.drawable.ag))
        arrTeachers.add(Teacher("ak", R.drawable.ag))
        arrTeachers.add(Teacher("aku", R.drawable.ag))
        arrTeachers.add(Teacher("am", R.drawable.ag))
        arrTeachers.add(Teacher("av", R.drawable.ag))
        arrTeachers.add(Teacher("hkp", R.drawable.ag))
        arrTeachers.add(Teacher("imageabhi", R.drawable.ag))
        arrTeachers.add(Teacher("kb", R.drawable.ag))
        arrTeachers.add(Teacher("kk", R.drawable.ag))
        arrTeachers.add(Teacher("kr", R.drawable.ag))
        arrTeachers.add(Teacher("krs", R.drawable.ag))
        arrTeachers.add(Teacher("mm", R.drawable.ag))
        arrTeachers.add(Teacher("mv", R.drawable.ag))
        arrTeachers.add(Teacher("neha", R.drawable.ag))



        listView.adapter = CustomAdapter(applicationContext, arrTeachers)
    }
}

