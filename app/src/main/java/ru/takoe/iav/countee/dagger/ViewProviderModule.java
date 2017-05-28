package ru.takoe.iav.countee.dagger;

import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import ru.takoe.iav.countee.activity.ViewRenderer;
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
    ViewProvider provideViewProvider(AppCompatActivity activity) {
        return new ViewProvider(activity);
    }

    @Provides
    @ActivityScope
    ViewRenderer provideViewRenderer(ViewProvider viewProvider, AppCompatActivity activity) {
        return new ViewRenderer(activity, viewProvider);
    }

}
