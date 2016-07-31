package com.augmentis.ayp.crimin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.util.Date;
import java.util.UUID;

/**
 * Created by Therdsak on 7/18/2016.
 */
public class CrimeFragment extends Fragment {
    private static final String CRIME_ID =  "CrimeFragment.CRIME_ID";
    private static final String CRIME_POSITION =  "CrimeFragment.CRIME_POS";
    private static final String DIALOG_DATE= "CrimeFragment.CRIME_DATE";
    private static final int REQUEST_DATE = 123234;
    private static final int REQUEST_TIME = 123456;
    private static final String DIALOG_TIME ="CrimeFragment.CRIME_TIME";


    private Crime crime;



    private Button   crimeTimeButton;
    private Button   crimeDateButton;
    private CheckBox crimeSloveCheckbox;
    private EditText editText;
    private Button   deleteButton;



    public CrimeFragment(){

    }
    public  static CrimeFragment newInstance(UUID crimeId, int position){
        Bundle args = new Bundle();


        args.putSerializable(CRIME_ID, crimeId);
        args.putInt(CRIME_POSITION, position);


        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        UUID crimeId =  (UUID) getArguments().getSerializable(CRIME_ID);


        crime = CrimeLab.getInstance(getActivity()).getCrimeById(crimeId);

        Log.d(CrimeListFragment.TAG, "crime.getTitle()=" + crime.getTitle());

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_crime, container,false);



        editText = (EditText) v.findViewById(R.id.crime_title);
        editText.setText(crime.getTitle());
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
        crimeDateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment diaglogFragment = DatePickerFragment.newInstance(crime.getCrimeDate());

                diaglogFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                diaglogFragment.show(fm, DIALOG_DATE);


            }

        });


        crimeTimeButton = (Button) v.findViewById(R.id.crime_button);
        crimeTimeButton.setText(getFormattedTime(crime.getCrimeDate()));
        crimeTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                TimePickerFragment dialogFragment = TimePickerFragment.newInstance(crime.getCrimeDate());
                dialogFragment.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialogFragment.show(fm, DIALOG_TIME);
             }
        });


        deleteButton = (Button) v.findViewById(R.id.crime_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CrimeLab crimeLab =  CrimeLab.getInstance(getActivity());
                crimeLab.getCrime().remove(crimeLab.getCrimeById(crime.getId()));
//                getActivity().finish();
            Intent intent = new Intent(getActivity(),CrimeListActivity.class);
                startActivity(intent);
            }

        });



        crimeSloveCheckbox = (CheckBox) v.findViewById(R.id.crime_slove);
        crimeSloveCheckbox.setChecked(crime.isSolve());
        crimeSloveCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolve(isChecked);

                Log.d(CrimeListFragment.TAG, "Crime:" + crime.toString());
            }
        });


        Intent intent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, intent);



        return v;

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);


                    crime.setCrimeDate(date);
                    crimeDateButton.setText(getFormattedDate(crime.getCrimeDate()));

        }
        if(requestCode == REQUEST_TIME){
            Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);


            crime.setCrimeDate(date);
            crimeTimeButton.setText(getFormattedTime(crime.getCrimeDate()));

        }
    }





        private String getFormattedDate(Date date){
        return new SimpleDateFormat("dd MMMM yyyy").format(date);
    }
    private String getFormattedTime(Date date){
        return new SimpleDateFormat("hh:mm").format(date);
    }



}
