package com.example.moodtracker;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import io.realm.Realm;

public class MainApplication extends Application {
//  initialize Real and Android Three Ten
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        AndroidThreeTen.init(this);

    }
}
