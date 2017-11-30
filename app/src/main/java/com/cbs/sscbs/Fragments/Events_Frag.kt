package com.cbs.sscbs.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cbs.sscbs.Events
import com.cbs.sscbs.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by gautam on 19/2/17.
 */

class Events_Frag : Fragment() {


    internal var bundle: Bundle? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_events, container, false)

        activity.toolbar.setTitle("Events")
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent: Intent = Intent(context, Events::class.java)
        startActivity(intent)
    }

    companion object {

        fun newInstance(): Events_Frag {
            val fragment = Events_Frag()
            val bundle = Bundle()
            //bundle.putString(CONSTANT.fabintent,CONSTANT.fabintent);
            fragment.arguments = bundle
            return fragment
        }
    }

}