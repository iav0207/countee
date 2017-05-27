package ru.takoe.iav.countee.dagger;

import dagger.Component;
import ru.takoe.iav.countee.activity.CreateCostActivity;
import ru.takoe.iav.countee.fragment.StatsFragment;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataFacade;
import ru.takoe.iav.countee.fragment.listener.ChartItemSelectedListener;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = StatsModule.class
)
public interface StatsComponent {

    void inject(CreateCostActivity activity);

    void injectInto(StatsFragment chartFragment);

    void injectInto(ChartItemSelectedListener chartItemSelectedListener);

    void injectInto(BarDataFacade barDataFacade);

}
