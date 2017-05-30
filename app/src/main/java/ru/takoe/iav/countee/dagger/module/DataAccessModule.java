package ru.takoe.iav.countee.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.da.CostFileNamesFactory;
import ru.iav.takoe.countee.da.CostsCache;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.FileFactory;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.persistence.file.LocalWriter;

@Module
public class DataAccessModule {

    @Provides
    @Singleton
    CostFileNamesFactory provideFileNamesFactoryGivenFileFactory(FileFactory fileFactory) {
        return new CostFileNamesFactory(fileFactory);
    }

    @Provides
    @Singleton
    FileFactory provideFileFactory() {
        return FileFactory.getInstance();
    }

    @Provides
    @Singleton
    CostsCache provideCostsCache() {
        return CostsCache.getInstance();
    }

    @Provides
    @Singleton
    JsonConverter provideSerializer() {
        return JsonConverter.getInstance();
    }

    @Provides
    @Singleton
    JsonParser provideDeserializer() {
        return JsonParser.getInstance();
    }

    @Provides
    @Singleton
    LocalWriter provideWriter() {
        return LocalWriter.getInstance();
    }

    @Provides
    @Singleton
    LocalReader provideReader() {
        return LocalReader.getInstance();
    }

}
