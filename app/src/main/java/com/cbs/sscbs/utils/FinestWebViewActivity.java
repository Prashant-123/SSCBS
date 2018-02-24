package com.cbs.sscbs.utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cbs.sscbs.R;
import com.thefinestartist.finestwebview.FinestWebView;

public class FinestWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finest_web_view);

        new FinestWebView.Builder(this).show("http://sscbs.du.ac.in/");

    }
}
