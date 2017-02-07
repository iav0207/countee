package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.da.DataExporter;

/**
 * Created by takoe on 07.02.17.
 */
public class DataExportService {

    private static DataExportService instance;

    private DataExporter dataExporter;

    public static DataExportService getInstance() {
        if (instance == null) {
            instance = new DataExportService();
        }
        return instance;
    }

    private DataExportService() {
        dataExporter = new DataExporter();
    }

    public String exportAllData(String password) {
        return dataExporter.exportAllData(password);
    }

}
