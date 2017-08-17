package ru.iav.takoe.countee.crypt.utils;

import java.io.UnsupportedEncodingException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static ru.iav.takoe.countee.logging.LogService.logError;

@ParametersAreNonnullByDefault
public class BytesToStringParser {

    private static final String ENCODING = "utf-8"; // ??

    @Nonnull
    public String parseBytesAsText(byte[] bytes) {
        try {
            return new String(bytes, ENCODING);
        } catch (UnsupportedEncodingException uee) {
            logError("\nUnsupported ENCODING!", uee);
            return new String(bytes);
        }
    }

}
