package com.cbs.sscbs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Grievances extends AppCompatActivity {

    ImageView image ;
    private Uri imgUri;
    public static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievances);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_grievances);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        image = (ImageView) findViewById(R.id.imgComplaint ) ;
    }

    public void sendMail(View view)
    {
        EditText subject = (EditText)findViewById(R.id.subject);
        EditText body = (EditText) findViewById(R.id.body);
        String emailBody = body.getText().toString().trim();
        String emailSubject = subject.getText().toString().trim();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pk021998@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT , emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT ,emailBody );
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(imgUri)));
        intent.setType("image/*");

        startActivity(Intent.createChooser(intent,"Send Email Using"));
    }

    public void btnBrowse(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select-File"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data!= null && data.getData()!=null)
            imgUri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
            image.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
