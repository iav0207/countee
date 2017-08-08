package ru.iav.takoe.countee.persistence.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;

import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

@ParametersAreNonnullByDefault
public class LocalWriter {

    public void clearWrite(String text, File file) {
        write(text, file, false);
    }

    public void append(String text, File file) {
        write(text, file, true);
    }

    private void write(String text, File file, boolean append) {
        try (FileWriter fileWriter = new FileWriter(file, append);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(text);
            logInfo("Written to file " + file.getAbsolutePath());
        } catch (IOException ioe) {
            logError("Couldn't write to file " + file.getName(), ioe);
        }
    }

}
