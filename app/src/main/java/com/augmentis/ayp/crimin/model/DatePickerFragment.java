package com.augmentis.ayp.crimin.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.augmentis.ayp.crimin.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Therdsak on 7/28/2016.
 */
public class DatePickerFragment extends DialogFragment implements DialogInterface.OnClickListener {



    public static String EXTRA_DATE = "Date";

    public static DatePickerFragment newInstance(Date date) {


        // step 1
        DatePickerFragment df = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("ARG_DATE",date);
        df.setArguments(args);
        return df;
    }

    DatePicker _datePicker;

    @NonNull
    @Override

    // step 3
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable("ARG_DATE");


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayofMonth = calendar.get(Calendar.DAY_OF_MONTH);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        _datePicker = (DatePicker) v.findViewById (R.id.date_picker_in_dialog);
        _datePicker.init(year, month, dayofMonth, null);
        builder.setView(v);
        builder.setTitle(R.string.date_picker_title);
        builder.setPositiveButton(android.R.string.ok , this);
        return builder.create();

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        // Datepicker ----> Model
        int datofMonth = _datePicker.getDayOfMonth();
        int month = _datePicker.getMonth();
        int year = _datePicker.getYear();


        Date date = new GregorianCalendar(year, month , datofMonth).getTime();

        sendResult(Activity.RESULT_OK, date);


    }

    private void sendResult(int resultCode , Date date){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);


        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode, intent);
    }

}
