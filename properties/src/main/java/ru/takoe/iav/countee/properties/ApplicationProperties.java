package ru.takoe.iav.countee.properties;

import java.io.File;

public class ApplicationProperties {

    private static File outputDirectory;

    private static String exportedDataPath = "Countee/data";

    private static String exportFileNameBase = "exp-%s.cnt.cpt";

    private static String dateFormat = "ssmmhhddMMyyyy";

    private static boolean clearPreviousRecords = false;

    private static boolean writingDataActually = true;

    private static boolean generateRandomDataForCharts = false;

    private ApplicationProperties() {}

    public static File getOutputDirectory() {
        return outputDirectory;
    }

    public static void setOutputDirectory(File outputDirectory) {
        ApplicationProperties.outputDirectory = outputDirectory;
    }

    public static String getExportedDataPath() {
        return exportedDataPath;
    }

    public static void setExportedDataPath(String exportedDataPath) {
        ApplicationProperties.exportedDataPath = exportedDataPath;
    }

    public static String getExportFileNameBase() {
        return exportFileNameBase;
    }

    public static void setExportFileNameBase(String exportFileNameBase) {
        ApplicationProperties.exportFileNameBase = exportFileNameBase;
    }

    public static String getDateFormat() {
        return dateFormat;
    }

    public static void setDateFormat(String dateFormat) {
        ApplicationProperties.dateFormat = dateFormat;
    }

    public static boolean isClearPreviousRecords() {
        return clearPreviousRecords;
    }

    public static void setClearPreviousRecords(boolean clearPreviousRecords) {
        ApplicationProperties.clearPreviousRecords = clearPreviousRecords;
    }

    public static boolean isWritingDataActually() {
        return writingDataActually;
    }

    public static void setWritingDataActually(boolean writingDataActually) {
        ApplicationProperties.writingDataActually = writingDataActually;
    }

    public static boolean isGenerateRandomDataForCharts() {
        return generateRandomDataForCharts;
    }

    public static void setGenerateRandomDataForCharts(boolean generateRandomDataForCharts) {
        ApplicationProperties.generateRandomDataForCharts = generateRandomDataForCharts;
    }
}
