package com.cbs.sscbs.Fragments

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import com.cbs.sscbs.CreateEvent
import com.cbs.sscbs.R
import com.cbs.sscbs.auth.AuthUiActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.attendence_fragment.*

class Attendence_Frag : Fragment() {

    val USERNAME = "username"
    val PASSWORD = "password"

    internal var i = 1
    internal var bundle: Bundle? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.attendence_fragment, container, false)

        activity.toolbar.setTitle("Attendance")

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GoogleSignInOptions() gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build();
        faculty.setOnClickListener{



                //inflateDescription();
                val inflater = layoutInflater
                val alertLayout = inflater.inflate(R.layout.fragment_login, null)
                val username = alertLayout.findViewById<EditText>(R.id.User)
                val password = alertLayout.findViewById<EditText>(R.id.Pass)
                val cbToggle = alertLayout.findViewById<CheckBox>(R.id.cb_show_pass)

                cbToggle.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked)
                        password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else
                        password.inputType = 129
                }

                val alert = AlertDialog.Builder(context)
                alert.setTitle("Sign-In")
                alert.setView(alertLayout)
                alert.setCancelable(false)
                alert.setNegativeButton("Cancel") { dialog, which -> Toast.makeText(context, "Log-In Cancelled", Toast.LENGTH_SHORT).show() }

                alert.setPositiveButton("Log In") { dialog, which ->
                    val mDocRef = FirebaseFirestore.getInstance().document("username/society")
                    Log.i("tag", "Log-1")
                    mDocRef.get().addOnSuccessListener { documentSnapshot ->
                        Log.i("tag", "Log-2")
                        if (documentSnapshot.exists()) {

                            Log.i("tag", "Log-3")
                            val u = documentSnapshot.getString(USERNAME)
                            val p = documentSnapshot.getString(PASSWORD)
                            if (username.text.toString() == u && password.text.toString() == p) {
                                Toast.makeText(context, "Authentication Successfull", Toast.LENGTH_SHORT).show()
                                createEvent()
                            } else
                                Toast.makeText(context, "You Lost it :)", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                val dialog = alert.create()
                dialog.show()

        }
        }


    fun createEvent() {
        val intent = Intent(context, CreateEvent::class.java)
        intent.putExtra("COUNT", i)
        startActivity(intent)
    }


    companion object {

        fun newInstance(): Attendence_Frag {
            val fragment = Attendence_Frag()
            val bundle = Bundle()
            //bundle.putString(CONSTANT.fabintent,CONSTANT.fabintent);
            fragment.arguments = bundle
            return fragment
        }
    }

}