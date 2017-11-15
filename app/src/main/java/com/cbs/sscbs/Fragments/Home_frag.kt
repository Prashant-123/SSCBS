package com.cbs.sscbs.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cbs.sscbs.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Home_frag.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Home_frag.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home_frag : Fragment() {

    internal var bundle: Bundle? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home_frag, container, false)

        activity.toolbar.setTitle("Welcome to CBS")
        return view
    }


    companion object {

        fun newInstance(): Home_frag {
            val fragment = Home_frag()
            val bundle = Bundle()
            //bundle.putString(CONSTANT.fabintent,CONSTANT.fabintent);
            fragment.arguments = bundle
            return fragment
        }
    }
}// Required empty public constructor
