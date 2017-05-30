package ru.takoe.iav.countee.dagger;

import dagger.Component;
import ru.takoe.iav.countee.dagger.module.StatsModule;
import ru.takoe.iav.countee.dagger.module.ViewProviderModule;
import ru.takoe.iav.countee.fragment.StatsFragment;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataFacade;
import ru.takoe.iav.countee.fragment.content.stats.data.CostsBarDataProvider;
import ru.takoe.iav.countee.fragment.content.stats.data.FundsDailyBarDataProvider;
import ru.takoe.iav.countee.fragment.content.stats.data.FundsMonthlyBarDataProvider;
import ru.takoe.iav.countee.fragment.listener.ChartItemSelectedListener;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                StatsModule.class,
                ViewProviderModule.class
        }
)
public interface StatsComponent {

    void injectInto(StatsFragment chartFragment);

    void injectInto(ChartItemSelectedListener chartItemSelectedListener);

    void injectInto(BarDataFacade barDataFacade);

    void injectInto(CostsBarDataProvider costsBarDataProvider);

    void injectInto(FundsDailyBarDataProvider fundsDailyBarDataProvider);

    void injectInto(FundsMonthlyBarDataProvider fundsMonthlyBarDataProvider);

}
