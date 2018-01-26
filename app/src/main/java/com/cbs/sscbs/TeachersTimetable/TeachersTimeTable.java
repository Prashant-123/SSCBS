package com.cbs.sscbs.TeachersTimetable;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cbs.sscbs.DataClass.Teacher;
import com.cbs.sscbs.R;

import java.util.ArrayList;

public class TeachersTimeTable extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_time_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tt);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final SearchView sv = (SearchView) findViewById(R.id.mSearch);
        RecyclerView rv = (RecyclerView) findViewById(R.id.myRecycler) ;

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        final TeachersAdapter adapter = new TeachersAdapter(this , getTeachers()) ;
        rv.setAdapter(adapter);

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.setIconified(false);
            }
        });
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
        Teacher t;
        t = new Teacher() ;
        t.setName("Abhimanyu Verma"); ; t.setPos(""); ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("He has done his graduation from University of Delhi and MBA (Marketing) from Guru Gobind Singh Indraprastha University, Delhi.");
        t.setEmail("vermaabhi@sscbsdu.ac.in");teachers.add(t);
        t= new Teacher();
        t.setName("Abhishek Tandon"); ; t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;
        t = new Teacher();
        t.setName("Ajay Jaiswal") ; t.setPos("") ; t.setImg(R.drawable.ajay_jaiswal) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Amit Kumar") ; t.setPos("") ; t.setImg(R.drawable.amit_kumar) ;
        t.setQualification("Amit Kumar has been working as an Assistant Professor at University of Delhi. His core areas of interest include taxation, corporate finance and international business");
        t.setEmail("amit.catlog@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Amrina Kausar") ; t.setPos("") ; t.setImg(R.drawable.amrina_kausar) ;
        t.setQualification(" She holds a Ph.D. & M.Phil degree in Inventory Management and Masters degree in Applied Operational Research from the Department of Operational Research, University of Delhi.");
        t.setEmail("amrinakausar@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Anamika Gupta") ; t.setPos("You Lost It :)") ; t.setImg(R.drawable.anamika_gupta) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Anuja Mathur") ; t.setPos("") ; t.setImg(R.drawable.anuja_mathur) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Anusha Goel") ; t.setPos("") ; t.setImg(R.drawable.anusha_goel) ;
        t.setQualification("Anusha Goel has graduated from Shri Ram College of Commerce and post graduation from Delhi School of Economics. she is pursuing M.Phil in Finance from DSE, University of Delhi.");
        t.setEmail("anusha.goel92@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Ashima Arora") ; t.setPos("") ; t.setImg(R.drawable.ashima_arora) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Hamendra Kumar Porwal") ; t.setPos("") ; t.setImg(R.drawable.hamendra_kumar_porwal) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Kavita Kanpur") ; t.setPos("") ; t.setImg(R.drawable.kavita_kapur) ;
        t.setQualification("Kavita Kapur completed Economics (Hons.) from Lady Shri Ram College and post-graduation in Business Economics from University of Delhi. ");
        t.setEmail("kavitakapur@sscbsdu.ac.in ");teachers.add(t);
        t = new Teacher() ;
        t.setName("Kavita Rastogi") ; t.setPos("") ; t.setImg(R.drawable.kavita_rastogi) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Kishori Ravi Shankar") ; t.setPos("") ; t.setImg(R.drawable.kishori_ravi_shankar) ;
        t.setQualification("Kishori Ravi Shankar has industry experience of about ten years at Karnataka Ballt Bearings Ltd and JIL Plastics Ltd . She has taught subjects like Legal Aspects of Indian Business, Entrepreneurship & Small Business.");
        t.setEmail("kishorirshankar@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Kuamr Bijoy") ; t.setPos("") ; t.setImg(R.drawable.kumar_bijoy) ;
        t.setQualification("Dr. Kumar Bijoy, a Chartered Financial Analyst and Masters in Economics, has completed his Ph. D in Finance (Debt Market) from the Department of Financial Studies, University of Delhi. ");
        t.setEmail("kumarbijoy@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Madhu Maheshwari") ; t.setPos("") ; t.setImg(R.drawable.madhu_maheshwari) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Madhu Totla") ; t.setPos("") ; t.setImg(R.drawable.madhu_maheshwari) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Mona Verma") ; t.setPos("") ; t.setImg(R.drawable.mona_verma) ;
        t.setQualification("Dr. Mona Verma has earned her Ph.D. in Inventory Management from  University of Delhi. She has obtained her M.Phil.in Inventory Management and Masters in Operational Research from University of Delhi");
        t.setEmail("monavermag@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Narendra Kumar Nigam") ; t.setPos("") ; t.setImg(R.drawable.narendra_kumar_nigam) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Neeraj Kumar Sehrawat") ; t.setPos("") ; t.setImg(R.drawable.neeraj_sehrawat) ;
        t.setQualification("Neeraj Kumar Sehrawat an Alumnus of IIM, Ahmedabad and FMS, University of Delhi and currently he is an Assistant Professor of Applied Accounting and finance in Department of Management Studies at SSCBS.");
        t.setEmail("nrjsehrawat@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Neha") ; t.setPos("") ; t.setImg(R.drawable.neha) ;
        t.setQualification("Neha has been teaching for 3 years. She has taught Human Resource Management, Organizational Behavior, Business Communication & Negotiation, Business Entrepreneurship & Management .");
        t.setEmail("neha1109@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Nidhi Kesari") ; t.setPos("") ; t.setImg(R.drawable.nidhi_kesari) ;
        t.setQualification("She received her Ph.D. in 2007 from University of Lucknow and M.Com and B.Com in 2004 and 2002 respectively from D.D.U Gorakhpur University. Her areas of specialization are Corporate Laws, CSR and HRM.");
        t.setEmail(" nidhikesari@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Onkar Singh") ; t.setPos("") ; t.setImg(R.drawable.onkar_singh) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Paridhi") ; t.setPos("") ; t.setImg(R.drawable.paridhi) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Poonam Verma") ; t.setPos("") ; t.setImg(R.drawable.poonam_verma) ;
        t.setQualification("Poonam Verma has been the Principal since May 2008. She joined the University of Delhi in 1985 as Assistant Professor and has taught at the under graduate and post graduate level.");
        t.setEmail(" principal@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Preeti Rajpal Goyal") ; t.setPos("") ; t.setImg(R.drawable.preeti_rajpal_goyal) ;
        t.setQualification("Preeti Rajpal Singh has completed her Bachelor’s degree in Economics from Lady Shri Ram College, University of Delhi, an M.B.A. from Faculty of Management Studies, University of Delhi and a Ph.D from University of Delhi.");
        t.setEmail(" preetirsingh@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Raj Kumar") ; t.setPos("") ; t.setImg(R.drawable.raj_kumar) ;
        t.setQualification(" He holds a Master’s degree in Economics from the Centre for International Trade & Development at Jawaharlal Nehru University, New Delhi. He completed his B.A.Economics from University of Delhi.");
        t.setEmail("rajkumarecon@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Ramesh Kumar Barpa") ; t.setPos("") ; t.setImg(R.drawable.ramesh_kumar_barpa) ;
        t.setQualification("He has worked with education department of H.P. Govt. as lecturer in commerce for 9 years. During his services he has done B.ed from IGNOU.");
        t.setEmail("rameshbarpa78@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Rishi Ranjan Sahay") ; t.setPos("") ; t.setImg(R.drawable.rishi_ranjan_sahay) ;
        t.setQualification("Dr. Sahay has a Ph.D in Operations Research from Delhi University. He got awarded Junior Research Fellowship (JRF) in Mathematical Sciences by UGC in year 2001. ");
        t.setEmail("rajansahay@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Rohini Singh") ; t.setPos("") ; t.setImg(R.drawable.rohini_singh) ;
        t.setQualification("Rohini Singh completed her Ph.D. from FMS, University of Delhi. She pursued her post-graduation in management from IIM, Ahmedabad, and graduation in Economics from LSR, University of Delhi.");
        t.setEmail("rohinisingh@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Sameer Anand") ; t.setPos("") ; t.setImg(R.drawable.sameer_anand) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Sanjay Kumar") ; t.setPos("") ; t.setImg(R.drawable.sanjay_kumar) ;
        t.setQualification("anjay Kumar Goyal completed his graduation from Shri Ram College of Commerce, his Post Graduation from Delhi School of Economics (University of Delhi) and his Ph.D. from Jamia Millia Islamia.");
        t.setEmail("sanjaygoyal@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Saumya Jain") ; t.setPos("") ; t.setImg(R.drawable.saumya_jain) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Shalini Prakash") ; t.setPos("") ; t.setImg(R.drawable.shalini_prakash) ;
        t.setQualification("Shalini Prakash has been teaching in the University of Delhi since September 1991. She has taught Micro-Economics, Macro-Economics and Statistics.");
        t.setEmail("shaliniprakash@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Shikha Gupta") ; t.setPos("") ; t.setImg(R.drawable.shikha_gupta) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Sonika Thakral") ; t.setPos("") ; t.setImg(R.drawable.sonika_thakral) ; teachers.add(t);
        t = new Teacher() ;
        t.setName("Sushmita") ; t.setPos("") ; t.setImg(R.drawable.sushmita) ;
        t.setQualification("Sushmita has received her Ph.D in the field of infrastructural macroeconomics . she also has a very rich corporate experience of the Business Intelligence Unit at ICICI Bank ltd. ");
        t.setEmail("sushmita.bhu@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher();
        t.setName("Tarranum Ahmed") ; t.setPos("") ; t.setImg(R.drawable.tarranum_ahmed) ;
        t.setQualification("Tarannum Ahmad has teaching experience of 22 years. Over the years, she has taught Human Resource Management, Training & Management Development, International Resource Management.");
        t.setEmail("tarannumahmad@sscbsdu.ac.in");teachers.add(t);
        t = new Teacher() ;
        t.setName("Tushar Marwha") ; t.setPos("") ; t.setImg(R.drawable.tushar_marwha) ;
        t.setQualification("Tushar Marwaha has completed his MBA from IMT, Ghaziabad and holds an M.A. (English) degree as well. He has a rich teaching experience (in management:");
        t.setEmail("tusharmarwaha@sscbsdu.ac.in");teachers.add(t);


        t= new Teacher();
        t.setName("Md. Rashid Shamim"); t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;
        t= new Teacher();
        t.setName("Suman Sia");t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;
        t= new Teacher();
        t.setName("Dinesh Meena"); t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;
        t= new Teacher();
        t.setName("Hemant Kumar"); t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;
        t= new Teacher();
        t.setName("Surbhi Jain"); t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;
        t= new Teacher();
        t.setName("Pratibha Maurya"); t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;
        t= new Teacher();
        t.setName("Kunjana Malik"); t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;
        t= new Teacher();
        t.setName("Gurjeet Kaur") ; t.setPos("") ; t.setImg(R.drawable.abhimanyu_verma);
        t.setQualification("Abhishek Tandon has done his MSc and PhD in Operational Research from Department of Operational Research, University of Delhi.");
        t.setEmail("abhishektandon@sscbsdu.ac.in");teachers.add(t) ;

        return teachers ;
    }

}