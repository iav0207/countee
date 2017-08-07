package ru.takoe.iav.countee.dagger.comp;

import dagger.Component;
import ru.takoe.iav.countee.activity.CreateCostActivity;
import ru.takoe.iav.countee.dagger.module.SettingsFragmentModule;
import ru.takoe.iav.countee.dagger.module.ViewProviderModule;
import ru.takoe.iav.countee.dagger.scope.ActivityScope;
import ru.takoe.iav.countee.fragment.CreateCostFragment;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = ViewProviderModule.class
)
public interface ViewProviderComponent {

    void inject(CreateCostActivity createCostActivity);

    void injectInto(CreateCostFragment createCostFragment);

    SettingsFragmentComponent fragmentComponent(SettingsFragmentModule settingsFragmentModule);

}
