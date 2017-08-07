package ru.takoe.iav.countee.application;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import ru.takoe.iav.countee.dagger.DaggerAppComponent;
import ru.takoe.iav.countee.dagger.DaggerStatsComponent;
import ru.takoe.iav.countee.dagger.DaggerViewProviderComponent;
import ru.takoe.iav.countee.dagger.comp.AppComponent;
import ru.takoe.iav.countee.dagger.comp.SettingsFragmentComponent;
import ru.takoe.iav.countee.dagger.comp.StatsComponent;
import ru.takoe.iav.countee.dagger.comp.ViewProviderComponent;
import ru.takoe.iav.countee.dagger.module.SettingsFragmentModule;
import ru.takoe.iav.countee.dagger.module.StatsModule;
import ru.takoe.iav.countee.dagger.module.ViewProviderModule;

import static com.google.common.base.Preconditions.checkState;

public class CounteeApp extends Application {

    private static CounteeApp instance;

    private AppComponent applicationComponent;

    private volatile WeakReference<FragmentActivity> activity = new WeakReference<>(null);

    private volatile ViewProviderComponent viewProviderComponent;
    private volatile StatsComponent statsComponent;
    private volatile SettingsFragmentComponent settingsFragmentComponent;

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

    public static CounteeApp getInstance() {
        return instance;
    }

    public static void setInstance(CounteeApp instance) {
        CounteeApp.instance = instance;
    }

    public AppComponent getApplicationComponent() {
        return applicationComponent;
    }

    public ViewProviderComponent getViewProviderComponent(FragmentActivity activity) {
        if (activity != null && activity != this.activity.get()) {
            this.activity = new WeakReference<>(activity);
            viewProviderComponent = DaggerViewProviderComponent.builder()
                    .appComponent(applicationComponent)
                    .viewProviderModule(new ViewProviderModule((AppCompatActivity) activity))
                    .build();
        }
        return viewProviderComponent;

    }

    public StatsComponent getStatsComponent(Activity activity) {
        return getStatsComponent(activity.getAssets());
    }

    public StatsComponent getStatsComponent(AssetManager assets) {
        if (statsComponent == null) {
            statsComponent = DaggerStatsComponent.builder()
                    .appComponent(applicationComponent)
                    .statsModule(new StatsModule(assets))
                    .build();
        }
        return statsComponent;
    }

    public SettingsFragmentComponent getSettingsFragmentComponent(Fragment fragment) {
        if (settingsFragmentComponent == null) {
            if (viewProviderComponent != null) {
                settingsFragmentComponent = viewProviderComponent.fragmentComponent(new SettingsFragmentModule(fragment));
            }
        }
        return settingsFragmentComponent;
    }

    public SettingsFragmentComponent getSettingsFragmentComponent() {
        checkState(settingsFragmentComponent != null,
                "SettingsFragmentComponent has not been set yet!");
        return settingsFragmentComponent;
    }

}
