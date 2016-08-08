package com.augmentis.ayp.crimin;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
public abstract class SingleFragmentActivity extends AppCompatActivity {

//protected static final String TAG = "augmentist-ayp";


//เพื่อสร้างเป็นเเท็ปเล็ป โดยใช้ถูกกับ layout ของตัวที่ทำTablet
    @LayoutRes
    public int getLayoutResId()
    {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_crime);
        setContentView(getLayoutResId());


        Log.d(CrimeListFragment.TAG, "On create activity");

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);


        if (f == null)//เช็คว่ามีหรือยัง บอกว่าเจอไหม จะทำงานรอบเดียว
        {
            f = onCreateFragment();

            fm.beginTransaction().add(R.id.fragment_container, f)
                    .commit();

            Log.d(CrimeListFragment.TAG, "Fragment is creatd");

        } else {

            Log.d(CrimeListFragment.TAG, "Fagment have already beencreated");

        }
    }

    protected abstract Fragment onCreateFragment();
}
