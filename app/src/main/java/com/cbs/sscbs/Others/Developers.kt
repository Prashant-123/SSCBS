package com.cbs.sscbs.Others

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.cbs.sscbs.R
import kotlinx.android.synthetic.main.activity_developers.*

class Developers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)
        setSupportActionBar(toolbar_developers)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar_developers.setNavigationOnClickListener {
            onBackPressed()
            }

//        val mem1ln = findViewById<ImageView>(R.id.mem1linkedIn_image) as ImageView
        val mem2ln = findViewById<ImageView>(R.id.mem2linkedIn_image) as ImageView
//        val mem1fb = findViewById<ImageView>(R.id.mem1facebook_image) as ImageView
        val mem2fb = findViewById<ImageView>(R.id.mem2facebook_image) as ImageView
//        val mem1gt = findViewById<ImageView>(R.id.mem1github_image) as ImageView
        val mem2gt = findViewById<ImageView>(R.id.mem2github_image) as ImageView

//        mem1ln.setOnClickListener({
//            val uri = Uri.parse("https://www.linkedin.com/in/prashant-kumar-86785012b/")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
        mem2ln.setOnClickListener({
            val uri = Uri.parse("https://www.linkedin.com/in/tanvi-goyal-2b34b015a/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
//        mem1fb.setOnClickListener({
//            val uri = Uri.parse("https://www.facebook.com/pk021998")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
        mem2fb.setOnClickListener({
            val uri = Uri.parse("https://www.facebook.com/profile.php?id=100012257649419")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
//        mem1gt.setOnClickListener({
//            val uri = Uri.parse("https://github.com/Prashant-123")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
        mem2gt.setOnClickListener({
            val uri = Uri.parse("https://github.com/Tanvi-Goyal")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })

    }
}
