package com.cbs.sscbs.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.cbs.sscbs.DataClass
import com.cbs.sscbs.Events
import com.cbs.sscbs.EventsAdapter
import com.cbs.sscbs.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

/**
 * Created by gautam on 19/2/17.
 */

class Events_Frag : Fragment() {


    internal var bundle: Bundle? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_events, container, false)

        val intent: Intent = Intent(context, Events::class.java)
        startActivity(intent)


//        val USERNAME = "username"
//        val PASSWORD = "password"
//        var database: FirebaseDatabase? = null
//         var databaseRef: DatabaseReference? = null
//         var imageView: ImageView? = null
//
//         var count: Int = 0; var i = 1
//        var data = ArrayList<DataClass>()
//
//        activity.toolbar.setTitle("Events")
//
//
//        rView.layoutManager = LinearLayoutManager(context)
//        rView.itemAnimator = DefaultItemAnimator()
//        val adapter = EventsAdapter(context, data)
//        rView.adapter = adapter
//

        return view
    }


    companion object {

        fun newInstance(): Events_Frag {
            val fragment = Events_Frag()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

}