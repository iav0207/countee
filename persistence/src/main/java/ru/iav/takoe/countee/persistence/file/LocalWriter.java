package ru.iav.takoe.countee.persistence.file;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

import static ru.iav.takoe.countee.logging.LogService.logDebug;
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
        Writer writer = null;
        boolean append = false;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            writer.write(text);
            logInfo("Written to file " + file.getAbsolutePath());
        } catch (IOException ioe) {
            logError("Couldn't write to file " + file.getName(), ioe);
        } finally {
            close(writer);
        }
    }

    private static void close(@Nullable Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            logDebug(e);
        }
    }

}
