package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.teachers_item_list.*


class Teachers : AppCompatActivity() {

    internal var toolbar = findViewById<View>(R.id.toolbar) as Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teachers)
        setSupportActionBar(toolbar)

                teacher_relative.setOnClickListener {

            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
        }

    }
}
