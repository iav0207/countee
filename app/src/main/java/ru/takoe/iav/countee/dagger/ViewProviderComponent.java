package ru.takoe.iav.countee.dagger;

import dagger.Component;
import ru.takoe.iav.countee.activity.CreateCostActivity;
import ru.takoe.iav.countee.dagger.module.ViewProviderModule;
import ru.takoe.iav.countee.fragment.CreateCostFragment;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = ViewProviderModule.class
)
public interface ViewProviderComponent {

    void inject(CreateCostActivity createCostActivity);

    void injectInto(CreateCostFragment createCostFragment);

}
