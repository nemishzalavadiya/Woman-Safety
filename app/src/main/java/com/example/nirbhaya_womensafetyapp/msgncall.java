package com.example.nirbhaya_womensafetyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class msgncall extends MainActivity {
    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_msg = 1;
    private EditText mEditTextNumber;
    private EditText meditmsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextNumber = findViewById(R.id.phone);
        ImageView imageCall = findViewById(R.id.call);

        meditmsg = findViewById(R.id.msg);
        ImageView imagemsg = findViewById(R.id.msg1);


        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        imagemsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMsg();
            }
        });

    }

    private void makePhoneCall() {
        String number = mEditTextNumber.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(msgncall.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(msgncall.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(msgncall.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeMsg() {
        String number = mEditTextNumber.getText().toString();
        String msg = meditmsg.getText().toString();


        if (number.trim().length() > 0 && msg.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(msgncall.this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(msgncall.this,
                        new String[]{Manifest.permission.SEND_SMS}, REQUEST_msg);

            } else {
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(number,null,msg,null,null);
                String send = "smsto:" + number;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(send)));
            }

        } else {
            Toast.makeText(msgncall.this, "Enter Phone Number & Enter Message", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_msg )
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeMsg();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
