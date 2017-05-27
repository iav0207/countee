package ru.takoe.iav.countee.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;

@Module
public class AppModule {

    @Provides
    @Singleton
    SaveCostService provideSaveCostService() {
        return SaveCostService.getInstance();
    }

    @Provides
    @Singleton
    CostOutputService provideCostOutputService() {
        return CostOutputService.getInstance();
    }

    @Provides
    @Singleton
    public CostCommentsService provideCostCommentsService() {
        return CostCommentsService.getInstance();
    }

}
