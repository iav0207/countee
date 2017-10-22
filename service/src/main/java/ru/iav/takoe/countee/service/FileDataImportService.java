package ru.iav.takoe.countee.service;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import ru.iav.takoe.countee.da.DataImporter;

@ParametersAreNonnullByDefault
public class FileDataImportService {

    private DataImporter<File> dataImporter;

    private MonthOutputService monthOutputService;

    @Inject
    public FileDataImportService(DataImporter<File> dataImporter, MonthOutputService monthOutputService) {
        this.dataImporter = dataImporter;
        this.monthOutputService = monthOutputService;
    }

    public boolean importData(File source, String password) {
        monthOutputService.invalidate();
        return dataImporter.importData(source, password);
    }

}
