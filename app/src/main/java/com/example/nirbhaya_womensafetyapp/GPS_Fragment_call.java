package com.example.nirbhaya_womensafetyapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.Manifest;

import android.content.DialogInterface;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class GPS_Fragment_call extends Fragment {
    final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    FusedLocationProviderClient mFusedLocationClient;
    Double latitude =0.0;
    Double longitude =0.0;
    DatabaseHelper myDb;
    View view;
    public GPS_Fragment_call () {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myDb = new DatabaseHelper(getContext());

        view=inflater.inflate(R.layout.fragment_gpslocation, container, false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        getActivity().setTitle("Calling");


            fetchLocation(inflater,container);

        return view;
    }
    public void fetchLocation(final LayoutInflater inflater, final ViewGroup container) {



            Toast.makeText(getActivity(),"Getting Location", Toast.LENGTH_SHORT).show();

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location==null)
                                Toast.makeText(getActivity(),"No Location found", Toast.LENGTH_SHORT).show();

                            if (location != null) {

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                            }
                            try {
                                Cursor res = myDb.getAllData();

                                if (res != null && res.getCount() == 0) {
                                    Toast.makeText(getContext(), "No contact to send message", Toast.LENGTH_LONG).show();

                                }
                                if (res != null) {
                                    while (res.moveToNext()) {
                                        String number = res.getString(2);


                                        String s = "http://maps.google.com/?q=" + latitude + "," + longitude + "";
                                        SmsManager smsManager = SmsManager.getDefault();
                                        smsManager.sendTextMessage(number, null, s, null, null);

                                    }
                                    res = myDb.getAllData();
                                    if (res != null) {

                                        res.moveToNext();
                                        String number = res.getString(2);


                                        String dial = "tel:" + number;
                                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                                        Toast.makeText(getContext(), "Calling", Toast.LENGTH_LONG).show();


                                    }
                                    Toast.makeText(getActivity(), "Latitude = " + latitude + "\nLongitude = " + longitude, Toast.LENGTH_SHORT).show();

                                }
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getActivity(), "Not Data Available " , Toast.LENGTH_SHORT).show();
                                ContactFragment fragment = new ContactFragment();
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout, fragment, "EMERGENCY CONTACTS");
                                fragmentTransaction.commit();

                            }
                        }})
                    .addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"note able to fetch data", Toast.LENGTH_SHORT).show();

                        }
                    });
    }



}