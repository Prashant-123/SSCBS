package com.cbs.sscbs.SideBar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ViewFlipper
import com.bumptech.glide.Glide
import com.cbs.sscbs.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_gallery_.*


class Gallery_Activity : AppCompatActivity() {

    internal lateinit var fade_in: Animation
    internal lateinit var fade_out: Animation
    internal lateinit var viewFlipper: ViewFlipper

    lateinit var firebasedb: FirebaseDatabase
    lateinit var firebaseref: DatabaseReference
    lateinit var dataSnapshot : DataSnapshot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_)
        setToolbar()

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper1) as ViewFlipper
        showImage(R.id.flipper1, "infrastructure", 5)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper2) as ViewFlipper
        showImage(R.id.flipper2, "highlights", 6)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper3) as ViewFlipper
        showImage(R.id.flipper3, "events", 3)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper4) as ViewFlipper
        showImage(R.id.flipper4, "society", 5)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper5) as ViewFlipper
        showImage(R.id.flipper5, "victories", 2)

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper6) as ViewFlipper
        showImage(R.id.flipper6, "sports", 2)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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
            progressbarEnable()


        firebaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                progressbarDisable()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)
                setImageInFlipr(findViewById(id), url)
                progressbarDisable()
            }
        })}
}

    private fun setImageInFlipr(frame: ViewFlipper, imgUrl: String?) {

        val image = ImageView(applicationContext)
        Glide.with(applicationContext).load(imgUrl).into(image)
        frame.addView(image)
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        frame.setInAnimation(fade_in)
        frame.setOutAnimation(fade_out)
        frame.setAutoStart(true)
        frame.setFlipInterval(1300)
        frame.startFlipping()
    }

    private fun progressbarEnable() {
        pr1.visibility = View.VISIBLE
        pr2.visibility = View.VISIBLE
        pr3.visibility = View.VISIBLE
        pr4.visibility = View.VISIBLE
        pr5.visibility = View.VISIBLE
        pr6.visibility = View.VISIBLE

    }

    private fun progressbarDisable() {
        pr1.visibility = View.INVISIBLE
        pr2.visibility = View.INVISIBLE
        pr3.visibility = View.INVISIBLE
        pr4.visibility = View.INVISIBLE
        pr5.visibility = View.INVISIBLE
        pr6.visibility = View.INVISIBLE
    }

}