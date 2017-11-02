package com.cbs.sscbs.Fragments


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.cbs.sscbs.CONSTANTS
import com.cbs.sscbs.FullScreenImage
import com.cbs.sscbs.R
import com.google.firebase.database.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.timetable_fragment.*
import java.util.*


/**
 * Created by gautam on 19/2/17.
 */

class TimeTable_frag : Fragment() {
    lateinit var firebasedb: FirebaseDatabase
    lateinit var firebaseref: DatabaseReference
    lateinit var sharedpref:SharedPreferences
    var selectedindes:Int = -1
    internal var bundle: Bundle? = null
    var courselist:ArrayList<String> = ArrayList(Arrays.asList("Bsc 1","Bsc 2", "Bsc 3","BMS","BMS 2"))


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.timetable_fragment, container, false)

        activity.toolbar.setTitle("Time Table")

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress_bar.visibility = View.INVISIBLE


        btn_change_course.setOnClickListener {
            MaterialDialog.Builder(activity)
                    .title("Select Course")
                    .items(courselist)
                    .itemsCallbackSingleChoice(selectedindes, MaterialDialog.ListCallbackSingleChoice { dialog, view, which, text ->
                        selectedindes = which
                        text_course.text = courselist.get(which)
                        getTimeTable(which)
                        true
                    })
                    .show()
        }

    }

    private fun getTimeTable(which: Int) {
        firebasedb = FirebaseDatabase.getInstance()
        // val tietable:Timetable = Timetable()
        firebaseref = firebasedb.getReference("timetable/${which}")
        progress_bar.visibility = View.VISIBLE

        firebaseref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                progress_bar.visibility = View.INVISIBLE
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)
                Log.d("url","url:${url}")
//                val file = File(Environment.getExternalStorageDirectory().toString(), "/sscbsimg/" + "${which}.jpg")
//                var dm:DownloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//                val request = DownloadManager.Request(
//                        Uri.parse(url))
//                request.setDestinationInExternalPublicDir("/sscbsimg/","${which}.jpg")
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//
//                var enqueue = dm.enqueue(request)
//                if(file.exists()){
//                    Picasso.with(context).load(file).into(image_timetable)
//                }else {
//                    Picasso.with(context).load(url).into(image_timetable, object : Callback {
//                        override fun onSuccess() {
//                            image_timetable.setOnClickListener {
//                                val intent: Intent = Intent(context, FullScreenImage::class.java)
//                                intent.putExtra(CONSTANTS.imageurl, url)
//                                startActivity(intent)
//                            }
//                        }
//
//                        override fun onError() {
//
//                            Toast.makeText(context, "Image Loading error", Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                }


                               Picasso.with(context).load(url).into(image_timetable,object: Callback{
                                   override fun onSuccess() {
                                       image_timetable.setOnClickListener {
                                           val intent: Intent = Intent(context, FullScreenImage::class.java)
                                           intent.putExtra(CONSTANTS.imageurl, url)
                                           startActivity(intent)
                                       }
                                   }
                                   override fun onError() {
                                       TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                   }

                               })

                // Picasso.with(context).load(url).into(PhotoLoader.getTarget(which.toString()+".jpeg"))
                progress_bar.visibility = View.INVISIBLE

            }

        })


    }

    companion object {

        fun newInstance(): TimeTable_frag {
            val fragment = TimeTable_frag()
            val bundle = Bundle()
            //bundle.putString(CONSTANT.fabintent,CONSTANT.fabintent);
            fragment.arguments = bundle
            return fragment
        }
    }




}
