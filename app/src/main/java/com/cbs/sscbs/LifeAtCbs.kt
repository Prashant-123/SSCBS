package com.cbs.sscbs

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.MediaController
import android.widget.VideoView

class LifeAtCbs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_at_cbs)

        val videoView = findViewById<VideoView>(R.id.playVideo) as VideoView
        videoView.setMediaController(MediaController(this))
        videoView.setVideoURI(Uri.parse("https://github.com/Prashant-123/Stuff/blob/master/Life%20At%20CBS%20New%20campus%20-%20converted%20with%20Clipchamp.mp4?raw=true"))
        videoView.requestFocus()
        videoView.start()

    }
}
