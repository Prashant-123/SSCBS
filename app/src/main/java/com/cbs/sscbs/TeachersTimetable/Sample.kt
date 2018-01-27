package com.cbs.sscbs.TeachersTimetable

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.TextView
import com.cbs.sscbs.DataClass.CONSTANTS
import com.cbs.sscbs.Fragments.TimeTable_frag
import com.cbs.sscbs.Others.FullScreenImage
import com.cbs.sscbs.R
import com.cbs.sscbs.utils.FileDownloader
import com.google.firebase.database.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sample.*
import java.io.File
import java.io.IOException

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
                            val builder = AlertDialog.Builder(this@Sample)
                            builder.setTitle("Select")
                            builder.setItems(arrayOf<CharSequence>("View Image", "Download")
                            ) { dialog, which ->
                                // The 'which' argument contains the index position
                                // of the selected item
                                when (which) {
                                    0 -> {
                                        val intent: Intent = Intent(this@Sample, FullScreenImage::class.java)
                                        intent.putExtra(CONSTANTS.imageurl, url)
                                        startActivity(intent)
                                    }
                                    1 -> {
                                        TimeTable_frag.DownloadFile().execute(url, "Image.jpg")
                                        Log.wtf("TAG", "ok")
                                    }
                                }
                            }
                            builder.create().show()
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

    private class DownloadFile : AsyncTask<String, Void, Void>() {

        override fun doInBackground(vararg strings: String): Void? {
            val fileUrl = strings[0]   // -> http://maven.apache.org/maven-1.x/maven.pdf
            val fileName = strings[1]  // -> maven.pdf
            val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
            val folder = File(extStorageDirectory, "Down")
            folder.mkdir()

            val pdfFile = File(folder, fileName)

            try {
                pdfFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            FileDownloader.downloadFile(fileUrl, pdfFile)
            return null
        }
    }

}

