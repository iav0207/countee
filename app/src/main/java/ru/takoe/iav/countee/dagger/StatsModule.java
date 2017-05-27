package ru.takoe.iav.countee.dagger;

import android.app.Activity;
import android.content.res.AssetManager;
import dagger.Module;
import dagger.Provides;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataFacade;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataGenerator;

@Module
public class StatsModule {

    private Activity activity;

    public StatsModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public AssetManager provideAssets() {
        return activity.getAssets();
    }

    @Provides
    @ActivityScope
    public BarDataFacade provideBarDataFacade() {
        return new BarDataFacade(provideAssets());
    }

    @Provides
    @ActivityScope
    public BarDataGenerator provideBarDataGenerator() {
        return new BarDataGenerator(provideAssets());
    }

}
