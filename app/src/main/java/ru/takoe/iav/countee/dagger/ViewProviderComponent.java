package ru.takoe.iav.countee.dagger;

import dagger.Component;
import ru.takoe.iav.countee.fragment.CreateCostFragment;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = ViewProviderModule.class
)
public interface ViewProviderComponent {

    void injectInto(CreateCostFragment createCostFragment);

}
