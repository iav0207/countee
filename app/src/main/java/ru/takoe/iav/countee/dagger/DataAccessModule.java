package ru.takoe.iav.countee.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.da.CostSaver;

@Module
public class DataAccessModule {

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

}
