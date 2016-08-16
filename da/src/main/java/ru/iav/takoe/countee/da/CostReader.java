package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static ru.iav.takoe.countee.da.CostFileNamesFactory.getActualFile;
import static ru.iav.takoe.countee.logging.LogService.logError;

/**
 * Created by takoe on 16.08.16.
 */
public class CostReader {

    private static CostReader instance = new CostReader();

    private JsonParser jsonParser;

    private LocalReader reader;

    public static CostReader getInstance() {
        return instance;
    }

    private CostReader() {
        jsonParser = JsonParser.getInstance();
        reader = LocalReader.getInstance();
    }

    public List<Cost> readCostsForThisMonth() {
        try {
            return getCosts(getDeserializedData());
        } catch (Exception e) {
            logError(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Nonnull
    CostsData getDeserializedData() {
        CostsData data = jsonParser.deserialize(readJson(), CostsData.class);
        return data == null ? new CostsData() : data;
    }

    private String readJson() {
        return reader.read(getActualFile());
    }

    private List<Cost> getCosts(CostsData data) {
        List<Cost> costs = new ArrayList<>();
        costs.addAll(data.getDescriptor().values());
        return costs;
    }

}
