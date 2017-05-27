package ru.takoe.iav.countee.dagger;

import android.support.v4.app.FragmentActivity;
import dagger.Module;
import dagger.Provides;
import ru.takoe.iav.countee.view.ViewProvider;

@Module
public class ViewProviderModule {

    private ViewProvider viewProvider;

    public ViewProviderModule(FragmentActivity activity) {
        this.viewProvider = new ViewProvider(activity);
    }

    @Provides
    @ActivityScope
    public ViewProvider provideViewProvider() {
        return viewProvider;
    }

}
