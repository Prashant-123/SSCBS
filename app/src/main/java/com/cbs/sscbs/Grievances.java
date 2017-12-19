package com.cbs.sscbs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Grievances extends AppCompatActivity {

//    public static final int MY_REQUEST_CAMERA = 10;
//    public static final int MY_REQUEST_WRITE_CAMERA = 11;
//    public static final int CAPTURE_CAMERA = 12 ;
//
//    public static final int MY_REQUEST_READ_GALLERY = 13 ;
//    public static final int MY_REQUEST_WRITE_WRITE_GALLERY = 14 ;
//    public static final int MY_REQUEST_GALLERY = 15 ;
//
//    private BottomSheetBehavior slectPhotoBehaviour ;
//    private SimpleDraweeView swView ;

   // public File filen = null ;

    ImageView imageView ;
    Integer REQUEST_CAMERA =  1 , SELECT_FILE = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievances);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_grievances);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView = (ImageView) findViewById(R.id.imgComplaint ) ;

    }


    public void uploadImage(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


    }

    private void selectImage()
    {
        final CharSequence[] items = {"Camera" , "Gallery" , "Cancel"} ;
        AlertDialog.Builder builder = new AlertDialog.Builder(Grievances.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(items[which].equals("Camera")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null)
                       startActivityForResult(intent,REQUEST_CAMERA);

                }else if(items[which].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*") ;
                    startActivityForResult(intent.createChooser(intent,"Select File") , SELECT_FILE);
                }else if(items[which].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    @Override
    public void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap)bundle.get("data");
                imageView.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectImageUri = data.getData();
                imageView.setImageURI(selectImageUri);
            }
        }
    }

    public void sendMail(View view) {

        EditText subject = (EditText)findViewById(R.id.subject);
        EditText body = (EditText) findViewById(R.id.body);
        String emailBody = body.getText().toString().trim();
        String emailSubject = subject.getText().toString().trim();

        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("mailto","pk021998@gmail.com",null));
        intent.putExtra(Intent.EXTRA_SUBJECT , emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT ,emailBody );
        startActivity(Intent.createChooser(intent,"Send Email Using"));
    }
}
