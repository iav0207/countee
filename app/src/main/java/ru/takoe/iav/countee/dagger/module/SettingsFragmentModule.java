package ru.takoe.iav.countee.dagger.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import dagger.Module;
import dagger.Provides;
import ru.takoe.iav.countee.dagger.FragmentScope;
import ru.takoe.iav.countee.fragment.listener.ExportButtonListener;
import ru.takoe.iav.countee.view.ViewProvider;

@Module
public class SettingsFragmentModule {

    private final Fragment fragment;

    public SettingsFragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Fragment provideFragment() {
        return fragment;
    }

    @Provides
    @FragmentScope
    public LoaderManager provideLoaderManager() {
        return fragment.getLoaderManager();
    }

    @Provides
    @FragmentScope
    public ExportButtonListener provideExportButtonListener(Context context,
            ViewProvider viewProvider,
            LoaderManager loaderManager)
    {
        return new ExportButtonListener(context, viewProvider, loaderManager);
    }

}
