package ru.takoe.iav.countee.properties;

import java.io.File;

/**
 * Created by takoe on 16.08.16.
 */
public class ApplicationProperties {

    private static File outputDirectory;

    private static boolean clearPreviousRecords = false;

    private static boolean writingDataActually = false;

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

}
