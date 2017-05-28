package ru.takoe.iav.countee.dagger;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.da.CostSaver;
import ru.iav.takoe.countee.model.BalanceCalculator;
import ru.iav.takoe.countee.service.BalanceService;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.iav.takoe.countee.service.CostInputParser;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.MonthOutputService;
import ru.iav.takoe.countee.service.SaveCostService;

@Module
@ParametersAreNonnullByDefault
public class ServiceModule {

    @Provides
    @Singleton
    SaveCostService provideSaveCostService(CostSaver costSaver,
            CostInputParser inputParser,
            MonthOutputService monthOutputService)
    {
        return new SaveCostService(costSaver, inputParser, monthOutputService);
    }

    @Provides
    @Singleton
    CostOutputService provideCostOutputService(CostReader costReader,
            BalanceService balanceService,
            MonthOutputService monthOutputService)
    {
        return new CostOutputService(costReader, balanceService, monthOutputService);
    }

    @Provides
    @Singleton
    CostCommentsService provideCostCommentsService(CostReader costReader) {
        return new CostCommentsService(costReader);
    }

    @Provides
    @Singleton
    CostReader provideCostReader() {
        return CostReader.getInstance();
    }

    @Provides
    @Singleton
    CostSaver provideCostSaver() {
        return CostSaver.getInstance();
    }

    @Provides
    @Singleton
    CostInputParser provideCostInputParser() {
        return new CostInputParser();
    }

    @Provides
    @Singleton
    BalanceService provideBalanceService(CostReader costReader, BalanceCalculator balanceCalculator) {
        return new BalanceService(costReader, balanceCalculator);
    }

    @Provides
    @Singleton
    MonthOutputService provideMonthOutputService() {
        return MonthOutputService.getInstance();
    }

    @Provides
    @Singleton
    BalanceCalculator provideBalanceCalculator() {
        return BalanceCalculator.getInstance();
    }

}
