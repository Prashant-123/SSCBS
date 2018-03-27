package com.cbs.sscbs.Others

//import com.cbs.sscbs.utils.web
//import com.rom4ek.arcnavigationview.ArcNavigationView
import am.appwise.components.ni.NoInternetDialog
import android.content.Context
import android.content.Intent
import android.os.*
import android.support.annotation.MainThread
import android.support.annotation.StringRes
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cbs.sscbs.Fragments.*
import com.cbs.sscbs.R
import com.cbs.sscbs.SideBar.About_Activity
import com.cbs.sscbs.SideBar.Gallery_Activity
import com.cbs.sscbs.auth.AuthUiActivity
import com.cbs.sscbs.utils.BottomNavigationViewHelper
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.thefinestartist.finestwebview.FinestWebView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.timetable_fragment.*


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
        //-------------------------------------------------------------------
        mDatabase = FirebaseDatabase.getInstance()
        reference = mDatabase.getReference("title/")
//        reference!!.child("title").child("child").setValue("Hi, there")

//        reference!!.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError?) {
//                progress_br.visibility = View.INVISIBLE
//            }
//
//            override fun onDataChange(p0: DataSnapshot?) {
//                for (dsp: DataSnapshot in p0!!.children) {
//                    Log.wtf("TAG", dsp.key.toString())
//                    list?.add(dsp.value.toString())
//                }
//                Log.wtf("TAG", "bhhihh"+list.toString())
//            }
//        })

        //-------------------------------------------------------------------

//         val getLink = FirebaseFirestore.getInstance().collection("Attendance")
//         getLink.get().addOnCompleteListener { task ->
//             if (task.isSuccessful)
//                 for (snapshot in task.result) {
//                     listBfia?.add(snapshot.id)
////                     Log.i("TAG", snapshot.id)
//                 }
//         }



        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null || currentUser!!.email!!.contains("gmail.com") == false) {
            startActivity(AuthUiActivity.createIntent(this))
            finish()
            return
        }
        setToolbar()
        setDrawer()
        setNavigationView()
        setbottomnavigator(savedInstanceState)
        NoInternetDialog.Builder(this).build()
    }

//    fun getDetails(users: Map<String, Object>){
//        val names: ArrayList<String>
//        for (entry: Map<String, Object> : users.entries){
//
//        }
//    }

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
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.ic_timetable -> {
                            val f = TimeTable_frag()
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            this.toolbar.setTitle("Time Table")
                            fragmentTransaction.replace(R.id.main_Frame, f).commit()
                        }

                        R.id.ic_updates -> {
                            val simpleAlert = AlertDialog.Builder(this@MainActivity).create()
                            simpleAlert.setTitle("Coming Soon...")
                            simpleAlert.setMessage("Waiting for API \uD83D\uDE42")
                            simpleAlert.show()
                        }
                        R.id.ic_events -> {
                            val nf = Events_Fragment()
                            val nm = fragmentManager.beginTransaction()
                            this.toolbar.setTitle("Events")
                            nm.replace(R.id.main_Frame, nf).commit()
                        }

                        R.id.ic_home -> {
                            val main_fragment = Home_frag()
                            val ft = supportFragmentManager.beginTransaction()
                            this.toolbar.setTitle("Welcome to CBS")
                            ft.replace(R.id.main_Frame, main_fragment).commit()
                        }
                        R.id.ic_attendence -> {
                            val pf = Attendance_Frag()
                            val fm = fragmentManager.beginTransaction()
                            this.toolbar.setTitle("Attendance")

                            getCls.get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    for (document in task.result) {
                                        classesList.add(document.id)
                                    }
                                }
                            }

                            fm.replace(R.id.main_Frame, pf).commit()
                        }
                    }
                    true
                })
    }

    private fun setDrawer() {
        mDrawerLayout = findViewById(R.id.drawerLayout)
        if (mDrawerLayout != null && toolbar != null) {
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
//        val userPic: ImageView = header.findViewById(R.id.user_profile_picture)
        val username: TextView = header.findViewById(R.id.user_display_name)
//        if (user.photoUrl != null) {
//            Glide.with(this)
//                    .load(user.photoUrl)
//                    .into(userPic)
//        }
        username.text = user.displayName?.capitalize()
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            Handler().postDelayed({ casebyid(id) }, 500)


            // mDrawerLayout.closeDrawers();
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
            R.id.qppr -> {
                FinestWebView.Builder(this).show("http://sscbs.bestbookbuddies.com/cgi-bin/koha/question-papers.pl")
            }
            R.id.about_college -> {
                val intent = Intent(this, About_Activity::class.java)
                startActivity(intent)
            }
            R.id.developers -> {
                val main_fragment = Developer_Frag()
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.main_Frame, main_fragment).commit()
            }
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
    private fun showSnackbar(@StringRes errorMessageRes: Int) {0
        Snackbar.make(drawerLayout, errorMessageRes, Snackbar.LENGTH_LONG).show()
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

//        @JvmStatic
//        fun getBMS(): String {
//            Log.i("TAG", listBfia.toString())
//            return listBfia.toString()
//        }

    }


    override fun onBackPressed() {
        AlertDialog.Builder(this@MainActivity)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id -> finish() }
                .setNegativeButton("No", null)
                .show()
    }

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

        constructor(`in`: Parcel) {
            logo = `in`.readInt()
            theme = `in`.readInt()
            providerInfo = ArrayList()
            `in`.readList(providerInfo, AuthUI.IdpConfig::class.java.classLoader)
            tosUrl = `in`.readString()
            isCredentialSelectorEnabled = `in`.readInt() != 0
            isHintSelectorEnabled = `in`.readInt() != 0
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

        companion object {

            val CREATOR: Parcelable.Creator<SignedInConfig> = object : Parcelable.Creator<SignedInConfig> {
                override fun createFromParcel(`in`: Parcel): SignedInConfig {
                    return SignedInConfig(`in`)
                }

                override fun newArray(size: Int): Array<SignedInConfig?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
