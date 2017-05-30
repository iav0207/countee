package ru.takoe.iav.countee.properties;

import java.io.File;

public class ApplicationProperties {

    private static File outputDirectory;

    private static boolean clearPreviousRecords = false;

    private static boolean writingDataActually = true;

    private static boolean generateRandomDataForCharts = false;

    public static File getOutputDirectory() {
        return outputDirectory;
    }

    public static void setOutputDirectory(File outputDirectory) {
        ApplicationProperties.outputDirectory = outputDirectory;
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
