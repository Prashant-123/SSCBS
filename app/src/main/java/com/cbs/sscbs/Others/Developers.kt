package com.cbs.sscbs.Others

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cbs.sscbs.R

class Developers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)
        val toolbar = findViewById<View>(R.id.toolbar_developers) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val goToRepo = findViewById<TextView>(R.id.GoToRepo) as TextView
        val mem1ln = findViewById<TextView>(R.id.mem1linkedIn_image) as ImageView
        val mem2ln = findViewById<TextView>(R.id.mem2linkedIn_image) as ImageView
        val mem1fb = findViewById<TextView>(R.id.mem1facebook_image) as ImageView
        val mem2fb = findViewById<TextView>(R.id.mem2facebook_image) as ImageView
        val mem1gt = findViewById<TextView>(R.id.mem1github_image) as ImageView
        val mem2gt = findViewById<TextView>(R.id.mem2github_image) as ImageView

        goToRepo.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https:/github.com/Prashant-123/SSCBS")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })

        mem1ln.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://www.linkedin.com/in/prashant-kumar-86785012b/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })

        mem2ln.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https:/github.com/Prashant-123/SSCBS")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
        mem1fb.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://www.facebook.com/pk021998")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
        mem2fb.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://www.facebook.com/profile.php?id=100012257649419")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
        mem1gt.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://github.com/Prashant-123")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })

        mem2gt.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://github.com/Tanvi-Goyal")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })

    }
}
