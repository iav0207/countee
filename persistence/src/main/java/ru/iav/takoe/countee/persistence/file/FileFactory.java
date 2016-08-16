package ru.iav.takoe.countee.persistence.file;

import ru.takoe.iav.countee.properties.ApplicationProperties;

import java.io.File;
import java.io.IOException;

/**
 * Created by takoe on 25.07.16.
 */
public class FileFactory {

    private static FileFactory instance;

    public final String IO_ABSOLUTE_PATH = getOutputPath();

    static final String IO_RELATIVE_PATH = "io/";

    private File customIoDirectory;

    private FileFactory() {
        customIoDirectory = ApplicationProperties.getOutputDirectory();
    }

    public static FileFactory getInstance() {
        if (instance == null) {
            instance = new FileFactory();
        }
        return instance;
    }

    public File create(String fileName) {
        File file = getFileForName(fileName);
        createIfDoesNotExist(file);
        return file;
    }

    private boolean createIfDoesNotExist(File file) {
        try {
            createDirectoryIfDoesNotExist(file.getAbsolutePath());
            return file.createNewFile();
        } catch (IOException ioe) {

            return false;
        }
    }

    public boolean delete(String fileName) {
        return delete(getFileForName(fileName));
    }

    private boolean delete(File file) {
        return !file.isDirectory() && file.exists() && file.delete();
    }

    public boolean exists(String fileName) {
        return getFileForName(fileName).exists();
    }

    public File getFileForName(String fileName) {
        return new File(getOutputPath() + fileName);
    }

    private String getOutputPath() {
        if (customIoDirectory != null) {
            return customIoDirectory.getAbsolutePath();
        }
        String outputPath = getExecutionPath() + IO_RELATIVE_PATH;
        if (!createDirectoryIfDoesNotExist(outputPath)) {
            return null;
        }
        return outputPath;
    }

    private static String getExecutionPath() {
        return (new File("").getAbsolutePath()).replaceAll("\\\\","/") + "/";
    }

    private static boolean createDirectoryIfDoesNotExist(String pathToFile) {
        int indexTo = pathToFile.lastIndexOf("/");
        if (indexTo < 0) {
            return false;
        }
        File directory = new File(pathToFile.substring(0, indexTo));
        return !directory.exists() && directory.mkdirs();
    }

}
