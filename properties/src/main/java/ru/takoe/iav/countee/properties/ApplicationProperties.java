package ru.takoe.iav.countee.properties;

import java.io.File;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import static ru.takoe.iav.countee.properties.PropNames.DEBUG_CLEAR_PREV_RECS;
import static ru.takoe.iav.countee.properties.PropNames.DEBUG_GENERATE_RANDOM_CHARTS;
import static ru.takoe.iav.countee.properties.PropNames.DEBUG_WRITE_DATA;
import static ru.takoe.iav.countee.properties.PropNames.EXPORT_DATA_PATH;
import static ru.takoe.iav.countee.properties.PropNames.EXPORT_DATE_FORMAT;
import static ru.takoe.iav.countee.properties.PropNames.EXPORT_FILENAME_FORMAT;
import static ru.takoe.iav.countee.properties.PropNames.OUTPUT_DIRECTORY;

public enum ApplicationProperties {

    INSTANCE;

    private File outputDirectory;
    private String exportedDataPath;
    private String exportFileNameBase;
    private String dateFormat;
    private boolean clearPreviousRecords;
    private boolean writingDataActually;
    private boolean generateRandomDataForCharts;

    ApplicationProperties() {
        Config config = ConfigFactory.load();

        outputDirectory = new File(config.getString(OUTPUT_DIRECTORY));
        exportedDataPath = config.getString(EXPORT_DATA_PATH);
        exportFileNameBase = config.getString(EXPORT_FILENAME_FORMAT);
        dateFormat = config.getString(EXPORT_DATE_FORMAT);
        clearPreviousRecords = config.getBoolean(DEBUG_CLEAR_PREV_RECS);
        writingDataActually = config.getBoolean(DEBUG_WRITE_DATA);
        generateRandomDataForCharts = config.getBoolean(DEBUG_GENERATE_RANDOM_CHARTS);
    }

    public static ApplicationProperties applicationProperties() {
        return INSTANCE;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getExportedDataPath() {
        return exportedDataPath;
    }

    public void setExportedDataPath(String exportedDataPath) {
        this.exportedDataPath = exportedDataPath;
    }

    public String getExportFileNameBase() {
        return exportFileNameBase;
    }

    public void setExportFileNameBase(String exportFileNameBase) {
        this.exportFileNameBase = exportFileNameBase;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isClearPreviousRecords() {
        return clearPreviousRecords;
    }

    public void setClearPreviousRecords(boolean clearPreviousRecords) {
        this.clearPreviousRecords = clearPreviousRecords;
    }

    public boolean isWritingDataActually() {
        return writingDataActually;
    }

    public void setWritingDataActually(boolean writingDataActually) {
        this.writingDataActually = writingDataActually;
    }

    public boolean isGenerateRandomDataForCharts() {
        return generateRandomDataForCharts;
    }

    public void setGenerateRandomDataForCharts(boolean generateRandomDataForCharts) {
        this.generateRandomDataForCharts = generateRandomDataForCharts;
    }
}
