package ru.iav.takoe.countee.service;

import javax.inject.Inject;

import ru.iav.takoe.countee.da.DataExporter;

/**
 * Created by takoe on 07.02.17.
 */
public class DataExportService {

    private DataExporter dataExporter;

    @Inject
    public DataExportService(DataExporter dataExporter) {
        this.dataExporter = dataExporter;
    }

    public String exportAllData(String password) {
        return dataExporter.exportAllData(password);
    }

}
