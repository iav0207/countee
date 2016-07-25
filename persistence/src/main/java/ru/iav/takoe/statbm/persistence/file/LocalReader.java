package ru.iav.takoe.statbm.persistence.file;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

import static ru.iav.takoe.statbm.logging.LogService.logError;

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
            return toOneString(Files.readAllLines(file.toPath()));
        } catch (IOException ioe) {
            logError("Couldn't read file " + file.getName(), ioe);
            return null;
        }
    }

    private String toOneString(Collection<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String each : strings) {
            sb.append(each).append("\n");
        }
        removeLastSymbol(sb);   // last appended "\n"
        return sb.toString();
    }

    private void removeLastSymbol(StringBuilder sb) {
        sb.setLength(sb.length() - 1);
    }

}
