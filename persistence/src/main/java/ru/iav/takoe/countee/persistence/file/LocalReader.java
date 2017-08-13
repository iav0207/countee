package ru.iav.takoe.countee.persistence.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import static ru.iav.takoe.countee.logging.LogService.logError;

@ParametersAreNonnullByDefault
public class LocalReader {

    public String read(File file) {
        try {
            return Files.toString(file, Charsets.UTF_8);
        } catch (IOException ioe) {
            logError("Couldn't read file " + file.getName(), ioe);
            return null;
        }
    }

    public FileReader getIterativeFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            logError("File " + file.getName() + "not found", e);
            throw new RuntimeException(e);
        }
    }

}
