package com.cbs.sscbs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toolbar

class Contact_us : AppCompatActivity() {

    internal var t: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        findViewById<View>(R.id.call).setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL)
            val p = "tel:01122154581\n"
            i.data = Uri.parse(p)
            startActivity(i)
        }


        findViewById<View>(R.id.email).setOnClickListener {
            val sub = "Get-Lost"
            val body = "Body"
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse("mailto:pk021998@gmail.com?subject=$sub&body=$body")
            intent.data = data
            startActivity(intent)
        }

    }

}
