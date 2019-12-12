package com.example.nirbhaya_womensafetyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactusFragment extends Fragment {


    public ContactusFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_contactus, container, false);
        view.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/html");
                i.putExtra(Intent.EXTRA_EMAIL,"abc@gmail.com");
                i.putExtra(Intent.EXTRA_SUBJECT,"Nibhaya");
                i.putExtra(Intent.EXTRA_TEXT,"Send Mail");
                startActivity(Intent.createChooser(i,"Send Mail"));
            }
        });
        return view;
    }

}