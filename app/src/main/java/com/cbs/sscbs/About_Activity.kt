package com.cbs.sscbs

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*

class About_Activity : AppCompatActivity() {

    //OK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setToolbar();

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
       // setSupportActionBar(toolbar_about)
       // setTitle("About Us")
       // toolbar_about.setBackgroundColor(resources.getColor(R.color.white))
     //   toolbar_about.setNavigationIcon(resources.getDrawable(R.drawable.back_arrow))

        setSupportActionBar(toolbar_about)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        toolbar_about.setNavigationOnClickListener{
                onBackPressed()
        }
    }

}
