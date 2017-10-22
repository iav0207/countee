package ru.takoe.iav.countee.dagger.module;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.iav.takoe.countee.crypt.CryptFacade;
import ru.iav.takoe.countee.crypt.impl.SimpleGostCryptFacade;
import ru.iav.takoe.countee.da.DataExporter;
import ru.iav.takoe.countee.da.DataImporter;
import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.da.Saver;
import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.da.impl.CostBulkSaver;
import ru.iav.takoe.countee.da.impl.CostFileNamesFactory;
import ru.iav.takoe.countee.da.impl.CostReader;
import ru.iav.takoe.countee.da.impl.CostSaver;
import ru.iav.takoe.countee.da.impl.CostsCache;
import ru.iav.takoe.countee.da.impl.DataExporterImpl;
import ru.iav.takoe.countee.da.impl.DateCostMultimap;
import ru.iav.takoe.countee.da.impl.MergeFileDataImporter;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.FileFactory;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;

import static ru.takoe.iav.countee.properties.ApplicationProperties.applicationProperties;

@Module
public class DataAccessModule {

    @Provides
    @Singleton
    Saver<Cost, CostNotSavedException> provideCostSaver(CostFileNamesFactory fileNamesFactory, CostReader costReader,
            CostsCache cache, JsonConverter jsonConverter, LocalWriter writer) {
        return new CostSaver(fileNamesFactory, costReader, cache, jsonConverter, writer);
    }

    @Provides
    @Singleton
    DataImporter<File> provideFileDataImporter(
            LocalReader reader,
            JsonParser jsonParser,
            Reader<Cost> costReader,
            Saver<DateCostMultimap, RuntimeException> costBulkSaver,
            CryptFacade cryptFacade)
    {
        return new MergeFileDataImporter(reader, jsonParser, costReader, costBulkSaver, cryptFacade);
    }

    @Provides
    @Singleton
    Saver<DateCostMultimap, RuntimeException> provideCostBulkSaver(
            CostFileNamesFactory fileNamesFactory,
            CostsCache cache,
            JsonConverter jsonConverter,
            LocalWriter writer)
    {
        return new CostBulkSaver(fileNamesFactory, cache, jsonConverter, writer);
    }

    @Provides
    @Singleton
    DataExporter provideDataExporter(CostFileNamesFactory fileNamesFactory,
            LocalReader reader,
            LocalWriter writer,
            CryptFacade cryptFacade)
    {
        return new DataExporterImpl(fileNamesFactory, reader, writer, cryptFacade);
    }

    @Provides
    @Singleton
    Reader<Cost> provideCostReader(CostFileNamesFactory fileNamesFactory, JsonParser jsonParser,
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
        return new FileFactory(applicationProperties().getOutputDirectory());
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

    @Provides
    @Singleton
    CryptFacade provideCryptFacade() {
        return new SimpleGostCryptFacade();
    }

}
