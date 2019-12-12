package com.example.nirbhaya_womensafetyapp;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class GPS_LocationFragment extends Fragment {
    final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    FusedLocationProviderClient mFusedLocationClient;
    Double latitude =0.0;
    Double longitude =0.0;
    DatabaseHelper myDb;

    Intent intent,chooser;
    public GPS_LocationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myDb = new DatabaseHelper(getContext());

        View view=inflater.inflate(R.layout.fragment_gpslocation, container, false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        getActivity().setTitle("GPS Location");
fetchLocation();

        return view;
    }
    public void fetchLocation() {

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
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("http://maps.google.com/?q="+latitude+","+longitude+""));
                                chooser = Intent.createChooser(intent, "Lauch Maps");
                                startActivity(chooser);

                            }

                    }})
                    .addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"note able to fetch data", Toast.LENGTH_SHORT).show();

                        }
                    });


        getActivity().startActivity(new Intent(getActivity(),MainActivity.class));


    }



}