package ru.takoe.iav.countee.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by takoe on 04.12.16.
 */
public class ApplicationLoader extends Application {

    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
    }

}
