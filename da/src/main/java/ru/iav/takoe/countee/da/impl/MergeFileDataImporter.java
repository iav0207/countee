package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import ru.iav.takoe.countee.crypt.CryptFacade;
import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.da.Saver;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.da.impl.Constants.EOF;
import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;
import static ru.iav.takoe.countee.vo.util.CostDateUtil.month;

@ParametersAreNonnullByDefault
public class MergeFileDataImporter extends FileDataImporter {

    private final Reader<Cost> costReader;
    private final Saver<DateCostMultimap, ? extends RuntimeException> costBulkSaver;

    @Inject
    public MergeFileDataImporter(
            LocalReader reader,
            JsonParser jsonParser,
            Reader<Cost> costReader,
            Saver<DateCostMultimap, ? extends RuntimeException> costBulkSaver,
            CryptFacade cryptFacade)
    {
        super(reader, jsonParser, cryptFacade);
        this.costReader = costReader;
        this.costBulkSaver = costBulkSaver;
    }

    @Override
    void doImport(File source, String password) {
        try {
            String sourceContent = reader.read(source);

            Worker worker = new Worker(sourceContent, password);
            worker.processBlockByBlock();
            worker.mergeCosts();
            logInfo("All data processed successfully. Saving changes...");

            costBulkSaver.save(worker.getMonthMultimap());

        } catch (RuntimeException ex) {
            logError("Failed to import data from file: " + source, ex);
            throw ex;
        }
    }

    private class Worker {

        private final String sourceContent;
        private final String password;

        private List<String> split;
        private List<String> openSplit;

        private Map<String, Cost> uuidMap;
        private DateCostMultimap monthMultimap;

        Worker(String sourceContent, String password) {
            this.sourceContent = sourceContent;
            this.password = password;
            uuidMap = new HashMap<>();
            monthMultimap = new DateCostMultimap();
            split = Arrays.asList(sourceContent.split(EOF));
        }

        void processBlockByBlock() {
            splitBySourceFiles();
            decrypt();
            parseFilesAndAddObtainedCostsToMaps();
        }

        DateCostMultimap getMonthMultimap() {
            return monthMultimap;
        }

        private void splitBySourceFiles() {
            split = Arrays.asList(sourceContent.split(EOF));
        }

        private void decrypt() {
            openSplit = new ArrayList<>();
            for (String sourceFileContent : split) {
                openSplit.add(cryptFacade.decrypt(sourceFileContent, password));
            }
        }

        private void parseFilesAndAddObtainedCostsToMaps() {
            for (String singleFileContent : openSplit) {
                CostsData singleFileData = jsonParser.deserialize(singleFileContent, CostsData.class);
                addToMaps(singleFileData.getDescriptor().values());
            }
        }
        
        private void mergeCosts() {
            addToMaps(costReader.readAll());
        }

        private void addToMaps(Iterable<Cost> costs) {
            for (Cost each : costs) {
                addToMaps(each);
            }
        }

        private void addToMaps(Cost cost) {
            String uuid = cost.getUuid().toString();
            if (!uuidMap.containsKey(uuid)) {
                uuidMap.put(uuid, cost);
                monthMultimap.put(month(cost), cost);
            }
        }

    }

}
