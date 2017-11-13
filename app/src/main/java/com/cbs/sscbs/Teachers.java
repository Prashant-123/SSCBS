package com.cbs.sscbs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class Teachers extends AppCompatActivity {

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        setSupportActionBar(toolbar);

    }
}
