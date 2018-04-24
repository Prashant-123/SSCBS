package com.cbs.sscbs.NewsUpdates;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cbs.sscbs.Fragments.Home_frag;
import com.cbs.sscbs.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by Tanya on 2/14/2018.
 */


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<NewsDataClass> objectList;
    private LayoutInflater inflater;
    public NewsAdapter(Context applicationContext, List<NewsDataClass> objectList) {
        inflater = LayoutInflater.from(applicationContext);
        this.objectList = objectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.news_sample, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        NewsDataClass object = objectList.get(position);
        holder.setData(object);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView news;
        RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.news = itemView.findViewById(R.id.news_text);
            this.layout = itemView.findViewById(R.id.layout);
        }

        public void setData(final NewsDataClass currentObject) {
            this.news.setText(currentObject.getNews());
            this.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomTabsIntent intent = new CustomTabsIntent.Builder().setToolbarColor(0).build();
                    intent.launchUrl(view.getContext(), Uri.parse(Home_frag.url+currentObject.getLink()));
                }
            });
        }
    }

    public static class NewsDataClass {
        String news, link;

        public NewsDataClass(String news, String link) {
            this.news = news;
            this.link = link;
        }

        public String getNews() {
            return news;
        }

        public void setNews(String news) {
            this.news = news;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
