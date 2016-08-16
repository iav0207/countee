package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.da.exception.PersistenceException;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;

import static ru.iav.takoe.countee.da.CostFileNamesFactory.getActualFile;
import static ru.iav.takoe.countee.logging.LogService.logError;

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
            CostsData data = costReader.getDeserializedData();
            data.getDescriptor().put(cost.getUuid().toString(), cost);
            writeToFile(getSerialized(data));
        } catch (Exception e) {
            handle(e);
        }
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
