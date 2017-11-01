package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import butterknife.ButterKnife
import com.cbs.sscbs.utils.TouchImageView
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fullimage_activity.*

/**
 * Created by gauti on 29/10/17.
 */
class FullScreenImage:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.fullimage_activity)
        val troops_image : PhotoView = findViewById(R.id.troops_image)

        Log.d("Fullscreen",intent.getStringExtra(CONSTANTS.imageurl))

        Picasso.with(applicationContext).load(intent.getStringExtra(CONSTANTS.imageurl)).fit()
                .into(troops_image)


        // initilizeFullScreen();
    }

}