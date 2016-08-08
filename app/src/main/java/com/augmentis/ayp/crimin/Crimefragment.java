package com.augmentis.ayp.crimin;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.augmentis.ayp.crimin.model.Crime;
import com.augmentis.ayp.crimin.model.CrimeCursorWrapper;
import com.augmentis.ayp.crimin.model.CrimeLab;
import com.augmentis.ayp.crimin.model.DatePickerFragment;
import com.augmentis.ayp.crimin.model.Dialogpicture;
import com.augmentis.ayp.crimin.model.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;


/**
 * Created by Therdsak on 7/18/2016.
 */
public class CrimeFragment extends Fragment {
    private static final String CRIME_ID = "CrimeFragment.CRIME_ID";
    private static final String CRIME_POSITION = "CrimeFragment.CRIME_POS";
    private static final String DIALOG_DATE = "CrimeFragment.CRIME_DATE";
    private static final int REQUEST_DATE = 123234;
    private static final int REQUEST_TIME = 123456;
    private static final String DIALOG_TIME = "CrimeFragment.CRIME_TIME";
    private static final String SUBTITLE_VISIBLE_STATE1 = "sawadee";
    private static final int REQUEST_CONTACT_SUSPECT = 2990;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2990;
    private static final int REQEUST_CAPTURE_PHOTO = 1111;
    private static final String TAG = "CrimeFragment";
    private static final int REQUEST_PHOTO = 12345;
    private static final String DIALOG_PICTURE = "sawadee";

    private Crime crime;

    private boolean _subtitleVisible;




    private Button crimeTimeButton;
    private Button crimeDateButton;
    private CheckBox crimeSloveCheckbox;
    private EditText editText;
    private Button deleteButton;
    private Button crimeReportButton;
    private Button crimeSuspectButton;
    private Button  crimeCallbutton;
    private ImageView photoView;
    private ImageButton photoButton;
    private File photoFile;
    private Callbacks callbacks;


    public CrimeFragment() {

    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(CRIME_ID, crimeId);


        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }

    // Callback
    public interface Callbacks {
        void onCrimeUpdated(Crime crime);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onAttach(Context context) {

        callbacks = (Callbacks) context;
        super.onAttach(context);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crime_delete_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_crime:
                CrimeLab.getInstance(getActivity()).deleteCrime(crime.getId());
//                getActivity().finish();
               updateCrime();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);



        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        UUID crimeID = (UUID) getArguments().getSerializable(CRIME_ID);
        crime = CrimeLab.getInstance(getActivity()).getCrimeById(crimeID);
        Log.d(CrimeListFragment.TAG, "crime.getTitle()=" + crime.getTitle());

         photoFile = CrimeLab.getInstance(getActivity()).getPhotofile(crime);



    }








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        if (savedInstanceState != null) {
            _subtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBLE_STATE1);
        }

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
                updateCrime();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        crimeDateButton = (Button) v.findViewById(R.id.crime_date);
        crimeDateButton.setText(new SimpleDateFormat("dd MMMM yyyy").format(crime.getCrimeDate()));
        crimeDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialogFragment = DatePickerFragment.newInstance(crime.getCrimeDate());

                dialogFragment.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialogFragment.show(fm, DIALOG_DATE);



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


//        deleteButton = (Button) v.findViewById(R.id.crime_delete);
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CrimeLab.getInstance(getActivity()).deleteCrime(crime.getId());
//                getActivity().finish();
//

//               CrimeLab crimeLab =  CrimeLab.getInstance(getActivity());
//                crimeLab.getCrime().remove(crimeLab.getCrimeById(crime.getId()));
//
//            Intent intent = new Intent(getActivity(),CrimeListActivity.class);
//                startActivity(intent);       }
//
//        });

//

        crimeSloveCheckbox = (CheckBox) v.findViewById(R.id.crime_slove);
        crimeSloveCheckbox.setChecked(crime.isSolve());
        crimeSloveCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolve(isChecked);
                updateCrime();
                Log.d(CrimeListFragment.TAG, "Crime:" + crime.toString());
            }
        });

        crimeReportButton =(Button) v.findViewById(R.id.crime_report);
        crimeReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));

                i = Intent.createChooser(i, getString(R.string.send_report));

                startActivity(i);
            }
        });
         final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
//       pickContact.addCategory(Intent.CATEGORY_HOME);

        crimeSuspectButton = (Button) v.findViewById(R.id.crime_suspect);
        crimeSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult (pickContact,REQUEST_CONTACT_SUSPECT);
//                crimeSuspectButton.setText(crime.getSusspect());

            }
        });


        if (crime.getSusspect() != null){
            crimeSuspectButton.setText(crime.getSusspect());
        }



        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null){

            crimeSuspectButton.setEnabled(false);
        }
