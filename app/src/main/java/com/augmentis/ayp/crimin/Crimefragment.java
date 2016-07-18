package com.augmentis.ayp.crimin;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;

/**
 * Created by Therdsak on 7/18/2016.
 */
public class CrimeFragment extends Fragment {
    private Crime crime;
    private Button crimeDateButton;
    private CheckBox crimeSloveCheckbox;


    private EditText editText;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crime = new Crime();

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_crime, container,false);

        editText = (EditText) v.findViewById(R.id.crime_title);
        editText.addTextChangedListener(new TextWatcher()



        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        crimeDateButton = (Button) v.findViewById(R.id.crime_date);
        crimeDateButton.setText(new SimpleDateFormat("dd MMMM yyyy").format(crime.getCrimeDate()));
        crimeDateButton.setEnabled(false);





        crimeSloveCheckbox = (CheckBox) v.findViewById(R.id.crime_slove);
        crimeSloveCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolve(isChecked);
                Log.d(CrimeActivity.TAG, "Crime:" + crime.toString());
            }
        });



        return v;

    }




}
