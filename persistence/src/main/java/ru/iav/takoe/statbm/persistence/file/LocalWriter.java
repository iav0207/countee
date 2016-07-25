package ru.iav.takoe.statbm.persistence.file;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

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
        boolean append = true;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            writer.write(text);
        } catch (IOException ioe) {
            // TODO log error
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
            // ignore
        }
    }

}