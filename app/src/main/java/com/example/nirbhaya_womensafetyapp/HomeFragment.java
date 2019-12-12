package com.example.nirbhaya_womensafetyapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    DatabaseHelper myDb;
    static Switch s123;
    public HomeFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Home");
        myDb = new DatabaseHelper(getActivity());

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        Switch s123= (Switch) view.findViewById(R.id.switch1);

        s123.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    Intent intent=new Intent(getContext(),Exservice.class);
                    getActivity().startService(intent);
                }
                else{
                    Intent intent=new Intent(getContext(),Exservice.class);
                    getActivity().stopService(intent);

                }
            }
        });

        return view;
    }

}