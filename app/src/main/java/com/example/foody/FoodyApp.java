package com.example.foody;
<<<<<<< HEAD

import android.app.Application;
import android.content.Context;

public class FoodyApp extends Application {
    static public Context context;

=======
import android.app.Application;
import android.content.Context;


public class FoodyApp extends Application {

    static public Context context;

    @Override
>>>>>>> origin/master
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
<<<<<<< HEAD
=======

>>>>>>> origin/master
}
