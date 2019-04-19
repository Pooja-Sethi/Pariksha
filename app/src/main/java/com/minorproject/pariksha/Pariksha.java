package com.minorproject.pariksha;

import android.app.Application;

import com.firebase.client.Firebase;

public class Pariksha extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
