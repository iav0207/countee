package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import ru.iav.takoe.countee.crypt.CryptFacade;
import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.da.Saver;
import ru.iav.takoe.countee.da.exception.DataNotImportedException;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.da.impl.Constants.EOF;
import static ru.iav.takoe.countee.da.impl.Constants.IMPORT_BUFFER_SIZE;
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
    public boolean importData(File source, String password) {

        logInfo("Starting data import. Source file: " + source);

        backupData();

        try {
            FileReader iterativeReader = reader.getIterativeFileReader(source);
            Worker worker = new Worker(iterativeReader, password);

            while (worker.readNextBlock() > -1) {
                worker.processBlock();
            }
            worker.mergeCosts();
            logInfo("All data processed successfully. Saving changes...");

            costBulkSaver.save(worker.getMonthMultimap());
            logInfo("Data import finished successfully.");

        } catch (RuntimeException ex) {
            logError("Failed to import data from file: " + source, ex);

            restoreDataFromBackup();
            throw new DataNotImportedException(ex);
        }

        return true;
    }

    private void backupData() {
        // TODO implement
    }

    private void restoreDataFromBackup() {
        // TODO implement
    }

    private class Worker {

        private final char[] buffer = new char[IMPORT_BUFFER_SIZE];
        private final FileReader iterativeReader;
        private final String password;

        private int charsCurrentlyRead;
        private int charsReadAtLastIteration;
        private String decrypted;
        private String[] split;

        private String prevTail = "";       // tail of decrypted string
        private int offsetForNextIteration; // unprocessed buffer tail length

        private Map<String, Cost> uuidMap;
        private DateCostMultimap monthMultimap;

        Worker(FileReader iterativeReader, String password) {
            this.iterativeReader = iterativeReader;
            this.password = password;
            uuidMap = new HashMap<>();
            monthMultimap = new DateCostMultimap();
        }

        /**
         * @return Number of chars read from file.
         */
        int readNextBlock() {
            try {
                int limit = buffer.length - offsetForNextIteration;
                if (offsetForNextIteration > 0) {
                    System.arraycopy(buffer, limit, buffer, 0, offsetForNextIteration);
                }
                charsReadAtLastIteration = iterativeReader.read(buffer, offsetForNextIteration, limit);
                charsCurrentlyRead += charsReadAtLastIteration;
                return charsReadAtLastIteration;
            } catch (IOException e) {
                logError("Failed to read next block. Current offset = " + charsCurrentlyRead, e);
                throw new DataNotImportedException(e);
            }
        }

        void processBlock() {
            decrypt();
            splitByFiles();
            parseFilesAndAddObtainedCostsToMaps();
        }

        DateCostMultimap getMonthMultimap() {
            return monthMultimap;
        }

        private void decrypt() {
            decrypted = cryptFacade.decrypt(String.copyValueOf(buffer), password);
        }

        private void splitByFiles() {
            split = decrypted.split(EOF);
            int len = split.length;
            if (len > 1) {
                prevTail = split[len - 1];
                split = Arrays.copyOf(split, len - 1);
                offsetForNextIteration = toCypherLength(prevTail.length());
                charsCurrentlyRead -= offsetForNextIteration;
            } else {
                prevTail = "";
                offsetForNextIteration = 0;
            }
        }

        private void parseFilesAndAddObtainedCostsToMaps() {
            for (String singleFileContent : Arrays.asList(split)) {
                CostsData singleFileData = jsonParser.deserialize(singleFileContent, CostsData.class);
                addToMaps(singleFileData.getDescriptor().values());
            }
        }
        
        private void mergeCosts() {
            addToMaps(costReader.readAll());
        }

        private int toCypherLength(int openTextLength) {
            int t = decrypted.length();
            int c = charsReadAtLastIteration;
            if (t > c) {
                int ratio = t / c;
                return openTextLength / ratio;
            } else if (t < c) {
                int ratio = c / t;
                return openTextLength * ratio;
            } else {
                return openTextLength;
            }
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
