package com.cbs.sscbs.SideBar

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cbs.sscbs.Others.Contact_us
import com.cbs.sscbs.R
import kotlinx.android.synthetic.main.activity_about.*

class About_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setToolbar()

        contact_us.setOnClickListener {
            val intent = Intent(this@About_Activity, Contact_us::class.java)
            startActivity(intent)
        }

        lifeCbs.setOnClickListener {
            val intent = Intent(this@About_Activity, LifeAtCbs::class.java)
            startActivity(intent)
        }

        facilities.setOnClickListener {
            val intent = Intent(this@About_Activity, Facilities::class.java)
            startActivity(intent)
        }

    }

    fun setToolbar() {
        setSupportActionBar(toolbar_about)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        toolbar_about.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}
