package com.cbs.sscbs;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class LifeAtCbs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_at_cbs);

        VideoView videoView = (VideoView) findViewById(R.id.playVideo);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse("https://github.com/Prashant-123/ImagesVideos/raw/master/Life%20At%20CBS%20New%20campus.mp4"));
        videoView.requestFocus();
        videoView.start();

    }
}
