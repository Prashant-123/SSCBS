package com.cbs.sscbs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;

public class SampleWEB extends AppCompatActivity {


    WebView wv;
    Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_web);

        wv = (WebView) findViewById(R.id.sampleWeb);
        wv.getSettings().setJavaScriptEnabled(false);
        new LoadData().execute();
    }

    private class  LoadData extends AsyncTask<Void,Void,Void>
    {
        String primeDiv="div2";
        String html=new String();
        Document doc = null;
        @Override
        protected Void doInBackground(Void... params) {

            try {

                doc = (Document) Jsoup.connect("http://10.0.2.2/foodbookdhaka/webview.html").timeout(100000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //get total document

            Elements alldivs=doc.select("div");
            ArrayList<String> list=new ArrayList<String>();

            for(org.jsoup.nodes.Element e: alldivs)
            {
                if(!e.id().equals(""))
                    list.add(e.id());
            }
            //removing all <div> without "div2"
            for(int i=0;i<list.size();i++)
            {
                if(!list.get(i).equals(primeDiv))
                    doc.select("div[id="+list.get(i)+"]").remove();
            }

            html=alldivs.outerHtml();

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            wv.loadDataWithBaseURL(null,doc.,
                    "text/html", "utf-8", null);
//            String summary = "<html><body>You scored <b>192</b> points.</body></html>";
//            wv.loadData(summary, "text/html", null);

        }
    }


}

