package ru.takoe.iav.countee.dagger.module;

import android.content.res.AssetManager;
import dagger.Module;
import dagger.Provides;
import ru.takoe.iav.countee.dagger.scope.ActivityScope;
import ru.takoe.iav.countee.fragment.content.stats.StatsFragmentSelectionHolder;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataFacade;
import ru.takoe.iav.countee.fragment.content.stats.data.BarDataGenerator;
import ru.takoe.iav.countee.fragment.content.stats.data.CostsDailyBarDataProvider;
import ru.takoe.iav.countee.fragment.content.stats.data.CostsMonthlyBarDataProvider;
import ru.takoe.iav.countee.fragment.content.stats.data.FundsDailyBarDataProvider;
import ru.takoe.iav.countee.fragment.content.stats.data.FundsMonthlyBarDataProvider;
import ru.takoe.iav.countee.view.TypefaceHolder;

@Module
public class StatsModule {

    private AssetManager assets;

    public StatsModule(AssetManager assets) {
        this.assets = assets;
    }

    @Provides
    @ActivityScope
    AssetManager provideAssets() {
        return assets;
    }

    @Provides
    @ActivityScope
    BarDataFacade provideBarDataFacade() {
        return new BarDataFacade(provideAssets());
    }

    @Provides
    @ActivityScope
    BarDataGenerator provideBarDataGenerator() {
        return new BarDataGenerator(provideAssets());
    }

    @Provides
    @ActivityScope
    TypefaceHolder provideTypefaceHolder(AssetManager assets) {
        return new TypefaceHolder(assets);
    }

    @Provides
    @ActivityScope
    CostsDailyBarDataProvider provideCostsDailyBarDataProvider() {
        return new CostsDailyBarDataProvider(provideAssets());
    }

    @Provides
    @ActivityScope
    CostsMonthlyBarDataProvider provideCostsMonthlyBarDataProvider() {
        return new CostsMonthlyBarDataProvider(provideAssets());
    }

    @Provides
    @ActivityScope
    FundsDailyBarDataProvider provideFundsDailyBarDataProvider() {
        return new FundsDailyBarDataProvider(provideAssets());
    }

    @Provides
    @ActivityScope
    FundsMonthlyBarDataProvider providerFundsMonthlyBarDataProvider() {
        return new FundsMonthlyBarDataProvider(provideAssets());
    }

    @Provides
    @ActivityScope
    StatsFragmentSelectionHolder provideStatsFragmentSelectionHolder() {
        return new StatsFragmentSelectionHolder();
    }

}
