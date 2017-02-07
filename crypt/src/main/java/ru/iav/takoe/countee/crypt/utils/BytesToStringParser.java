package ru.iav.takoe.countee.crypt.utils;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;

import static ru.iav.takoe.countee.logging.LogService.logError;

/**
 * Created by takoe on 02.02.17.
 */
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
