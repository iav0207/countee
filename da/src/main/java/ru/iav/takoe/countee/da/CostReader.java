package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.vo.Cost;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.utils.ObjectUtils.defensiveCopy;
import static ru.iav.takoe.countee.utils.StreamUtils.getStream;

/**
 * Created by takoe on 16.08.16.
 */
public class CostReader {

    private static CostReader instance = new CostReader();

    private CostFileNamesFactory fileNamesFactory;

    private JsonParser jsonParser;

    private LocalReader reader;

    private CostsCache cache;

    public static CostReader getInstance() {
        return instance;
    }

    private CostReader() {
        fileNamesFactory = CostFileNamesFactory.getInstance();
        jsonParser = JsonParser.getInstance();
        reader = LocalReader.getInstance();
        cache = CostsCache.getInstance();
    }

    @Nonnull
    public List<Cost> readCostsForThisMonth() {
        try {
            putAllCostsToCacheIfItIsEmpty();
            return cache.getCostsForThisMonth();
        } catch (RuntimeException e) {
            logError(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Nonnull
    public List<Cost> readAllCosts() {
        try {
            putAllCostsToCacheIfItIsEmpty();
            return cache.getAllCosts();
        } catch (RuntimeException e) {
            logError(e.getMessage());
            return new ArrayList<>();
        }
    }

    private void putAllCostsToCacheIfItIsEmpty() {
        if (cache.isEmpty()) {
            cache.put(getStream(getAllCostFiles())
                    .flatMap(file -> getStream(getCostsFrom(file)))
                    .collect(Collectors.toList()));
        }
    }

    private List<Cost> getCostsFrom(File file) {
        return getCosts(getDeserializedData(file));
    }

    @Nonnull
    CostsData getDeserializedData(File file) {
        CostsData data = jsonParser.deserialize(readJson(file), CostsData.class);
        return data == null ? new CostsData() : data;
    }

    private String readJson(File file) {
        return reader.read(file);
    }

    private List<Cost> getCosts(CostsData data) {
        return defensiveCopy(data.getDescriptor().values());
    }

    private List<File> getAllCostFiles() {
        return fileNamesFactory.getAllCostFiles();
    }

}
