package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.vo.Cost;

import static java.util.Collections.emptyList;
import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.utils.ObjectUtils.defensiveCopy;

public class CostReader implements Reader<Cost> {

    private CostFileNamesFactory fileNamesFactory;

    private JsonParser jsonParser;

    private LocalReader reader;

    private CostsCache cache;

    @Inject
    public CostReader(CostFileNamesFactory fileNamesFactory, JsonParser jsonParser,
            LocalReader reader, CostsCache cache)
    {
        this.fileNamesFactory = fileNamesFactory;
        this.jsonParser = jsonParser;
        this.reader = reader;
        this.cache = cache;
    }

    @Nonnull
    public List<Cost> readForThisMonth() {
        try {
            putAllCostsToCacheIfItIsEmpty();
            return cache.getCostsForThisMonth();
        } catch (RuntimeException e) {
            logError(e.getMessage());
            return emptyList();
        }
    }

    @Nonnull
    public List<Cost> readAll() {
        try {
            putAllCostsToCacheIfItIsEmpty();
            return cache.getAllCosts();
        } catch (RuntimeException e) {
            logError(e.getMessage());
            return emptyList();
        }
    }

    private void putAllCostsToCacheIfItIsEmpty() {
        if (cache.isEmpty()) {
            List<Cost> allCosts = new ArrayList<>();
            for (File eachCostFile : getAllCostFiles()) {
                allCosts.addAll(getCostsFrom(eachCostFile));
            }
            cache.put(allCosts);
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
