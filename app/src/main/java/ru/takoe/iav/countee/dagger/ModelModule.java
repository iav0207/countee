package ru.takoe.iav.countee.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.model.BalanceCalculator;

@Module
public class ModelModule {

    @Provides
    @Singleton
    BalanceCalculator provideBalanceCalculator() {
        return new BalanceCalculator();
    }

}
