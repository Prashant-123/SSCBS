package com.cbs.sscbs

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_life_at_cbs.*

class LifeAtCbs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_at_cbs)
        val toolbar = findViewById<View>(R.id.toolbar_life_At_Cbs) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val videoView = findViewById<VideoView>(R.id.playVideo) as VideoView
        videoView.setMediaController(MediaController(this))
        videoView.setVideoURI(Uri.parse("https://github.com/Prashant-123/Stuff/blob/master/Life%20At%20CBS%20New%20campus%20-%20converted%20with%20Clipchamp.mp4?raw=true"))
        videoView.requestFocus()
        videoView.start()

    }




}
