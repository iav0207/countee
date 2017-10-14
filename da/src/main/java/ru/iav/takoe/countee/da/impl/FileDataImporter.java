package ru.iav.takoe.countee.da.impl;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.iav.takoe.countee.crypt.CryptFacade;
import ru.iav.takoe.countee.da.DataImporter;
import ru.iav.takoe.countee.da.exception.DataNotImportedException;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;

import static ru.iav.takoe.countee.logging.LogService.logInfo;

/**
 * Imports costs from the specified file on disk.
 */
@ParametersAreNonnullByDefault
abstract class FileDataImporter implements DataImporter<File> {

    final LocalReader reader;
    final JsonParser jsonParser;
    final CryptFacade cryptFacade;

    FileDataImporter(
            LocalReader reader,
            JsonParser jsonParser,
            CryptFacade cryptFacade)
    {
        this.reader = reader;
        this.jsonParser = jsonParser;
        this.cryptFacade = cryptFacade;
    }

    @Override
    public boolean importData(File source, String password) {
        logInfo("Starting data import. Source file: " + source);
        backupData();
        try {
            doImport(source, password);

        } catch (RuntimeException ex) {
            restoreDataFromBackup();
            throw new DataNotImportedException(ex);
        }

        logInfo("Data import finished successfully.");
        return true;
    }

    abstract void doImport(File source, String password);


    private void backupData() {
        // TODO implement
    }

    private void restoreDataFromBackup() {
        // TODO implement
    }

}
