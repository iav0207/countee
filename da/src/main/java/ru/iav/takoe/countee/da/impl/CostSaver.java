package ru.iav.takoe.countee.da.impl;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;
import javax.inject.Singleton;

import ru.iav.takoe.countee.da.Cache;
import ru.iav.takoe.countee.da.Saver;
import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;
import static ru.takoe.iav.countee.properties.ApplicationProperties.applicationProperties;

@Singleton
@ParametersAreNonnullByDefault
public class CostSaver implements Saver<Cost, CostNotSavedException> {

    private final CostFileNamesFactory fileNamesFactory;
    private final CostReader costReader;
    private final Cache costsCache;
    private final JsonConverter jsonConverter;
    private final LocalWriter writer;

    @Inject
    public CostSaver(
            CostFileNamesFactory fileNamesFactory,
            CostReader costReader,
            Cache costsCache,
            JsonConverter jsonConverter,
            LocalWriter writer)
    {
        this.fileNamesFactory = fileNamesFactory;
        this.costReader = costReader;
        this.costsCache = costsCache;
        this.jsonConverter = jsonConverter;
        this.writer = writer;
    }

    @Override
    public void save(Cost cost) throws CostNotSavedException {
        try {
            clearPreviousRecordsIfNeeded();       // for debugging
            CostsData data = costReader.getDeserializedData(getActualFile());
            addNewCostToTheDataSet(cost, data);
            writeToFile(getSerialized(data));
            logInfo("Cost saved!");
        } catch (Exception e) {
            handleAndRethrow(e);
        } finally {
            costsCache.invalidate();
        }
    }

    private void clearPreviousRecordsIfNeeded() {
        if (applicationProperties().isClearPreviousRecords()) {
            writeToFile("");
        }
    }

    private void addNewCostToTheDataSet(Cost cost, CostsData data) {
        if (applicationProperties().isWritingDataActually()) {
            data.getDescriptor().put(cost.getUuid().toString(), cost);
        }
    }

    private String getSerialized(CostsData data) {
        return jsonConverter.serialize(data);
    }

    private void writeToFile(String json)  {
        writer.clearWrite(json, getActualFile());
    }

    private File getActualFile() {
        return fileNamesFactory.getActualFile();
    }

    private static void handleAndRethrow(Exception e) throws CostNotSavedException {
        logError("Failed to save cost data", e);
        throw new CostNotSavedException(e.getMessage(), e);
    }

}
