package com.cbs.sscbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class st extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_st);
    }
 

    public void a(View view)
      {
          Intent adm = new Intent(this,Admin.class);
        startActivity(adm);}
    public void t(View view)
    {
        Intent teach= new Intent(this,Teacher.class);
        startActivity(teach);}
    public void s()
    {
        Intent stu = new Intent(this,Student.class);
        startActivity(stu);}

}
