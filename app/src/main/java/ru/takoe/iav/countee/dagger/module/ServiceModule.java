package ru.takoe.iav.countee.dagger.module;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.da.DataExporter;
import ru.iav.takoe.countee.da.DataImporter;
import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.da.Saver;
import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.model.BalanceCalculator;
import ru.iav.takoe.countee.model.map.DateCostMultimapBuilder;
import ru.iav.takoe.countee.service.BalanceService;
import ru.iav.takoe.countee.service.ChartsDataService;
import ru.iav.takoe.countee.service.CostCommentsService;
import ru.iav.takoe.countee.service.CostInputParser;
import ru.iav.takoe.countee.service.CostInputValidator;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.iav.takoe.countee.service.DataExportService;
import ru.iav.takoe.countee.service.FileDataImportService;
import ru.iav.takoe.countee.service.MonthOutputService;
import ru.iav.takoe.countee.service.SaveCostService;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.CostFactory;
import ru.iav.takoe.countee.vo.CostValidator;

@Module(includes = {
        DataAccessModule.class,
        ModelModule.class
})
public class ServiceModule {

    @Provides
    @Singleton
    CostInputValidator provideCostInputValidator() {
        return new CostInputValidator();
    }

    @Provides
    @Singleton
    CostValidator provideCostValidator() {
        return new CostValidator();
    }

    @Provides
    @Singleton
    CostFactory provideCostFactory(CostValidator validator) {
        return new CostFactory(validator);
    }


    @Provides
    @Singleton
    SaveCostService provideSaveCostService(Saver<Cost, CostNotSavedException> costSaver,
            CostInputParser inputParser,
            MonthOutputService monthOutputService)
    {
        return new SaveCostService(costSaver, inputParser, monthOutputService);
    }

    @Provides
    @Singleton
    CostOutputService provideCostOutputService(Reader<Cost> costReader,
            BalanceService balanceService,
            MonthOutputService monthOutputService)
    {
        return new CostOutputService(costReader, balanceService, monthOutputService);
    }

    @Provides
    @Singleton
    CostCommentsService provideCostCommentsService(Reader<Cost> costReader) {
        return new CostCommentsService(costReader);
    }

    @Provides
    @Singleton
    CostInputParser provideCostInputParser(CostInputValidator validator, CostFactory factory) {
        return new CostInputParser(validator, factory);
    }

    @Provides
    @Singleton
    BalanceService provideBalanceService(Reader<Cost> costReader, BalanceCalculator balanceCalculator) {
        return new BalanceService(costReader, balanceCalculator);
    }

    @Provides
    @Singleton
    ChartsDataService provideChartsDataService(Reader<Cost> reader) {
        return new ChartsDataService(reader);
    }

    @Provides
    @Singleton
    MonthOutputService provideMonthOutputService(DateCostMultimapBuilder multimapBuilder,
            Reader<Cost> costReader) {
        return new MonthOutputService(multimapBuilder, costReader);
    }

    @Provides
    @Singleton
    DataExportService provideDataExportService(DataExporter dataExporter) {
        return new DataExportService(dataExporter);
    }
    
    @Provides
    @Singleton
    FileDataImportService provideFileDataImportService(DataImporter<File> fileDataImporter) {
        return new FileDataImportService(fileDataImporter);
    }

    @Provides
    @Singleton
    DateCostMultimapBuilder provideMultimapBuilder() {
        return new DateCostMultimapBuilder();
    }

}
