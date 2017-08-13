package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import org.joda.time.DateTime;
import ru.iav.takoe.countee.da.Cache;
import ru.iav.takoe.countee.da.Saver;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.logging.LogService.logDebug;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

@ParametersAreNonnullByDefault
public class CostBulkSaver implements Saver<DateCostMultimap, RuntimeException> {

    private final CostFileNamesFactory fileNamesFactory;
    private final Cache costsCache;
    private final JsonConverter jsonConverter;
    private final LocalWriter writer;

    @Inject
    public CostBulkSaver(
            CostFileNamesFactory fileNamesFactory,
            Cache costsCache,
            JsonConverter jsonConverter,
            LocalWriter writer)
    {
        this.fileNamesFactory = fileNamesFactory;
        this.costsCache = costsCache;
        this.jsonConverter = jsonConverter;
        this.writer = writer;
    }

    @Override
    public void save(DateCostMultimap bulk) {

        if (bulk.isEmpty()) {
            logInfo("No data to save.");
        } else {
            costsCache.invalidate();
        }

        Set<File> filesWritten = new HashSet<>();

        for (DateTime month : bulk.keySet()) {
            File targetFile = fileNamesFactory.getFileForDate(month);

            if (!filesWritten.contains(targetFile)) {
                clear(targetFile);
                filesWritten.add(targetFile);
                logInfo("File cleared before writing: " + targetFile);
            }

            CostsData costsData = new CostsData();
            for (Cost cost : bulk.get(month)) {
                addToDescriptor(cost, costsData);
            }

            String json = jsonConverter.serialize(costsData);
            writer.appendNewLine(json, targetFile);
            logDebug("Appended to file " + targetFile);
        }

        logInfo("Summary of files modified during bulk saving:");
        for (File file : filesWritten) {
            logInfo("\t\t" + file);
        }
    }

    private void clear(File file) {
        writer.clearWrite("", file);
    }

    private void addToDescriptor(Cost cost, CostsData data) {
        data.getDescriptor().put(cost.getUuid().toString(), cost);
    }

}
