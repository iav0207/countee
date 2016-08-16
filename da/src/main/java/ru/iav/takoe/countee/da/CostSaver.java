package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.da.exception.PersistenceException;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.FileFactory;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.io.File;

import static ru.iav.takoe.countee.da.CostFileNamesFactory.getFileNameToSaveCostTo;
import static ru.iav.takoe.countee.logging.LogService.logError;

public class CostSaver {

    private static CostSaver instance;

    private FileFactory fileFactory;

    private JsonConverter jsonConverter;

    private LocalWriter writer;

    public CostSaver() {
        fileFactory = FileFactory.getInstance();
        jsonConverter = JsonConverter.getInstance();
        writer = LocalWriter.getInstance();
    }

    public void save(@Nonnull Cost cost) throws PersistenceException {
        try {
            writeToFile(getSerialized(cost));
        } catch (Exception e) {
            handle(e);
        }
    }

    private String getSerialized(@Nonnull Cost cost) {
        return jsonConverter.serialize(cost);
    }

    private void writeToFile(String json)  {
        File target = fileFactory.create(getFileNameToSaveCostTo());
        writer.append(json, target);
    }

    private static void handle(Exception e) throws PersistenceException {
        logError("Failed to save cost data", e);
        throw new PersistenceException(e.getMessage(), e);
    }

}
