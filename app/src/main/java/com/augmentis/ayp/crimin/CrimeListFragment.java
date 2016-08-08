package com.augmentis.ayp.crimin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.augmentis.ayp.crimin.model.Crime;
import com.augmentis.ayp.crimin.model.CrimeCursorWrapper;
import com.augmentis.ayp.crimin.model.CrimeDbSchema;
import com.augmentis.ayp.crimin.model.CrimeLab;
import com.augmentis.ayp.crimin.model.PictureUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Therdsak on 7/18/2016.
 */
public class CrimeListFragment extends Fragment {
    private static final int REQUEST_UPDATE_CRIME = 200;
    private static final String SUBTITLE_VISIBLE_STATE = "" ;
    private RecyclerView _crimeRecyelerView;
    public TextView titleText1View;

    private CrimeAdapter _adapter;

    protected static final String TAG = "CRIME_LIST";
    private Integer[] crimePos;
    private boolean _subtitleVisible;
    private Callbacks callbacks;



    public interface  Callbacks{
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        List<Crime> crimes = CrimeLab.getInstance(getActivity()).getCrime();

        if(!crimes.isEmpty()) {
            Crime crime = CrimeLab.getInstance(getActivity()).getCrime().get(0);
            callbacks.onCrimeSelected(crime);

        }


        _crimeRecyelerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        _crimeRecyelerView.setLayoutManager(new LinearLayoutManager(getActivity()));







        //// TODO: 8/3/2016 what is this??
        Log.d("Golf", "onCreateView: savedInstanceState "+savedInstanceState);
        if(savedInstanceState != null){
            _subtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBLE_STATE);
        }
        Log.d("Golf", "onCreateView: _subtitleVisible : "+ _subtitleVisible);




        titleText1View = (TextView) v.findViewById(R.id.add_to_list);




        updateUI();

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crime_list_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_show_subtitle);
            //เป็นการเช็คให้ menu ด้านบนเปลี่ยนไอคอนในการทำงานต่างๆ
        if(_subtitleVisible) {
            menuItem.setIcon(R.drawable.ic_hide_subtitle);
            menuItem.setTitle(R.string.hide_subtitle);
        }else{
            menuItem.setIcon(R.drawable.ic_show_subtitle);
            menuItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime :

                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime); // TODO : Add addCrime() to Crime
//                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
//                startActivity(intent);

                //support  tablet

                updateUI();
                callbacks.onCrimeSelected(crime);

                return  true;

            case R.id.menu_item_show_subtitle:

                _subtitleVisible = !_subtitleVisible;
                getActivity().invalidateOptionsMenu(); // สลับ menu SHOWSUBTITLE ไปมากับ HIDESUBTITLE
                updateSubtitle();

                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }

    /**
     * Update UI
     */

    public void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrime();

        if (_adapter == null) {
            _adapter = new CrimeAdapter(crimes);
            _crimeRecyelerView.setAdapter(_adapter);
        } else {
            _adapter.setCrimes(crimeLab.getCrime());
          _adapter.notifyDataSetChanged();
          //  if(crimePos != null) {
              //  for (Integer pos : crimePos){
                   // _adapter.notifyItemChanged(pos);
             //   Log.d(TAG, "" + crimePos);
            }
      //      }

  //  }



        int crimeCount = crimes.size();
        if(crimeCount == 0){
            titleText1View.setVisibility(View.VISIBLE );
        }else {
            titleText1View.setVisibility(View.INVISIBLE);

        }



        updateSubtitle();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setAddFragmentFrist();

        setHasOptionsMenu(true);
    }

    private void setAddFragmentFrist() {
//        Crime crime = new Crime();
     //   CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
     //\   int crimecount =  crimeLab.getCrime().size();
//        if(crimecount != 0 ) {
//        callbacks.onCrimeSelected(crimeLab.getCrime().get(0));




//             callbacks.onCrimeSelected(crime);
//        }
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
        public ImageView viewImage;
        public File photofile;



        Crime _crime;
       int _position;


        public CrimeHolder(final View itemView) {
            super(itemView);






            titleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            solvedcheckbox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);

            dateTextView =(TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);

            viewImage = (ImageView) itemView.findViewById(R.
            id.choose_picture);

            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime, int position) {
            _crime = crime;
            _position = position;
            titleTextView.setText(_crime.getTitle());
            dateTextView.setText(_crime.getCrimeDate().toString());
            solvedcheckbox.setChecked(_crime.isSolve());
            solvedcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    _crime.setSolve(isChecked);
                    CrimeLab.getInstance(getActivity()).updateCrime(_crime);
                    callbacks.onCrimeSelected(_crime);


                }
            });



            photofile = CrimeLab.getInstance(getActivity()).getPhotofile(crime);

            if(photofile == null || !photofile.exists()) {
                viewImage.setImageDrawable(null);
            }else{
                Bitmap bitmap = PictureUtils.getScaleBitmap(photofile.getPath(),getActivity());

                viewImage.setImageBitmap(bitmap);
            }


        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), "Press ! " + titleTextView.getText(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "send position : " + _position);
//
            //1.2
//            Intent intent = CrimePagerActivity.newIntent(getActivity(), _crime.getId());
//            startActivityForResult(intent, REQUEST_UPDATE_CRIME);
            callbacks.onCrimeSelected(_crime);
        }
    }

    private  class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> _crimes;
        private int viewCreatingCount;
        public CrimeAdapter(List<Crime> crimes){

            _crimes = crimes;
        }

        private void setCrimes(List<Crime> crimes) {
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


            //plurals
            String subtitle = getResources().getQuantityString(R.plurals.subtitle_format, crimeCount, crimeCount);

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

