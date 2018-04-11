package com.cbs.sscbs.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cbs.sscbs.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class Developer_Frag : Fragment() {

    lateinit var reference: DatabaseReference

    internal var bundle: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_developer, container, false)
        activity.toolbar.setTitle("Developers")

        var image = view.findViewById<ImageView>(R.id.mem1)
        reference = FirebaseDatabase.getInstance().getReference("Developers/0")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val url = dataSnapshot.getValue(String::class.java)
                Picasso.with(view.context).load(url).into(image)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        var image1 = view.findViewById<ImageView>(R.id.mem2)
        reference = FirebaseDatabase.getInstance().getReference("Developers/1")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val url = dataSnapshot.getValue(String::class.java)
                Picasso.with(context).load(url).into(image1)
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        val mem1ln = view.findViewById<ImageView>(R.id.mem1linkedIn_image) as ImageView
        val mem2ln = view.findViewById<ImageView>(R.id.mem2linkedIn_image) as ImageView
        val mem1fb = view.findViewById<ImageView>(R.id.mem1facebook_image) as ImageView
        val mem2fb = view.findViewById<ImageView>(R.id.mem2facebook_image) as ImageView
        val mem1gt = view.findViewById<ImageView>(R.id.mem1github_image) as ImageView
        val mem2gt = view.findViewById<ImageView>(R.id.mem2github_image) as ImageView

        mem1ln.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://www.linkedin.com/in/prashant-kumar-86785012b/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
        mem2ln.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://www.linkedin.com/in/tanvi-goyal-2b34b015a/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
        mem1fb.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://www.facebook.com/pk021998")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
        mem2fb.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://www.facebook.com/profile.php?id=100012257649419")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
        mem1gt.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://github.com/Prashant-123")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
        mem2gt.setOnClickListener(View.OnClickListener { view ->
            val uri = Uri.parse("https://github.com/Tanvi-Goyal")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })

        return view
    }

}
