package com.example.foody;

import android.app.Application;
import android.content.Context;

public class FoodyApp extends Application {
    static public Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
