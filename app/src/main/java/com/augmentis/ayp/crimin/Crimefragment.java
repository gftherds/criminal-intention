package com.augmentis.ayp.crimin;

import android.app.Activity;
import android.content.Intent;
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
import java.util.UUID;

/**
 * Created by Therdsak on 7/18/2016.
 */
public class CrimeFragment extends Fragment {
    private static final String CRIME_ID =  "CrimeFragment.CRIME_ID";
    private static final String CRIME_POSITION =  "CrimeFragment.CRIME_POS";


    private Crime crime;
    private int positon;



    private Button   crimeDateButton;
    private CheckBox crimeSloveCheckbox;
    private EditText editText;

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
        positon = (int) getArguments().getInt(CRIME_POSITION);

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
                addThisPositionToResult(positon);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        crimeDateButton = (Button) v.findViewById(R.id.crime_date);
        crimeDateButton.setText(new SimpleDateFormat("dd MMMM yyyy").format(crime.getCrimeDate()));
        crimeDateButton.setEnabled(false);





        crimeSloveCheckbox = (CheckBox) v.findViewById(R.id.crime_slove);
        crimeSloveCheckbox.setChecked(crime.isSolve());
        crimeSloveCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolve(isChecked);
                addThisPositionToResult(positon);
                Log.d(CrimeListFragment.TAG, "Crime:" + crime.toString());
            }
        });


        Intent intent = new Intent();
        intent.putExtra("position", positon);
        Log.d(CrimeListFragment.TAG,"Send position back" + positon);
        getActivity().setResult(Activity.RESULT_OK, intent);



        return v;

    }

   private void addThisPositionToResult(int position){
       if(getActivity() instanceof  CrimePagerActivity){
           ((CrimePagerActivity) getActivity()) .addPageUpdate(positon);
       }
   }



//    private String getFormattedDate(Date date){
//        return new SimpleDateFormat("dd MMMM yyyy").format(date);
//    }



}
