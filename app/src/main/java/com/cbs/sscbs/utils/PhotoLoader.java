package com.cbs.sscbs.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
public class PhotoLoader  {
    //target to save
    public static Target getTarget(final String url){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.i("PRODUTOS_FOLDER", CreateAppFolder.getProdutosFolder());
                        File file = new File(Environment.getExternalStorageDirectory().toString() ,"/sscbsimg/" + url);
                        if (!file.exists()) {
                            file.mkdir();
                        }

                        try {
                        //    file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                            ostream.flush();
                            ostream.close();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("Drawable error ",errorDrawable.toString());

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
}