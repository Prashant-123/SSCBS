package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class Gallery_Activity : AppCompatActivity() {

    lateinit var firebasedb: FirebaseDatabase
    lateinit var firebaseref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_)
        setToolbar()
        showImage()

    }

    fun setToolbar() {
        setSupportActionBar(toolbar)
        setTitle("Gallery")
    }

    fun showImage()
    {
        firebasedb = FirebaseDatabase.getInstance()
        firebaseref = firebasedb.getReference("timetable/0")
        firebaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)

                //Glide.with(this@Gallery_Activity).load(url).into(image)
            }
        })
    }
}
