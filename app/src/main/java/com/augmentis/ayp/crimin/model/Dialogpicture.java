package com.augmentis.ayp.crimin.model;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.augmentis.ayp.crimin.R;

import java.io.File;

/**
 * Created by Therdsak on 8/4/2016.
 */
public class Dialogpicture extends DialogFragment {
    String pathpicture;
    ImageView dialogPicture;


    public static Dialogpicture newInstance(String photofile) {



        Bundle args = new Bundle();
        Dialogpicture fragment = new Dialogpicture();
        args.putSerializable("ARG_FILE",photofile);
        fragment.setArguments(args);
        return fragment;
    }




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        pathpicture = (String) getArguments().getSerializable("ARG_FILE");
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo,null);
        dialogPicture = (ImageView) v.findViewById(R.id.your_image);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        Bitmap bitmap = PictureUtils.getScaleBitmap(pathpicture , getActivity());
        dialogPicture.setImageBitmap(bitmap);




        return builder.create();






    }
}
