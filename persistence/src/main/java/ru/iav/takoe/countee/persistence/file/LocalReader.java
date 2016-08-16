package ru.iav.takoe.countee.persistence.file;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

import static ru.iav.takoe.countee.logging.LogService.logError;

/**
 * Created by takoe on 25.07.16.
 */
public class LocalReader {

    private static LocalReader instance;

    private LocalReader() {}

    public static LocalReader getInstance() {
        if (instance == null) {
            instance = new LocalReader();
        }
        return instance;
    }

    public String read(@Nonnull File file) {
        try {
            return Files.toString(file, Charsets.UTF_8);
        } catch (IOException ioe) {
            logError("Couldn't read file " + file.getName(), ioe);
            return null;
        }
    }

}
