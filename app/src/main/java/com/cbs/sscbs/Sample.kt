package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sample.*

class Sample : AppCompatActivity() {

    lateinit var firebasedb: FirebaseDatabase
    lateinit var firebaseref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)



        val intentName = intent.extras!!.get("intentName") as String
        val tv1 = findViewById<TextView>(R.id.t_naam) as TextView
        tv1.text = intentName

        val intentQua = intent.extras!!.get("intentQualification") as String
        val tv2 = findViewById<TextView>(R.id.t_qua) as TextView
        tv2.text = intentQua

        val intentPos = intent.extras!!.getInt("intentPos")
        showTimeTable(intentPos)

    }

    fun showTimeTable(pos: Int) {
        firebasedb = FirebaseDatabase.getInstance()
        firebaseref = firebasedb.getReference("teacherTimetable/${pos}")
        progress_bar.visibility = View.VISIBLE

        firebaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                progress_bar.visibility = View.INVISIBLE
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)

                Glide.with(this@Sample).load(url).into(tt)

                progress_bar.visibility = View.INVISIBLE
            }
        })
    }
}
