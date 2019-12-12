package com.example.nirbhaya_womensafetyapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class Exservice extends Service {
    public static final String CHANNAL_ID="Nirbhaya APP";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String intput ;
        GPS_LocationFragment gps=new GPS_LocationFragment();
        intput="data";
        Intent Notiintent= new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,Notiintent,0);
        Notification notification= new NotificationCompat.Builder(this,CHANNAL_ID)
                .setContentTitle( "Location")
                .setContentText(intput)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);
        Toast.makeText(getApplicationContext(),"Service running" ,Toast.LENGTH_LONG).show();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }
}
