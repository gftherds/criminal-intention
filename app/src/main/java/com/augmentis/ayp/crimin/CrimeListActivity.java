package com.augmentis.ayp.crimin;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.augmentis.ayp.crimin.model.Crime;
import com.augmentis.ayp.crimin.model.CrimeLab;

import java.util.List;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {

    @Override
    protected Fragment onCreateFragment()
    {

        return new CrimeListFragment();
    }

    @Override
    public void onOpenSelectFirst() {
        if(findViewById(R.id.detail_fragment_container) == null) {
            List<Crime> crimeList = CrimeLab.getInstance(this).getCrime();
            if (crimeList != null && crimeList.size() > 0) {
                //get first item
                Crime crime = crimeList.get(0);


                //two pane
                Fragment newDetailFragment = CrimeFragment.newInstance(crime.getId());

                // replace old fragment with new one
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.detail_fragment_container, newDetailFragment)
                        .commit();

            }
        }
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.detail_fragment_container) == null) {
            // single pane
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        }else {
            Fragment newDetailFragment = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetailFragment).commit();


        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        // update list
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        listFragment.updateUI();
    }
}

