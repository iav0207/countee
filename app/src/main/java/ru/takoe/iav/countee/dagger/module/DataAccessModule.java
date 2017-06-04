package ru.takoe.iav.countee.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.da.CostFileNamesFactory;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.da.CostSaver;
import ru.iav.takoe.countee.da.CostsCache;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.FileFactory;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.takoe.iav.countee.properties.ApplicationProperties;

@Module
public class DataAccessModule {

    @Provides
    @Singleton
    CostSaver provideCostSaver(CostFileNamesFactory fileNamesFactory, CostReader costReader,
            CostsCache cache, JsonConverter jsonConverter, LocalWriter writer) {
        return new CostSaver(fileNamesFactory, costReader, cache, jsonConverter, writer);
    }

    @Provides
    @Singleton
    CostReader provideCostReader(CostFileNamesFactory fileNamesFactory, JsonParser jsonParser,
            LocalReader reader, CostsCache cache) {
        return new CostReader(fileNamesFactory, jsonParser, reader, cache);
    }

    @Provides
    @Singleton
    CostFileNamesFactory provideFileNamesFactoryGivenFileFactory(FileFactory fileFactory) {
        return new CostFileNamesFactory(fileFactory);
    }

    @Provides
    @Singleton
    FileFactory provideFileFactory() {
        return new FileFactory(ApplicationProperties.getOutputDirectory());
    }

    @Provides
    @Singleton
    CostsCache provideCostsCache() {
        return new CostsCache();
    }

    @Provides
    @Singleton
    JsonConverter provideSerializer() {
        return new JsonConverter();
    }

    @Provides
    @Singleton
    JsonParser provideDeserializer() {
        return new JsonParser();
    }

    @Provides
    @Singleton
    LocalWriter provideWriter() {
        return new LocalWriter();
    }

    @Provides
    @Singleton
    LocalReader provideReader() {
        return new LocalReader();
    }

}
