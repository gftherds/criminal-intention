package com.augmentis.ayp.crimin;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Therdsak on 7/18/2016.
 */
public class CrimeListFragment extends Fragment {
    private static final int REQUEST_UPDATE_CRIME = 200;
    private static final String SUBTITLE_VISIBLE_STATE = "" ;
    private RecyclerView _crimeRecyelerView;


    private CrimeAdapter _adapter;

    protected static final String TAG = "CRIME_LIST";
    private Integer[] crimePos;

        private boolean _subtitleVisible;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_crime_list, container, false);


        _crimeRecyelerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        _crimeRecyelerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null){
            _subtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBLE_STATE);
        }

        updateUI();


        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.crime_list_menu, menu);


        MenuItem menuItem = menu.findItem(R.id.menu_item_show_subtitle);

        if(_subtitleVisible) {
            menuItem.setTitle(R.string.hide_subtitle);
        }else{
            menuItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime :

                Crime crime = new Crime();


                CrimeLab.getInstance(getActivity()).addCrime(crime); // TODO : Add addCrime() to Crime
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);


                return  true;

            case R.id.menu_item_show_subtitle:

                _subtitleVisible = !_subtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Update UI
     */

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrime();

        if (_adapter == null) {
            _adapter = new CrimeAdapter(crimes);
            _crimeRecyelerView.setAdapter(_adapter);
        } else {
          _adapter.notifyDataSetChanged();
          //  if(crimePos != null) {
              //  for (Integer pos : crimePos){
                   // _adapter.notifyItemChanged(pos);
             //   Log.d(TAG, "" + crimePos);
            }
      //      }

  //  }
            updateSubtitle();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Resume list");
        updateUI();
    }





    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleTextView;
        public TextView dateTextView;
        public CheckBox solvedcheckbox;


        Crime _crime;
       int _position;


        public CrimeHolder(View itemView) {
            super(itemView);



            titleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            solvedcheckbox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);
            dateTextView =(TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);

            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime, int position) {
            _crime = crime;
            _position = position;
            titleTextView.setText(_crime.getTitle());
            dateTextView.setText(_crime.getCrimeDate().toString());
            solvedcheckbox.setChecked(_crime.isSolve());

        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), "Press ! " + titleTextView.getText(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "send position : " + _position);
            Intent intent = CrimePagerActivity.newIntent(getActivity(), _crime.getId());
            startActivityForResult(intent, REQUEST_UPDATE_CRIME);
        }
    }

    private  class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> _crimes;
        private int viewCreatingCount;
        public CrimeAdapter(List<Crime> crimes){

            _crimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            viewCreatingCount++;
            Log.d(TAG, "Blind view holder for CrimeList : viewType = "+ viewCreatingCount);


            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_crime , parent, false);

            return new CrimeHolder(v);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Log.d(TAG, "Blind view holder for CrimeList : position = " + position);

            Crime crime = _crimes.get(position);
            holder.bind(crime, position);

        }


        @Override
        public int getItemCount()
        {

            return _crimes.size();
        }
    }

        public void updateSubtitle() {
            CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
            int crimeCount = crimeLab.getCrime().size();
            String subtitle = getString(R.string.subtitle_format, crimeCount);

            if(!_subtitleVisible) {
                subtitle = null;
            }
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
        }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(SUBTITLE_VISIBLE_STATE, _subtitleVisible);
    }
}

