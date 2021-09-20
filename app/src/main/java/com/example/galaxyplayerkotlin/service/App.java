package com.example.galaxyplayerkotlin.service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;

public class App extends Application {

    public static final String CHANNEL_ID = "ServiceChannel";


    @Override
    public void onCreate() {

        super.onCreate();

        createNotificationChannel("Galaxy Player");
    }



    private void createNotificationChannel(String channel_name) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    channel_name
                    , NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);




        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

