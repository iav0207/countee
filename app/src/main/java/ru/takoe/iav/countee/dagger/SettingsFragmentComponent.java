package ru.takoe.iav.countee.dagger;

import android.content.Context;
import dagger.Subcomponent;
import ru.takoe.iav.countee.dagger.module.SettingsFragmentModule;
import ru.takoe.iav.countee.fragment.SettingsFragment;
import ru.takoe.iav.countee.fragment.listener.ExportButtonListener;
import ru.takoe.iav.countee.fragment.loader.ExportDataLoader;
import ru.takoe.iav.countee.view.ViewProvider;

@FragmentScope
@Subcomponent(
        modules = SettingsFragmentModule.class
)
public interface SettingsFragmentComponent {

    void injectInto(SettingsFragment settingsFragment);
    void injectInto(ExportButtonListener exportButtonListener);
    void injectInto(ExportDataLoader exportDataLoader);

    Context getContext();
    ViewProvider getViewProvider();

}
