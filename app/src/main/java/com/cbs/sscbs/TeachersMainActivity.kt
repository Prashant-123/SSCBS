package com.cbs.sscbs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView

/**
 * Created by Tanya on 11/10/2017.
 */
class TeachersMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teachers)

        var listView = findViewById<View>(R.id.listView) as ListView
        var arrTeachers: ArrayList<Teacher> = ArrayList()

        arrTeachers.add(Teacher("Abhishek Tandon", R.drawable.abhihek_tandon))
        arrTeachers.add(Teacher("Abhimanyu Verma", R.drawable.abhimanyu_verma))
        arrTeachers.add(Teacher("Ajay Jaiswal", R.drawable.ajay_jaiswal))
        arrTeachers.add(Teacher("Amit Kumar", R.drawable.amit_kumar))
        arrTeachers.add(Teacher("Amrina Kausar", R.drawable.amrina_kausar))
        arrTeachers.add(Teacher("Anamika Gupta", R.drawable.anamika_gupta))
        arrTeachers.add(Teacher("Anuja Mathur", R.drawable.anuja_mathur))
        arrTeachers.add(Teacher("Anuja Goel", R.drawable.anusha_goel))
        arrTeachers.add(Teacher("Ashima Arora", R.drawable.ashima_arora))
        arrTeachers.add(Teacher("Hamendra Kumar Porwal", R.drawable.hamendra_kumar_porwal))
        arrTeachers.add(Teacher("Kavita Kanpur", R.drawable.kavita_kapur))
        arrTeachers.add(Teacher("Kavita Rastogi", R.drawable.kavita_rastogi))
        arrTeachers.add(Teacher("Kishori Ravi Shankar", R.drawable.kishori_ravi_shankar))
        arrTeachers.add(Teacher("Kumar Bijoy", R.drawable.kumar_bijoy))
        arrTeachers.add(Teacher("Madhu Maheshwari", R.drawable.madhu_maheshwari))
        arrTeachers.add(Teacher("Mona Verma", R.drawable.mona_verma))
        arrTeachers.add(Teacher("Narendra Kumar Gupta", R.drawable.narendra_kumar_nigam))
        arrTeachers.add(Teacher("Neeraj Sehrawat", R.drawable.neeraj_sehrawat))
        arrTeachers.add(Teacher("Neha", R.drawable.neha))
        arrTeachers.add(Teacher("Nidhi Kesari", R.drawable.nidhi_kesari))
        arrTeachers.add(Teacher("Onkar Singh", R.drawable.onkar_singh))
        arrTeachers.add(Teacher("Paridhi", R.drawable.paridhi))
        arrTeachers.add(Teacher("Poonam Verma", R.drawable.poonam_verma))
        arrTeachers.add(Teacher("Preeti Rajpal Goyal", R.drawable.preeti_rajpal_goyal))
        arrTeachers.add(Teacher("Raj Kumar", R.drawable.raj_kumar))
        arrTeachers.add(Teacher("Ramesh Kumar Barpa", R.drawable.ramesh_kumar_barpa))
        arrTeachers.add(Teacher("Rishi ranjan Sahay", R.drawable.rishi_ranjan_sahay))
        arrTeachers.add(Teacher("Rohini Singh", R.drawable.rohini_singh))
        arrTeachers.add(Teacher("Sameer Anand", R.drawable.sameer_anand))
        arrTeachers.add(Teacher("Sanjay Kumar", R.drawable.sanjay_kumar))
        arrTeachers.add(Teacher("Saumaya Jain", R.drawable.saumya_jain))
        arrTeachers.add(Teacher("Shalini Prakash", R.drawable.shalini_prakash))
        arrTeachers.add(Teacher("Shikha Gupta", R.drawable.shikha_gupta))
        arrTeachers.add(Teacher("Sonika Thakral", R.drawable.sonika_thakral))
        arrTeachers.add(Teacher("Sushmita", R.drawable.sushmita))
        arrTeachers.add(Teacher("Tarranum Ahmed", R.drawable.tarranum_ahmed))
        arrTeachers.add(Teacher("Tushar Marwha", R.drawable.tushar_marwha))

        listView.adapter = CustomAdapter(applicationContext, arrTeachers)


    }
}

