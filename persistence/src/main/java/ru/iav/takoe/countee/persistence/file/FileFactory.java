package ru.iav.takoe.countee.persistence.file;

import java.io.File;
import java.io.IOException;

public class FileFactory {

    static final String IO_RELATIVE_PATH = "io/";

    public final String ioAbsolutePath = getOutputPath();

    private File customIoDirectory;

    public FileFactory() {}

    public FileFactory(File customIoDirectory) {
        this.customIoDirectory = customIoDirectory;
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
        createDirectoryIfDoesNotExist(outputPath);
        return outputPath;
    }

    private static String getExecutionPath() {
        return (new File("").getAbsolutePath()).replaceAll("\\\\","/") + "/";
    }

    private static boolean createDirectoryIfDoesNotExist(String pathToFile) {
        int indexTo = pathToFile.lastIndexOf('/');
        if (indexTo < 0) {
            return false;
        }
        File directory = new File(pathToFile.substring(0, indexTo));
        return !directory.exists() && directory.mkdirs();
    }

}
