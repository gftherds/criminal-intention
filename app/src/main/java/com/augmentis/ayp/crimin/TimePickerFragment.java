package com.augmentis.ayp.crimin;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Therdsak on 7/28/2016.
 */
public class TimePickerFragment extends DialogFragment implements DialogInterface.OnClickListener {


    public static final String EXTRA_TIME = "Therdsak";
    TimePicker _timepicker;

    Date date ;


    //step 1
    public static TimePickerFragment newInstance(Date date) {
        TimePickerFragment dg = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("ARG_DATE", date);
        dg.setArguments(args);
        return dg;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        date = (Date) getArguments().getSerializable("ARG_DATE");


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time_date, null);
        _timepicker = (TimePicker) v.findViewById(R.id.time_picker_in_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _timepicker.setHour(hour);
            _timepicker.setMinute(min);
        }

        builder.setView(v);
        builder.setTitle("PickTime");
        builder.setPositiveButton(android.R.string.ok, this);
        return builder.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
       int hour = 0;
       int minute = 0;




        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
             hour = _timepicker.getHour();
             minute = _timepicker.getMinute();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        sendResult(Activity.RESULT_OK, calendar.getTime());


    }

    private void sendResult(int resultCode , Date date){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);


        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode, intent);
    }
    }





