package ru.iav.takoe.countee.persistence.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.io.Files;

import static ru.iav.takoe.countee.logging.LogService.logDebug;
import static ru.iav.takoe.countee.logging.LogService.logError;

@ParametersAreNonnullByDefault
public class LocalWriter {

    private enum Option {
        CLEAR_WRITE,
        APPEND,
        APPEND_NEW_LINE
    }

    public void clear(File file) {
        clearWrite("", file);
    }

    public void clearWrite(String text, File file) {
        write(text, file, Option.CLEAR_WRITE);
    }

    public void appendNewLine(String text, File file) {
        write(text, file, Option.APPEND_NEW_LINE);
    }

    public void append(String text, File file) {
        write(text, file, Option.APPEND);
    }

    private void write(String text, File file, Option option) {
        try (BufferedWriter writer = new BufferedWriter(createFileWriter(file, option)))
        {
            logDebug("Writing to file " + file.getAbsolutePath() + " ...");
            if (option == Option.APPEND_NEW_LINE && !isEmptyOrAbsent(file)) {
                writer.newLine();
            }
            writer.write(text);
            logDebug("...success!");
        } catch (IOException ioe) {
            logError("Couldn't write to file " + file.getName(), ioe);
        }
    }

    private FileWriter createFileWriter(File file, Option option) throws IOException {
        if (option == Option.CLEAR_WRITE) {
            return new FileWriter(file, false);
        } else {
            return new FileWriter(file, true);
        }
    }

    private boolean isEmptyOrAbsent(File file) throws IOException {
        return !file.exists() || Files.toString(file, Charset.defaultCharset()).isEmpty();
    }

}
