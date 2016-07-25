package ru.iav.takoe.statbm.persistence.file;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * Created by takoe on 25.07.16.
 */
public class FileFactory {

    private static FileFactory instance;

    public static final String IO_ABSOLUTE_PATH = getOutputPath();

    static final String IO_RELATIVE_PATH = "io/";

    private FileFactory() {}

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

    public boolean exists(@Nonnull String fileName) {
        return getFileForName(fileName).exists();
    }

    public static File getFileForName(String fileName) {
        return new File(IO_RELATIVE_PATH + fileName);
    }

    private static String getOutputPath() {
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
