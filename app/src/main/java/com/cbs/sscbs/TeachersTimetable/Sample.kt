package com.cbs.sscbs.TeachersTimetable

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.cbs.sscbs.DataClass.CONSTANTS
import com.cbs.sscbs.Others.FullScreenImage
import com.cbs.sscbs.R
import com.google.firebase.database.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sample.*

class Sample : AppCompatActivity() {

    lateinit var firebasedb: FirebaseDatabase
    lateinit var firebaseref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        val toolbar = findViewById<View>(R.id.toolbar_grievances) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)



        val intentName = intent.extras!!.get("intentName") as String
        val tv1 = findViewById<TextView>(R.id.t_naam) as TextView
        tv1.text = intentName

        toolbar.setTitle(intentName)

        val intentQua = intent.extras!!.get("intentQualification") as String
        val tv2 = findViewById<TextView>(R.id.t_qua) as TextView
        tv2.text = intentQua

       val intentEmail = intent.extras!!.get("intentEmail") as String
        val tv3 = findViewById<TextView>(R.id.teacheremail_text) as TextView
        tv3.text = intentEmail

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

                Picasso.with(this@Sample).load(url).into(tt, object : Callback {
                    override fun onSuccess() {
                        tt.setOnClickListener {
                            val intent: Intent = Intent(this@Sample, FullScreenImage::class.java)
                            intent.putExtra(CONSTANTS.imageurl, url)
                            startActivity(intent)
                        }
                    }
                    override fun onError() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
                progress_bar.visibility = View.INVISIBLE
            }
        })
    }
}

