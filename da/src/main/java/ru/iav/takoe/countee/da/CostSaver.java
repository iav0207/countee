package ru.iav.takoe.countee.da;

import java.io.File;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;
import ru.takoe.iav.countee.properties.ApplicationProperties;

import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

@Singleton
public class CostSaver {

    private CostFileNamesFactory fileNamesFactory;

    private CostReader costReader;

    private Invalidable cache;

    private JsonConverter jsonConverter;

    private LocalWriter writer;

    public CostSaver() {
        fileNamesFactory = CostFileNamesFactory.getInstance();
        costReader = new CostReader();
        cache = CostsCache.getInstance();
        jsonConverter = JsonConverter.getInstance();
        writer = LocalWriter.getInstance();
    }

    @Inject
    public CostSaver(CostFileNamesFactory fileNamesFactory, CostReader costReader,
            CostsCache cache, JsonConverter jsonConverter, LocalWriter writer)
    {
        this.fileNamesFactory = fileNamesFactory;
        this.costReader = costReader;
        this.cache = cache;
        this.jsonConverter = jsonConverter;
        this.writer = writer;
    }

    public void save(@Nonnull Cost cost) throws CostNotSavedException {
        try {
            clearPreviousRecordsIfNeeded();       // for debugging
            CostsData data = costReader.getDeserializedData(getActualFile());
            addNewCostToTheDataSet(cost, data);
            writeToFile(getSerialized(data));
            logInfo("Cost saved!");
        } catch (Exception e) {
            handleAndRethrow(e);
        } finally {
            cache.invalidate();
        }
    }

    private void clearPreviousRecordsIfNeeded() {
        if (ApplicationProperties.isClearPreviousRecords()) {
            writeToFile("");
        }
    }

    private void addNewCostToTheDataSet(Cost cost, CostsData data) {
        if (ApplicationProperties.isWritingDataActually()) {
            data.getDescriptor().put(cost.getUuid().toString(), cost);
        }
    }

    private String getSerialized(@Nonnull CostsData data) {
        return jsonConverter.serialize(data);
    }

    private void writeToFile(String json)  {
        writer.append(json, getActualFile());
    }

    private File getActualFile() {
        return fileNamesFactory.getActualFile();
    }

    private static void handleAndRethrow(Exception e) throws CostNotSavedException {
        logError("Failed to save cost data", e);
        throw new CostNotSavedException(e.getMessage(), e);
    }

}
