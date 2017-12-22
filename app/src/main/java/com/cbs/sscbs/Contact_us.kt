package com.cbs.sscbs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View


class Contact_us : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        val toolbar = findViewById<View>(R.id.toolbar_contact_us) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        findViewById<View>(R.id.call).setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL)
            val p = "tel:01122154581\n"
            i.data = Uri.parse(p)
            startActivity(i)
        }


        findViewById<View>(R.id.email).setOnClickListener {
            val sub = ""
            val body = ""
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse("mailto:sscbs@gmail.com?subject=$sub&body=$body")
            intent.data = data
            startActivity(Intent.createChooser(intent, "Send Email Using"))
        }

        findViewById<View>(R.id.website).setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse("http://www.sscbs.du.ac.in")
            startActivity(browserIntent) }

        findViewById<View>(R.id.address).setOnClickListener {
            //val uri = String.format(Locale.ENGLISH, ":%f,%f", 28.7327, 77.1188)
            val geoUri = "http://maps.google.com/maps?q=loc:$28.7327,$77.1188 ($ Dr. K.N. Katju Marg, Sector 16, Rohini, New Delhi, Delhi 110089)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
            startActivity(intent)
        }


    }
}
