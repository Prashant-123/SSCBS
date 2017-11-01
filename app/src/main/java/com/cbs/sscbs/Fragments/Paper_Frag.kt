package com.cbs.sscbs.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cbs.sscbs.R
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by gautam on 19/2/17.
 */

class Paper_Frag : Fragment() {


    internal var bundle: Bundle? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.paper_fragment, container, false)

        activity.toolbar.setTitle("Question Papers")



        return view
    }


    companion object {

        fun newInstance(): Paper_Frag {
            val fragment = Paper_Frag()
            val bundle = Bundle()
            //bundle.putString(CONSTANT.fabintent,CONSTANT.fabintent);
            fragment.arguments = bundle
            return fragment
        }
    }

}
