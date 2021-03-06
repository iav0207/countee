package ru.takoe.iav.countee.dagger.comp;

import android.content.Context;
import dagger.Subcomponent;
import ru.takoe.iav.countee.dagger.module.SettingsFragmentModule;
import ru.takoe.iav.countee.dagger.scope.FragmentScope;
import ru.takoe.iav.countee.fragment.SettingsFragment;
import ru.takoe.iav.countee.fragment.loader.ExportDataLoader;
import ru.takoe.iav.countee.fragment.loader.ImportDataLoader;
import ru.takoe.iav.countee.view.ViewProvider;

@FragmentScope
@Subcomponent(
        modules = SettingsFragmentModule.class
)
public interface SettingsFragmentComponent {

    void injectInto(SettingsFragment settingsFragment);
    void injectInto(ExportDataLoader exportDataLoader);
    void injectInto(ImportDataLoader importDataLoader);

    Context getContext();
    ViewProvider getViewProvider();

}
