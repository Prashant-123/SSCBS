package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
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
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        viewFlipper.setInAnimation(fade_in)
        viewFlipper.setOutAnimation(fade_out)
        viewFlipper.setAutoStart(true)
        viewFlipper.setFlipInterval(1500)
        viewFlipper.startFlipping()

        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper2) as ViewFlipper
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        viewFlipper.setInAnimation(fade_in)
        viewFlipper.setOutAnimation(fade_out)
        viewFlipper.setAutoStart(true)
        viewFlipper.setFlipInterval(1500)
        viewFlipper.startFlipping()


        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper3) as ViewFlipper
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        viewFlipper.setInAnimation(fade_in)
        viewFlipper.setOutAnimation(fade_out)
        viewFlipper.setAutoStart(true)
        viewFlipper.setFlipInterval(1500)
        viewFlipper.startFlipping()


        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper4) as ViewFlipper
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        viewFlipper.setInAnimation(fade_in)
        viewFlipper.setOutAnimation(fade_out)
        viewFlipper.setAutoStart(true)
        viewFlipper.setFlipInterval(1500)
        viewFlipper.startFlipping()


        viewFlipper = this.findViewById<ViewFlipper>(R.id.flipper5) as ViewFlipper
        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        viewFlipper.setInAnimation(fade_in)
        viewFlipper.setOutAnimation(fade_out)
        viewFlipper.setAutoStart(true)
        viewFlipper.setFlipInterval(1500)
        viewFlipper.startFlipping()


    }


    fun setToolbar() {
        setSupportActionBar(toolbar_gallery)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar_gallery.setNavigationOnClickListener {
            onBackPressed()
            setTitle("Gallery")
        }
    }

    fun showImage() {
        firebasedb = FirebaseDatabase.getInstance()
        firebaseref = firebasedb.getReference("timetable/0")
        firebaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)

            }
        })
    }
}

