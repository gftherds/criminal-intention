package com.augmentis.ayp.crimin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
public abstract class SingleFragmentActivity extends FragmentActivity {

protected static final String TAG = "augmentist-ayp";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);


        Log.d(TAG, "On create activity");

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);


        if (f == null)//เช็คว่ามีหรือยัง บอกว่าเจอไหม จะทำงานรอบเดียว
        {
            f = onCreateFragment();

            fm.beginTransaction().add(R.id.fragment_container, f)
                    .commit();

            Log.d(TAG, "Fragment is creatd");

        } else {

            Log.d(TAG, "Fagment have already beencreated");

        }
    }

    protected abstract Fragment onCreateFragment();
}
