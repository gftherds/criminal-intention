package com.augmentis.ayp.crimin.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Therdsak on 8/4/2016.
 */
public class PictureUtils extends DialogFragment {
    public static Bitmap getScaleBitmap(String path, int destWidth, int destHeight ){


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;


        // return null and put meta data
        BitmapFactory.decodeFile(path,options);

        float  srcWidth = options.outWidth;
        float  srcHeight = options.outHeight;

        int inSampleSize = 1;

        if(srcHeight > destHeight || srcWidth > destHeight) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);

            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
            options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;

            return BitmapFactory.decodeFile(path , options);

        }


    public static Bitmap getScaleBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaleBitmap(path, size.x, size.y);
    }









    }

