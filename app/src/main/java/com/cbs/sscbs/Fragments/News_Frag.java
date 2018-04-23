package com.cbs.sscbs.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbs.sscbs.NewsUpdates.NewsAdapter;
import com.cbs.sscbs.R;

public class News_Frag extends Fragment {
    public News_Frag() {
    }

    RecyclerView recyclerView;
    NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View myView = inflater.inflate(R.layout.fragment_news, container, false);
//        new Fetch_News().execute();

        recyclerView = myView.findViewById(R.id.news_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsAdapter(getContext(), Home_frag.news);
        recyclerView.setAdapter(adapter);

        return myView;
    }

}