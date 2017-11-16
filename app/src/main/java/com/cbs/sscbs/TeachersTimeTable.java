package com.cbs.sscbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class TeachersTimeTable extends AppCompatActivity {

    SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_time_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        sv = (SearchView) findViewById(R.id.mSearch) ;
        RecyclerView rv = (RecyclerView) findViewById(R.id.myRecycler) ;

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        final MyAdapter adapter = new MyAdapter(this , getTeachers()) ;
        rv.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });


     }

    private ArrayList<Teacher> getTeachers()
    {
        ArrayList<Teacher> teachers = new ArrayList<>() ;
        Teacher t = new Teacher();t.setName("Abhishek Tandon"); ; t.setPos("") ; t.setImg(R.drawable.abhihek_tandon) ; teachers.add(t) ;
        t = new Teacher() ;
        t.setName("Abhimanyu Verma"); ; t.setPos(""); ; t.setImg(R.drawable.abhimanyu_verma); ; teachers.add(t);
        t = new Teacher() ;
        t.setName("ajay Jaiswal") ; t.setPos("") ; t.setImg(R.drawable.ajay_jaiswal) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Amit Kumar") ; t.setPos("") ; t.setImg(R.drawable.amit_kumar) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Amrina Kausar") ; t.setPos("") ; t.setImg(R.drawable.amrina_kausar) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Anamika Gupta") ; t.setPos("") ; t.setImg(R.drawable.anamika_gupta) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Anuja Mathur") ; t.setPos("") ; t.setImg(R.drawable.anuja_mathur) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Anusha Goel") ; t.setPos("") ; t.setImg(R.drawable.anusha_goel) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Ashima Arora") ; t.setPos("") ; t.setImg(R.drawable.ashima_arora) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Hamendra Kumar Porwal") ; t.setPos("") ; t.setImg(R.drawable.hamendra_kumar_porwal) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Kavita Kanpur") ; t.setPos("") ; t.setImg(R.drawable.kavita_kapur) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Kavita Rastogi") ; t.setPos("") ; t.setImg(R.drawable.kavita_rastogi) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Kishori Ravi Shankar") ; t.setPos("") ; t.setImg(R.drawable.kishori_ravi_shankar) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Kuamr Bijoy") ; t.setPos("") ; t.setImg(R.drawable.kumar_bijoy) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Madhu Maheshwari") ; t.setPos("") ; t.setImg(R.drawable.madhu_maheshwari) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Mona Verma") ; t.setPos("") ; t.setImg(R.drawable.mona_verma) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Narendra Kumar Nigam") ; t.setPos("") ; t.setImg(R.drawable.narendra_kumar_nigam) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Neeraj Sehrawat") ; t.setPos("") ; t.setImg(R.drawable.neeraj_sehrawat) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Neha") ; t.setPos("") ; t.setImg(R.drawable.neha) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Nidhi Kesari") ; t.setPos("") ; t.setImg(R.drawable.nidhi_kesari) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Onkar Singh") ; t.setPos("") ; t.setImg(R.drawable.onkar_singh) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Paridhi") ; t.setPos("") ; t.setImg(R.drawable.paridhi) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Poonam Verma") ; t.setPos("") ; t.setImg(R.drawable.poonam_verma) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Preeti Rajpal Goyal") ; t.setPos("") ; t.setImg(R.drawable.preeti_rajpal_goyal) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Raj Kumar") ; t.setPos("") ; t.setImg(R.drawable.raj_kumar) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Ramesh Kumar Barpa") ; t.setPos("") ; t.setImg(R.drawable.ramesh_kumar_barpa) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Rishi Ranjan Sahay") ; t.setPos("") ; t.setImg(R.drawable.rishi_ranjan_sahay) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Rohini Singh") ; t.setPos("") ; t.setImg(R.drawable.rohini_singh) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Sameer Anand") ; t.setPos("") ; t.setImg(R.drawable.sameer_anand) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Sanjay Kumar") ; t.setPos("") ; t.setImg(R.drawable.sanjay_kumar) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Saumya Jain") ; t.setPos("") ; t.setImg(R.drawable.saumya_jain) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Shalini Prakash") ; t.setPos("") ; t.setImg(R.drawable.shalini_prakash) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Shikha Gupta") ; t.setPos("") ; t.setImg(R.drawable.shikha_gupta) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Sonika Thakral") ; t.setPos("") ; t.setImg(R.drawable.sonika_thakral) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Sushmita") ; t.setPos("") ; t.setImg(R.drawable.sushmita) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Tarranum Ahmed") ; t.setPos("") ; t.setImg(R.drawable.tarranum_ahmed) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Tushar Marwha") ; t.setPos("") ; t.setImg(R.drawable.tushar_marwha) ; teachers.add(t);

        return teachers ;
    }

}