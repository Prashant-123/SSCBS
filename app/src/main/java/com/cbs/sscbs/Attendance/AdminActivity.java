package com.cbs.sscbs.Attendance;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cbs.sscbs.Others.HttpHandler;
import com.cbs.sscbs.R;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

   private static final String CLASSURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=16WP-U687v4q2MtsJbHM-yCkqQK856tJ5IkiYvgowe90";
    private static final String SUBURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1ztpTfrOZ-Ntehx01ab5jRNqQa96cvqbDcDS0nPekVDI";
    private static final String CLASSLISTURL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=1E9NuomsFVbCqIu_HwG5EXO9XSWDDAcnLw470JlF6Q-Y";
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());
    String getYear = formattedDate.substring(7, 11);
    String getMonth = formattedDate.substring(3, 6);
    Map<String, Object> default_map1 = new HashMap<>();
    Map<String, Object> default_map2 = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button uploadClassList = findViewById(R.id.uploadClassList);
        uploadClassList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new uploadListFromExcel().execute();
            }
        });
    }

    public class uploadListFromExcel extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr1 = sh.makeServiceCall(CLASSURL);
                String jsonStr = sh.makeServiceCall(CLASSLISTURL);
                String jsonStr2 = sh.makeServiceCall(SUBURL);
                JSONObject object = new JSONObject(jsonStr);
                JSONArray contacts = object.getJSONArray("Bsc-2");
                JSONObject object2 = new JSONObject(jsonStr2);
                JSONArray contacts2 = object2.getJSONArray("Bsc-2");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    String name = c.getString("Name");
                    String roll_no = c.getString("Roll_No");
                    String grp = c.getString("Lab_Group");

                    default_map1.put("name", name);
                   // default_map1.put("group", grp);
                    db.collection("Attendance").document("Bsc-2").collection("Students")
                            .document(roll_no).set(default_map1);

                    for (int j = 0; j < contacts2.length(); j++) {
                        JSONObject c2 = contacts2.getJSONObject(j);
                        String sub = c2.getString("Subjects");

                        default_map2.put("attendance", 0);
                        default_map2.put("total", 0);

                        db.collection("Attendance").document("Bsc-2").collection("Students")
                                .document(roll_no).collection("Subjects").document(sub).collection("Year").document(getYear)
                                .collection("Months").document(getMonth).set(default_map2);
                    }
                }
                Toast.makeText(AdminActivity.this, "List Uploaded", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Log.e("TAG", "getListFromExcel", ex);
                Toast.makeText(AdminActivity.this, "An Error Occured! Please try Again", Toast.LENGTH_LONG).show();
            }

            return null;
        }
    }
}
