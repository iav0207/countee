package ru.iav.takoe.countee.persistence.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.Nonnull;

import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

public class LocalWriter {

    public void append(@Nonnull String text, @Nonnull File file) {
        boolean append = false;
        try (FileWriter fileWriter = new FileWriter(file, append);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(text);
            logInfo("Written to file " + file.getAbsolutePath());
        } catch (IOException ioe) {
            logError("Couldn't write to file " + file.getName(), ioe);
        }
    }

}
