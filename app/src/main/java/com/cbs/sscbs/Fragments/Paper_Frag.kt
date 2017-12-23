package com.cbs.sscbs.Fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cbs.sscbs.R
import com.cbs.sscbs.web
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.paper_fragment.*


class Paper_Frag : Fragment() {


    internal var bundle: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.paper_fragment, container, false)

        activity.toolbar.setTitle("Question Papers")
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStarted.setOnClickListener {
            val intent: Intent = Intent(context, web::class.java)
            startActivity(intent)
        }

    }
}
