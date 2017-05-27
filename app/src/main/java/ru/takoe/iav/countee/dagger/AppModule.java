package ru.takoe.iav.countee.dagger;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.SaveCostService;

import javax.inject.Singleton;

/**
 * Created by takoe on 20.01.17.
 */
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

}
