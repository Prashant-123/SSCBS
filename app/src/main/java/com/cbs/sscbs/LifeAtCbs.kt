package com.cbs.sscbs

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.MediaController
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_life_at_cbs.*

class LifeAtCbs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_at_cbs)

        val videoView = findViewById<VideoView>(R.id.playVideo) as VideoView
        videoView.setMediaController(MediaController(this))
        videoView.setVideoURI(Uri.parse("https://github.com/Prashant-123/ImagesVideos/raw/master/Life%20At%20CBS%20New%20campus.mp4"))
        videoView.requestFocus()

        PlayButton.setOnClickListener()
        {
            videoView.start()
        }

    }
}
