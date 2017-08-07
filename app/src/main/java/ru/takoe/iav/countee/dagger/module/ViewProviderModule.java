package ru.takoe.iav.countee.dagger.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import ru.takoe.iav.countee.activity.ViewRenderer;
import ru.takoe.iav.countee.dagger.scope.ActivityScope;
import ru.takoe.iav.countee.view.ViewProvider;

@Module
public class ViewProviderModule {

    private final AppCompatActivity activity;

    public ViewProviderModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    Context provideContext() {
        return activity.getApplicationContext();
    }

    @Provides
    @ActivityScope
    ViewProvider provideViewProvider(AppCompatActivity activity) {
        return new ViewProvider(activity);
    }

    @Provides
    @ActivityScope
    ViewRenderer provideViewRenderer(AppCompatActivity activity) {
        return new ViewRenderer(activity);
    }

}
