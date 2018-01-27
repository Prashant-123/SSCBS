package com.cbs.sscbs.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cbs.sscbs.R
import kotlinx.android.synthetic.main.activity_main.*


class Developer_Frag : Fragment() {


    internal var bundle: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_developer, container, false)
        activity.toolbar.setTitle("Developers")

//        val goToRepo = view.findViewById<TextView>(R.id.GoToRepo) as TextView
//        val mem1ln = view.findViewById<ImageView>(R.id.mem1linkedIn_image) as ImageView
//        val mem2ln = view.findViewById<ImageView>(R.id.mem2linkedIn_image) as ImageView
//        val mem1fb = view.findViewById<ImageView>(R.id.mem1facebook_image) as ImageView
//        val mem2fb = view.findViewById<ImageView>(R.id.mem2facebook_image) as ImageView
//        val mem1gt = view.findViewById<ImageView>(R.id.mem1github_image) as ImageView
//        val mem2gt = view.findViewById<ImageView>(R.id.mem2github_image) as ImageView

//        goToRepo.setOnClickListener(View.OnClickListener { view ->
//            val uri = Uri.parse("https:/github.com/Prashant-123/SSCBS")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
//        mem1ln.setOnClickListener(View.OnClickListener { view ->
//            val uri = Uri.parse("https://www.linkedin.com/in/prashant-kumar-86785012b/")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
//        mem2ln.setOnClickListener(View.OnClickListener { view ->
//            val uri = Uri.parse("https:/github.com/Prashant-123/SSCBS")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
//        mem1fb.setOnClickListener(View.OnClickListener { view ->
//            val uri = Uri.parse("https://www.facebook.com/pk021998")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
//        mem2fb.setOnClickListener(View.OnClickListener { view ->
//            val uri = Uri.parse("https://www.facebook.com/profile.php?id=100012257649419")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
//        mem1gt.setOnClickListener(View.OnClickListener { view ->
//            val uri = Uri.parse("https://github.com/Prashant-123")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })
//        mem2gt.setOnClickListener(View.OnClickListener { view ->
//            val uri = Uri.parse("https://github.com/Tanvi-Goyal")
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
//        })


        return view
    }
}
