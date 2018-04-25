package com.cbs.sscbs.NewsUpdates;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.cbs.sscbs.Fragments.Home_frag;
import com.cbs.sscbs.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class News_Frag extends Fragment {
    public News_Frag() {
    }

    RecyclerView recyclerView;
    NewsAdapter adapter;
    LinearLayout layout;
    ProgressBar bar;
    public static ArrayList<NewsAdapter.NewsDataClass> news = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Fetch_News().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_news, container, false);

        layout = myView.findViewById(R.id.loading);
        recyclerView = myView.findViewById(R.id.news_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsAdapter(getContext(), news);
        recyclerView.setAdapter(adapter);

        return myView;
    }

    public class Fetch_News extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            news.clear();
        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                Document document = Jsoup.connect(Home_frag.url).get();
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
            adapter.notifyDataSetChanged();
            layout.setVisibility(View.GONE);
        }
    }

}