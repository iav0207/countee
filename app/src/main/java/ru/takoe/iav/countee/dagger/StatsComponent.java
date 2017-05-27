package ru.takoe.iav.countee.dagger;

import dagger.Component;
import ru.takoe.iav.countee.activity.CreateCostActivity;
import ru.takoe.iav.countee.fragment.AbstractChartFragment;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = StatsModule.class
)
public interface StatsComponent {

    void inject(CreateCostActivity activity);

    void injectInto(AbstractChartFragment chartFragment);

}
