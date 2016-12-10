package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;
import ru.takoe.iav.countee.properties.ApplicationProperties;

import javax.annotation.Nonnull;
import java.io.File;

import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

public class CostSaver {

    private static CostSaver instance;

    private CostFileNamesFactory fileNamesFactory;

    private CostReader costReader;

    private Invalidable cache;

    private JsonConverter jsonConverter;

    private LocalWriter writer;

    private CostSaver() {
        fileNamesFactory = CostFileNamesFactory.getInstance();
        costReader = CostReader.getInstance();
        cache = CostsCache.getInstance();
        jsonConverter = JsonConverter.getInstance();
        writer = LocalWriter.getInstance();
    }

    public static CostSaver getInstance() {
        if (instance == null) {
            instance = new CostSaver();
        }
        return instance;
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
