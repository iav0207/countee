package ru.takoe.iav.countee.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import ru.takoe.iav.countee.dagger.AppComponent;
import ru.takoe.iav.countee.dagger.DaggerAppComponent;
import ru.takoe.iav.countee.dagger.DaggerStatsComponent;
import ru.takoe.iav.countee.dagger.DaggerViewProviderComponent;
import ru.takoe.iav.countee.dagger.StatsComponent;
import ru.takoe.iav.countee.dagger.StatsModule;
import ru.takoe.iav.countee.dagger.ViewProviderComponent;
import ru.takoe.iav.countee.dagger.ViewProviderModule;

public class ApplicationLoader extends Application {

    private static ApplicationLoader instance;

    private AppComponent applicationComponent;

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

    public static ApplicationLoader getInstance() {
        return instance;
    }

    public static void setInstance(ApplicationLoader instance) {
        ApplicationLoader.instance = instance;
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }

    public ViewProviderComponent getViewProviderComponent(FragmentActivity activity) {
        return DaggerViewProviderComponent.builder()
                .appComponent(applicationComponent)
                .viewProviderModule(new ViewProviderModule((AppCompatActivity) activity))
                .build();
    }

    public StatsComponent getStatsComponent(Activity activity) {
        return getStatsComponent(activity.getAssets());
    }

    public StatsComponent getStatsComponent(AssetManager assets) {
        return DaggerStatsComponent.builder()
                .appComponent(applicationComponent)
                .statsModule(new StatsModule(assets))
                .build();
    }

}
