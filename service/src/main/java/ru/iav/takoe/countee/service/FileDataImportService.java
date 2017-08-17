package ru.iav.takoe.countee.service;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import ru.iav.takoe.countee.da.DataImporter;

@ParametersAreNonnullByDefault
public class FileDataImportService {

    private final DataImporter<File> dataImporter;

    @Inject
    public FileDataImportService(DataImporter<File> dataImporter) {
        this.dataImporter = dataImporter;
    }

    public boolean importData(File source, String password) {
        return dataImporter.importData(source, password);
    }

}