//        Intent intent = new Intent();
//        getActivity().setResult(Activity.RESULT_OK, intent);


        crimeCallbutton = (Button) v.findViewById(R.id.crime_call_suspect);
        crimeCallbutton.setEnabled( crime.getSusspect() != null);
        crimeCallbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasCallPermission()){
                    callSuspect();
                }

            }
        });



        photoButton = (ImageButton) v.findViewById(R.id.crime_camera);



        photoView = (ImageView) v.findViewById(R.id.crime_photo);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                Dialogpicture dialogpicture = Dialogpicture.newInstance(photoFile.getPath());
                dialogpicture.show(fm, DIALOG_PICTURE);




            }

        });


        //Call camera intent

        final Intent captureImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        // check if we can take photo
        boolean canTakePhoto = photoFile != null
                && captureImageIntent.resolveActivity(packageManager) != null;

        if(canTakePhoto){

            Uri uri = Uri.fromFile(photoFile);
            Log.d(TAG, "onCreateView: " + photoFile.getAbsolutePath());
            captureImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);


            // on click -> start activity for camera
            photoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(captureImageIntent, REQEUST_CAPTURE_PHOTO);
                }
            });
        }

        updatePhotoView();
        return v;

    }





    private void callSuspect() {
        Intent i = new Intent(Intent.ACTION_CALL);
        StringTokenizer tokenizer = new StringTokenizer(crime.getSusspect(), ":");
        String name = tokenizer.nextToken();
        String phone = tokenizer.nextToken();
        i.setData(Uri.parse("tel:" + phone));

        startActivity(i);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE : {
                // if request is cancelled, the result arrays are empty.
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                    callSuspect();
                }else {

                    Toast.makeText(getActivity(),R.string.denied_permission_to_call, Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private boolean hasCallPermission() {
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{
                         Manifest.permission.CALL_PHONE
            },
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            return false;

        }


        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);


            crime.setCrimeDate(date);
            updateCrime();
            crimeDateButton.setText(getFormattedDate(crime.getCrimeDate()));

        }
        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);


            crime.setCrimeDate(date);
            updateCrime();
            crimeTimeButton.setText(getFormattedTime(crime.getCrimeDate()));

        }

        if (requestCode == REQUEST_CONTACT_SUSPECT) {
            if (data != null) {
                Uri contactUri = data.getData();
                String[] queryFields = new String[]{
                        ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER
                };

                Cursor c = getActivity()
                        .getContentResolver()
                        .query(contactUri
                                , queryFields
                                , null
                                , null
                                , null);


                try {
                    if (c.getCount() == 0) {
                        return;
                    }
                    c.moveToFirst();
                    String suspect = c.getString(0);
                    suspect = suspect + ":" + c.getString(1);


// (
//                            c.getColumnIndex(
//                                    ContactsContract.Contacts.DISPLAY_NAME));
                    crime.setSusspect(suspect);
                    updateCrime();
                    crimeSuspectButton.setText(suspect);
                    crimeCallbutton.setEnabled(suspect != null);


                } finally {
                    c.close();
                }
            }

            if (requestCode == REQEUST_CAPTURE_PHOTO) {
                updatePhotoView();
            }
        }
    }


    private String getFormattedDate(Date date) {
        return new SimpleDateFormat("dd MMMM yyyy").format(date);
    }

    private String getFormattedTime(Date date) {
        return new SimpleDateFormat("hh:mm").format(date);
    }

    @Override
    public void onPause() {
        super.onPause();
        updateCrime();
//        CrimeLab.getInstance(getActivity()).updateCrime(crime); // update crime in db
    }

    private void updateCrime() {
        CrimeLab.getInstance(getActivity()).updateCrime(crime);
        if(CrimeFragment.this.isResumed())
        callbacks.onCrimeUpdated(crime);

    }

    private String getCrimeReport(){
        String solevedString = null;
        if(crime.isSolve()){
            solevedString = getString(R.string.crime_report_solved);
        }else {
            solevedString = getString(R.string.crime_report_unsolved);
        }


        String dateFormat = "EEE, MMM dd";
        String dateString = android.text.format.DateFormat.format(dateFormat, crime.getCrimeDate()).toString();

        String susspect = crime.getSusspect();

        if(susspect == null) {
            susspect = getString(R.string.crime_report_no_susspect);
        } else {
            susspect = getString(R.string.crime_report_with_susspect);
        }
        String report = getString(R.string.crime_report,
        crime.getTitle(), dateString, solevedString, susspect);
            return report;
    }

   private void updatePhotoView(){
       if(photoFile == null || !photoFile.exists()) {
           photoView.setImageDrawable(null);
       }else{
           Bitmap bitmap = PictureUtils.getScaleBitmap(photoFile.getPath(),getActivity());

           photoView.setImageBitmap(bitmap);
       }

   }

}
