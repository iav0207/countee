package ru.takoe.iav.countee.dagger.comp;

import javax.inject.Singleton;

import dagger.Component;
import ru.iav.takoe.countee.da.impl.CostReader;
import ru.iav.takoe.countee.da.impl.CostSaver;
import ru.iav.takoe.countee.service.BalanceService;
import ru.iav.takoe.countee.service.ChartsDataService;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.DataExportService;
import ru.iav.takoe.countee.service.MonthOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.takoe.iav.countee.dagger.module.AppModule;
import ru.takoe.iav.countee.fragment.content.addcost.CreateCostPageFragment;
import ru.takoe.iav.countee.fragment.content.addcost.CreateCostPagerAdapter;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataColorGenerator;

@Singleton
@Component(
        modules = AppModule.class
)
public interface AppComponent {

    void injectInto(CreateCostPageFragment createCostPageFragment);
    void injectInto(CreateCostPagerAdapter createCostPagerAdapter);

    SaveCostService getSaveCostService();
    CostOutputService getCostOutputService();
    CostCommentsService getCostCommentsService();
    ChartsDataService getChartsDataService();
    BarDataColorGenerator getBarDataColorGenerator();
    DataExportService getDataExportService();

    void injectInto(SaveCostService saveCostService);
    void injectInto(CostOutputService costOutputService);
    void injectInto(CostCommentsService costCommentsService);
    void injectInto(BalanceService balanceService);
    void injectInto(MonthOutputService monthOutputService);
    void injectInto(DataExportService dataExportService);

    void injectInto(CostSaver costSaver);
    void injectInto(CostReader costReader);

}
