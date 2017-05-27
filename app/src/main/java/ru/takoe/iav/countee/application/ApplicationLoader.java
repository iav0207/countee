package ru.takoe.iav.countee.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import ru.takoe.iav.countee.dagger.AppComponent;
import ru.takoe.iav.countee.dagger.DaggerAppComponent;

/**
 * Created by takoe on 04.12.16.
 */
public class ApplicationLoader extends Application {

    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);

        applicationComponent = DaggerAppComponent.create();
        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
    }

    private static ApplicationLoader instance;

    private AppComponent applicationComponent;

    public static ApplicationLoader getInstance() {
        return instance;
    }

    public static void setInstance(ApplicationLoader instance) {
        ApplicationLoader.instance = instance;
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }

}
