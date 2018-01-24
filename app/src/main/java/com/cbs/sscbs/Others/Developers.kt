package com.cbs.sscbs.Others

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.cbs.sscbs.R

class Developers : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)
        val toolbar = findViewById<View>(R.id.toolbar_developers) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val goToRepo = findViewById<TextView>(R.id.GoToRepo) as TextView

        goToRepo.setOnClickListener(View.OnClickListener { view ->
//            val btnAnimation = ButtonAnimation()
//            btnAnimation.animateButton(view, applicationContext)
            val uri = Uri.parse("https:/github.com/Prashant-123/SSCBS")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        })
    }
}

class ButtonAnimation {
//    fun animateButton(v: View, context: Context) {
//        val pressAnim = AnimationUtils.loadAnimation(context, R.anim.button_press_anim)
//        v.startAnimation(pressAnim)
//    }
}
