package com.cbs.sscbs;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cbs.sscbs.Adapters.StudentsAdapter;
import com.cbs.sscbs.Fragments.Attendance_Frag;
import com.cbs.sscbs.Others.HttpHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowToStudents extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentsAdapter studentsAdapter;
    ImageView pic;
    String classIntent, roll, naam;
    TextView textView , setRollText;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private static final String URL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_rv);


        pic = findViewById(R.id.stuImg);
        recyclerView = (RecyclerView) findViewById(R.id.st_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        studentsAdapter = new StudentsAdapter(this  ,Attendance_Frag.allSub);
        recyclerView.setAdapter(studentsAdapter);

        textView = findViewById(R.id.r_name);
        setRollText = findViewById(R.id.roll_text);
        studentsAdapter.notifyDataSetChanged();
        classIntent = getIntent().getStringExtra("class");
        roll = getIntent().getStringExtra("roll");

        setRollText.setText(roll);
        Uri photoUrl = user.getPhotoUrl();
        Glide.with(this).load(photoUrl).into(pic);

        new showStudentName().execute();
        studentsAdapter.notifyDataSetChanged();

    }


    public class showStudentName extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(URL);
//              Log.i(TAG, "Response from url: " + jsonStr2);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray contacts = object.getJSONArray(classIntent);
//                Log.wtf(TAG, String.valueOf(contacts.length()));
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    if (roll.compareToIgnoreCase(c.getString("Roll_No"))==0)
                    {
                        naam = c.getString("Name");
//                        Log.i("TAG", c.getString("Roll_No"));
                        break;
                    }
                }

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
        }


    }


}
