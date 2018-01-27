package com.cbs.sscbs.Others

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import com.cbs.sscbs.R


class LifeAtCbs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_at_cbs)
        val toolbar = findViewById<View>(R.id.toolbar_life_At_Cbs) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val bar: ProgressBar = findViewById(R.id.life_bar)
        val videoView = findViewById<VideoView>(R.id.playVideo) as VideoView
        videoView.setMediaController(MediaController(this))
        videoView.setVideoURI(Uri.parse(getString(R.string.vid_url)))
        videoView.requestFocus()
//        videoView.start()
//        bar.visibility = View.INVISIBLE


        bar.setVisibility(View.VISIBLE)

        videoView.setOnPreparedListener { mp ->
            mp.start()
            bar.setVisibility(View.GONE)
            }
        }
}
