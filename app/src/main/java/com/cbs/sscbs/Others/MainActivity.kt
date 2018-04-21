package com.cbs.sscbs.Others

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.support.annotation.MainThread
import android.support.annotation.StringRes
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cbs.sscbs.Fragments.*
import com.cbs.sscbs.R
import com.cbs.sscbs.SideBar.About_Activity
import com.cbs.sscbs.SideBar.Gallery_Activity
import com.cbs.sscbs.TeachersTimetable.DayWiseTTDataClass
import com.cbs.sscbs.TeachersTimetable.TeacherDataClass
import com.cbs.sscbs.auth.AuthUiActivity
import com.cbs.sscbs.utils.BottomNavigationViewHelper
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mActionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView
    lateinit var user: FirebaseUser
    private lateinit var mDatabase: FirebaseDatabase
    private var reference: DatabaseReference? = null

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDatabase = FirebaseDatabase.getInstance()
        reference = mDatabase.getReference("title/")

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null || currentUser.email!!.contains("gmail.com") == false) {
            startActivity(AuthUiActivity.createIntent(this))
            finish()
            return
        }
        setToolbar()
        setDrawer()
        setNavigationView()
        setbottomnavigator(savedInstanceState)
         loadTT().execute()
         loadDayWiseTT().execute()
    }

    fun setToolbar() {
        setSupportActionBar(toolbar)
        title = "Welcome to SSCBS"
    }

    private fun setbottomnavigator(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val main_fragment = Home_frag()
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.main_Frame, main_fragment).commit()
        }

        var getCls = FirebaseFirestore.getInstance().collection("Attendance")
        var  classesList = ArrayList<String>()

        bottomNavigationView = this.findViewById(R.id.bottom_navigation)
       BottomNavigationViewHelper.disableShiftMode(bottomNavigationView)
        val fragmentManager = supportFragmentManager
        bottomNavigationView.setOnNavigationItemSelectedListener(
                { item ->
                    when (item.itemId) {

                        R.id.ic_home -> {
                            val main_fragment = Home_frag()
                            val ft = supportFragmentManager.beginTransaction()
                            this.toolbar.title = "Welcome to SSCBS"
                            ft.replace(R.id.main_Frame, main_fragment).commit()
                        }

                        R.id.ic_timetable -> {
                            val f = TimeTable_frag()
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            this.toolbar.title = "Time Table"
                            fragmentTransaction.replace(R.id.main_Frame, f).commit()
                        }

                        R.id.ic_attendence -> {
                            val pf = Attendance_Frag()
                            val fm = fragmentManager.beginTransaction()
                            this.toolbar.title = "Attendance"

                            getCls.get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    for (document in task.result) {
                                        classesList.add(document.id)
                                    }
                                }
                            }

                            fm.replace(R.id.main_Frame, pf).commit()
                        }

                        R.id.ic_events -> {
                            val nf = Events_Fragment()
                            val nm = fragmentManager.beginTransaction()
                            this.toolbar.title = "Events"
                            nm.replace(R.id.main_Frame, nf).commit()
                        }

                        R.id.ic_updates -> {
                            val simpleAlert = AlertDialog.Builder(this@MainActivity).create()
                            simpleAlert.setTitle("Coming Soon...")
                            simpleAlert.setMessage("Waiting for API \uD83D\uDE42")
                            simpleAlert.show()
                        }
                    }
                    true
                })
    }

    private fun setDrawer() {
        mDrawerLayout = findViewById(R.id.drawerLayout)
        if ( toolbar != null) {
            mActionBarDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.close, R.string.close) {

            }
            mDrawerLayout.addDrawerListener(mActionBarDrawerToggle)
            mActionBarDrawerToggle.isDrawerIndicatorEnabled = true
            mActionBarDrawerToggle.syncState()
        }
    }

    private fun setNavigationView() { // @Nullable
        user = FirebaseAuth.getInstance().currentUser!!

        navigationView = findViewById(R.id.navigation_view)
        navigationView.itemIconTintList = null
        val header = navigationView.getHeaderView(0)
        val userPic: ImageView = header.findViewById(R.id.user_profile_picture)
        val username: TextView = header.findViewById(R.id.user_display_name)
        if (user.photoUrl != null) {
            Glide.with(this)
                    .load(user.photoUrl)
                    .into(userPic)
        }
        username.text = user.displayName?.capitalize()
        navigationView.setNavigationItemSelectedListener({ item ->
            val id = item.itemId
            Handler().postDelayed({ casebyid(id) }, 500)
            mDrawerLayout.closeDrawer(GravityCompat.START)
            false
        })

    }

    private fun casebyid(id: Int) {

        when (id) {

            R.id.home -> {
                val main_fragment = Home_frag()
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.main_Frame, main_fragment).commit()
            }
            R.id.college_gallery -> {
                val intent1 = Intent(this, Gallery_Activity::class.java)
                startActivity(intent1)
            }
            R.id.grievances -> {
                val intent1 = Intent(this, Grievances::class.java)
                startActivity(intent1)
            }
            R.id.library -> {

                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                builder.setToolbarColor(titleColor)
                customTabsIntent.launchUrl(this, Uri.parse(getString(R.string.LibraryLink)))

            }
            R.id.about_college -> {
                val intent = Intent(this, About_Activity::class.java)
                startActivity(intent)
            }
            R.id.developers -> {
                val intent = Intent(this, Developers::class.java)
                startActivity(intent)
            }

            R.id.share -> Rateus()
            R.id.sign_out ->
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(AuthUiActivity.createIntent(this))
                                finish()
                            } else {
                                showSnackbar(R.string.sign_out_failed)
                            }
                        }
        }
    }

    @MainThread
    private fun showSnackbar(@StringRes errorMessageRes: Int) {
        Snackbar.make(drawerLayout, errorMessageRes, Snackbar.LENGTH_LONG).show()
    }

    private fun Rateus() {

        val uri = Uri.parse("market://details?id=" + packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)))
        }

    }

    companion object {
        private val EXTRA_IDP_RESPONSE = "extra_idp_response"
        private val EXTRA_SIGNED_IN_CONFIG = "extra_signed_in_config"

        @JvmStatic
        fun createIntent(
                context: Context,
                idpResponse: IdpResponse?,
                signedInConfig: SignedInConfig): Intent {

            val startIntent = Intent()
            if (idpResponse != null) {
                startIntent.putExtra(EXTRA_IDP_RESPONSE, idpResponse)
            }

            return startIntent.setClass(context, MainActivity::class.java)
                    .putExtra(EXTRA_SIGNED_IN_CONFIG, signedInConfig)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@MainActivity)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ -> finish() }
                .setNegativeButton("No", null)
                .show()
    }

    @SuppressLint("ParcelCreator")
    class SignedInConfig : Parcelable {
        var logo: Int = 0
        var theme: Int = 0
        var providerInfo: List<AuthUI.IdpConfig>
        var tosUrl: String
        var isCredentialSelectorEnabled: Boolean = false
        var isHintSelectorEnabled: Boolean = false

        constructor(logo: Int,
                    theme: Int,
                    providerInfo: List<AuthUI.IdpConfig>,
                    tosUrl: String,
                    isCredentialSelectorEnabled: Boolean,
                    isHintSelectorEnabled: Boolean) {
            this.logo = logo
            this.theme = theme
            this.providerInfo = providerInfo
            this.tosUrl = tosUrl
            this.isCredentialSelectorEnabled = isCredentialSelectorEnabled
            this.isHintSelectorEnabled = isHintSelectorEnabled
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeInt(logo)
            dest.writeInt(theme)
            dest.writeList(providerInfo)
            dest.writeString(tosUrl)
            dest.writeInt(if (isCredentialSelectorEnabled) 1 else 0)
            dest.writeInt(if (isHintSelectorEnabled) 1 else 0)
        }

        companion object
    }

    class loadTT : AsyncTask<String, Void, Void>() {

        override fun doInBackground(vararg strings: String): Void? {
            val databaseRef = FirebaseDatabase.getInstance().getReference("TeacherTimeTable")
            databaseRef.addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                    if (p0!!.hasChild("name") && p0.hasChild("image") && p0.hasChild("timetable")) {
                        val teacher_data = TeacherDataClass(p0.child("name").value?.toString(),
                                p0.child("image").value?.toString(), p0.child("timetable").value?.toString())
                        Home_frag.data.add(teacher_data)
                    } else
                        Log.wtf("TAG", "Nope")
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {}
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}
                override fun onCancelled(databaseError: DatabaseError) {
                    //                    Log.i(TAG, "hugiyujv");
                }
            })
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Home_frag.data.clear()
        }
    }

    class loadDayWiseTT : AsyncTask<String, Void, Void>() {

        override fun doInBackground(vararg strings: String): Void? {
            val databaseRef = FirebaseDatabase.getInstance().getReference("DayWiseTeacherTimeTable/Vipin Rathi/Monday")
            databaseRef.addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                    if (p0!!.hasChild("9:00am-10:00am") && p0.hasChild("10:00am-11:00am") && p0.hasChild("11:00am-12:00pm")
                             && p0!!.hasChild("12:00pm-01:00pm") &&p0!!.hasChild("01:00pm-02:00pm") &&p0!!.hasChild("02:00pm-03:00pm")
                            &&p0!!.hasChild("03:00pm-04:00pm") &&p0!!.hasChild("04:00pm-05:00pm") ) {
                        val teacher_DayData = DayWiseTTDataClass(p0.child("9:00am-10:00am").value?.toString(),
                                p0.child("10:00am-11:00am").value?.toString(), p0.child("11:00am-12:00pm").value?.toString(), p0.child("12:00pm-01:00pm").value?.toString(),
                                p0.child("01:00pm-02:00pm").value?.toString(), p0.child("02:00pm-03:00pm").value?.toString(),
                                p0.child("03:00pm-04:00pm").value?.toString(), p0.child("04:00pm-05:00pm").value?.toString())
                        Home_frag.dayData.add(teacher_DayData)
                    } else
                        Log.wtf("TAG", "Nope")//
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {}
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {}
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Home_frag.dayData.clear()
        }
    }
}
