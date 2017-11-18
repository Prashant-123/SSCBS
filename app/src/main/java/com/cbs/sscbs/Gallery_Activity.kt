package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ViewFlipper
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_gallery_.*


class Gallery_Activity : AppCompatActivity() {

    internal lateinit var fade_in: Animation
    internal lateinit var fade_out: Animation
    internal lateinit var viewFlipper: ViewFlipper

    lateinit var firebasedb: FirebaseDatabase
    lateinit var firebaseref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_)
        setToolbar()

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper1) as ViewFlipper
        showImage(R.id.flipper1, "infrastructure", 2)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper2) as ViewFlipper
        showImage(R.id.flipper2, "events", 3)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper3) as ViewFlipper
        showImage(R.id.flipper3, "society", 3)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper4) as ViewFlipper
        showImage(R.id.flipper4, "victories", 3)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper5) as ViewFlipper
        showImage(R.id.flipper5, "sports", 3)

    }

    fun setToolbar() {
        setSupportActionBar(toolbar_gallery)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar_gallery.setNavigationOnClickListener {
            onBackPressed()
            setTitle("Gallery")
        }
    }

    private fun showImage(id: Int, folder: String, noOfPics: Int) {

        for (i in 0 until noOfPics)
        {
        firebasedb = FirebaseDatabase.getInstance()
        firebaseref = firebasedb.getReference("${folder}/${i}")

        firebaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)
                setImageInFlipr(findViewById(id), url)
            }
        })}
}
    private fun setImageInFlipr(frame: ViewFlipper, imgUrl: String?) {

        val image = ImageView(applicationContext)
        Glide.with(this).load(imgUrl).into(image)
        frame.addView(image)
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        frame.setInAnimation(fade_in)
        frame.setOutAnimation(fade_out)
        frame.setAutoStart(true)
        frame.setFlipInterval(1500)
        frame.startFlipping()
    }

}