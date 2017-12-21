package com.cbs.sscbs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    File pic = null;
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
    File imageFile;


    @Override
    public void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                Uri selectedImageURI = data.getData();
                imageFile = new File(getRealPathFromURI(selectedImageURI));

                final Bitmap bmp = (Bitmap)bundle.get("data");
                imageView.setImageBitmap(bmp);

                try {
                    String root = Environment.getExternalStorageDirectory() + "/ok.jpg";
//                    if (root.canWrite()){
//                        pic = new File(root);

                        FileOutputStream out = new FileOutputStream(imageFile);
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                    }
                 catch (IOException e){
                Log.e("BROKEN", "Could not write file " + e.getMessage());
            }


            }else if(requestCode==SELECT_FILE){

                Toast.makeText(this, "Select-File", Toast.LENGTH_SHORT).show();
                Uri selectImageUri = data.getData();
                imageView.setImageURI(selectImageUri);
                imageFile = new File(getRealPathFromURI(selectImageUri));
            }
        }
    }

    public void sendMail(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    EditText subject = (EditText)findViewById(R.id.subject);
                    EditText body = (EditText) findViewById(R.id.body);
                    String emailBody = body.getText().toString().trim();
                    String emailSubject = subject.getText().toString().trim();

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pk021998@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT , emailSubject);
                    intent.putExtra(Intent.EXTRA_TEXT ,emailBody );

                    //Uri fileUri = FileProvider.getUriForFile(getApplicationContext(), "com.myfileprovider", imageFile);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                File path = new File(Environment.getExternalStorageDirectory() + "/ok.jpg");
                Toast.makeText(Grievances.this, "PAth: " + path.getAbsolutePath(), Toast.LENGTH_LONG).show();

                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory() + "/ok.jpg"));

                    intent.setType("image/*");
                    //Toast.makeText(Grievances.this, "WORKING", Toast.LENGTH_SHORT).show();
                    startActivity(Intent.createChooser(intent,"Send Email Using"));
            }
        });
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
