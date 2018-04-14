package com.cbs.sscbs.Fragments


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.cbs.sscbs.R
import com.cbs.sscbs.TeachersTimetable.TeacherDataClass
import com.cbs.sscbs.TeachersTimetable.TeachersTimeTable
import com.cbs.sscbs.utils.CONSTANTS
import com.cbs.sscbs.utils.FileDownloader
import com.cbs.sscbs.utils.FullScreenImage
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.google.firebase.database.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.timetable_fragment.*
import java.io.File
import java.io.IOException
import java.util.*

class TimeTable_frag : Fragment() {

    lateinit var processBar: ProgressDialog

    lateinit var firebasedb: FirebaseDatabase
    lateinit var firebaseref: DatabaseReference
    internal var bundle: Bundle? = null
//    var data = ArrayList<TeacherDataClass>()
    var mProgressDialog: ProgressDialog? = null
    var courselist: ArrayList<String> = ArrayList(Arrays.asList("Bsc 1", "Bsc 2", "Bsc 3", "BMS", "BFIA"))
    var years: ArrayList<String> = ArrayList(Arrays.asList("First Year", "Second Year", "Third Year"))
    var bms_sections: ArrayList<String> = ArrayList(Arrays.asList("BMS-A", "BMS-B", "BMS-C", "BMS-D"))
    var bfia_sections: ArrayList<String> = ArrayList(Arrays.asList("BFIA-A", "BFIA-B"))

    var folder = ""

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.timetable_fragment, container, false)
        activity.toolbar.setTitle("Time Table")
        mProgressDialog = ProgressDialog(view.context);
        mProgressDialog!!.setMessage("A message");
        mProgressDialog!!.setIndeterminate(true);
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog!!.setCancelable(true);
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress_br.visibility = View.INVISIBLE
//        loadTT().execute()

        students_card.setOnClickListener {
            MaterialDialog.Builder(activity)
                    .title("Select Course")
                    .items(courselist)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, selectCourse, text ->
                        var selectedindes: Int = selectCourse
                        text_course.text = courselist.get(selectCourse)
                        if (selectCourse == 3 || selectCourse == 4)
                            others(selectCourse)
                        folder = getString(R.string.timetable)
                        showTimeTable(selectCourse)

                        true
                    })
                    .show()
        }

        teachers_card.setOnClickListener {
            val intent: Intent = Intent(context, TeachersTimeTable::class.java)
            startActivity(intent)

        }
    }

    private fun others(index: Int) {
        MaterialDialog.Builder(activity)
                .title("Select Year")
                .items(years)
                .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, Year, text ->
                    var selectedindes: Int = Year
                    text_course.text = years.get(Year)
                    if (index == 3)
                        getTimeTable_bms(Year)
                    if (index == 4)
                        getTimeTable_bfia(Year)
                    true
                })
                .show()
    }

    private fun getTimeTable_bms(index: Int) {
        if (index == 0 || index == 1 || index == 2) {
            if (index == 0) {
                folder = getString(R.string.bmsFirstYear)
            }
            if (index == 1) {
                folder = getString(R.string.bmsSecondYear)
            }
            if (index == 2) {
                folder = getString(R.string.bmsThirdYear)
            }

            MaterialDialog.Builder(activity)
                    .title("Select Section")
                    .items(bms_sections)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, indexBmsSection, text ->
                        var selectedindes: Int = indexBmsSection
                        text_course.text = bms_sections.get(indexBmsSection)
                        showTimeTable(indexBmsSection)

                        true
                    })
                    .show()
        }
    }

    private fun getTimeTable_bfia(index: Int) {
        if (index == 0 || index == 1 || index == 2) {
            if (index == 0) {
                folder = getString(R.string.bfiaFirstYear)
            }
            if (index == 1) {
                folder = getString(R.string.bfiaSecondYear)
            }
            if (index == 2) {
                folder = getString(R.string.bfiaThirdYear)
            }

            MaterialDialog.Builder(activity)
                    .title("Select Section")
                    .items(bfia_sections)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, indexBmsSection, text ->
                        var selectedindes: Int = indexBmsSection
                        text_course.text = bms_sections.get(indexBmsSection)
                        showTimeTable(indexBmsSection)
                        true
                    })
                    .show()
        }
    }

    private fun showTimeTable(number: Int) {
        firebaseref = FirebaseDatabase.getInstance().getReference("${folder}/${number}")
        progress_br.visibility = View.VISIBLE
        val imagePopup = ImagePopup(context)

        firebaseref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                progress_br.visibility = View.INVISIBLE
            }

            override fun onDataChange(p0: DataSnapshot?) {

                    val url: String? = p0?.getValue(String::class.java)

                    imagePopup.windowHeight = 800
                    imagePopup.windowWidth = 800
                    imagePopup.backgroundColor = Color.BLACK
                    imagePopup.isFullScreen = true
                    imagePopup.initiatePopupWithGlide(url)

                    Picasso.with(context).load(url).into(image_timetable, object : Callback {
                        override fun onSuccess() {
                            image_timetable.setOnClickListener {
                                imagePopup.viewPopup()
                            }
                        }

                        override fun onError() {
                            Toast.makeText(context, "Error getting your timetable :(", Toast.LENGTH_LONG).show()
                        }
                    })
                    progress_br.visibility = View.INVISIBLE
            }
        })
    }

    companion object {
        fun newInstance(): TimeTable_frag {
            val fragment = TimeTable_frag()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    class DownloadFile : AsyncTask<String, Void, Void>() {

            override fun doInBackground(vararg strings: String): Void? {
                val fileUrl = strings[0]   // -> http://maven.apache.org/maven-1.x/maven.pdf
                val fileName = strings[1]  // -> maven.pdf
                val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
                val folder = File(extStorageDirectory, "Download")
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

    class loadTT : AsyncTask<String, Void, Void>() {
        override fun doInBackground(vararg strings: String): Void? {
            val databaseRef = FirebaseDatabase.getInstance().getReference("TeacherTimeTable")
            databaseRef.addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                    if (p0!!.hasChild("name") && p0!!.hasChild("image") && p0!!.hasChild("timetable")) {
                        val teacher_data = TeacherDataClass(p0!!.child("name").value?.toString(),
                                p0!!.child("image").value?.toString(), p0!!.child("timetable").value?.toString())
                        Home_frag.data.add(teacher_data)
                    } else
                        Log.wtf("TAG", "Nope")
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {}
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}
                override fun onCancelled(databaseError: DatabaseError) {
                    //                    Log.i(TAG, "hugiyujv");
                }
            })
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Home_frag.data.clear()
        }
    }
}