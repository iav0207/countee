package ru.iav.takoe.statbm.da;

import ru.iav.takoe.statbm.da.exception.PersistenceException;
import ru.iav.takoe.statbm.json.JsonConverter;
import ru.iav.takoe.statbm.persistence.file.FileFactory;
import ru.iav.takoe.statbm.persistence.file.LocalWriter;
import ru.iav.takoe.statbm.vo.Cost;

import javax.annotation.Nonnull;
import java.io.File;

import static ru.iav.takoe.statbm.da.CostFileNamesFactory.getFileNameToSaveCostTo;
import static ru.iav.takoe.statbm.logging.LogService.logError;

public class CostSaver {

    private static CostSaver instance;

    private FileFactory fileFactory;

    private JsonConverter jsonConverter;

    private LocalWriter writer;

    private CostSaver() {
        fileFactory = FileFactory.getInstance();
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
            writeToFile(getSerialized(cost));
        } catch (Exception e) {
            logError("Failed to save cost data", e);
            throw new PersistenceException(e.getMessage(), e);
        }
    }

    private String getSerialized(@Nonnull Cost cost) {
        return jsonConverter.serialize(cost);
    }

    private void writeToFile(String json)  {
        File target = fileFactory.create(getFileNameToSaveCostTo());
        writer.append(json, target);
    }

}
