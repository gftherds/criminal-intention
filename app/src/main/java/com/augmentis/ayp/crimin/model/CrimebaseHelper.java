package com.augmentis.ayp.crimin.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.augmentis.ayp.crimin.model.CrimeDbSchema.CrimeTable;

import java.util.jar.Attributes;

/**
 * Created by Therdsak on 8/1/2016.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {


    private static final  int VERSION = 5;
    private static final  String DATABASE_NAME = "crimeBase.db";
    private static final String TAG = "crimeBaseHelper";

    public CrimeBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
      Log.d(TAG, "Create Database");



        db.execSQL("CREATE TABLE " + CrimeTable.NAME
                + "("
                + "_id integer primary key autoincrement, "
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED + ","
                + CrimeTable.Cols.SUSPECT + ")"

        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG , "Running ");
        //1. rename table to _(oldversion)
        db.execSQL("alter table " + CrimeTable.NAME + " rename to " + CrimeTable.NAME+ "_" + oldVersion);
        //2. drop table
        db.execSQL("drop table if exists " + CrimeTable.NAME);

        //3. create new table
        db.execSQL("CREATE TABLE " + CrimeTable.NAME
                + "("
                + "_id integer primary key autoincrement, "
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED + ","
                + CrimeTable.Cols.SUSPECT
                + ")"

        );

            // 4. insert data

        db.execSQL("insert into " + CrimeTable.NAME
                + "("
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED
                + ")"
                + " select "
                + CrimeTable.Cols.UUID + ","
                + CrimeTable.Cols.TITLE + ","
                + CrimeTable.Cols.DATE + ","
                + CrimeTable.Cols.SOLVED + " from " + CrimeTable.NAME + "_" + oldVersion);

        db.execSQL("drop table if exists " + CrimeTable.NAME  + "_" + oldVersion);
    }
}
