package com.cbs.sscbs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class st extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st);
    }
      public void adminac()
      {
          Intent adm = new Intent(this,Admin.class);
        startActivity(adm);}
    public void teacher()
    {
        Intent teach= new Intent(this,Teacher.class);
        startActivity(teach);}
    public void stud()
    {
        Intent stu = new Intent(this,Student.class);
        startActivity(stu);}

}
