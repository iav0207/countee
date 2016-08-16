package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.da.exception.PersistenceException;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;
import ru.takoe.iav.countee.properties.ApplicationProperties;

import javax.annotation.Nonnull;

import static ru.iav.takoe.countee.da.CostFileNamesFactory.getActualFile;
import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

public class CostSaver {

    private static CostSaver instance;

    private CostReader costReader;

    private JsonConverter jsonConverter;

    private LocalWriter writer;

    private CostSaver() {
        costReader = CostReader.getInstance();
        jsonConverter = JsonConverter.getInstance();
        writer = LocalWriter.getInstance();
    }

    public static CostSaver getInstance() {
        if (instance == null) {
            instance = new CostSaver();
        }
        return instance;
    }

    public void save(@Nonnull Cost cost) throws PersistenceException {
        try {
            clearPreviousRecordsIfNeeded();       // for debugging
            CostsData data = costReader.getDeserializedData();
            addNewCostToTheDataSet(cost, data);
            writeToFile(getSerialized(data));
            logInfo("Cost saved!");
        } catch (Exception e) {
            handle(e);
        }
    }

    private void clearPreviousRecordsIfNeeded() {
        if (ApplicationProperties.isClearPreviousRecords()) {
            writeToFile("");
        }
    }

    private void addNewCostToTheDataSet(Cost cost, CostsData data) {
        data.getDescriptor().put(cost.getUuid().toString(), cost);
    }

    private String getSerialized(@Nonnull CostsData data) {
        return jsonConverter.serialize(data);
    }

    private void writeToFile(String json)  {
        writer.append(json, getActualFile());
    }

    private static void handle(Exception e) throws PersistenceException {
        logError("Failed to save cost data", e);
        throw new PersistenceException(e.getMessage(), e);
    }

}
