package com.cbs.sscbs.Others

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.cbs.sscbs.TeachersTimetable.CONSTANTS
import com.cbs.sscbs.R
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso

/**
 * Created by gauti on 29/10/17.
 */
class FullScreenImage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.fullimage_activity)
        val troops_image: PhotoView = findViewById(R.id.troops_image)

        Picasso.with(applicationContext).load(intent.getStringExtra(CONSTANTS.imageurl)).fit()
                .into(troops_image)

    }

}