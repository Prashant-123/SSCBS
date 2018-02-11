package com.cbs.sscbs.Others;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.cbs.sscbs.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class Deadlines extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final String URL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";

    ArrayList<HashMap<String, String>> myData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadlines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_deadlines);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        new yourDataTask().execute();

    }

    public class yourDataTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {

            String str="https://api.androidhive.info/contacts/";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(URL);

                Log.i(TAG, "Response from url: " + jsonStr);

                JSONObject object = new JSONObject(jsonStr);
                JSONArray contacts = object.getJSONArray("Sheet1");

                Log.wtf(TAG, String.valueOf(contacts.length()));

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String total = c.getString("Total");
                    String attendance = c.getString("Attendance");
                    String waivers = c.getString("Waivers");

                    Log.wtf(TAG, name + "-> " + roll_no + "-> " + total + "-> " + attendance + "-> " + waivers);

                    HashMap<String, String> contact = new HashMap<>();

                    contact.put("Name", name);
                    contact.put("Roll_No", roll_no);
                    contact.put("Total", total);
                    contact.put("Attendance", attendance);
                    contact.put("Waivers", waivers);
                    myData.add(contact);
                }




//                return new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            Log.i(TAG, myData.toString());
        }
    }

}

