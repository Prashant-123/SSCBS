package com.cbs.sscbs.Others;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cbs.sscbs.R;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grievances extends AppCompatActivity {

    ImageView image ;
    ArrayList<String> list = new ArrayList<>();
    private Uri imgUri;
    public static final int REQUEST_CODE = 1234;
    private static final int CAMERA_REQUEST = 1888;

    String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkAndRequestPermissions();

        setContentView(R.layout.activity_grievances);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_grievances);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        image = (ImageView) findViewById(R.id.imgComplaint ) ;

    }

    public void sendMSG(View view)
    {
        EditText subject = (EditText)findViewById(R.id.subject);
        EditText body = (EditText) findViewById(R.id.body);
        final String emailBody = body.getText().toString();

        AlertDialog.Builder b =  new  AlertDialog.Builder(this)
                .setTitle("Do you want to send a text message for immediate action?")
                .setPositiveButton("Leave a Message",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(!emailBody.isEmpty())
                                sendSMS(emailBody);
                                else
                                    Toast.makeText(Grievances.this, "Fill in your concern appropriately.", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );
        b.create();
        b.show();

    }

    public void sendSMS(String message){
        try {
            String redefinedMessage = "Dear Sir/Ma'am,\nI have the following concern: \n" + message + " \n\n\n Name: " + displayName;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+919711414586", null, redefinedMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, Please try again later!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void sendMail(View view){

        EditText subject = (EditText)findViewById(R.id.subject);
        EditText body = (EditText) findViewById(R.id.body);
        final String emailBody = body.getText().toString();
        String emailSubject = subject.getText().toString();

        if(!emailBody.isEmpty()&& !emailSubject.isEmpty()){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pk021998@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT , emailSubject);
            intent.putExtra(Intent.EXTRA_TEXT ,emailBody );
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(imgUri)));
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent,"Send Email Using"));
        }else
            Toast.makeText(this, "Fill in your concern appropriately.", Toast.LENGTH_SHORT).show();

    }

    public void btnBrowse(View view) {


        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Grievances.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    try{
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select-File"), REQUEST_CODE);
                    } catch (Exception e){
                        Toast.makeText(Grievances.this, "Select image to upload", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == CAMERA_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(photo);
                imgUri = getImageUri(getApplicationContext(), photo);
                grantUriPermission("com.cbs.sscbs", imgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

            } else
            {
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
        } catch (Exception e){
            Toast.makeText(this, "Select any image to upload", Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private boolean checkAndRequestPermissions() {

        int storagePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), CAMERA_REQUEST);
            return false;
        }

        return true;
    }
}
