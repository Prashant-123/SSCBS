package com.cbs.sscbs.Attendance;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbs.sscbs.Fragments.Attendance_Frag;
import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowToStudents extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentsAdapter studentsAdapter;
    ImageView pic;
    String link;
    String clas, roll, naam, month, year;
    TextView textView , setRollText, monthText;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Attendance");

//    private static final String bmsURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=18_YyZhOv3me5QWWPn_ByF_IPiSgvDYcq-W3RfQxkHvQ";
//    private static final String bscURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_rv);
        recyclerView = findViewById(R.id.st_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        studentsAdapter = new StudentsAdapter(this  ,Attendance_Frag.allSub);
        recyclerView.setAdapter(studentsAdapter);

        textView = findViewById(R.id.r_name);
        monthText = findViewById(R.id.monthText);
        setRollText = findViewById(R.id.roll_text);
        studentsAdapter.notifyDataSetChanged();
        clas = getIntent().getStringExtra("class");
        roll = getIntent().getStringExtra("roll");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("year");


        new showStudentName().execute();
        studentsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Attendance_Frag.allSub.clear();
    }

    public class showStudentName extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
//                reference.child(clas).child(year).
            }
            catch(Exception ex)
            {
                Log.e("TAG", "getListFromExcel", ex);
            }

            return null;


        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            textView.setText(naam);
            setRollText.setText(roll);
        }


    }


}
