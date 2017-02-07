package ru.iav.takoe.countee.service;

import ru.iav.takoe.countee.da.DataExporter;

/**
 * Created by takoe on 07.02.17.
 */
public class DataExportService {

    private DataExporter dataExporter = new DataExporter();

    public String exportAllData(String password) {
        return dataExporter.exportAllData(password);
    }

}
