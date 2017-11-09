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

class TimeTable_frag : Fragment() {
    lateinit var firebasedb: FirebaseDatabase
    lateinit var firebaseref: DatabaseReference
    lateinit var sharedpref:SharedPreferences
    internal var bundle: Bundle? = null
    var courselist:ArrayList<String> = ArrayList(Arrays.asList("Bsc 1","Bsc 2", "Bsc 3","BMS","BFIA"))
    var courselist_bms:ArrayList<String> = ArrayList(Arrays.asList("First Year" , "Second Year" , "Third Year"))
    var courselist_bms_year:ArrayList<String> = ArrayList(Arrays.asList("BMS-A" , "BMS-B" , "BMS-C","BMS-D"))
    var courselist_bfia:ArrayList<String> = ArrayList(Arrays.asList("BFIA-A" , "BFIA-B"))



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.timetable_fragment, container, false)

        activity.toolbar.setTitle("Time Table")
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // progress_bar.visibility = View.INVISIBLE

        btn_change_course.setOnClickListener {
            MaterialDialog.Builder(activity)
                    .title("Select Course")
                    .items(courselist)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which, text ->
                        println("Created...")
                        var selectedindes:Int = which
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
        if(which == 3 ) {
            MaterialDialog.Builder(activity)
                    .title("Select Year")
                    .items(courselist_bms)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which_bms, text ->
                        println("Created...")
                        //var which_bms
                        var selectedindes:Int = which_bms
                        text_course.text = courselist_bms.get(which_bms)
                        getTimeTable_bms(which_bms)
                        true
                    })
                    .show()
        }

        else if(which == 4 ) {
            MaterialDialog.Builder(activity)
                    .title("Select Year")
                    .items(courselist_bms)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which_bms, text ->
                        println("Created...")
                        //var which_bms
                        var selectedindes:Int = which_bms
                        text_course.text = courselist_bms.get(which_bms)
                        getTimeTable_bfia(which_bms)
                        true
                    })
                    .show()
        }



        firebaseref = firebasedb.getReference("timetable/${which}")
       //progress_bar.visibility = View.VISIBLE

        firebaseref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
               // progress_bar.visibility = View.INVISIBLE
            }

            override fun onDataChange(p0: DataSnapshot?) {

                val url: String? = p0?.getValue(String::class.java)
                Log.d("url","url:${url}")

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
                //progress_bar.visibility = View.INVISIBLE

            }

        })
    }


    private fun getTimeTable_bms(which_bms: Int) {
        firebasedb = FirebaseDatabase.getInstance()
        // val tietable:Timetable = Timetable()

        if(which_bms == 0 || which_bms==1|| which_bms==2) {

            MaterialDialog.Builder(activity)
                    .title("Select Section")
                    .items(courselist_bms_year)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which_bms_year, text ->
                        println("Created...")
                        //var which_bms
                        var selectedindes:Int = which_bms_year
                        text_course.text = courselist_bms_year.get(which_bms_year)
                        getTimeTable_bms_year(which_bms_year)
                        true
                    })
                    .show()
        }
        //firebaseref = firebasedb.getReference("timetable_bms/${which_bms}")
       // progress_bar.visibility = View.VISIBLE

        firebaseref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
              // progress_bar.visibility = View.INVISIBLE
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)
                Log.d("url","url:${url}")

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
               // progress_bar.visibility = View.INVISIBLE

            }

        })
    }

    private fun getTimeTable_bfia(which_bms: Int) {
        firebasedb = FirebaseDatabase.getInstance()
        // val tietable:Timetable = Timetable()

        if(which_bms == 0 || which_bms==1|| which_bms==2) {

            MaterialDialog.Builder(activity)
                    .title("Select Section")
                    .items(courselist_bfia)
                    .itemsCallbackSingleChoice(-1, MaterialDialog.ListCallbackSingleChoice { dialog, view, which_bfia, text ->
                        println("Created...")
                        //var which_bms
                        var selectedindes:Int = which_bfia
                        text_course.text = courselist_bfia.get(which_bfia)
                        getTimeTable_bfia_year(which_bfia)
                        true
                    })
                    .show()
        }
        //firebaseref = firebasedb.getReference("timetable_bfia/${which_bms}")
        // progress_bar.visibility = View.VISIBLE

        firebaseref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                // progress_bar.visibility = View.INVISIBLE
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)
                Log.d("url","url:${url}")

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
                // progress_bar.visibility = View.INVISIBLE

            }

        })
    }

    private fun getTimeTable_bms_year(which_bms_year: Int) {
        firebasedb = FirebaseDatabase.getInstance()
        // val tietable:Timetable = Timetable()

        firebaseref = firebasedb.getReference("timetable_bms_section/${which_bms_year}")
       // progress_bar.visibility = View.VISIBLE

        firebaseref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
               // progress_bar.visibility = View.INVISIBLE
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)
                Log.d("url","url:${url}")

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
               // progress_bar.visibility = View.INVISIBLE

            }

        })
    }

    private fun getTimeTable_bfia_year(which_bms_year: Int) {
        firebasedb = FirebaseDatabase.getInstance()
        // val tietable:Timetable = Timetable()

        firebaseref = firebasedb.getReference("timetable_bfia_section/${which_bms_year}")
        // progress_bar.visibility = View.VISIBLE

        firebaseref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                // progress_bar.visibility = View.INVISIBLE
            }

            override fun onDataChange(p0: DataSnapshot?) {
                val url: String? = p0?.getValue(String::class.java)
                Log.d("url","url:${url}")

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
                // progress_bar.visibility = View.INVISIBLE

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
}
