package ru.takoe.iav.countee.properties;

import java.io.File;

/**
 * Created by takoe on 16.08.16.
 */
public class ApplicationProperties {

    private static File outputDirectory;

    public static File getOutputDirectory() {
        return outputDirectory;
    }

    public static void setOutputDirectory(File outputDirectory) {
        ApplicationProperties.outputDirectory = outputDirectory;
    }
}
