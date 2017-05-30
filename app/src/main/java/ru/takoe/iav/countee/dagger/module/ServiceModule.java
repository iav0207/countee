package ru.takoe.iav.countee.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.da.CostSaver;
import ru.iav.takoe.countee.model.BalanceCalculator;
import ru.iav.takoe.countee.model.map.DateCostMultimapBuilder;
import ru.iav.takoe.countee.service.BalanceService;
import ru.iav.takoe.countee.service.ChartsDataService;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.iav.takoe.countee.service.CostInputParser;
import ru.iav.takoe.countee.service.CostInputValidator;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.MonthOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.iav.takoe.countee.vo.CostFactory;
import ru.iav.takoe.countee.vo.CostValidator;

@Module(includes = {
        DataAccessModule.class,
        ModelModule.class
})
public class ServiceModule {

    @Provides
    @Singleton
    public CostInputValidator provideCostInputValidator() {
        return new CostInputValidator();
    }

    @Provides
    @Singleton
    public CostValidator provideCostValidator() {
        return new CostValidator();
    }

    @Provides
    @Singleton
    public CostFactory provideCostFactory(CostValidator validator) {
        return new CostFactory(validator);
    }


    @Provides
    @Singleton
    public SaveCostService provideSaveCostService(CostSaver costSaver,
            CostInputParser inputParser,
            MonthOutputService monthOutputService)
    {
        return new SaveCostService(costSaver, inputParser, monthOutputService);
    }

    @Provides
    @Singleton
    public CostOutputService provideCostOutputService(CostReader costReader,
            BalanceService balanceService,
            MonthOutputService monthOutputService)
    {
        return new CostOutputService(costReader, balanceService, monthOutputService);
    }

    @Provides
    @Singleton
    public CostCommentsService provideCostCommentsService(CostReader costReader) {
        return new CostCommentsService(costReader);
    }

    @Provides
    @Singleton
    public CostInputParser provideCostInputParser(CostInputValidator validator, CostFactory factory) {
        return new CostInputParser(validator, factory);
    }

    @Provides
    @Singleton
    public BalanceService provideBalanceService(CostReader costReader, BalanceCalculator balanceCalculator) {
        return new BalanceService(costReader, balanceCalculator);
    }

    @Provides
    @Singleton
    public ChartsDataService provideChartsDataService(CostReader reader) {
        return new ChartsDataService(reader);
    }

    @Provides
    @Singleton
    public MonthOutputService provideMonthOutputService(DateCostMultimapBuilder multimapBuilder, CostReader costReader) {
        return new MonthOutputService(multimapBuilder, costReader);
    }

    @Provides
    @Singleton
    public DateCostMultimapBuilder provideMultimapBuilder() {
        return new DateCostMultimapBuilder();
    }

}
