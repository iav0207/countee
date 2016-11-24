package ru.iav.takoe.countee.persistence.file;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

public class LocalWriter {

    private static LocalWriter instance;

    private LocalWriter() {}

    public static LocalWriter getInstance() {
        if (instance == null) {
            instance = new LocalWriter();
        }
        return instance;
    }

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
