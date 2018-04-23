package com.cbs.sscbs.Fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbs.sscbs.NewsUpdates.NewsAdapter;
import com.cbs.sscbs.R;
import com.cbs.sscbs.TeachersTimetable.DayWiseTTDataClass;
import com.cbs.sscbs.TeachersTimetable.TeacherDataClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home_frag extends Fragment {
    public Home_frag() {
    }

    public static ArrayList<String> faculty_list = new ArrayList<>();
    public static ArrayList<String> bfia3List = new ArrayList<>();
    public static ArrayList<String> bms3List = new ArrayList<>();
    String user;
    public static ArrayList<DayWiseTTDataClass> dayData= new ArrayList<>();
    public static ArrayList<TeacherDataClass> data = new ArrayList<>();
    public static ArrayList<String> classes_alloted = new ArrayList<>();
    public static ArrayList<String> myClasses = new ArrayList<>();
    public static String url = "http://sscbs.du.ac.in/";
    public static ArrayList<NewsAdapter.NewsDataClass> news = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_home_frag, container, false);
        user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        faculty_list();                 //Get all teachers from Firebase to verify.
        faculty_subjects();             //Get subjects for Logged in User.
        Bfia_3_List();
        Class_List();
        Bms_3_List();
        new Fetch_News().execute();
        return myView;
    }

    public void faculty_list(){
        CollectionReference reference = FirebaseFirestore.getInstance().collection("Teachers");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                faculty_list.clear();
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                        faculty_list.add(snapshot.getId());
            }
        });
    }

    public void Bfia_3_List(){

        bfia3List.clear();

        CollectionReference reference = FirebaseFirestore.getInstance().collection("Attendance");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                        if (snapshot.getId().contains("BFIA-3"))
                            bfia3List.add(snapshot.getId());
            }
        });
    }
    public void Bms_3_List(){

        bms3List.clear();

        CollectionReference reference = FirebaseFirestore.getInstance().collection("Attendance");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                        if (snapshot.getId().contains("BMS-3F"))
                            bms3List.add(snapshot.getId());
            }
        });
    }

    public void Class_List(){

        myClasses.clear();
        CollectionReference reference = FirebaseFirestore.getInstance().collection("Attendance");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                            myClasses.add(snapshot.getId());
            }
        });
    }

    public void faculty_subjects(){
        CollectionReference getSubjects = FirebaseFirestore.getInstance().collection("Teachers").document(user).collection("Classes");
        getSubjects.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                classes_alloted.clear();
                if (task.isSuccessful())
                    for (DocumentSnapshot snapshot : task.getResult())
                        classes_alloted.add(snapshot.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "Faculty Subjects ERROR");
            }
        });
    }

    public class Fetch_News extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                Document document = Jsoup.connect(url).get();
                Elements text = document.select("div[class=gn_news]");

                List<String> desc = text.eachText();
                List<String> link = text.select("a").eachAttr("href");
                for (int i=0; i<desc.size(); i++){
                    NewsAdapter.NewsDataClass data = new NewsAdapter.NewsDataClass(desc.get(i), link.get(i));
                    news.add(data);
                }


            } catch (IOException e) {
            }
            return null;
        }

        protected void onPostExecute(Void s) {
        }
    }
}
