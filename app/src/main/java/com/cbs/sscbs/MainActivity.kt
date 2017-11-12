package com.cbs.sscbs

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.MainThread
import android.support.annotation.StringRes
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cbs.sscbs.Fragments.*
import com.cbs.sscbs.auth.AuthUiActivity
import com.cbs.sscbs.utils.BottomNavigationViewHelper
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var mDrawerLayout:DrawerLayout
    lateinit var mActionBarDrawerToggle:ActionBarDrawerToggle
    lateinit var navigationView:NavigationView
    lateinit var user:FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startActivity(AuthUiActivity.createIntent(this))
            finish()
            return
        }
        setToolbar();
        setDrawer()
        setNavigationView()
        setbottomnavigator(savedInstanceState)
    }


    fun setToolbar() {
        setSupportActionBar(toolbar)
        setTitle("Time Table")
    }


    private fun setbottomnavigator(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            val main_fragment = TimeTable_frag()
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.main_Frame, main_fragment).commit()
        }


        bottomNavigationView = findViewById(R.id.bottom_navigation)
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView)
        bottomNavigationView.setSelectedItemId(R.id.ic_timetable)
        val fragmentManager = supportFragmentManager

        bottomNavigationView.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.ic_timetable -> {
                            val f = TimeTable_frag()
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.main_Frame, f).commit()


                        }

                        R.id.ic_paper -> {
                            val fragment = Paper_Frag()
                            val fragmentTransactions = fragmentManager.beginTransaction()
                            fragmentTransactions.replace(R.id.main_Frame, fragment).commit()

                        }
                        R.id.ic_events -> {
                            val nf = Events_Frag()
                            val nm = fragmentManager.beginTransaction()
                            nm.replace(R.id.main_Frame, nf).commit()
                        }

                        R.id.ic_deadlines -> {
                            val pf = DeadLine_Frag()
                            val fm = fragmentManager.beginTransaction()
                            fm.replace(R.id.main_Frame, pf).commit()
                        }
                        R.id.ic_attendence -> {
                            val pf = Attendence_Frag()
                            val fm = fragmentManager.beginTransaction()
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
                override fun onDrawerOpened(drawerView: View?) {
                    super.onDrawerOpened(drawerView)

                }

                override fun onDrawerClosed(drawerView: View?) {
                    super.onDrawerClosed(drawerView)

                }
            }
            mDrawerLayout.addDrawerListener(mActionBarDrawerToggle)
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true)
            mActionBarDrawerToggle.syncState()
        }
    }

    private fun setNavigationView() { // @Nullable
        user = FirebaseAuth.getInstance().currentUser!!

        navigationView = findViewById(R.id.navigation_view)
        navigationView.setItemIconTintList(null)
        val header = navigationView.getHeaderView(0)
        val userPic : ImageView = header.findViewById(R.id.user_profile_picture)
        val username: TextView = header.findViewById(R.id.user_display_name)
        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(userPic)
        }
        username.setText(user.displayName?.capitalize())
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
            R.id.about_college -> {
                val intent = Intent(this, About_Activity::class.java)
                startActivity(intent)
            }
            R.id.college_gallery -> {
                val intent1 = Intent(this, Gallery_Activity::class.java)
                startActivity(intent1)
            }

            R.id.correction -> {
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "gautamkumar268249@gmail.com", null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, " ")
                startActivity(Intent.createChooser(emailIntent, "Send email..."))
            }
            R.id.rec_friend -> try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_SUBJECT, "NCERT BOOK & SOLUTION")
                var sAux = "\nLet me recommend you this application\n\n"
                sAux = sAux + "https://play.googl"
                i.putExtra(Intent.EXTRA_TEXT, sAux)
                startActivity(Intent.createChooser(i, "Share this App \uD83C\uDF1D"))
            } catch (e: Exception) {
                //e.toString();
            }

            R.id.rate_us -> Rateus()
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
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    /*override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.ic_notification -> {
                val intent:Intent = Intent(applicationContext,Notification_Activity::class.java)
                startActivity(intent)

            }

        }
        return super.onOptionsItemSelected(item)
    }*/

}
