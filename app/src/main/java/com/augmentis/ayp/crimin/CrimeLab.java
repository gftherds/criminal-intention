package com.augmentis.ayp.crimin;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Therdsak on 7/18/2016.
 */
public class CrimeLab {
    List<Crime> crimeList;


    private static CrimeLab instance;

    public static CrimeLab getInstance(Context context) {
        if(instance == null){
            instance = new CrimeLab();

        }
        return instance;
    }




    private CrimeLab() {
        crimeList = new ArrayList<>();
    }



    public Crime getCrimeById(UUID uuid){
        for( Crime crime : crimeList){
            if(crime.getId().equals(uuid)){
                return crime;

            }
        }
        return null;
    }






    public int getCrimePositionById(UUID uuid) {
        int size = crimeList.size();
        for (int i = 0; i < size; i++) {
            if (crimeList.get(i).getId().equals(uuid)) {
                return i;

            }
        }

        return -1;

    }









    public  List<Crime> getCrime() {
        return this.crimeList;
    }




    public static void main(String[] args){
//        CrimeLab crimeLab = CrimeLab.getInstance();
        List<Crime> crimeList = CrimeLab.getInstance(null).getCrime();
        int size = crimeList.size();
        for(int i = 0; i< size;i++){
            System.out.println(crimeList.get(i));
        }


    }


    public void addCrime(Crime crime) {
        crimeList.add(crime);
    }
}
